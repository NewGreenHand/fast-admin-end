package com.admin.user.service;

import com.admin.core.basice.BaseService;
import com.admin.user.entity.SysDepartment;

/**
 * 部门业务
 * @author fei
 * @date 2018/10/13
 */
public interface DepartmentService extends BaseService<SysDepartment, Integer> {

  /**
   * 获取有效的部门节点树
   * @return 部门集合
   */
  SysDepartment validTree();
}
