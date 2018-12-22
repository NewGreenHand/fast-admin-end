package com.admin.user.repository;

import com.admin.core.basice.BaseRepository;
import com.admin.user.entity.SysPermissionRole;

import java.util.List;

/**
 * 权限角色部门表
 * @author fei
 * @date 2018/10/14
 */
public interface PermissionRoleDepartmentRepository
    extends BaseRepository<SysPermissionRole, Integer> {

  /**
   * 获取角色权限
   * @param roleId 角色ID
   * @return 权限信息集合
   */
  List<SysPermissionRole> findAllByRid(Integer roleId);
}

