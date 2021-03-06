package com.admin.auth.enums;

/**
 * redis key 前缀枚举
 *
 * @author fei
 * @since 2019/12/25 14:54
 */
public enum RedisKeyPrefixEnums {
  /** token 前缀 */
  CACHE_TOKEN("token_");
  private String value;

  RedisKeyPrefixEnums(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
