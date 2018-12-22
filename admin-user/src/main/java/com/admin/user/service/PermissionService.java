package com.admin.user.service;

import com.admin.core.basice.BaseService;
import com.admin.user.entity.SysPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 菜单业务接口
 *
 * @author fei
 * @date 2017/9/19
 */
public interface PermissionService extends BaseService<SysPermission, Integer> {

  /**
   * 获取菜单树
   *
   * @return 菜单集合
   */
  List<SysPermission> findMenuTree();

  /**
   * 获取权限树
   *
   * @return 权限集合
   */
  List<SysPermission> findPermissionTree(List<SysPermission> sysPermissions);

  /**
   * 权限维护主页的列表数据
   * @param menuName 权限名称
   * @param pageable 分页参数
   * @return 权限集合
   */
  Page<SysPermission> indexList(String menuName, Pageable pageable);

  /**
   * 获取所有的菜单
   * @param menuName 菜单名称
   * @param pageable 分页参数
   * @return 菜单集合
   */
  Page<SysPermission> findAllForMenu(String menuName, Pageable pageable);

  /**
   * 根据ID集合获取授权列表
   * @param ids ID集合
   * @return 授权列表
   */
  List<SysPermission> rolePermission(Integer... ids);
}
