package com.admin.user.service;

import com.admin.core.basic.InterfaceService;
import com.admin.user.entity.SysUserRoleEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户部门角色关系业务
 * @author fei
 * @date 2018/10/14
 */
public interface SysUserRoleService extends InterfaceService<SysUserRoleEntity, Long> {

  /**
   * 获取用户的部门角色
   * @param userId 用户ID
   * @return 部门角色集合
   */
  List<SysUserRoleEntity> findAllByUserId(@NotNull Long userId);

  /**
   * 保存用户部门角色关系表
   * @param userId 用户ID
   * @param roleIds 角色ID集合
   * @return 用户部门角色对象
   */
  List<SysUserRoleEntity> save(@NotNull Long userId, List<Long> roleIds);
}
