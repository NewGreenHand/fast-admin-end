package com.admin.auth.controller;

import com.admin.auth.service.OnlineUserService;
import com.admin.core.annotations.JsonParam;
import com.admin.user.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 在线用户 控制器
 * @author fei
 * @since 2019/12/25 14:35
 */
@Validated
@RestController
@RequestMapping("sys/online")
public class OnlineUserController {
  @Autowired private OnlineUserService onlineUserService;

  /**
   * 获取在线用户
   * @param pageable 分页参数
   * @return 用户集合
   */
  @GetMapping("index")
  public Page<SysUserEntity> onlineUser(@PageableDefault Pageable pageable) {
    return onlineUserService.findAllOnlineUser(pageable);
  }

  /**
   * 强制下线
   * @param userId 用户ID
   */
  @PutMapping("offline")
  public void offline(@JsonParam Long userId) {

  }
}

