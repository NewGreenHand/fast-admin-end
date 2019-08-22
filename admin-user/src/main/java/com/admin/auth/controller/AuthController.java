package com.admin.auth.controller;

import com.admin.core.exception.UserNotInitException;
import com.admin.auth.dto.LoginDto;
import com.admin.auth.service.AuthService;
import com.admin.core.log.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * 授权接口
 * @author fei
 * @date 2018/10/21
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
  private final AuthService authService;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  /**
   * 用户登陆( 用户 密码 )
   * @param dto 用户表单
   * @return token
   * @throws AuthenticationException 授权异常
   */
  @WebLog(description = "用户登录。。。。。")
  @PostMapping("login")
  public String createAuthenticationToken(
    @RequestBody LoginDto dto) throws AuthenticationException {
    return authService.login(dto.getUserName(), dto.getPassword());
  }

  /**
   * 用户登陆( 微信登录 )
   * @param openId 用户微信的 openId
   * @return token
   * @throws AuthenticationException 授权异常
   */
  @PostMapping("wx_login")
  public String wxLogin(@NotBlank String openId) throws AuthenticationException {
    return authService.login(openId);
  }

  /**
   * 用户登陆( 微信登录 )
   * @param dto 表单
   * @param openId 用户微信的 openId
   * @return token
   * @throws AuthenticationException 授权异常
   */
  @PutMapping("wx_bind")
  public String wxBind(@RequestBody LoginDto dto, @NotBlank String openId) throws AuthenticationException {
    return authService.bind(dto.getUserName(), dto.getPassword(), openId);
  }

  /**
   * 用户注销
   */
  @PutMapping("logout")
  public void logout() {
    Boolean state = authService.logout();
    if (!state) {
      throw new UserNotInitException();
    }
  }

  /**
   * 刷新 token 的有效时间
   * @param request 请求对象
   * @return 新 token
   * @throws AuthenticationException 授权异常
   */
  @GetMapping("refresh")
  public String refreshAndGetAuthenticationToken(
    HttpServletRequest request) throws AuthenticationException{
    String token = request.getHeader(tokenHeader);

    return authService.refresh(token);
  }
}

