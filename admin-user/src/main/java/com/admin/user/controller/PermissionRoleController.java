package com.admin.user.controller;

import com.admin.user.dto.PermissionRoleDepartmentDto;
import com.admin.user.entity.SysPermission;
import com.admin.user.entity.SysPermissionRole;
import com.admin.user.service.PermissionRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限角色部门表
 *
 * @author fei
 * @date 2018/10/14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/auth")
public class PermissionRoleController {
  @Autowired private PermissionRoleService permissionRoleService;

  /**
   * 角色所拥有的权限
   *
   * @param roleId 角色ID
   * @return 权限角色部门对象
   */
  @GetMapping("get_have_permission")
  public List<SysPermissionRole> getHavePermission(@NotNull Integer roleId) {
    SysPermissionRole sysPermissionRole = new SysPermissionRole();
    sysPermissionRole.setRid(roleId);

    return permissionRoleService.findAll(Example.of(sysPermissionRole));
  }

  /**
   * 更新角色权限
   * @param dto 表单
   * @return 权限集合
   */
  @PostMapping
  public List<SysPermissionRole> save(@Valid @RequestBody PermissionRoleDepartmentDto dto) {
    return permissionRoleService.save(dto);
  }

  /**
   * 角色授权树
   * @return 当前角色所拥有的权限树
   */
  @GetMapping("permission_tree")
  public List<SysPermission> permissionTree() {
    return permissionRoleService.permissionTree();
  }


  /**
   * 获取用户菜单
   *
   * @return 菜单树集合
   */
  @GetMapping(value = "user_menu")
  public List<SysPermission> findUserMenu() {
    return permissionRoleService.findUserMenu();
  }
}
