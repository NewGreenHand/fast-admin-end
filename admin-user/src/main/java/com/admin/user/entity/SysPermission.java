package com.admin.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * 权限表
 *
 * @author fei
 * @date 2018/3/1
 */
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_permission", schema = "adminModel")
public class SysPermission implements Serializable {
  private static final long serialVersionUID = 5397087209077338432L;

  private Integer id;
  /** 路径地址 */
  private String path;
  /** 组件地址 */
  private String component;
  /** 名称 */
  private String name;
  /** 图标地址 */
  private String iconCls;

  private Byte keepAlive;
  /** 请求授权 */
  private Byte requireAuth;
  /** 上级ID */
  private Integer parentId;
  /** 是否开启 */
  private Integer enabled;
  /** 创建人 */
  private String createUser;
  /** 创建时间 */
  private Date createDate;
  /** 更新人 */
  private String updateUser;
  /** 更新时间 */
  private Date updateDate;
  /** 权限类型（menu/api） */
  private String type;
  /** 请求方法(PUT/POST/DELETE) */
  private String requestMethod;

  private List<SysPermission> children;

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
  @Column(name = "path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @Basic
  @Column(name = "component")
  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
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
  @Column(name = "icon_cls")
  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  @Basic
  @Column(name = "keep_alive")
  public Byte getKeepAlive() {
    return keepAlive;
  }

  public void setKeepAlive(Byte keepAlive) {
    this.keepAlive = keepAlive;
  }

  @Basic
  @Column(name = "require_auth")
  public Byte getRequireAuth() {
    return requireAuth;
  }

  public void setRequireAuth(Byte requireAuth) {
    this.requireAuth = requireAuth;
  }

  @JsonIgnore
  @Basic
  @Column(name = "parent_id")
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
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
  @Column(name = "type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Basic
  @Column(name = "request_method")
  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysPermission sysMenu = (SysPermission) o;
    return Objects.equals(id, sysMenu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @Transient
  public List<SysPermission> getChildren() {
    return children;
  }

  public void setChildren(List<SysPermission> children) {
    this.children = children;
  }
}
