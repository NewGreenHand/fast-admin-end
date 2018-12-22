package com.admin.user.model;

import com.admin.user.entity.SysDepartment;
import com.admin.core.exception.UserNotInitException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 获取当前用户信息
 *
 * @author fei
 * @date 2018/2/28
 */
public class CurrentUser {
  private static final JwtUser JWT_USER;

  static {
    JWT_USER = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * 获取当前用户方法
   * @return 当前用户
   */
  public static JwtUser currentUser() {
    return JWT_USER;
  }

  /**
   * 获取当前登录用户的用户名
   *
   * @return 用户名
   */
  public static Optional<String> currentUserName() {
    return Optional.of(JWT_USER)
      .map(JwtUser::getUsername);
  }

  /**
   * 获取当前用户ID
   * @return 用户ID
   */
  public static Long currentUserId() {
    return Optional.of(JWT_USER)
      .map(JwtUser::getId)
      .orElseThrow(UserNotInitException::new);
  }

  /**
   * 获取当前登陆用户的公司code
   * @return 公司code
   */
  @NotNull
  public static String getCompanyCode() {
    JwtUser jwtUser = CurrentUser.currentUser();
    SysDepartment department = jwtUser.getDepartment();
    Assert.notNull(department, "你还不属于任何部门");
    String[] codeSplit = department.getCode().split("-");
    return codeSplit.length == 1 ? codeSplit[0] : codeSplit[0].concat("-").concat(codeSplit[1]);
  }

  /**
   * 获取部门编码
   * @return 部门编码
   */
  @NotNull
  public static String getDepartmentCode() {
    JwtUser jwtUser = CurrentUser.currentUser();
    SysDepartment department = jwtUser.getDepartment();
    Assert.notNull(department, "你还不属于任何部门");
    return department.getCode();
  }
}

