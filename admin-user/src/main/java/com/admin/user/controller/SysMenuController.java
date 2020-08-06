package com.admin.user.controller;

import com.admin.core.basic.AbstractController;
import com.admin.user.dto.MenuDto;
import com.admin.user.entity.SysMenuEntity;
import com.admin.user.service.SysMenuService;
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
 * 菜单资源接口.
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class SysMenuController extends AbstractController<SysMenuEntity, Long> {

  private final SysMenuService sysMenuService;

  @Autowired
  public SysMenuController(SysMenuService sysMenuService) {
    super(sysMenuService);
    this.sysMenuService = sysMenuService;
  }

  /**
   * 获取菜单树.
   *
   * @return 菜单集合
   */
  @GetMapping("menu_tree")
  public List<SysMenuEntity> findMenuTree() {
    return sysMenuService.findMenuTree();
  }

  /**
   * 获取有效子菜单集合
   * @param name 菜单名称
   * @param pageable 分页参数
   * @return 子菜单集合(分页)
   */
  @GetMapping(value = "children_list", params = {"page", "size"})
  public Page<SysMenuEntity> findChildrenList(String name, @PageableDefault Pageable pageable) {
    return sysMenuService.findChildrenList(name, pageable);
  }

  /**
   * 获取子菜单集合
   * @param name 菜单名称
   * @return 子菜单集合(不分页)
   */
  @GetMapping("children_list")
  public List<SysMenuEntity> findChildrenList(String name) {
    return sysMenuService.findChildrenList(name);
  }

  /**
   * 获取所有的菜单.
   *
   * @param name 菜单名称
   * @param pageable 分页参数
   * @return 菜单集合
   */
  @GetMapping("index")
  public Page<SysMenuEntity> findAll(String name, @PageableDefault Pageable pageable) {
    return sysMenuService.indexList(name, pageable);
  }

  /**
   * 保存菜单.
   *
   * @param dto 菜单表单
   * @return 保存后的菜单对象
   */
  @PostMapping
  public SysMenuEntity save(@Valid @RequestBody MenuDto dto) {

    SysMenuEntity sysMenu = dto.convert(SysMenuEntity.class);
    // 处理一下上级菜单
    List<Integer> parentIds = dto.getParentIdChain();
    if (!ObjectUtils.isEmpty(parentIds)) {
      sysMenu.setParentId(Long.valueOf(parentIds.get(parentIds.size() - 1)));
    }

    return sysMenuService.save(sysMenu);
  }

  /**
   * 更新菜单信息.
   * @param id 菜单ID
   * @param dto 菜单信息
   * @return 更新后的菜单信息
   */
  @PutMapping("{id}")
  public SysMenuEntity update(@NotNull @PathVariable Long id, @Valid @RequestBody MenuDto dto) {
    SysMenuEntity sysMenu = sysMenuService.findById(id);

    BeanUtils.copyProperties(dto, sysMenu);
    // 处理一下上级菜单
    List<Integer> parentIds = dto.getParentIdChain();
    if (!ObjectUtils.isEmpty(parentIds)) {
      sysMenu.setParentId(Long.valueOf(parentIds.get(parentIds.size() - 1)));
    }

    return sysMenuService.update(sysMenu);
  }
}
