package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户部门角色表单
 * @author fei
 * @date 2018/10/15
 */
@Data
public class UserDepartmentRoleDto implements DtoConvert {
  /** 部门ID */
  @NotNull private Integer departmentId;
  /** 角色ID */
  @NotNull private Integer rid;
}
