package com.admin.user.service.impl;

import com.admin.core.basic.AbstractServiceImpl;
import com.admin.user.entity.SysInterfaceEntity;
import com.admin.core.enums.EnabledEnum;
import com.admin.user.repository.SysInterfaceRepository;
import com.admin.user.service.SysInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * 接口权限业务
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class SysInterfaceServiceImpl extends AbstractServiceImpl<SysInterfaceEntity, Long>
    implements SysInterfaceService {

  private final SysInterfaceRepository sysInterfaceRepository;

  @Autowired
  public SysInterfaceServiceImpl(SysInterfaceRepository sysInterfaceRepository) {
    super(sysInterfaceRepository);
    this.sysInterfaceRepository = sysInterfaceRepository;
  }

  @Override
  public List<SysInterfaceEntity> findAllByMenuIds(@NotEmpty Long[] menuIds) {
    return sysInterfaceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> criteriaQuery.where(
      root.get("menuId").in((Object[]) menuIds),
      criteriaBuilder.equal(root.get("enabled"), EnabledEnum.ON)
    ).getRestriction());
  }
}
