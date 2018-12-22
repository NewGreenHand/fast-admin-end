package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import com.admin.user.enums.EnabledEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户注册时接收参数
 *
 * @author fei
 * @date 2017/9/30
 */
@Data
public class RegisterUserDto implements DtoConvert {

  /** 用户名 */
  @NotBlank(message = "用户名不能为空")
  private String userName;
  /** 手机号码 */
  @NotBlank
  @Pattern(regexp="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message="手机号格式不正确")
  private String iphone;
  /** email */
  private String email;
  /** 部门ID */
  @NotNull private Integer departmentId;
  /** 用户状态 */
  @NotNull private EnabledEnum enabled;
}
