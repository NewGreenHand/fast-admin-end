package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限部门角色表单
 *
 * @author fei
 * @date 2018/10/14
 */
@Data
public class PermissionRoleDepartmentDto implements DtoConvert {
  /** 角色ID */
  @NotNull private Integer roleId;
  /** 权限ID */
  @NotNull @NotEmpty private List<Integer> permissionIds;
}
