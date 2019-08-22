package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import com.admin.core.enums.EnabledEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户注册时接收参数.
 *
 * @author fei
 * @date 2017/9/30
 */
@Data
public class RegisterUserDto implements DtoConvert {

  /** 用户名. */
  @NotBlank(message = "用户名不能为空")
  private String userName;
  /** 手机号码. */
  @NotBlank
  @Pattern(regexp="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message="手机号格式不正确")
  private String iphone;
  /** email. */
  private String email;
  /** 用户状态. */
  @NotNull private Boolean enabled;
}
