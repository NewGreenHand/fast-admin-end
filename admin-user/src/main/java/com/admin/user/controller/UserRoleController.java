package com.admin.user.controller;

import com.admin.user.dto.UserDepartmentRoleDto;
import com.admin.user.entity.SysUserRole;
import com.admin.user.service.UserDepartmentRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserRoleController {
  @Autowired private UserDepartmentRoleService userDepartmentRoleService;

  @GetMapping("get_have_position")
  public List<SysUserRole> getHavePosition(Integer userId) {
    SysUserRole sysUserRole = new SysUserRole();
    sysUserRole.setUid(userId);

    return userDepartmentRoleService.findAll(Example.of(sysUserRole));
  }

  @PostMapping("/{userId}")
  public List<SysUserRole> save(@NotNull @PathVariable Integer userId, @Valid @RequestBody List<Integer> roleIds) {
    return userDepartmentRoleService.save(userId, roleIds);
  }
}


