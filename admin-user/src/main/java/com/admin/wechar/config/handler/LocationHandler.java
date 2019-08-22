package com.admin.wechar.config.handler;

import com.admin.wechar.config.builder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 地理位置消息处理器
 *
 * @author fei
 * @date 2018/10/11
 */
public class LocationHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMpXmlMessage,
      Map<String, Object> map,
      WxMpService wxMpService,
      WxSessionManager wxSessionManager)
      throws WxErrorException {
    if (wxMpXmlMessage.getMsgType().equals(WxConsts.XmlMsgType.LOCATION)) {
      //TODO 接收处理用户发送的地理位置消息
      try {
        String content = "感谢反馈，您的的地理位置已收到！";
        return new TextBuilder().build(content, wxMpXmlMessage, null);
      } catch (Exception e) {
        this.logger.error("位置消息接收处理失败", e);
        return null;
      }
    }

    //上报地理位置事件
    this.logger.info("上报地理位置，纬度 : {}，经度 : {}，精度 : {}",
      wxMpXmlMessage.getLatitude(), wxMpXmlMessage.getLongitude(), String.valueOf(wxMpXmlMessage.getPrecision()));

    //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用

    return null;
  }
}
