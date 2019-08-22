package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色菜单授权表单.
 *
 * @author fei
 * @since 2019-02-03 10:38
 */
@Data
public class RoleMenuDto implements DtoConvert {
  /** 角色ID. */
  @NotNull private Long roleId;
  /** 菜单ID集合. */
  @NotEmpty
  private List<Long> menuIds;
}
