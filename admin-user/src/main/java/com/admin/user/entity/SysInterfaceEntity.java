package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 权限表.
 *
 * @author fei
 * @date 2018/3/1
 */
@Entity
@Table(name = "sys_interface", schema = "fastAdmin")
public class SysInterfaceEntity extends AbstractEntity implements Serializable {
  private static final long serialVersionUID = 5397087209077338432L;
  /** 名称. */
  private String name;
  /** 路径地址. */
  private String path;
  /** 是否开启. */
  private Boolean enabled;
  /** 请求方法(PUT/POST/DELETE). */
  private String requestMethod;
  /** 菜单ID. */
  private Long menuId;

  @Basic
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  @Column(name = "enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Basic
  @Column(name = "request_method")
  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  @Basic
  @Column(name = "menu_id")
  public Long getMenuId() {
    return menuId;
  }

  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) {  return false; }
    if (!super.equals(o)) { return false; }
    SysInterfaceEntity that = (SysInterfaceEntity) o;
    return Objects.equals(name, that.name) &&
      Objects.equals(path, that.path) &&
      Objects.equals(enabled, that.enabled) &&
      Objects.equals(requestMethod, that.requestMethod) &&
      Objects.equals(menuId, that.menuId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, path, enabled, requestMethod, menuId);
  }

  @Override
  public String toString() {
    return "SysInterface{" +
      "name='" + name + '\'' +
      ", path='" + path + '\'' +
      ", enabled=" + enabled +
      ", requestMethod='" + requestMethod + '\'' +
      ", menuId=" + menuId +
      '}';
  }
}
