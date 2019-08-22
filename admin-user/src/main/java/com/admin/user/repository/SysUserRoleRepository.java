package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户角色中间表
 *
 * @author fei
 * @date 2018/10/14
 */
public interface SysUserRoleRepository extends BaseRepository<SysUserRoleEntity, Long> {

  /**
   * 获取用户的部门角色
   * @param uid 用户ID
   * @return 部门角色集合
   */
  List<SysUserRoleEntity> findAllByUid(Long uid);
}
