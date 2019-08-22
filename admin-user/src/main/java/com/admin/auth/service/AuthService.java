package com.admin.auth.service;

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
   * 微信登录.
   * @param openId 微信ID
   * @return token
   */
  String login(@NotBlank String openId);

  /**
   * 用户绑定微信号
   * @param userName 用户名
   * @param password 密码
   * @param openId 微信ID
   * @return token
   */
  String bind(@NotBlank String userName, @NotBlank String password, @NotBlank String openId);

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
