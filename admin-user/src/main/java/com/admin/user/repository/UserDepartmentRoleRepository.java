package com.admin.user.repository;

import com.admin.core.basice.BaseRepository;
import com.admin.user.entity.SysUserRole;

import java.util.List;

/**
 * 用户部门角色表
 *
 * @author fei
 * @date 2018/10/14
 */
public interface UserDepartmentRoleRepository extends BaseRepository<SysUserRole, Integer> {

  /**
   * 获取用户的部门角色
   * @param uid 用户ID
   * @return 部门角色集合
   */
  List<SysUserRole> findAllByUid(Integer uid);
}
