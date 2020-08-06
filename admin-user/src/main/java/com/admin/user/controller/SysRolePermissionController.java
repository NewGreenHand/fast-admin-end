package com.admin.user.controller;

import com.admin.user.dto.RoleInterfaceDto;
import com.admin.user.dto.RoleMenuDto;
import com.admin.user.dto.RolePermissionDto;
import com.admin.user.entity.SysInterfaceEntity;
import com.admin.user.entity.SysMenuEntity;
import com.admin.user.entity.SysRoleInterfaceEntity;
import com.admin.user.entity.SysRoleMenuEntity;
import com.admin.user.service.SysRolePermissionService;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 角色权限接口.
 *
 * @author fei
 * @date 2018/10/14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/auth")
public class SysRolePermissionController {
  private final SysRolePermissionService sysRolePermissionsService;

  @Autowired
  public SysRolePermissionController(SysRolePermissionService sysRolePermissionsService) {
    this.sysRolePermissionsService = sysRolePermissionsService;
  }

  /**
   * 角色授权更新接口.
   * @param dto 角色授权表单
   * @return 成功提示
   */
  @PostMapping
  public String rolePermission(@RequestBody @Valid RolePermissionDto dto) {

    sysRolePermissionsService.saveRolePermission(
        dto.getRoleId(), dto.getMenuIds(), dto.getApis());

    return "授权成功";
  }

  /**
   * 角色所拥有的权限.
   *
   * @param roleId 角色ID
   * @return 权限角色部门对象
   */
  @GetMapping("role_interface")
  public List<SysInterfaceEntity> roleInterface(@NotNull Long roleId) {
    return sysRolePermissionsService.roleInterface(roleId);
  }

  /**
   * 更新角色权限.
   *
   * @param dto 表单
   * @return 权限集合
   */
  @PostMapping("role_interface")
  public List<SysRoleInterfaceEntity> saveRoleInterface(@Valid @RequestBody RoleInterfaceDto dto) {
    return sysRolePermissionsService.saveRoleInterface(dto.getRoleId(), dto.getApis());
  }

  /**
   * 获取角色所拥有的菜单.
   *
   * @param roleId 角色ID
   * @return 菜单集合
   */
  @GetMapping("role_menu")
  public List<SysMenuEntity> roleMenu(@NotNull Long roleId) {
    return sysRolePermissionsService.roleMenu(roleId);
  }

  /**
   * 更新角色的菜单.
   *
   * @param dto 表单
   * @return 菜单集合
   */
  @PostMapping("role_menu")
  public List<SysRoleMenuEntity> saveRoleMenu(@Valid @RequestBody RoleMenuDto dto) {
    return sysRolePermissionsService.saveRoleMenu(dto.getRoleId(), dto.getMenuIds());
  }

  /**
   * 获取用户菜单.
   *
   * @return 菜单树集合
   */
  @GetMapping(value = "user_menu")
  public List<SysMenuEntity> findUserMenu() {
    return sysRolePermissionsService.findUserMenu();
  }

  /**
   * 获取角色所拥有的菜单下面的权限列表.
   * @param roleId 角色ID
   * @return 接口权限列表
   */
  @GetMapping("role_menu_interface")
  public List<SysMenuEntity> findRoleMenuInterface(@NotNull Long roleId) {
    return sysRolePermissionsService.findRoleMenuInterface(roleId);
  }
}

