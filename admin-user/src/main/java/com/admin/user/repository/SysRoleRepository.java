package com.admin.user.repository;

import com.admin.core.repository.BaseRepository;
import com.admin.user.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色数据访问接口
 *
 * @author fei
 * @date 2017/9/19
 */
public interface SysRoleRepository extends BaseRepository<SysRoleEntity, Long> {

  /**
   * 获取角色集合
   *
   * @param ids 角色ID
   * @return 角色集合
   */
  List<SysRoleEntity> findAllByIdIn(Long... ids);

  /**
   * 根据有效状态获取所有的角色信息
   * @param enabled 有效状态
   * @return 角色集合
   */
  List<SysRoleEntity> findAllByEnabled(Integer enabled);
}
