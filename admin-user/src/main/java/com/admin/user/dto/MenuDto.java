package com.admin.user.dto;

import com.admin.core.basice.DtoConvert;
import com.admin.user.enums.EnabledEnum;
import com.admin.user.enums.PermissionTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 菜单表单
 * @author fei
 * @date 2018/7/30
 */
@Data
public class MenuDto implements DtoConvert {
  @NotBlank
  private String path;
  private String component;
  @NotBlank
  private String name;
  private String iconCls;
  private Integer[] parentId;
  @NotNull
  private EnabledEnum enabled;
}
