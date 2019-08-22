package com.admin.wechar.config.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 日志处理器(这东西有啥子用吗)
 *
 * @author fei
 * @date 2018/10/11
 */
public class LogHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMpXmlMessage,
      Map<String, Object> map,
      WxMpService wxMpService,
      WxSessionManager wxSessionManager)
      throws WxErrorException {
    this.logger.info("\n接收到请求消息，内容：{}", wxMpXmlMessage);
    return null;
  }
}
