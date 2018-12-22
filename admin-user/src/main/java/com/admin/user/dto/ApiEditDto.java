package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import com.admin.user.enums.EnabledEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接口权限属性更新
 *
 * @author fei
 * @date 2018/10/8
 */
@Data
public class ApiEditDto implements DtoConvert {
  /** 接口用途 */
  @NotBlank private String name;
  /** 接口路径 */
  @NotBlank private String path;
  /** 请求方法 */
  @NotBlank private String requestMethod;
  /** 状态 */
  @NotNull private EnabledEnum enabled;
}
