package com.admin.auth.config.security;

import com.admin.user.entity.SysUserEntity;
import com.admin.user.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 用户名查询业务(security 用)
 *
 * @author fei
 * @date 2018/10/21
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
  private final SysUserRepository userRepository;

  @Autowired
  public JwtUserDetailsServiceImpl(SysUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    SysUserEntity sysUser = userRepository.findByUserName(s);
    Assert.notNull(sysUser, "用户名错误");

    return JwtUserFactory.create(sysUser);
  }

}
