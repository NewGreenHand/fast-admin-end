package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;
import com.admin.user.enums.LayoutsContainerEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 菜单对应实体.
 *
 * @author fei
 * @since 2019-01-02
 */
@Entity
@Table(name = "sys_menu", schema = "fastAdmin")
public class SysMenuEntity extends AbstractEntity {
  /** 菜单名称. */
  private String menuName;
  /** uri 路径. */
  private String uriPath;
  /** 文件所在路径. */
  private String component;
  /** 菜单图标. */
  private String iconCls;
  /** 是否需要缓存. */
  private Boolean keepAlive;
  /** 请求是否需要授权. */
  private Boolean requireAuth;
  /** 上级菜单ID. */
  private Long parentId;
  /** 是否启用. */
  private Boolean enabled;
  /** 布局容器. */
  private LayoutsContainerEnum container;
  /** 是否需要隐藏导航(即面包屑组件). */
  private Boolean hideHeader;
  /** 上级菜单 id 链. */
  private List<Integer> parentIdChain;
  /** 子菜单集合. */
  private List<SysMenuEntity> children;
  /** 菜单下关联权限集合. */
  private Set<SysInterfaceEntity> permissions;

  @Basic
  @Column(name = "menu_name")
  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  @Basic
  @Column(name = "uri_path")
  public String getUriPath() {
    return uriPath;
  }

  public void setUriPath(String uriPath) {
    this.uriPath = uriPath;
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
  @Column(name = "icon_cls")
  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  @Basic
  @Column(name = "keep_alive")
  public Boolean getKeepAlive() {
    return keepAlive;
  }

  public void setKeepAlive(Boolean keepAlive) {
    this.keepAlive = keepAlive;
  }

  @Basic
  @Column(name = "require_auth")
  public Boolean getRequireAuth() {
    return requireAuth;
  }

  public void setRequireAuth(Boolean requireAuth) {
    this.requireAuth = requireAuth;
  }

  @Basic
  @Column(name = "parent_id")
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
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
  @Column(name = "hide_header")
  public Boolean getHideHeader() {
    return hideHeader;
  }

  public void setHideHeader(Boolean hideHeader) {
    this.hideHeader = hideHeader;
  }

  @Basic
  @Enumerated(EnumType.STRING)
  @Column(name = "container", length = 10)
  public LayoutsContainerEnum getContainer() {
    return container;
  }

  public void setContainer(LayoutsContainerEnum container) {
    this.container = container;
  }

  @Basic
  @Type(
      type = "com.admin.core.repository.HibernateListConvert",
      parameters = {
        @org.hibernate.annotations.Parameter(name = "elementType", value = "java.lang.Integer"),
      })
  @Column(name = "parent_id_chain")
  public List<Integer> getParentIdChain() {
    return parentIdChain;
  }

  public void setParentIdChain(List<Integer> parentIdChain) {
    this.parentIdChain = parentIdChain;
  }

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "menu_id", updatable = false, insertable = false)
  public Set<SysInterfaceEntity> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<SysInterfaceEntity> permissions) {
    this.permissions = permissions;
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  public List<SysMenuEntity> getChildren() {
    return children;
  }

  public void setChildren(List<SysMenuEntity> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "SysMenu{" +
      "menuName='" + menuName + '\'' +
      ", uriPath='" + uriPath + '\'' +
      ", component='" + component + '\'' +
      ", iconCls='" + iconCls + '\'' +
      ", keepAlive=" + keepAlive +
      ", requireAuth=" + requireAuth +
      ", parentId=" + parentId +
      ", enabled=" + enabled +
      ", container=" + container +
      ", hideHeader=" + hideHeader +
      '}';
  }
}
