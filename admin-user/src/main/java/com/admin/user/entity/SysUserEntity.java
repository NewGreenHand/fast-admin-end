package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * 用户表.
 *
 * @author fei
 * @date 2018/2/27
 */
@Entity
@Table(name = "sys_user", schema = "fastAdmin")
public class SysUserEntity extends AbstractEntity implements Serializable {
  private static final long serialVersionUID = -2028298888228036L;
  /** 用户名. */
  @NotBlank private String userName;
  /** 密码. */
  @NotBlank private String password;
  /** 邮箱. */
  private String email;
  /** 状态. */
  @NotNull private Boolean enabled;
  /** 最后登陆时间. */
  private Date lastLoginTime;
  /** 手机号码. */
  @NotNull private Long iphone;
  /** 头像. */
  private String avatar;
  /** 微信用户ID. */
  private String openId;
  /** 角色集合. */
  private Set<SysRoleEntity> roles;

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
  @Column(name = "enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
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

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
      fetch = FetchType.EAGER) // FetchType.LAZY：延迟加载 。FetchType.EAGER：急加载
  @JoinTable(
      name = "sys_user_role",
      joinColumns = {@JoinColumn(name = "uid")},
      inverseJoinColumns = {@JoinColumn(name = "rid")})
  @org.hibernate.annotations.Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
  public Set<SysRoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<SysRoleEntity> roles) {
    this.roles = roles;
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
