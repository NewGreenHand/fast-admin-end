package com.admin.user.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 权限角色部门
 *
 * @author fei
 * @date 2018/10/14
 */
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_permission_role", schema = "adminModel")
public class SysPermissionRole implements Serializable {
  private static final long serialVersionUID = -1707634470264551472L;

  private Integer id;
  /** 菜单（权限）表ID */
  private Integer mid;
  /** 角色表ID */
  private Integer rid;
  /** 创建人 */
  private String createUser;
  /** 创建时间 */
  private Date createDate;
  /** 更新人 */
  private String updateUser;
  /** 更新时间 */
  private Date updateDate;
  /** 菜单（权限） */
  private SysPermission permission;
  /** 角色 */
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
  @Column(name = "mid")
  public Integer getMid() {
    return mid;
  }

  public void setMid(Integer mid) {
    this.mid = mid;
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
    SysPermissionRole that = (SysPermissionRole) o;
    return id == that.id
        && Objects.equals(mid, that.mid)
        && Objects.equals(rid, that.rid)
        && Objects.equals(createUser, that.createUser)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(updateUser, that.updateUser)
        && Objects.equals(updateDate, that.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, mid, rid, createUser, createDate, updateUser, updateDate);
  }

  @ManyToOne
  @JoinColumn(name = "mid", referencedColumnName = "id", updatable = false, insertable = false)
  public SysPermission getPermission() {
    return permission;
  }

  public void setPermission(SysPermission permission) {
    this.permission = permission;
  }

  @ManyToOne
  @JoinColumn(name = "rid", referencedColumnName = "id", updatable = false, insertable = false)
  public SysRole getRole() {
    return role;
  }

  public void setRole(SysRole role) {
    this.role = role;
  }

}
