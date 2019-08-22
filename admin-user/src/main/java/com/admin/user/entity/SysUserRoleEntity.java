package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 用户部门角色表.
 *
 * @author fei
 * @date 2018/10/14
 */
@Entity
@DynamicInsert
@Table(name = "sys_user_role", schema = "fastAdmin")
public class SysUserRoleEntity extends AbstractEntity {
  /** 用户ID */
  @NotNull private Long uid;
  /** 角色ID */
  @NotNull private Long rid;
  /** 部门ID(废弃) */
  private Long departmentId;
  /** 用户对象 */
  private SysUserEntity sysUser;
  /** 角色对象 */
  private SysRoleEntity role;

  @Basic
  @Column(name = "uid")
  public Long getUid() {
    return uid;
  }

  public void setUid(Long uid) {
    this.uid = uid;
  }

  @Basic
  @Column(name = "rid")
  public Long getRid() {
    return rid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  @Basic
  @Column(name = "department_id")
  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  @JsonIgnore
  @ManyToOne()
  @JoinColumn(name = "uid", referencedColumnName = "id", insertable = false, updatable = false)
  public SysUserEntity getSysUser() {
    return sysUser;
  }

  public void setSysUser(SysUserEntity sysUser) {
    this.sysUser = sysUser;
  }

  @ManyToOne
  @JoinColumn(name = "rid", referencedColumnName = "id", insertable = false, updatable = false)
  public SysRoleEntity getRole() {
    return role;
  }

  public void setRole(SysRoleEntity role) {
    this.role = role;
  }
}
