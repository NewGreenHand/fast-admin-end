package com.admin.user.controller;

import com.admin.core.basic.AbstractController;
import com.admin.user.dto.ApiDto;
import com.admin.user.dto.ApiEditDto;
import com.admin.user.entity.SysInterfaceEntity;
import com.admin.user.service.SysInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 接口权限接口
 *
 * @author fei
 * @since 2019-01-14 23:37
 */
@Slf4j
@Validated
@RestController
@RequestMapping("interface")
public class SysInterfaceController extends AbstractController<SysInterfaceEntity, Long> {

  private final SysInterfaceService sysInterfaceService;

  @Autowired
  public SysInterfaceController(SysInterfaceService sysInterfaceService) {
    super(sysInterfaceService);
    this.sysInterfaceService = sysInterfaceService;
  }

  /**
   * 获取所有的接口权限列表.
   * @return 接口权限列表集合
   */
  @GetMapping
  public List<SysInterfaceEntity> findAll() {
    return sysInterfaceService.findAll();
  }

  /**
   * 保存 API 接口路径.
   *
   * @param dto 菜单表单
   * @return 保存后的菜单对象
   */
  @PostMapping
  public SysInterfaceEntity saveApi(@Valid @RequestBody ApiDto dto) {

    SysInterfaceEntity api = dto.convert(SysInterfaceEntity.class);

    return sysInterfaceService.save(api);
  }

  /**
   * 更新 API 权限.
   *
   * @param id 权限ID
   * @param dto api 表单
   * @return 更新后的权限对象
   */
  @PutMapping("{id}")
  public SysInterfaceEntity updateApi(
      @NotNull @PathVariable Long id, @Valid @RequestBody ApiEditDto dto) {
    SysInterfaceEntity sysPermission = sysInterfaceService.findById(id);
    BeanUtils.copyProperties(dto, sysPermission);

    return sysInterfaceService.update(sysPermission);
  }
}

