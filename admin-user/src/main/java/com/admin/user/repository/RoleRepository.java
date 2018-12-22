package com.admin.user.repository;

import com.admin.core.basice.BaseRepository;
import com.admin.user.entity.SysRole;

import java.util.Collection;
import java.util.List;

/**
 * 角色数据访问接口
 *
 * @author fei
 * @date 2017/9/19
 */
public interface RoleRepository extends BaseRepository<SysRole, Integer> {

  /**
   * 获取角色集合
   *
   * @param ids 角色ID
   * @return 角色集合
   */
  List<SysRole> findAllByIdIn(Integer... ids);

  /**
   * 根据有效状态获取所有的角色信息
   * @param enabled 有效状态
   * @return 角色集合
   */
  List<SysRole> findAllByEnabled(Integer enabled);
}
