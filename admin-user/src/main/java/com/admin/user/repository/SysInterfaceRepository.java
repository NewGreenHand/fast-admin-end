package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysInterfaceEntity;

import java.util.List;

/**
 * 接口权限表.
 *
 * @author fei
 * @since 2019-01-10 22:06
 */
public interface SysInterfaceRepository extends BaseRepository<SysInterfaceEntity, Long> {

  /**
   * 根据菜单ID获取菜单下的接口权限集合.
   *
   * @param menuIds 菜单ID集合
   * @return 接口权限集合
   */
  List<SysInterfaceEntity> findAllByMenuIdIn(Long[] menuIds);
}
