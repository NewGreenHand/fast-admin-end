package com.admin.user.service.impl;

import com.admin.core.exception.AppException;
import com.admin.core.exception.UserNotInitException;
import com.admin.user.entity.SysUser;
import com.admin.user.service.AuthService;
import com.admin.user.service.UserService;
import com.admin.user.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 微信授权业务
 * @author fei
 * @date 2018/10/25
 */
@Slf4j
@Validated
@Service
public class WxAuthServiceImpl implements WxAuthService {
  private final AuthService authService;
  private final UserService userService;

  @Autowired
  public WxAuthServiceImpl(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @Override
  public String getToken(String openId) {
    SysUser sysUser = userService.findByOpenId(openId);
    if (null == sysUser) {
      throw new UserNotInitException("用户还没绑定过微信");
    }

    return authService.authLogin(sysUser.getUserName());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String bound(@NotBlank String userName, @NotBlank String password, @NotBlank String openId) {
    String token = authService.login(userName, password);

    // 获取当前用户
    SysUser sysUser = userService.findByUserName(userName);
    if (StringUtils.hasLength(sysUser.getOpenId())) {
      throw new AppException("该用户已经绑定过微信，请勿重复绑定");
    }

    // 绑定微信
    sysUser.setOpenId(openId);
    userService.update(sysUser);

    // 返回token
    return token;
  }
}

