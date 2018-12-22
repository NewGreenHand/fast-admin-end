package com.admin.user.service;

import com.admin.core.basice.BaseService;
import com.admin.user.dto.UserDepartmentRoleDto;
import com.admin.user.entity.SysUserRole;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户部门角色关系业务
 * @author fei
 * @date 2018/10/14
 */
public interface UserDepartmentRoleService extends BaseService<SysUserRole, Integer> {

  /**
   * 获取用户的部门角色
   * @param userId 用户ID
   * @return 部门角色集合
   */
  List<SysUserRole> findAllByUserId(@NotNull Integer userId);

  /**
   * 保存用户部门角色关系表
   * @param userId 用户ID
   * @param roleIds 角色ID集合
   * @return 用户部门角色对象
   */
  List<SysUserRole> save(@NotNull Integer userId, List<Integer> roleIds);
}
