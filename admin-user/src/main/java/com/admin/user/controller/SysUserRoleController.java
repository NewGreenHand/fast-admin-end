package com.admin.user.controller;

import com.admin.core.basic.AbstractController;
import com.admin.user.entity.SysUserRoleEntity;
import com.admin.user.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户部门角色控制器（分配职责）
 * @author fei
 * @date 2018/10/14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/position")
public class SysUserRoleController extends AbstractController<SysUserRoleEntity, Long> {
  private final SysUserRoleService userDepartmentRoleService;

  public SysUserRoleController(SysUserRoleService userDepartmentRoleService) {
    super(userDepartmentRoleService);
    this.userDepartmentRoleService = userDepartmentRoleService;
  }

  @GetMapping("get_have_position")
  public List<SysUserRoleEntity> getHavePosition(Long userId) {
    SysUserRoleEntity sysUserRole = new SysUserRoleEntity();
    sysUserRole.setUid(userId);

    return userDepartmentRoleService.findAll(Example.of(sysUserRole));
  }

  @PostMapping("/{userId}")
  public List<SysUserRoleEntity> save(@NotNull @PathVariable Long userId, @Valid @RequestBody List<Long> roleIds) {
    return userDepartmentRoleService.save(userId, roleIds);
  }
}


