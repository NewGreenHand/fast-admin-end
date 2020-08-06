package com.admin.user.controller;

import com.admin.auth.model.CurrentUser;
import com.admin.auth.config.security.JwtUser;
import com.admin.core.annotations.JsonParam;
import com.admin.core.basic.AbstractController;
import com.admin.core.exception.AppException;
import com.admin.user.dto.RegisterUserDto;
import com.admin.user.entity.SysUserEntity;
import com.admin.user.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * 用户接口层
 *
 * @author fei
 * @date 2017/9/19
 */
@RestController
@RequestMapping("users")
public class SysUserController extends AbstractController<SysUserEntity, Long> {

  private final SysUserService userService;
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
  public SysUserController(SysUserService userService) {
    super(userService);
    this.userService = userService;
  }

  /**
   * 用户注册接口
   * @param dto 表单数据
   * @return token
   */
  @PostMapping
  public SysUserEntity register(@RequestBody @Valid RegisterUserDto dto) {

    SysUserEntity sysUser = dto.convert(SysUserEntity.class);
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
  @PutMapping("{id}")
  public SysUserEntity update(@NotNull @PathVariable Long id, @RequestBody RegisterUserDto dto) {
    SysUserEntity sysUser = userService.findById(id);
    BeanUtils.copyProperties(dto, sysUser);

    return userService.update(sysUser);
  }

  /**
   * 更新用户信息 (用户自己修改)
   * @param dto 表单数据
   * @return 更新后的用户对象
   */
  @PutMapping("me")
  public SysUserEntity updateByMe(@RequestBody RegisterUserDto dto) {
    Long id = CurrentUser.currentUserId();
    SysUserEntity sysUser = userService.findById(id);
    BeanUtils.copyProperties(dto, sysUser, "enabled");

    return userService.update(sysUser);
  }

  /**
   * 修改密码
   * @param oldPassword 旧密码
   * @param newPassword 新密码
   * @return 当前用户对象
   */
  @PutMapping("modify")
  public SysUserEntity modify(
    @JsonParam @NotEmpty String oldPassword,
    @JsonParam @NotEmpty @Size(min = 6, max = 18)
      String newPassword) {
    Long id = CurrentUser.currentUserId();
    SysUserEntity sysUser = userService.findById(id);

    // 验证旧密码
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    if (!encoder.matches(oldPassword, sysUser.getPassword())) {
      throw new AppException("原密码不正确");
    }
    sysUser.setPassword(encoder.encode(newPassword));
    return userService.update(sysUser);
  }

  /**
   * 检查用户名是否存在
   *
   * @param name 用户名
   * @return true:不存在，false 存在
   */
  @GetMapping("checkUserName")
  public boolean checkUserName(@NotEmpty(message = "用户名不能为空") String name) {
    return userService.checkUserName(name);
  }

  /**
   * 获取用户信息
   *
   * @return 用户信息
   */
  @GetMapping("user_info")
  public SysUserEntity findByToken() {
    JwtUser jwtUser = CurrentUser.currentUser();

    return userService.findById(jwtUser.getId());
  }

  /**
   * 获取所有的用户
   *
   * @return 用户集合
   */
  @GetMapping("all_valid")
  public List<SysUserEntity> findAll() {
    SysUserEntity sysUser = new SysUserEntity();

    return userService.findAll(Example.of(sysUser, EXAMPLE_MATCHER));
  }

  /**
   * 获取所有的用户信息
   * @param userName 用户名
   * @param pageable 分页参数
   * @return 用户集合
   */
  @GetMapping(value = "index", params = {"page"})
  public Page<SysUserEntity> findAll(String userName, @PageableDefault Pageable pageable) {
    SysUserEntity sysUser = new SysUserEntity();
    sysUser.setUserName(userName);

    return userService.findAll(Example.of(sysUser, EXAMPLE_MATCHER), pageable);
  }

  /**
   * 获取所有的用户信息
   * @param userName 用户名
   * @return 用户集合
   */
  @GetMapping(value = "index")
  public List<SysUserEntity> index(String userName) {
    SysUserEntity sysUser = new SysUserEntity();
    sysUser.setUserName(userName);

    return userService.findAll(Example.of(sysUser, EXAMPLE_MATCHER));
  }
}
