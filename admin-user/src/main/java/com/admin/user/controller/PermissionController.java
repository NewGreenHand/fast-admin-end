package com.admin.user.controller;

import com.admin.user.dto.ApiDto;
import com.admin.user.dto.ApiEditDto;
import com.admin.user.dto.MenuDto;
import com.admin.user.entity.SysPermission;
import com.admin.user.enums.PermissionTypeEnum;
import com.admin.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单资源接口
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@RestController
@RequestMapping("permission")
public class PermissionController {

  private final PermissionService permissionService;

  @Autowired
  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  /**
   * 获取菜单树
   *
   * @return 菜单集合
   */
  @GetMapping("menu_tree")
  public List<SysPermission> findMenuTree() {
    return permissionService.findMenuTree();
  }

  /**
   * 获取所有的菜单
   *
   * @param name 菜单名称
   * @param pageable 分页参数
   * @return 菜单集合
   */
  @GetMapping("all_valid")
  public Page<SysPermission> findAll(String name, @PageableDefault Pageable pageable) {
    return permissionService.indexList(name, pageable);
  }

  /**
   * 保存菜单
   *
   * @param dto 菜单表单
   * @return 保存后的菜单对象
   */
  @PostMapping("menu")
  public SysPermission save(@Valid @RequestBody MenuDto dto) {

    SysPermission menu = dto.convert(SysPermission.class);
    menu.setEnabled(dto.getEnabled().value());
    menu.setType(PermissionTypeEnum.MENU.value());
    // 处理一下上级菜单
    Integer[] parentIds = dto.getParentId();
    if (!ObjectUtils.isEmpty(parentIds)) {
      menu.setParentId(parentIds[parentIds.length - 1]);
    }

    return permissionService.save(menu);
  }

  /**
   * 更新菜单信息
   * @param id 菜单ID
   * @param dto 菜单信息
   * @return 更新后的菜单信息
   */
  @PutMapping("/menu/{id}")
  public SysPermission update(@NotNull @PathVariable Integer id, @Valid @RequestBody MenuDto dto) {
    SysPermission sysMenu = permissionService.findByPk(id);

    BeanUtils.copyProperties(dto, sysMenu);
    sysMenu.setEnabled(dto.getEnabled().value());
    // 处理一下上级菜单
    Integer[] parentIds = dto.getParentId();
    if (!ObjectUtils.isEmpty(parentIds)) {
      sysMenu.setParentId(parentIds[parentIds.length - 1]);
    }

    return permissionService.update(sysMenu);
  }

  /**
   * 保存 API 接口路径
   *
   * @param dto 菜单表单
   * @return 保存后的菜单对象
   */
  @PostMapping("api")
  public SysPermission saveApi(@Valid @RequestBody ApiDto dto) {

    SysPermission api = dto.convert(SysPermission.class);
    api.setEnabled(dto.getEnabled().value());
    api.setType(PermissionTypeEnum.API.value());

    return permissionService.save(api);
  }

  /**
   * 更新 API 权限
   *
   * @param id 权限ID
   * @param dto api 表单
   * @return 更新后的权限对象
   */
  @PutMapping("/api/{id}")
  public SysPermission updateApi(
      @NotNull @PathVariable Integer id, @Valid @RequestBody ApiEditDto dto) {
    SysPermission sysPermission =
        permissionService.findByPk(id);
    BeanUtils.copyProperties(dto, sysPermission);
    sysPermission.setEnabled(dto.getEnabled().value());

    return permissionService.update(sysPermission);
  }

  /**
   * 获取一个菜单对象
   *
   * @param id 菜单ID
   * @return 菜单对象
   */
  @GetMapping("/{id}")
  public SysPermission findOne(@PathVariable Integer id) {
    return permissionService.findByPk(id);
  }
}
