package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色权限中间表
 *
 * @author fei
 * @since 2019-01-19 15:40
 */
public interface SysRoleMenuRepository extends BaseRepository<SysRoleMenuEntity, Long> {

  /**
   * 获取角色菜单
   * @param roleId 角色ID
   * @return 角色菜单集合
   */
  List<SysRoleMenuEntity> findAllByRoleId(Long roleId);
}
