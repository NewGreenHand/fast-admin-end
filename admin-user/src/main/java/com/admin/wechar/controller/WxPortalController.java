package com.admin.wechar.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信接入接口
 *
 * @author fei
 * @date 2018/10/11
 */
@Slf4j
@RestController
@RequestMapping("wx/portal")
public class WxPortalController {

  @Autowired
  private WxMpService wxMpService;
  @Autowired
  private WxMpMessageRouter route;

  @GetMapping
  public void authGet(
    @RequestParam(name = "signature", required = false) String signature,
    @RequestParam(name = "timestamp", required = false) String timestamp,
    @RequestParam(name = "nonce", required = false) String nonce,
    @RequestParam(name = "echostr", required = false) String echostr,
    HttpServletResponse response) throws IOException {
    String strResult = "非法请求";
    log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
    if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
      throw new IllegalArgumentException("请求参数非法，请核实!");
    }

    if (wxMpService == null) {
      throw new IllegalArgumentException("appid的配置有毛病，请核实！");
    }

    if (wxMpService.checkSignature(timestamp, nonce, signature)) {
      strResult = echostr;
    }

    // 直接返回会有双引号, 导致验证失败
    PrintWriter out = response.getWriter();
    out.write(strResult);
    out.flush();
    out.close();
  }

  @PostMapping
  public String post(
      @RequestBody String requestBody,
      @RequestParam("signature") String signature,
      @RequestParam("timestamp") String timestamp,
      @RequestParam("nonce") String nonce,
      @RequestParam("openid") String openid,
      @RequestParam(name = "encrypt_type", required = false) String encType,
      @RequestParam(name = "msg_signature", required = false) String msgSignature) {
    log.info(
        "\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
            + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
        openid,
        signature,
        encType,
        msgSignature,
        timestamp,
        nonce,
        requestBody);

    if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
      throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
    }

    String out = null;
    if (encType == null) {
      // 明文传输的消息
      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
      WxMpXmlOutMessage outMessage = route.route(inMessage);
      if (outMessage == null) {
        return "";
      }

      out = outMessage.toXml();
    } else if ("aes".equalsIgnoreCase(encType)) {
      // aes加密的消息
      WxMpXmlMessage inMessage =
          WxMpXmlMessage.fromEncryptedXml(
              requestBody, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
      log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
      WxMpXmlOutMessage outMessage = route.route(inMessage);
      if (outMessage == null) {
        return "";
      }

      out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
    }

    log.debug("\n组装回复信息：{}", out);
    return out;
  }
}
