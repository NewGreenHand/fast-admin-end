package com.admin.wechar.config;

import com.admin.wechar.config.handler.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 *
 * @author fei
 * @date 2018/10/11
 */
@Configuration("weCharMvcConfig")
@EnableConfigurationProperties(WecharProperties.class)
public class MvcConfig {
  @Autowired
  private WecharProperties properties;

  /**
   * 微信服务对象 bean
   *
   * @return 微信服务对象
   */
  @Bean
  public WxMpService services() {

    WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
    configStorage.setAppId(properties.getAppId());
    configStorage.setSecret(properties.getSecret());
    configStorage.setToken(properties.getToken());
    configStorage.setAesKey(properties.getAesKey());

    WxMpService service = new WxMpServiceImpl();
    service.setWxMpConfigStorage(configStorage);

    return service;
  }

  /**
   * 路由 bean
   *
   * @param wxMpService 微信服务对象
   * @return 路由对象
   */
  @Bean
  public WxMpMessageRouter newRouter(WxMpService wxMpService) {
    final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
    // 记录所有事件的日志 （异步执行）
    newRouter.rule().handler(new LogHandler()).next();

    // 接收客服会话管理事件
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.EVENT)
        .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
        .handler(new KfSessionHandler())
        .end();

    // 自定义菜单事件
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.EVENT)
        .event(WxConsts.MenuButtonType.CLICK)
        .handler(new MenuHandler())
        .end();

    // 关注事件
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.EVENT)
        .event(WxConsts.EventType.SUBSCRIBE)
        .handler(new SubscribeHandler())
        .end();

    // 取消关注事件
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.EVENT)
        .event(WxConsts.EventType.UNSUBSCRIBE)
        .handler(new UnsubscribeHandler())
        .end();

    // 上报地理位置事件
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.EVENT)
        .event(WxConsts.EventType.LOCATION)
        .handler(new LocationHandler())
        .end();

    // 接收地理位置消息
    newRouter
        .rule()
        .async(false)
        .msgType(WxConsts.XmlMsgType.LOCATION)
        .handler(new LocationHandler())
        .end();

    // 默认
    newRouter.rule().async(false).handler(new MsgHandler()).end();

    return newRouter;
  }
}
