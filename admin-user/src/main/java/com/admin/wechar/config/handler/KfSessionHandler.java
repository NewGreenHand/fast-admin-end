package com.admin.wechar.config.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 客服会话处理器
 *
 * @author fei
 * @date 2018/10/11
 */
public class KfSessionHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMpXmlMessage,
      Map<String, Object> map,
      WxMpService wxMpService,
      WxSessionManager wxSessionManager)
      throws WxErrorException {
    // TODO 对会话做处理
    return null;
  }
}
