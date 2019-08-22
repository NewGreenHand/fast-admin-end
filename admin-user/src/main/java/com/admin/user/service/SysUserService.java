package com.admin.user.service;

import com.admin.core.basic.InterfaceService;
import com.admin.user.entity.SysUserEntity;

/**
 * 系统用户业务
 * @author fei
 * @date 2017/9/19
 */
public interface SysUserService extends InterfaceService<SysUserEntity, Long> {

  /**
   * 检查用户名是否存在
   *
   * @param name 用户名
   * @return false：存在，true：不存在
   */
  boolean checkUserName(String name);

  /**
   * 获取用户根据用户名称
   *
   * @param userName 用户名称
   * @return 用户对象
   */
  SysUserEntity findByUserName(String userName);

  /**
   * 获取用户根据微信 openID
   * @param openId 微信 openID
   * @return 用户信息
   */
  SysUserEntity findByOpenId(String openId);
}
