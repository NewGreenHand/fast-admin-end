package com.admin.user.service;

import com.admin.core.basice.BaseService;
import com.admin.user.entity.SysPermission;
import com.admin.user.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 角色业务
 * @author fei
 * @date 2017/9/19
 */
public interface RoleService extends BaseService<SysRole, Integer> {

  /**
   * 根据角色ID集合获取角色信息
   * @param ids 角色ID集合
   * @return 角色集合
   */
  List<SysRole> findAllByIds(Integer... ids);

  /**
   * 获取角色权限
   * @param id 角色ID
   * @return 权限集合
   */
  Set<Integer> getRolePermission(@NotNull Integer id);

  /**
   * 获取所有有效的角色对象
   * @return 角色集合
   */
  @NotNull
  List<SysRole> findAllValid();
}
