package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.entity.SysDepartment;
import com.admin.user.entity.SysUser;
import com.admin.user.repository.UserRepository;
import com.admin.user.service.DepartmentService;
import com.admin.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 用户业务
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<SysUser, Long> implements UserService {

  private final UserRepository userRepository;
  private final DepartmentService departmentService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, DepartmentService departmentService) {
    super(userRepository);
    this.userRepository = userRepository;
    this.departmentService = departmentService;
  }

  @Override
  public SysUser save(@Valid SysUser entity) {
    // 加密用户密码
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encode = encoder.encode(entity.getPassword());
    entity.setPassword(encode);

    // 检查用户名是否存在
    boolean exist = userRepository.existsByUserName(entity.getUserName());
    Assert.isTrue(!exist, "用户已存在");
    // 验证部门
    SysDepartment department =
        departmentService.findByPk(entity.getDepartmentId());

    entity.setDepartmentCode(department.getCode());
    return userRepository.save(entity);
  }

  @Override
  public SysUser update(@Valid SysUser entity) {
    // 验证部门
    SysDepartment department =
      departmentService.findByPk(entity.getDepartmentId());

    entity.setDepartmentCode(department.getCode());
    return super.update(entity);
  }

  @Override
  public boolean checkUserName(String name) {
    return userRepository.existsByUserName(name);
  }

  @Override
  public SysUser findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public SysUser findByOpenId(String openId) {
    return userRepository.findByOpenId(openId);
  }
}
