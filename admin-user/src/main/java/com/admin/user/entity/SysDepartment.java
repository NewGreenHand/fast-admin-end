package com.admin.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * 部门表
 *
 * @author fei
 * @date 2018/10/13
 */
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_department", schema = "adminModel")
public class SysDepartment implements Serializable {
  private static final long serialVersionUID = 4317676256127362096L;

  private Integer id;
  /** 部门名称 */
  @NotBlank private String name;
  /** 部门编码 */
  private String code;
  /** 状态 */
  @NotNull private Integer enabled;
  /** 父级ID */
  private Integer parentId;

  private String createUser;
  private Date createDate;
  private String updateUser;
  private Date updateDate;
  /** 父级ID集合 */
  private String parentCode;
  /** 下级部门集合 */
  private Set<SysDepartment> children;

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
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  @Basic
  @Column(name = "parent_id")
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  @Basic
  @Column(name = "parent_code")
  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysDepartment that = (SysDepartment) o;
    return id == that.id
        && Objects.equals(name, that.name)
        && Objects.equals(code, that.code)
        && Objects.equals(enabled, that.enabled)
        && Objects.equals(createUser, that.createUser)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(updateUser, that.updateUser)
        && Objects.equals(updateDate, that.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, enabled, createUser, createDate, updateUser, updateDate);
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "parent_id",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  public Set<SysDepartment> getChildren() {
    return children;
  }

  public void setChildren(Set<SysDepartment> children) {
    this.children = children;
  }
}
