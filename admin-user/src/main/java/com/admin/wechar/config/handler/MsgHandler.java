package com.admin.wechar.config.handler;

import com.admin.wechar.config.builder.TextBuilder;
import com.admin.wechar.config.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 消息信息处理器
 *
 * @author fei
 * @date 2018/10/11
 */
public class MsgHandler extends AbstractHandler {
  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMpXmlMessage,
      Map<String, Object> map,
      WxMpService wxMpService,
      WxSessionManager wxSessionManager)
      throws WxErrorException {
    if (!wxMpXmlMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
      //TODO 可以选择将消息保存到本地
    }

    //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
    try {
      if (StringUtils.startsWithAny(wxMpXmlMessage.getContent(), "你好", "客服")
        && wxMpService.getKefuService().kfOnlineList()
        .getKfOnlineList().size() > 0) {
        return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
          .fromUser(wxMpXmlMessage.getToUser())
          .toUser(wxMpXmlMessage.getFromUser()).build();
      }
    } catch (WxErrorException e) {
      e.printStackTrace();
    }

    //TODO 组装回复消息
    String content = "收到信息内容：" + JsonUtils.toJson(wxMpXmlMessage);

    return new TextBuilder().build(content, wxMpXmlMessage, wxMpService);

  }
}
