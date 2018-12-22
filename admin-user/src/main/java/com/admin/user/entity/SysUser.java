package com.admin.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * 用户表
 *
 * @author fei
 * @date 2018/2/27
 */
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_user", schema = "adminModel")
public class SysUser implements Serializable {
  private static final long serialVersionUID = -2028298888228036L;

  private Long id;
  /** 用户名 */
  @NotBlank private String userName;
  /** 密码 */
  @NotBlank private String password;
  /** 邮箱 */
  private String email;
  /** 创建人 */
  private String creator;
  /** 状态 */
  @NotNull private Integer enabled;
  /** 最后登陆时间 */
  private Date lastLoginTime;
  /** 更新人 */
  private String updateUser;
  /** 更新时间 */
  private Date updateTime;
  /** 手机号码 */
  @NotNull private Long iphone;
  /** 头像 */
  private String avatar;
  /** 部门ID */
  private Integer departmentId;
  /** 部门表 */
  private SysDepartment department;
  /** 微信用户ID */
  private String openId;

  /** 角色集合 */
  private Set<SysRole> roles;

  /** 部门编码 */
  private String departmentCode;

  @Id
  @GeneratedValue
  @Column(name = "id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "user_name")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @JsonIgnore
  @Basic
  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @CreatedBy
  @Column(name = "creator")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  @Basic
  @Column(name = "enabled")
  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer enabled) {
    this.enabled = enabled;
  }

  @Basic
  @Column(name = "last_login_time")
  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
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
  @Column(name = "update_time")
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Basic
  @Column(name = "iphone")
  public Long getIphone() {
    return iphone;
  }

  public void setIphone(Long iphone) {
    this.iphone = iphone;
  }

  @Basic
  @Column(name = "avatar")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Basic
  @Column(name = "department_id")
  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysUser sysUser = (SysUser) o;
    return Objects.equals(id, sysUser.id)
        && Objects.equals(userName, sysUser.userName)
        && Objects.equals(password, sysUser.password)
        && Objects.equals(email, sysUser.email)
        && Objects.equals(iphone, sysUser.iphone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, password, email, iphone);
  }

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
      fetch = FetchType.EAGER) // FetchType.LAZY：延迟加载 。FetchType.EAGER：急加载
  // @JoinTable 描述了多对多关系的数据表关系。name 属性指定中间表名称，joinColumns 定义中间表与当前表的外键关系
  @JoinTable(
      name = "sys_user_role",
      joinColumns = {@JoinColumn(name = "uid")},
      inverseJoinColumns = {@JoinColumn(name = "rid")})
  @org.hibernate.annotations.Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
  public Set<SysRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<SysRole> roles) {
    this.roles = roles;
  }

  @JsonIgnoreProperties(value = "children")
  @ManyToOne
  @JoinColumn(
      name = "department_id",
      referencedColumnName = "id",
      updatable = false,
      insertable = false)
  public SysDepartment getDepartment() {
    return department;
  }

  public void setDepartment(SysDepartment department) {
    this.department = department;
  }

  @Basic
  @Column(name = "department_code")
  public String getDepartmentCode() {
    return departmentCode;
  }

  public void setDepartmentCode(String departmentCode) {
    this.departmentCode = departmentCode;
  }

  @Basic
  @Column(name = "open_id")
  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }
}
