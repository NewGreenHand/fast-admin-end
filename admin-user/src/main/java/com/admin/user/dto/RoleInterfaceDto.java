package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限部门角色表单.
 *
 * @author fei
 * @date 2018/10/14
 */
@Data
public class RoleInterfaceDto {
  /** 角色ID. */
  @NotNull private Long roleId;
  /** 接口权限ID. */
  @NotEmpty private List<Long> apis;
}
