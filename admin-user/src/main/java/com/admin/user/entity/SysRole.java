package com.admin.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * 系统角色映射类
 * @author fei
 * @date 2017/10/3
 */
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_role", schema = "adminModel")
public class SysRole implements Serializable{
  private static final long serialVersionUID = 2230732982891238618L;

  private Integer id;
  private String name;
  private String description;
  private Integer enabled;
  private Date createDate;
  private String creator;
  private String updateUser;
  private Date updateDate;
  private Set<Integer> permissionIds;

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  @Column(name = "id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  @CreatedBy
  @Column(name = "creator")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  @Basic
  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  @Basic
  @Column(name = "enabled")
  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer useFlag) {
    this.enabled = useFlag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysRole sysRole = (SysRole) o;
    return Objects.equals(id, sysRole.id)
        && Objects.equals(name, sysRole.name)
        && Objects.equals(createDate, sysRole.createDate)
        && Objects.equals(creator, sysRole.creator)
        && Objects.equals(description, sysRole.description)
        && Objects.equals(updateUser, sysRole.updateUser)
        && Objects.equals(updateDate, sysRole.updateDate)
        && Objects.equals(enabled, sysRole.enabled);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        id, name, enabled, description, createDate, creator, updateUser, updateDate);
  }

  @JsonIgnore
  @ElementCollection
  @CollectionTable(
    name = "sys_permission_role",
    joinColumns = {@JoinColumn(name = "rid")})
  @Column(name = "mid")
  public Set<Integer> getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(Set<Integer> permissionIds) {
    this.permissionIds = permissionIds;
  }
}
