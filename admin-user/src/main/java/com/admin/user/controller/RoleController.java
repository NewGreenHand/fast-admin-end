package com.admin.user.controller;

import com.admin.user.dto.RoleDto;
import com.admin.user.entity.SysRole;
import com.admin.user.enums.EnabledEnum;
import com.admin.user.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 角色接口
 *
 * @author fei
 * @date 2017/9/19
 */
@RestController
@RequestMapping("roles")
public class RoleController {

  private static final ExampleMatcher EXAMPLE_MATCHER;

  static {
    EXAMPLE_MATCHER =
        ExampleMatcher.matchingAll()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
            .withMatcher("departmentCode", ExampleMatcher.GenericPropertyMatchers.contains())
            .withIgnorePaths("permissionIds");
  }

  private final RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  /**
   * 获取所有有效的角色集合
   *
   * @return 角色集合
   */
  @GetMapping("all_valid")
  public List<SysRole> findAllValid() {
    SysRole sysRole = new SysRole();
    sysRole.setEnabled(EnabledEnum.ON.value());

    Example<SysRole> example = Example.of(sysRole, EXAMPLE_MATCHER);

    return roleService.findAll(example);
  }

  /**
   * 获取所有的角色信息
   *
   * @param roleName 角色名称
   * @param pageable 分页参数
   * @return 角色集合
   */
  @GetMapping(value = "index", params = {"page"})
  public Page<SysRole> findAll(String roleName, @PageableDefault Pageable pageable) {
    SysRole sysRole = new SysRole();
    sysRole.setName(roleName);

    return roleService.findAll(Example.of(sysRole, EXAMPLE_MATCHER), pageable);
  }

  /**
   * 获取一个角色对象
   *
   * @param id 角色ID
   * @return 角色对象
   */
  @GetMapping("/{id}")
  public SysRole findOne(@NotNull @PathVariable Integer id) {
    return roleService.findByPk(id);
  }

  /**
   * 保存角色信息
   *
   * @param dto 角色表单
   * @return 保存后的角色信息
   */
  @PostMapping
  public SysRole save(@Valid @RequestBody RoleDto dto) {
    SysRole sysRole = dto.convert(SysRole.class);
    sysRole.setEnabled(dto.getEnabled().value());
    return roleService.save(sysRole);
  }

  /**
   * 更新角色信息
   *
   * @param id 角色ID
   * @param dto 角色表单
   * @return 更新后的角色信息
   */
  @PutMapping("/{id}")
  public SysRole update(@NotNull @PathVariable Integer id, @Valid @RequestBody RoleDto dto) {
    SysRole sysRole = roleService.findByPk(id);
    BeanUtils.copyProperties(dto, sysRole);
    sysRole.setEnabled(dto.getEnabled().value());
    return roleService.update(sysRole);
  }

  /**
   * 更新角色权限
   *
   * @param id 角色ID
   * @param ids 权限ID集合
   * @return 更新后的角色信息
   */
  @PutMapping("/{id}/role_authorization")
  public SysRole authorization(@PathVariable Integer id, @RequestBody Set<Integer> ids) {
    SysRole sysRole = roleService.findByPk(id);
    sysRole.setPermissionIds(ids);

    return roleService.save(sysRole);
  }
}

