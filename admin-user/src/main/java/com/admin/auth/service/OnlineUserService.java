package com.admin.auth.service;

import com.admin.user.entity.SysUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 在线用户功能
 * @author fei
 * @since 2019/12/25 14:38
 */
public interface OnlineUserService {

  /**
   * 获取所有在线用户
   * @param pageable 分页参数
   * @return 用户集合
   */
  Page<SysUserEntity> findAllOnlineUser(Pageable pageable);
}
