package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import com.admin.user.enums.EnabledEnum;
import lombok.Data;

/**
 * 角色表单
 * @author fei
 * @date 2017/10/17
 */
@Data
public class RoleDto implements DtoConvert {
  private String name;
  private String description;
  private EnabledEnum enabled;

}
