package com.admin.user.service.impl;

import com.admin.user.entity.*;
import com.admin.auth.model.CurrentUser;
import com.admin.auth.config.security.JwtUser;
import com.admin.user.repository.SysRoleInterfaceRepository;
import com.admin.user.repository.SysRoleMenuRepository;
import com.admin.user.service.SysMenuService;
import com.admin.user.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限角色关系表.
 *
 * @author fei
 * @date 2018/10/14
 */
@Slf4j
@Service
@Validated
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
  private final SysRoleInterfaceRepository sysRoleInterfaceRepository;
  private final SysRoleMenuRepository sysRoleMenuRepository;
  private final SysMenuService sysMenuService;

  public SysRolePermissionServiceImpl(SysRoleInterfaceRepository sysRoleInterfaceRepository, SysMenuService sysMenuService, SysRoleMenuRepository sysRoleMenuRepository) {
    this.sysRoleInterfaceRepository = sysRoleInterfaceRepository;
    this.sysMenuService = sysMenuService;
    this.sysRoleMenuRepository = sysRoleMenuRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveRolePermission(
      @NotNull Long roleId,
      @NotEmpty List<Long> menuIds,
      @NotEmpty List<Long> interfaceIds) {
    // 更新菜单权限
    this.saveRoleMenu(roleId, menuIds);

    // 更新角色接口权限
    this.saveRoleInterface(roleId, interfaceIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @CacheEvict(cacheNames = "role_menu_cache", key = "#roleId")
  public List<SysRoleMenuEntity> saveRoleMenu(@NotNull Long roleId, @NotEmpty List<Long> menuIds) {
    List<SysRoleMenuEntity> oldSysRoleMenu = sysRoleMenuRepository.findAllByRoleId(roleId);

    // 删除旧的
    sysRoleMenuRepository.deleteAll(oldSysRoleMenu);

    List<SysRoleMenuEntity> newSysRoleMenu = new LinkedList<>();
    for (Long menuId : menuIds) {
      SysRoleMenuEntity v1 = new SysRoleMenuEntity();
      v1.setRoleId(roleId);
      v1.setMenuId(menuId);

      newSysRoleMenu.add(v1);
    }

    // 添加新的
    return sysRoleMenuRepository.saveAll(newSysRoleMenu);
  }

  @Override
  @Cacheable(cacheNames = "role_menu_cache", key = "#roleId")
  public List<SysMenuEntity> roleMenu(Long roleId) {
    List<SysRoleMenuEntity> permissionRoles = sysRoleMenuRepository.findAllByRoleId(roleId);
    return permissionRoles.stream().map(SysRoleMenuEntity::getMenu).collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @CacheEvict(cacheNames = "role_interface_cache", key = "#roleId")
  public List<SysRoleInterfaceEntity> saveRoleInterface(
      @NotNull Long roleId, @NotEmpty List<Long> interfaceIds) {
    List<SysRoleInterfaceEntity> oldSysPermissionRole = sysRoleInterfaceRepository.findAllByRid(roleId);

    // 删除旧的
    sysRoleInterfaceRepository.deleteAll(oldSysPermissionRole);

    List<SysRoleInterfaceEntity> newSysPermissionRole = new LinkedList<>();
    for (Long interfaceId : interfaceIds) {
      SysRoleInterfaceEntity v = new SysRoleInterfaceEntity();
      v.setRid(roleId);
      v.setPid(interfaceId);

      newSysPermissionRole.add(v);
    }

    // 添加新的
    return sysRoleInterfaceRepository.saveAll(newSysPermissionRole);
  }

  @Override
  @Cacheable(cacheNames = "role_interface_cache", key = "#roleId")
  public List<SysInterfaceEntity> roleInterface(Long roleId) {
    List<SysRoleInterfaceEntity> permissionRoles = sysRoleInterfaceRepository.findAllByRid(roleId);
    return permissionRoles.stream()
        .map(SysRoleInterfaceEntity::getPermission)
        .collect(Collectors.toList());
  }

  @Override
  public List<SysMenuEntity> findUserMenu() {
    JwtUser jwtUser = CurrentUser.currentUser();
    Set<SysRoleEntity> roles = jwtUser.getRoles();

    List<SysMenuEntity> menus = new LinkedList<>();
    for (SysRoleEntity role : roles) {
      menus.addAll(this.roleMenu(role.getId()));
    }

    return menus;
//    return sysMenuService.findMenuTree(menus);
  }

  @Override
  public List<SysMenuEntity> findRoleMenuInterface(Long roleId) {
    // 获取当前角色所拥有的菜单
    List<SysMenuEntity> sysMenus = this.roleMenu(roleId);

    return sysMenus.stream()
        .filter(v1 -> !ObjectUtils.isArray(v1.getPermissions()))
        .collect(Collectors.toList());
  }
}
