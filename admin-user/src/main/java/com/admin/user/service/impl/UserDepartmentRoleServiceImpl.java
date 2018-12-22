package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.dto.UserDepartmentRoleDto;
import com.admin.user.entity.SysUserRole;
import com.admin.user.repository.UserDepartmentRoleRepository;
import com.admin.user.service.UserDepartmentRoleService;
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
 * @date 2018/10/14
 */
@Slf4j
@Service
@Validated
public class UserDepartmentRoleServiceImpl extends BaseServiceImpl<SysUserRole, Integer> implements UserDepartmentRoleService {
  private final UserDepartmentRoleRepository userDepartmentRoleRepository;

  @Autowired
  public UserDepartmentRoleServiceImpl(UserDepartmentRoleRepository userDepartmentRoleRepository) {
    super(userDepartmentRoleRepository);
    this.userDepartmentRoleRepository = userDepartmentRoleRepository;
  }

  @Override
  public List<SysUserRole> findAllByUserId(@NotNull Integer userId) {
    return userDepartmentRoleRepository.findAllByUid(userId);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SysUserRole> save(@NotNull Integer userId, List<Integer> roleIds) {
    // 获取已有的职责
    List<SysUserRole> oldSysUserRole = userDepartmentRoleRepository.findAllByUid(userId);

    List<SysUserRole> newSysUserRole = new LinkedList<>();
    for (Integer roleId : roleIds){
      SysUserRole v = new SysUserRole();
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
