package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import com.admin.user.enums.EnabledEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门表单
 *
 * @author fei
 * @date 2018/10/13
 */
@Data
public class DepartmentDto implements DtoConvert {
  /** 部门名称 */
  @NotBlank private String name;
  /** 状态 */
  @NotNull private EnabledEnum enabled;
  /** 父级ID */
  private Integer parentId;
}
