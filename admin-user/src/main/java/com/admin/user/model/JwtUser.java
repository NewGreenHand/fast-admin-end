package com.admin.user.model;

import com.admin.user.entity.SysDepartment;
import com.admin.user.entity.SysRole;
import com.admin.user.enums.EnabledEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * 用来生成token 和 验证安全的 用户类
 *
 * @author fei
 * @date 2018/10/22
 */
@Data
@Builder
public class JwtUser implements UserDetails, Serializable {
  private static final long serialVersionUID = 1138529140227318349L;

  /** 用户ID */
  private Long id;
  /** 用户名 */
  private String userName;
  /** 密码 */
  @JsonIgnore
  private String password;
  /** 邮箱 */
  private String email;
  /** 状态 */
  private Integer enabled;
  /** 手机号码 */
  private Long iphone;
  /** 角色集合 */
  private Set<SysRole> roles;
  /** 部门ID */
  private Integer departmentId;
  /** 部门表 */
  private SysDepartment department;
  /** 部门编码 */
  private String departmentCode;
  /** 最后登陆时间 */
  private Date lastLoginTime;

  /**
   * 用来验证当前登陆用户的角色信息
   *
   * @return 角色名列表
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (SysRole role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }

  /**
   * 登陆密码(加密规则写在配置文件里)
   *
   * @return 密码
   */
  @JsonIgnore
  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * 当前登陆用户
   *
   * @return 用户名
   */
  @Override
  public String getUsername() {
    return this.userName;
  }

  /**
   * 账户是否未过期
   *
   * @return true: 正常， false: 过期
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * 账户是否未锁定
   *
   * @return true: 正常， false: 锁定
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * 密码是否未过期
   *
   * @return true: 正常， false: 过期
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * 账户是否激活
   *
   * @return true: 正常， false: 禁用
   */
  @Override
  public boolean isEnabled() {
    return EnabledEnum.ON.value().equals(enabled);
  }
}
