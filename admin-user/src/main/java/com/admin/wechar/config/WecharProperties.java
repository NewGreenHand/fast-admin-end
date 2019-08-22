package com.admin.wechar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信的参数配置实体
 * @author fei
 * @date 2018/10/11
 */
@Data
@ConfigurationProperties(prefix = "wechar")
public class WecharProperties {
  /** 设置微信公众号的appid */
  private String appId;
  /** 设置微信公众号的app secret */
  private String secret;
  /** 设置微信公众号的token */
  private String token;
  /** 设置微信公众号的EncodingAESKey */
  private String aesKey;
}
