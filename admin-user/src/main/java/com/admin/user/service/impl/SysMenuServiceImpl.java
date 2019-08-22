package com.admin.user.service.impl;

import com.admin.core.basic.AbstractServiceImpl;
import com.admin.user.entity.SysMenuEntity;
import com.admin.core.enums.EnabledEnum;
import com.admin.user.repository.SysMenuRepository;
import com.admin.user.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表
 *
 * @author fei
 * @since 2019-01-10 23:28
 */
@Slf4j
@Service
@Validated
public class SysMenuServiceImpl extends AbstractServiceImpl<SysMenuEntity, Long>
    implements SysMenuService {
  private final SysMenuRepository sysMenuRepository;

  @Autowired
  public SysMenuServiceImpl(SysMenuRepository sysMenuRepository) {
    super(sysMenuRepository);
    this.sysMenuRepository = sysMenuRepository;
  }

  @Override
  public Page<SysMenuEntity> indexList(String menuName, Pageable pageable) {
    return this.findAllForMenu(menuName, pageable);
  }

  @Override
  public Page<SysMenuEntity> findChildrenList(String menuName, Pageable pageable) {
    return sysMenuRepository.findAll(this.findChildrenWhere(menuName), pageable);
  }

  @Override
  public List<SysMenuEntity> findChildrenList(String menuName) {
    return sysMenuRepository.findAll(this.findChildrenWhere(menuName));
  }

  /**
   * 查询子菜单的公共条件
   * @param menuName 菜单名称
   * @return 条件构造对象
   */
  private Specification<SysMenuEntity> findChildrenWhere(String menuName) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> list = new LinkedList<>();
      if (StringUtils.hasLength(menuName)) {
        list.add(criteriaBuilder.equal(root.get("menuName"), menuName));
      }

      list.add(criteriaBuilder.isNotNull(root.get("parentId")));

      return criteriaQuery.where(list.toArray(new Predicate[0])).getRestriction();
    };
  }

  @Override
  public List<SysMenuEntity> findMenuTree() {
    return this.tree(null, this.findMenu(null));
  }

  @Override
  public List<SysMenuEntity> findMenuTree(List<SysMenuEntity> sysMenus) {
    return this.tree(null, sysMenus);
  }

  @Override
  public Page<SysMenuEntity> findAllForMenu(String menuName, Pageable pageable) {
    return sysMenuRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> {
          List<Predicate> list = new LinkedList<>();
          if (StringUtils.hasLength(menuName)) {
            list.add(criteriaBuilder.equal(root.get("menuName"), menuName));
          }

          list.add(criteriaBuilder.isNull(root.get("parentId")));

          return criteriaQuery.where(list.toArray(new Predicate[0])).getRestriction();
        },
        pageable);
  }

  private List<SysMenuEntity> findMenu(@NotEmpty List<Integer> containId) {
    return sysMenuRepository.findAll(
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

  private List<SysMenuEntity> tree(Long parentId, List<SysMenuEntity> menus) {
    List<SysMenuEntity> current =
        menus.stream()
            .parallel()
            .filter(v -> Objects.equals(parentId, v.getParentId()))
            .distinct()
            .collect(Collectors.toList());
    for (SysMenuEntity menu : current) {
      List<SysMenuEntity> childrenMenu = this.tree(menu.getId(), menus);
      menu.setChildren(childrenMenu);
    }

    return current;
  }
}
