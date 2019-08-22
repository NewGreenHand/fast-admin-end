package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;

import javax.persistence.*;

/**
 * 角色菜单中间表
 *
 * @author fei
 * @since 2019-01-19 15:39
 */
@Entity
@Table(name = "sys_role_menu", schema = "fastAdmin")
public class SysRoleMenuEntity extends AbstractEntity {
  /** 菜单ID */
  private Long menuId;
  /** 角色ID */
  private Long roleId;
  /** 菜单. */
  private SysMenuEntity menu;

  @Basic
  @Column(name = "menu_id")
  public Long getMenuId() {
    return menuId;
  }

  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }

  @Basic
  @Column(name = "role_id")
  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  @ManyToOne
  @JoinColumn(name = "menu_id", referencedColumnName = "id", updatable = false, insertable = false)
  public SysMenuEntity getMenu() {
    return menu;
  }

  public void setMenu(SysMenuEntity menu) {
    this.menu = menu;
  }
}
