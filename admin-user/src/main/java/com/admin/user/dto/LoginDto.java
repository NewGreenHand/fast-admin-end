package com.admin.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登陆表单
 *
 * @author fei
 * @date 2018/10/22
 */
@Data
public class LoginDto {
  /** 用户名 */
  @NotBlank private String userName;
  /** 密码 */
  @NotBlank private String password;
}
