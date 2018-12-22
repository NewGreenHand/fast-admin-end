package com.admin.user.service;

import com.admin.core.basice.BaseService;
import com.admin.user.dto.PermissionRoleDepartmentDto;
import com.admin.user.entity.SysPermission;
import com.admin.user.entity.SysPermissionRole;

import java.util.List;


/**
 * 权限角色部门关系业务
 * @author fei
 * @date 2018/10/14
 */
public interface PermissionRoleService extends BaseService<SysPermissionRole, Integer> {

  /**
   * 保存权限信息
   * @param dto 表单
   * @return 权限集合
   */
  List<SysPermissionRole> save(PermissionRoleDepartmentDto dto);

  /**
   * 获取部门角色的权限菜单
   * @param roleId 角色ID
   * @return 部门角色菜单集合
   */
  List<SysPermission> findAllByRoleId(Integer roleId);

  /**
   * 角色的授权树
   * @return 当前角色所拥有的权限
   */
  List<SysPermission> permissionTree();

    /**
   * 角色的授权树
   * @return 当前角色所拥有的权限
   */
  List<SysPermission> findUserMenu();


}
