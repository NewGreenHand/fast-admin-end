package com.admin.wechar.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 微信 OAuth2.0 授权
 * @author fei
 * @date 2018/10/24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("wx/auth2")
public class WxOauth2Controller {
  @Autowired
  private WxMpService wxMpService;

  /**
   * 通过code换取网页授权access_token，并拉取用户信息
   * @param code 授权code
   * @return 用户对象
   * @throws WxErrorException 微信请求异常
   */
  @GetMapping("wx_user_info")
  public String userInfo(@NotBlank String code) throws WxErrorException {
    // 通过code换取网页授权access_token
    WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

    // 拉取用户信息
    WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
    return wxMpUser.getOpenId();
  }
}
