package com.admin.user.service.impl;

import com.admin.core.basic.AbstractServiceImpl;
import com.admin.user.entity.*;
import com.admin.core.enums.EnabledEnum;
import com.admin.user.repository.SysRoleRepository;
import com.admin.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 角色业务
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@Service
@Validated
public class SysRoleServiceImpl extends AbstractServiceImpl<SysRoleEntity, Long> implements SysRoleService {

  private final SysRoleRepository roleRepository;

  @Autowired
  public SysRoleServiceImpl(SysRoleRepository roleRepository) {
    super(roleRepository);
    this.roleRepository = roleRepository;
  }

  @Override
  public List<SysRoleEntity> findAllByIds(Long... ids) {
    return roleRepository.findAllByIdIn(ids);
  }

  @Override
  public SysRoleEntity update(@Valid SysRoleEntity entity) {
    return super.update(entity);
  }

  @Override
  public Set<Long> getRolePermission(@NotNull Long id) {
    SysRoleEntity sysRole = this.findById(id);

    return sysRole.getMenuIds();
  }

  @Override
  public @NotNull List<SysRoleEntity> findAllValid() {
    return roleRepository.findAllByEnabled(EnabledEnum.ON.value());
  }

  @Override
  public void delete(SysRoleEntity entity) {
    super.delete(entity);
  }
}
