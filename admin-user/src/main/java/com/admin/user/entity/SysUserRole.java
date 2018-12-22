package com.admin.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * 用户部门角色表
 *
 * @author fei
 * @date 2018/10/14
 */
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_user_role", schema = "adminModel")
public class SysUserRole {
  private Integer id;
  /** 用户ID */
  @NotNull private Integer uid;
  /** 角色ID */
  @NotNull private Integer rid;
  /** 部门ID(废弃) */
  private Integer departmentId;
  /** 创建人 */
  private String createUser;
  /** 创建时间 */
  private Date createDate;
  /** 更新人 */
  private String updateUser;
  /** 更新时间 */
  private Date updateDate;
  /** 用户对象 */
  private SysUser sysUser;
  /** 角色对象 */
  private SysRole role;

  @Id
  @GeneratedValue
  @Column(name = "id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "uid")
  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  @Basic
  @Column(name = "rid")
  public Integer getRid() {
    return rid;
  }

  public void setRid(Integer rid) {
    this.rid = rid;
  }

  @Basic
  @Column(name = "department_id")
  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  @Basic
  @CreatedBy
  @Column(name = "create_user")
  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Basic
  @CreatedDate
  @Column(name = "create_date")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Basic
  @LastModifiedBy
  @Column(name = "update_user")
  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  @Basic
  @LastModifiedDate
  @Column(name = "update_date")
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysUserRole that = (SysUserRole) o;
    return id == that.id
        && Objects.equals(uid, that.uid)
        && Objects.equals(rid, that.rid)
        && Objects.equals(departmentId, that.departmentId)
        && Objects.equals(createUser, that.createUser)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(updateUser, that.updateUser)
        && Objects.equals(updateDate, that.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uid, rid, departmentId, createUser, createDate, updateUser, updateDate);
  }

  @JsonIgnore
  @ManyToOne()
  @JoinColumn(name = "uid", referencedColumnName = "id", insertable = false, updatable = false)
  public SysUser getSysUser() {
    return sysUser;
  }

  public void setSysUser(SysUser sysUser) {
    this.sysUser = sysUser;
  }

  @ManyToOne
  @JoinColumn(name = "rid", referencedColumnName = "id", insertable = false, updatable = false)
  public SysRole getRole() {
    return role;
  }

  public void setRole(SysRole role) {
    this.role = role;
  }
}
