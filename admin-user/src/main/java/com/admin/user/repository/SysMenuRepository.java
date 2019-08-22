package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单权限表
 * @author fei
 * @since 2017/9/19
 */
public interface SysMenuRepository extends BaseRepository<SysMenuEntity, Long> {

  /**
   * 获取菜单集合.
   *
   * @param ids 菜单ID数组
   * @param enabled 状态
   * @return 菜单集合
   */
  List<SysMenuEntity> findAllByIdInAndEnabled(Long[] ids, Integer enabled);

  /**
   * 获取所有的子菜单
   *
   * @param parentId 父级菜单ID
   * @return 菜单集合
   */
  List<SysMenuEntity> findAllByParentId(Long parentId);
}
