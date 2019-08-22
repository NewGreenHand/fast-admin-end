package com.admin.user.service;

import com.admin.user.entity.SysInterfaceEntity;
import com.admin.user.entity.SysRoleInterfaceEntity;
import com.admin.user.entity.SysMenuEntity;
import com.admin.user.entity.SysRoleMenuEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色权限业务.
 *
 * @author fei
 * @date 2018/10/14
 */
public interface SysRolePermissionService {

  /**
   * 角色授权.
   *
   * @param roleId 角色ID
   * @param menuIds 菜单ID集合
   * @param interfaceIds 接口权限ID集合
   */
  void saveRolePermission(
      @NotNull Long roleId,
      @NotEmpty List<Long> menuIds,
      @NotEmpty List<Long> interfaceIds);

  /**
   * 保存角色菜单.
   *
   * @param roleId 角色ID
   * @param menuIds 菜单ID集合
   * @return 角色菜单关系对象
   */
  List<SysRoleMenuEntity> saveRoleMenu(@NotNull Long roleId, @NotEmpty List<Long> menuIds);

  /**
   * 获取角色的菜单集合.
   *
   * @param roleId 角色ID
   * @return 菜单集合
   */
  List<SysMenuEntity> roleMenu(@NotNull Long roleId);

  /**
   * 保存权限信息.
   *
   * @param roleId 角色ID
   * @param interfaceIds 接口权限ID集合
   * @return 权限集合
   */
  List<SysRoleInterfaceEntity> saveRoleInterface(
      @NotNull Long roleId, @NotEmpty List<Long> interfaceIds);

  /**
   * 获取角色的权限.
   *
   * @param roleId 角色ID
   * @return 角色权限集合
   */
  List<SysInterfaceEntity> roleInterface(@NotNull Long roleId);

  /**
   * 角色的授权树.
   *
   * @return 当前角色所拥有的权限
   */
  List<SysMenuEntity> findUserMenu();

  /**
   * 角色所拥有的菜单下面的接口权限列表.
   *
   * @param roleId 角色ID
   * @return 接口权限列表
   */
  List<SysMenuEntity> findRoleMenuInterface(Long roleId);
}
