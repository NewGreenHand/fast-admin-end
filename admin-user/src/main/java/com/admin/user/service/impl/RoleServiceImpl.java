package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.entity.*;
import com.admin.user.enums.EnabledEnum;
import com.admin.user.repository.RoleRepository;
import com.admin.user.service.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<SysRole, Integer> implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    super(roleRepository);
    this.roleRepository = roleRepository;
  }

  @Override
  public List<SysRole> findAllByIds(Integer... ids) {
    return roleRepository.findAllByIdIn(ids);
  }

  @Override
  public SysRole update(@Valid SysRole entity) {
    return super.update(entity);
  }

  @Override
  public Set<Integer> getRolePermission(@NotNull Integer id) {
    SysRole sysRole = this.findByPk(id);

    return sysRole.getPermissionIds();
  }

  @Override
  public @NotNull List<SysRole> findAllValid() {
    return roleRepository.findAllByEnabled(EnabledEnum.ON.value());
  }

  @Override
  public void delete(SysRole entity) {
    super.delete(entity);
  }
}
