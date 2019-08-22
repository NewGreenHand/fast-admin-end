package com.admin.user.service.impl;

import com.admin.core.basic.AbstractServiceImpl;
import com.admin.user.entity.SysUserRoleEntity;
import com.admin.user.repository.SysUserRoleRepository;
import com.admin.user.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户部门角色关系业务
 * @author fei
 * @since 2018/10/14
 */
@Slf4j
@Service
@Validated
public class SysUserRoleServiceImpl extends AbstractServiceImpl<SysUserRoleEntity, Long> implements SysUserRoleService {
  private final SysUserRoleRepository userDepartmentRoleRepository;

  @Autowired
  public SysUserRoleServiceImpl(SysUserRoleRepository userDepartmentRoleRepository) {
    super(userDepartmentRoleRepository);
    this.userDepartmentRoleRepository = userDepartmentRoleRepository;
  }

  @Override
  public List<SysUserRoleEntity> findAllByUserId(@NotNull Long userId) {
    return userDepartmentRoleRepository.findAllByUid(userId);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SysUserRoleEntity> save(@NotNull Long userId, List<Long> roleIds) {
    // 获取已有的职责
    List<SysUserRoleEntity> oldSysUserRole = userDepartmentRoleRepository.findAllByUid(userId);

    List<SysUserRoleEntity> newSysUserRole = new LinkedList<>();
    for (Long roleId : roleIds){
      SysUserRoleEntity v = new SysUserRoleEntity();
      v.setRid(roleId);
      v.setUid(userId);

      newSysUserRole.add(v);
    }

    // 删除旧的
    userDepartmentRoleRepository.deleteAll(oldSysUserRole);

    // 添加新的
    return userDepartmentRoleRepository.saveAll(newSysUserRole);
  }
}
