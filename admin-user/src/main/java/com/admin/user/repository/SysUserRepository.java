package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户表
 * @author fei
 * @date 2017/9/19
 */
public interface SysUserRepository extends BaseRepository<SysUserEntity, Long> {

  /**
   * 获取一个用户
   *
   * @param userName 用户名
   * @return 用户对象
   */
  SysUserEntity findByUserName(String userName);

  /**
   * 根据微信ID获取用户 信息
   * @param openId 微信ID
   * @return 用户对象
   */
  SysUserEntity findByOpenId(String openId);

  /**
   * 检查用户名是否存在
   * @param userName 用户名
   * @return true: 已存在, false: 不存在
   */
  boolean existsByUserName(String userName);

  /**
   * 根据ID集合获取用户集合
   * @param pageable 分页参数
   * @param ids ID集合
   * @return 用户集合
   */
  Page<SysUserEntity> findAllById(Pageable pageable, Iterable<Long> ids);
}
