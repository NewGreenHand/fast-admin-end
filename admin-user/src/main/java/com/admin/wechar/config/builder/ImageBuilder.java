package com.admin.wechar.config.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 图片消息构造器
 * @author fei
 * @date 2018/10/11
 */
public class ImageBuilder extends AbstractBuilder {
  @Override
  public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {

    return WxMpXmlOutMessage.IMAGE().mediaId(content)
      .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
      .build();
  }
}
