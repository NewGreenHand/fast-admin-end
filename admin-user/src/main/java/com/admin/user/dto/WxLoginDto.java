package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author fei
 * @date 2018/10/24
 */
@Data
public class WxLoginDto implements DtoConvert {
  /** 用户名 */
  @NotBlank private String userName;
  /** 密码 */
  @NotBlank private String password;
  /** 微信用户ID */
  @NotBlank private String openId;
}
