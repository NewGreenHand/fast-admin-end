package com.admin.user.dto;

import com.admin.core.basic.DtoConvert;
import com.admin.user.enums.LayoutsContainerEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单表单
 *
 * @author fei
 * @date 2018/7/30
 */
@Data
public class MenuDto implements DtoConvert {
  @NotBlank private String uriPath;
  private String component;
  @NotBlank private String menuName;
  private String iconCls;
  private List<Integer> parentIdChain;
  /** 布局容器. */
  private LayoutsContainerEnum container;
  /** 隐藏面包屑. */
  private Boolean hideHeader;
  /** 状态. */
  @NotNull private Boolean enabled;
}
