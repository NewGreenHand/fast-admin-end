package com.admin.user.service.impl;

import com.admin.core.basic.AbstractServiceImpl;
import com.admin.user.entity.SysUserEntity;
import com.admin.user.repository.SysUserRepository;
import com.admin.user.service.SysUserService;
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
public class SysUserServiceImpl extends AbstractServiceImpl<SysUserEntity, Long> implements SysUserService {

  private final SysUserRepository userRepository;

  @Autowired
  public SysUserServiceImpl(SysUserRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public SysUserEntity save(@Valid SysUserEntity entity) {
    // 加密用户密码
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encode = encoder.encode(entity.getPassword());
    entity.setPassword(encode);

    // 检查用户名是否存在
    boolean exist = userRepository.existsByUserName(entity.getUserName());
    Assert.isTrue(!exist, "用户已存在");

    return userRepository.save(entity);
  }

  @Override
  public SysUserEntity update(@Valid SysUserEntity entity) {
    return super.update(entity);
  }

  @Override
  public boolean checkUserName(String name) {
    return userRepository.existsByUserName(name);
  }

  @Override
  public SysUserEntity findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public SysUserEntity findByOpenId(String openId) {
    return userRepository.findByOpenId(openId);
  }
}
