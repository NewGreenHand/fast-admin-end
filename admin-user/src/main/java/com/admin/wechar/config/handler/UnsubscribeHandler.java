package com.admin.wechar.config.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 取消关注处理器
 *
 * @author fei
 * @date 2018/10/11
 */
public class UnsubscribeHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMpXmlMessage,
      Map<String, Object> map,
      WxMpService wxMpService,
      WxSessionManager wxSessionManager)
      throws WxErrorException {

    String openId = wxMpXmlMessage.getFromUser();
    this.logger.info("取消关注用户 OPENID: " + openId);
    // TODO 可以更新本地数据库为取消关注状态
    return null;
  }
}
