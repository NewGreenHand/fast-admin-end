package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
  /** 菜单名称(路由名称). */
  private String name;
  /** 菜单名称. */
  private String title;
  /** 图标 */
  private String icon;
  /** uri 路径. */
  private String path;
  /** 组件名. */
  private String component;
  /** 是否需要缓存. */
  private Boolean keepAlive;
  /** 用于隐藏不需要在菜单中展示的子路由。用法可以查看 个人设置 的配置。 */
  private Boolean hideChildrenInMenu;
  /** 可以在菜单中不展示这个路由，包括子路由。效果可以查看 other 下的路由配置。 */
  private Boolean hidden;
  /** 可以强制当前页面不显示 PageHeader 组件中的页面带的 面包屑和页面标题栏 */
  private Boolean hiddenHeaderContent;
  /** 菜单重定向（目录菜单可用） */
  private String redirect;
  /** 上级菜单ID. */
  private Long parentId;
  /** 是否启用. */
  private Boolean enabled;
  /** 上级菜单 id 链. */
  private List<Integer> parentIdChain;
  /** 子菜单集合. */
  private List<SysMenuEntity> children;
  /** 菜单下关联权限集合. */
  private Set<SysInterfaceEntity> permissions;

  @Basic
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
  @Column(name = "icon")
  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
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
  @Column(name = "hide_children_in_menu")
  public Boolean getHideChildrenInMenu() {
    return hideChildrenInMenu;
  }

  public void setHideChildrenInMenu(Boolean hideChildrenInMenu) {
    this.hideChildrenInMenu = hideChildrenInMenu;
  }

  @Basic
  @Column(name = "hidden")
  public Boolean getHidden() {
    return hidden;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  @Basic
  @Column(name = "hidden_header_content")
  public Boolean getHiddenHeaderContent() {
    return hiddenHeaderContent;
  }

  public void setHiddenHeaderContent(Boolean hiddenHeaderContent) {
    this.hiddenHeaderContent = hiddenHeaderContent;
  }

  @Basic
  @Column(name = "redirect")
  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
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

  @Where(clause = "enabled = 1")
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    SysMenuEntity that = (SysMenuEntity) o;
    return Objects.equals(name, that.name) &&
      Objects.equals(title, that.title) &&
      Objects.equals(icon, that.icon) &&
      Objects.equals(path, that.path) &&
      Objects.equals(component, that.component) &&
      Objects.equals(keepAlive, that.keepAlive) &&
      Objects.equals(hideChildrenInMenu, that.hideChildrenInMenu) &&
      Objects.equals(hidden, that.hidden) &&
      Objects.equals(hiddenHeaderContent, that.hiddenHeaderContent) &&
      Objects.equals(redirect, that.redirect) &&
      Objects.equals(parentId, that.parentId) &&
      Objects.equals(enabled, that.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, title, icon, path, component, keepAlive, hideChildrenInMenu, hidden, hiddenHeaderContent, redirect, parentId, enabled);
  }
}
