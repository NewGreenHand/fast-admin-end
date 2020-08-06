package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import com.admin.user.entity.SysRoleEntity;
import lombok.Data;

/**
 * 角色表单.
 * @author fei
 * @date 2017/10/17
 */
@Data
public class RoleDto implements DtoConvert<SysRoleEntity> {
  private String name;
  private String description;
  private Boolean enabled;

}
