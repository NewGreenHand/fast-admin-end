package com.admin.user.entity;

import com.admin.core.basic.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限角色部门.
 *
 * @author fei
 * @since 2018/10/14
 */
@Entity
@DynamicInsert
@Table(name = "sys_role_interface", schema = "fastAdmin")
public class SysRoleInterfaceEntity extends AbstractEntity implements Serializable {
  private static final long serialVersionUID = -1707634470264551472L;
  /** 菜单（权限）表ID */
  private Long pid;
  /** 角色表ID */
  private Long rid;
  /** 菜单（权限） */
  private SysInterfaceEntity permission;
  /** 角色 */
  private SysRoleEntity role;

  @Basic
  @Column(name = "pid")
  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  @Basic
  @Column(name = "rid")
  public Long getRid() {
    return rid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  @ManyToOne
  @JoinColumn(name = "pid", referencedColumnName = "id", updatable = false, insertable = false)
  public SysInterfaceEntity getPermission() {
    return permission;
  }

  public void setPermission(SysInterfaceEntity permission) {
    this.permission = permission;
  }

  @ManyToOne
  @JoinColumn(name = "rid", referencedColumnName = "id", updatable = false, insertable = false)
  public SysRoleEntity getRole() {
    return role;
  }

  public void setRole(SysRoleEntity role) {
    this.role = role;
  }

}
