package com.admin.user.service;

import com.admin.core.basic.InterfaceService;
import com.admin.user.entity.SysMenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 菜单权限表
 * @author fei
 * @since 2019-01-10 23:27
 */
public interface SysMenuService extends InterfaceService<SysMenuEntity, Long> {

  /**
   * 权限维护主页的列表数据
   * @param menuName 权限名称
   * @param pageable 分页参数
   * @return 权限集合
   */
  Page<SysMenuEntity> indexList(String menuName, Pageable pageable);

  /**
   * 获取子菜单分页
   * @param menuName 菜单名称
   * @param pageable 分页参数
   * @return 子菜单集合（分页）
   */
  Page<SysMenuEntity> findChildrenList(String menuName, Pageable pageable);

  /**
   * 获取子菜单
   * @param menuName 菜单名称
   * @return 子菜单集合（不分页）
   */
  List<SysMenuEntity> findChildrenList(String menuName);

  /**
   * 获取菜单树
   *
   * @return 菜单集合
   */
  List<SysMenuEntity> findMenuTree();

  /**
   * 获取菜单树
   *
   * @return 菜单集合
   */
  List<SysMenuEntity> findMenuTree(List<SysMenuEntity> sysMenus);

  /**
   * 获取所有的菜单
   * @param menuName 菜单名称
   * @param pageable 分页参数
   * @return 菜单集合
   */
  Page<SysMenuEntity> findAllForMenu(String menuName, Pageable pageable);
}
