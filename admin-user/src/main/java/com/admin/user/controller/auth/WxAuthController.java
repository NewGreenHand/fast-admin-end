package com.admin.user.controller.auth;

import com.admin.user.dto.WxLoginDto;
import com.admin.user.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 微信端的授权
 * @author fei
 * @date 2018/10/25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("wx/auth")
public class WxAuthController {
  private final WxAuthService wxAuthService;

  @Autowired
  public WxAuthController(WxAuthService wxAuthService) {
    this.wxAuthService = wxAuthService;
  }

  /**
   * 获取token
   * @param openId 微信openID
   * @return token
   */
  @GetMapping("token")
  public String getToken(@NotBlank String openId) {
    return wxAuthService.getToken(openId);
  }

  /**
   * 微信登陆绑定
   * @param dto 表单
   * @return token
   */
  @PostMapping("wx_bound")
  public String wxLogin(@Valid @RequestBody WxLoginDto dto) {
    return wxAuthService.bound(dto.getUserName(), dto.getPassword(), dto.getOpenId());
  }

}

