package com.admin.user.enums;

import com.admin.core.enums.BaseEnum;

/**
 * 前端布局容器.
 *
 * @author fei
 * @since 2019-04-01 14:13
 */
public enum LayoutsContainerEnum implements BaseEnum<String> {
  /** 空. */
  NONE,
  /** 基本. */
  BASIC,
  /** 卡片. */
  CAR;

  @Override
  public String value() {
    return this.name();
  }
}
