package com.admin.user.dto;

import com.admin.user.entity.SysUser;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author fei
 * @date 2017/9/19
 */
@Data
@Accessors(chain = true)
public class UserDto {
  /** 用户ID */
  private Long id;
  /** 邮箱地址 */
  private String email;
  /** 用户状态，1启用，0禁用 */
  private Integer flag;
  /** 手机号码 */
  @NotNull private Long iphone;
  /** 用户昵称 */
  private String userName;
  /** 用户头像 */
  private String avatar;

  public UserDto convertFor(SysUser user) {

    UserDto convert = new UserDto();
    BeanUtils.copyProperties(user, convert);
    return convert;
  }
}
