package com.admin.wechar.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fei
 * @date 2018/10/11
 */
@Controller
@RequestMapping("/wx/redirect")
public class WxRedirectController {
  @Autowired
  private WxMpService wxMpService;

  @RequestMapping("/greet")
  public String greetUser(@RequestParam String code, ModelMap map) throws WxErrorException {

    WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
    WxMpUser user = wxMpService.oauth2getUserInfo(accessToken, null);
    map.put("user", user);

    return "greet_user";
  }
}
