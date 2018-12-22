package com.admin.user.service;

import javax.validation.constraints.NotBlank;

/**
 * 授权业务
 *
 * @author fei
 * @date 2018/10/25
 */
public interface AuthService {

  /**
   * 登陆
   *
   * @param userName 用户名
   * @param password 密码
   * @return token
   */
  String login(@NotBlank String userName, @NotBlank String password);

  /**
   * 授权登陆，不需要密码
   * @param userName 用户名
   * @return token
   */
  String authLogin(String userName);

  /**
   * 刷新 token
   *
   * @param oldToken 旧token
   * @return 新 token
   */
  String refresh(@NotBlank String oldToken);

  /**
   * 注销当前用户
   * @return true: 成功, false: 失败
   */
  Boolean logout();

  /**
   * 判断 token 是否已经注销
   *
   * @param token token
   * @return true: 木有， false: 已注销
   */
  Boolean isLogout(String token);
}
