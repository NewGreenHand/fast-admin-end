package com.admin.user.controller.auth;

import com.admin.core.exception.UserNotInitException;
import com.admin.user.dto.LoginDto;
import com.admin.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权接口
 * @author fei
 * @date 2018/10/21
 */
@RestController
@RequestMapping("auth/api")
public class AuthController {
  private final AuthService authService;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  /**
   * 如果没有登陆 security 会返回一个登陆页面
   * 这样写是要返回 403 状态码，让页面路由自动跳转到登陆页面上(前后端分离用状态码通信)
   */
  @RequestMapping("/login_page")
  public void login() {
    throw new UserNotInitException();
  }

  /**
   * 用户登陆
   * @param dto 用户表单
   * @return token
   * @throws AuthenticationException 授权异常
   */
  @PostMapping(value = "login")
  public String createAuthenticationToken(
    @RequestBody LoginDto dto) throws AuthenticationException {

    return authService.login(dto.getUserName(), dto.getPassword());
  }

  /**
   * 用户注销
   */
  @PutMapping("/logout")
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
  @GetMapping(value = "refresh")
  public String refreshAndGetAuthenticationToken(
    HttpServletRequest request) throws AuthenticationException{
    String token = request.getHeader(tokenHeader);

    return authService.refresh(token);
  }
}

