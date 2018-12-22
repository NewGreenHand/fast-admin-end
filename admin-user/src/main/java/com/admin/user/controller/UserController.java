package com.admin.user.controller;

import com.admin.user.model.CurrentUser;
import com.admin.user.model.JwtUser;
import com.admin.user.dto.RegisterUserDto;
import com.admin.user.entity.SysUser;
import com.admin.user.enums.EnabledEnum;
import com.admin.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户接口层
 *
 * @author fei
 * @date 2017/9/19
 */
@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;
  private static final ExampleMatcher EXAMPLE_MATCHER;
  private static final String DEFAULT_PASSWORD = "123456";

  static {
    EXAMPLE_MATCHER = ExampleMatcher
      .matchingAll()
      .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains())
      .withMatcher("departmentCode", ExampleMatcher.GenericPropertyMatchers.contains())
      .withIgnorePaths("password");
  }

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 用户注册接口
   * @param dto 表单数据
   * @return token
   */
  @PostMapping
  public SysUser register(@RequestBody @Valid RegisterUserDto dto) {

    SysUser sysUser = dto.convert(SysUser.class);
    sysUser.setEnabled(dto.getEnabled().value());
    sysUser.setPassword(DEFAULT_PASSWORD);
    sysUser.setIphone(Long.valueOf(dto.getIphone()));

    return userService.save(sysUser);
  }

  /**
   * 更新用户信息
   * @param id 用户ID
   * @param dto 表单数据
   * @return 更新后的用户对象
   */
  @PutMapping("/{id}")
  public SysUser update(@NotNull @PathVariable Long id, @RequestBody RegisterUserDto dto) {
    SysUser sysUser = userService.findByPk(id);
    BeanUtils.copyProperties(dto, sysUser);
    sysUser.setEnabled(dto.getEnabled().value());

    return userService.update(sysUser);
  }

  /**
   * 修改密码
   * @param oldPassword 旧密码
   * @param newPassword 新密码
   * @return 当前用户对象
   */
  @PutMapping("/modify")
  public SysUser modify(
    @NotEmpty String oldPassword,
    @NotEmpty @Size(min = 6, max = 18)
      String newPassword) {
    Long id = CurrentUser.currentUserId();
    SysUser sysUser = userService.findByPk(id);

    // 验证旧密码
    // TODO 加密方式以修改，此方法不可用
//    oldPassword = PwdUtils.pwd(oldPassword);
//    if (!Objects.equals(oldPassword, sysUser.getPassword())) {
//      throw new AppException("原密码不正确");
//    }
//
//    sysUser.setPassword(PwdUtils.pwd(newPassword));
    return userService.update(sysUser);
  }

  /**
   * 检查用户名是否存在
   *
   * @param name 用户名
   * @return true:不存在，false 存在
   */
  @GetMapping("/checkUserName")
  public boolean checkUserName(@NotEmpty(message = "用户名不能为空") String name) {
    return userService.checkUserName(name);
  }

  /**
   * 获取用户信息
   *
   * @param id 用户ID
   * @return 用户信息
   */
  @GetMapping("{id}")
  public SysUser findById(@PathVariable Long id) {
    return userService.findByPk(id);
  }

  /**
   * 获取用户信息
   *
   * @return 用户信息
   */
  @GetMapping("/user_info")
  public SysUser findByToken() {
    JwtUser jwtUser = CurrentUser.currentUser();

    return userService.findByPk(jwtUser.getId());
  }

  /**
   * 获取所有的用户
   *
   * @return 用户集合
   */
  @GetMapping("all_valid")
  public List<SysUser> findAll() {
    SysUser sysUser = new SysUser();
    sysUser.setEnabled(EnabledEnum.ON.value());
    sysUser.setDepartmentCode(CurrentUser.getCompanyCode());

    return userService.findAll(Example.of(sysUser, EXAMPLE_MATCHER));
  }

  /**
   * 获取所有的用户信息
   * @param userName 用户名
   * @param pageable 分页参数
   * @return 用户集合
   */
  @GetMapping(value = "index", params = {"page"})
  public Page<SysUser> findAll(String userName, @PageableDefault Pageable pageable) {
    SysUser sysUser = new SysUser();
    sysUser.setUserName(userName);

    sysUser.setDepartmentCode(CurrentUser.getCompanyCode());
    return userService.findAll(Example.of(sysUser, EXAMPLE_MATCHER), pageable);
  }

}
