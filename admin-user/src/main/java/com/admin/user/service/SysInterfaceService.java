package com.admin.user.service;

import com.admin.core.basic.InterfaceService;
import com.admin.user.entity.SysInterfaceEntity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 权限接口业务接口.
 *
 * @author fei
 * @date 2017/9/19
 */
public interface SysInterfaceService extends InterfaceService<SysInterfaceEntity, Long> {

  /**
   * 根据菜单ID集合获取接口权限列表.
   * @param menuIds 菜单ID集合
   * @return 接口权限集合
   */
  List<SysInterfaceEntity> findAllByMenuIds(@NotEmpty Long[] menuIds);
}
