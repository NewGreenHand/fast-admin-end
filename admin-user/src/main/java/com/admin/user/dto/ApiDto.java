package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import com.admin.core.enums.EnabledEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * api 权限表单.
 *
 * @author fei
 * @date 2018/8/16
 */
@Data
public class ApiDto implements DtoConvert {
  /** 接口用途. */
  @NotBlank private String name;
  /** 接口路径. */
  @NotBlank private String path;
  /** 请求方法. */
  @NotBlank private String requestMethod;
  /** 状态. */
  @NotNull private Boolean enabled;
  /** 菜单ID. */
  @NotNull private Integer menuId;
}
