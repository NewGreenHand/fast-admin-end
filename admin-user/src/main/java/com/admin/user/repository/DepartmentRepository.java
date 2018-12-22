package com.admin.user.repository;

import com.admin.core.basice.BaseRepository;
import com.admin.user.entity.SysDepartment;

import java.lang.annotation.Documented;
import java.util.Optional;

/**
 * 部门资源表
 *
 * @author fei
 * @date 2018/10/13
 */
public interface DepartmentRepository extends BaseRepository<SysDepartment, Integer> {

  /**
   * 获取编码等级的数量
   * @param code 编码
   * @return 数量
   */
  @Deprecated
  Optional<Long> countByCodeLike(String code);

  /**
   * 获取编码等级的数量
   * @param parentCode 编码
   * @return 数量
   */
  Optional<Long> countByParentCode(String parentCode);
}
