package com.admin.user.service;

import javax.validation.constraints.NotBlank;

/**
 * 微信授权业务
 * @author fei
 * @date 2018/10/25
 */
public interface WxAuthService {

  /**
   * 获取授权token
   * @param openId 微信openID
   * @return token
   */
  String getToken(String openId);

  /**
   * 微信账号绑定系统用户
   * @param userName 用户名称
   * @param password 密码
   * @param openId openID
   * @return token
   */
  String bound(@NotBlank String userName, @NotBlank String password, @NotBlank String openId);
}
