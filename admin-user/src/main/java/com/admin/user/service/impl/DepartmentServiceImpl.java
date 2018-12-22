package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.model.CurrentUser;
import com.admin.user.model.JwtUser;
import com.admin.user.entity.SysDepartment;
import com.admin.user.repository.DepartmentRepository;
import com.admin.user.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 部门业务
 * @author fei
 * @date 2018/10/13
 */
@Slf4j
@Service
@Validated
public class DepartmentServiceImpl extends BaseServiceImpl<SysDepartment, Integer> implements DepartmentService {
  private final DepartmentRepository departmentRepository;

  @Autowired
  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    super(departmentRepository);
    this.departmentRepository = departmentRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SysDepartment validTree() {
    // 当前登陆人的部门
    JwtUser jwtUser = CurrentUser.currentUser();
    Integer departmentId = jwtUser.getDepartmentId();
    Assert.notNull(departmentId, "当前用户还不属于任何部门，还不能进行此操作");

    return this.findByPk(departmentId);

  }

  @Override
  public SysDepartment save(@Valid SysDepartment entity) {
    // 生成部门编码
    SysDepartment old = this.findByPk(entity.getParentId());
    // 生成部门编码
    String code = this.buildCode(old);
    entity.setCode(code);
    entity.setParentCode(old.getCode());

    return super.save(entity);
  }

  /**
   * 生成部门编码
   * @param old 上级部门对象
   * @return 下级部门编码
   */
  private String buildCode(SysDepartment old){
    if (null == old) {
      return "A1";
    }

    String oldCode = old.getCode();
    Long number = departmentRepository.countByParentCode(oldCode).orElse(0L);
    String[] codes = oldCode.split("-");
    char[] a = codes[codes.length - 1].toCharArray();
    String nextCode = String.valueOf((char) (a[0] + 1)).concat(String.valueOf(number + 1));
    // 返回编码
    return oldCode.concat("-").concat(nextCode);
}
}
