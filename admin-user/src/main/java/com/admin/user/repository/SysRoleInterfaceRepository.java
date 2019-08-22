package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysRoleInterfaceEntity;

import java.util.List;

/**
 * 权限角色中间表
 * @author fei
 * @date 2018/10/14
 */
public interface SysRoleInterfaceRepository
    extends BaseRepository<SysRoleInterfaceEntity, Long> {

  /**
   * 获取角色权限
   * @param roleId 角色ID
   * @return 权限信息集合
   */
  List<SysRoleInterfaceEntity> findAllByRid(Long roleId);

  /**
   * 获取拥有该权限的角色
   * @param pid 接口权限ID
   * @return 权限接口中间表集合
   */
  List<SysRoleInterfaceEntity> findAllByPid(Long pid);
}

