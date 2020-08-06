package com.admin.auth.service.impl;

import com.admin.auth.service.OnlineUserService;
import com.admin.auth.enums.RedisKeyPrefixEnums;
import com.admin.user.entity.SysUserEntity;
import com.admin.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 在线用户业务
 * @author fei
 * @since 2019/12/25 14:42
 */
@Slf4j
@Validated
@Service
public class OnlineUserServiceImpl implements OnlineUserService {
  @Autowired private StringRedisTemplate stringRedisTemplate;
  @Autowired private SysUserService sysUserService;

  @Override
  public Page<SysUserEntity> findAllOnlineUser(Pageable pageable) {
    // 获取在线用户key
    Set<String> keys = stringRedisTemplate.keys(RedisKeyPrefixEnums.CACHE_TOKEN.getValue() + "*");
    if (null == keys) {
      keys = Collections.singleton("00000000");
    }
    // 去除 key 的前缀得到 user ID
    Set<Long> ids =
        keys.parallelStream()
            .map(v1 -> v1.replace(RedisKeyPrefixEnums.CACHE_TOKEN.getValue(), ""))
            .map(Long::valueOf)
            .collect(Collectors.toSet());

    return sysUserService.findAllById(pageable, ids);
  }
}
