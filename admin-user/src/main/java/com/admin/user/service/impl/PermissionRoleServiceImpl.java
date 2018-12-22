package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.model.CurrentUser;
import com.admin.user.model.JwtUser;
import com.admin.user.dto.PermissionRoleDepartmentDto;
import com.admin.user.entity.SysPermission;
import com.admin.user.entity.SysPermissionRole;
import com.admin.user.entity.SysRole;
import com.admin.user.repository.PermissionRoleDepartmentRepository;
import com.admin.user.service.PermissionRoleService;
import com.admin.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限角色关系表
 * @author fei
 * @date 2018/10/14
 */
@Slf4j
@Service
@Validated
public class PermissionRoleServiceImpl extends BaseServiceImpl<SysPermissionRole, Integer> implements PermissionRoleService {
  private final PermissionRoleDepartmentRepository permissionRoleDepartmentRepository;

  @Autowired private PermissionService permissionService;

  @Autowired
  public PermissionRoleServiceImpl(PermissionRoleDepartmentRepository permissionRoleDepartmentRepository) {
    super(permissionRoleDepartmentRepository);
    this.permissionRoleDepartmentRepository = permissionRoleDepartmentRepository;
  }

  @Override
  @CacheEvict(cacheNames = "role_cache", key = "#dto.roleId")
  @Transactional(rollbackFor = Exception.class)
  public List<SysPermissionRole> save(PermissionRoleDepartmentDto dto) {
    List<SysPermissionRole> oldSysPermissionRole =
        permissionRoleDepartmentRepository.findAllByRid(dto.getRoleId());

    List<SysPermissionRole> newSysPermissionRole = new LinkedList<>();
    for (Integer permissionId : dto.getPermissionIds()){
      SysPermissionRole v = new SysPermissionRole();
      v.setRid(dto.getRoleId());
      v.setMid(permissionId);

      newSysPermissionRole.add(v);
    }

    // 删除旧的
    permissionRoleDepartmentRepository.deleteAll(oldSysPermissionRole);

    // 添加新的
    return permissionRoleDepartmentRepository.saveAll(newSysPermissionRole);
  }

  @Override
  @Cacheable(cacheNames = "role_cache", key = "#roleId")
  public List<SysPermission> findAllByRoleId(Integer roleId) {
    List<SysPermissionRole> permissionRoles = permissionRoleDepartmentRepository.findAllByRid(roleId);
    return permissionRoles.stream().map(SysPermissionRole::getPermission).collect(Collectors.toList());
  }

  @Override
  public List<SysPermission> permissionTree() {
    JwtUser jwtUser = CurrentUser.currentUser();
    Set<SysRole> roles = jwtUser.getRoles();
    Assert.notEmpty(roles, "你还未被赋予角色");
    List<SysPermission> permissions = null;
    for (SysRole role : roles) {
      if (null == permissions) {
        permissions = new LinkedList<>();
      }
      permissions.addAll(this.findAllByRoleId(role.getId()));
    }
    return permissionService.findPermissionTree(permissions);
  }

  @Override
  public List<SysPermission> findUserMenu() {
    JwtUser jwtUser = CurrentUser.currentUser();
    Set<SysRole> roles = jwtUser.getRoles();

    List<SysPermission> permissions = new LinkedList<>();
    for (SysRole role : roles) {
      permissions.addAll(this.findAllByRoleId(role.getId()));
    }

    return permissionService.findPermissionTree(permissions);
  }
}
