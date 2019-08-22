package com.admin.auth.service.impl;

import com.admin.auth.model.CurrentUser;
import com.admin.auth.config.security.JwtUser;
import com.admin.auth.config.security.JwtUserFactory;
import com.admin.auth.util.JwtTokenUtil;
import com.admin.auth.config.security.*;
import com.admin.core.exception.AppException;
import com.admin.user.entity.SysUserEntity;
import com.admin.auth.service.AuthService;
import com.admin.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 授权业务
 *
 * @author fei
 * @date 2018/10/25
 */
@Slf4j
@Service
@Validated
public class AuthServiceImpl implements AuthService {
  private static final String CACHE_PREFIX_TOKEN = "token_";
  private final AuthenticationManager authenticationManager;
  private final JwtUserDetailsServiceImpl jwtUserDetailsService;
  private final JwtTokenUtil jwtTokenUtil;
  private final StringRedisTemplate stringRedisTemplate;
  private final SysUserService userService;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Value("${jwt.expiration}")
  private Long expiration;

  @Autowired
  public AuthServiceImpl(
      AuthenticationManager authenticationManager,
      JwtUserDetailsServiceImpl jwtUserDetailsService,
      JwtTokenUtil jwtTokenUtil,
      StringRedisTemplate stringRedisTemplate,
      SysUserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.stringRedisTemplate = stringRedisTemplate;
    this.userService = userService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String login(@NotBlank String userName, @NotBlank String password) {
    UsernamePasswordAuthenticationToken upToken =
        new UsernamePasswordAuthenticationToken(userName, password);
    final Authentication authentication = authenticationManager.authenticate(upToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    SysUserEntity sysUser = userService.findByUserName(userName);
    return this.authLogin(sysUser);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String login(@NotBlank String openId) {
    SysUserEntity sysUser = userService.findByOpenId(openId);
    Assert.notNull(sysUser, "该微信号[ " + openId + " ]还没有绑定用户");

    return this.authLogin(sysUser);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String bind(@NotBlank String userName, @NotBlank String password, @NotBlank String openId) {
    String token = this.login(userName, password);

    // 获取当前用户
    SysUserEntity sysUser = userService.findByUserName(userName);
    if (StringUtils.hasLength(sysUser.getOpenId())) {
      throw new AppException("该用户已经绑定过微信，请勿重复绑定");
    }

    // 绑定微信
    sysUser.setOpenId(openId);
    userService.update(sysUser);

    // 返回token
    return token;
  }

  private String authLogin(SysUserEntity sysUser) {
    // 更新用户的最后登陆时间
    sysUser.setLastLoginTime(new Date());
    userService.update(sysUser);

    // 生成用户token
    String token = jwtTokenUtil.generateToken(JwtUserFactory.create(sysUser));

    // 在 redis 里也保存一份
    stringRedisTemplate
        .boundValueOps(tokenCacheKey(sysUser.getId().toString()))
        .set(token, expiration, TimeUnit.MILLISECONDS);

    return token;
  }

  @Override
  public Boolean logout() {
    JwtUser jwtUser = CurrentUser.currentUser();
    // 删除当前用户在redis里的key
    return stringRedisTemplate.delete(tokenCacheKey(jwtUser.getId().toString()));
  }

  @Override
  public Boolean isLogout(String token) {
    String userName = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(userName);

    String cacheToken =
        stringRedisTemplate.boundValueOps(this.tokenCacheKey(jwtUser.getId().toString())).get();
    if (null == cacheToken) {
      return false;
    }
    return token.equals(cacheToken);
  }

  /**
   * 刷新token
   *
   * @param oldToken 旧 token
   * @return 新生成的 token
   */
  @Override
  public String refresh(String oldToken) {
    final String token = oldToken.substring(tokenHead.length());
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastLoginTime())) {
      String newToken = jwtTokenUtil.refreshToken(token);

      // 刷新 redis token 的有效时间和value
      stringRedisTemplate
          .boundValueOps(this.tokenCacheKey(user.getId().toString()))
          .set(newToken, expiration, TimeUnit.MILLISECONDS);

      return newToken;
    }
    return null;
  }

  private String tokenCacheKey(String userId) {
    return CACHE_PREFIX_TOKEN + userId;
  }
}
