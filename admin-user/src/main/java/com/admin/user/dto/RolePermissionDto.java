package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权表单.
 *
 * @author fei
 * @since 2019-02-07 22:04
 */
@Data
public class RolePermissionDto implements DtoConvert {
  /** 角色ID. */
  @NotNull private Long roleId;
  /** 菜单ID集合. */
  @NotEmpty private List<Long> menuIds;
  /** 接口权限ID. */
  @NotEmpty private List<Long> interfaceIds;
}
