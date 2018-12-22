package com.admin.user.service.impl;

import com.admin.core.basice.BaseServiceImpl;
import com.admin.user.entity.SysPermission;
import com.admin.user.enums.EnabledEnum;
import com.admin.user.enums.PermissionTypeEnum;
import com.admin.user.repository.PermissionRepository;
import com.admin.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单业务
 *
 * @author fei
 * @date 2017/9/19
 */
@Slf4j
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<SysPermission, Integer>
    implements PermissionService {

  private static final ExampleMatcher EXAMPLE_MATCHER;

  static {
    EXAMPLE_MATCHER =
        ExampleMatcher.matching()
            .withMatcher(
                "name",
                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
  }

  private final PermissionRepository permissionRepository;

  @Autowired
  public PermissionServiceImpl(PermissionRepository permissionRepository) {
    super(permissionRepository);
    this.permissionRepository = permissionRepository;
  }

  @Override
  public List<SysPermission> findMenuTree() {
    return this.tree(null, this.findMenu(null));
  }

  @Override
  public List<SysPermission> findPermissionTree(List<SysPermission> sysPermissions) {
    return this.tree(null, sysPermissions);
  }

  /**
   * 获取所有有效的权限集合
   *
   * @return 权限集合
   */
  private List<SysPermission> findAllByValid() {
    SysPermission sysPermission = new SysPermission();
    sysPermission.setEnabled(EnabledEnum.ON.value());

    return permissionRepository.findAll(Example.of(sysPermission));
  }

  @Override
  public Page<SysPermission> indexList(String menuName, Pageable pageable) {
    Page<SysPermission> permissionPage = this.findAllForMenu(menuName, pageable);

    for (SysPermission permission : permissionPage.getContent()) {
      List<SysPermission> children = this.findAllForApi(permission.getId());
      permission.setChildren(children);
    }

    return permissionPage;
  }

  private List<SysPermission> findAllForApi(Integer parentId) {
    SysPermission sysPermission = new SysPermission();
    sysPermission.setParentId(parentId);
    sysPermission.setType(PermissionTypeEnum.API.value());

    return permissionRepository.findAll(Example.of(sysPermission));
  }

  @Override
  public Page<SysPermission> findAllForMenu(String menuName, Pageable pageable) {
    SysPermission sysPermission = new SysPermission();
    sysPermission.setName(menuName);
    sysPermission.setType(PermissionTypeEnum.MENU.value());

    return permissionRepository.findAll(Example.of(sysPermission, EXAMPLE_MATCHER), pageable);
  }

  @Override
  public List<SysPermission> rolePermission(Integer... ids) {
    return permissionRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> {
          List<Predicate> predicates = new LinkedList<>();
          predicates.add(root.get("id").in((Object[]) ids));
          predicates.add(criteriaBuilder.equal(root.get("enabled"), EnabledEnum.ON.value()));
          predicates.add(criteriaBuilder.equal(root.get("type"), PermissionTypeEnum.API.value()));
          return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        });
  }

  private List<SysPermission> tree(Integer parentId, List<SysPermission> menus) {
    List<SysPermission> current =
        menus
            .stream()
            .parallel()
            .filter(v -> Objects.equals(parentId, v.getParentId()))
            .distinct()
            .collect(Collectors.toList());
    for (SysPermission menu : current) {
      List<SysPermission> childrenMenu = this.tree(menu.getId(), menus);
      menu.setChildren(childrenMenu);
    }

    return current;
  }

  private List<SysPermission> findMenu(List<Integer> containId) {
    return permissionRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> {
          List<Predicate> list = new LinkedList<>();
          list.add(criteriaBuilder.equal(root.get("enabled"), EnabledEnum.ON.value()));

          // 判断是否有过滤条件
          if (!ObjectUtils.isEmpty(containId)) {
            list.add(root.get("id").in(containId));
          }

          return criteriaQuery.where(list.toArray(new Predicate[0])).getRestriction();
        });
  }
}
