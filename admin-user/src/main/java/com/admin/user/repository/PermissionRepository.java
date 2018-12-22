package com.admin.user.repository;

import com.admin.core.basice.BaseRepository;
import com.admin.user.entity.SysPermission;

import java.util.List;

/**
 * @author fei
 * @date 2017/9/19
 */
public interface PermissionRepository extends BaseRepository<SysPermission, Integer> {

  /**
   * 获取菜单集合
   *
   * @param ids 菜单ID数组
   * @param enabled 状态
   * @return 菜单集合
   */
  List<SysPermission> findAllByIdInAndEnabled(Integer[] ids, Integer enabled);

  /**
   * 获取所有的子菜单
   *
   * @param parentId 父级菜单ID
   * @return 菜单集合
   */
  List<SysPermission> findAllByParentId(Integer parentId);
}
