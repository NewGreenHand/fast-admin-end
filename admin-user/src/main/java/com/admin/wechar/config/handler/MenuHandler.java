package com.admin.wechar.config.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 菜单消息处理器
 * @author fei
 * @date 2018/10/11
 */
public class MenuHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
    String msg = String.format("type:%s, event:%s, key:%s",
      wxMpXmlMessage.getMsgType(), wxMpXmlMessage.getEvent(),
      wxMpXmlMessage.getEventKey());
    if (WxConsts.MenuButtonType.VIEW.equals(wxMpXmlMessage.getEvent())) {
      return null;
    }

    return WxMpXmlOutMessage.TEXT().content(msg)
      .fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
      .build();

  }
}
