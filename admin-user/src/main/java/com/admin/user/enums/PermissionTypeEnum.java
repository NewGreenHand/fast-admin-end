package com.admin.user.enums;

import com.admin.core.model.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 权限类型枚举
 * @author fei
 * @date 2017/9/18*/
public enum PermissionTypeEnum implements BaseEnum<String> {
  /** 菜单 */
  MENU("menu", "菜单"),
  /** 操作权限 */
  API("api", "连接网址"),
  ;

  private String value;
  private String msg;

  PermissionTypeEnum(String value, String msg) {
    this.value = value;
    this.msg = msg;
  }

  @Override
  public String value() {
    return this.value;
  }

  public String note() {
    return this.msg;
  }

  @JsonCreator
  public static PermissionTypeEnum format(String value) {
    for (PermissionTypeEnum i : values()) {
      if (i.value().equals(value)) {
        return i;
      }
    }
    throw new IllegalArgumentException("没有这个枚举类型");
  }
}
