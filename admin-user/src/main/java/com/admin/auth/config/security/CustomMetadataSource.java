package com.admin.auth.config.security;

import com.admin.user.entity.SysInterfaceEntity;
import com.admin.user.entity.SysRoleInterfaceEntity;
import com.admin.user.entity.SysRoleEntity;
import com.admin.user.repository.SysRoleInterfaceRepository;
import com.admin.user.repository.SysRoleRepository;
import com.admin.user.service.SysInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * url 访问过滤器(以角色为单位)
 *
 * @author fei
 * @date 2018/10/21
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {
  private AntPathMatcher antPathMatcher = new AntPathMatcher();
  private final SysInterfaceService permissionService;
  private final SysRoleInterfaceRepository sysInterfaceRoleRepository;
  private final SysRoleRepository sysRoleRepository;

  @Value("${server.servlet.context-path}")
  private String serverName;

  @Autowired
  public CustomMetadataSource(SysInterfaceService permissionService, SysRoleInterfaceRepository sysInterfaceRoleRepository, SysRoleRepository sysRoleRepository) {
    this.permissionService = permissionService;
    this.sysInterfaceRoleRepository = sysInterfaceRoleRepository;
    this.sysRoleRepository = sysRoleRepository;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    HttpServletRequest request = ((FilterInvocation) o).getRequest();
    // 获取请求路径
    String requestUri = request.getRequestURI();
    // 获取请求方式
    String requestMethod = request.getMethod();

    // 获取所有有效的权限
    List<SysInterfaceEntity> interfaces = permissionService.findAll();
    List<String> roleNames = new LinkedList<>();
    for (SysInterfaceEntity sysInterface : interfaces) {
      // 匹配访问该路径是否需要权限(判断该路径是否有声明要访问权限)
      // 组装全路径(配置权限的时候就不用写死项目名了)
      String fullPath = serverName.concat(sysInterface.getPath());
      if (antPathMatcher.match(fullPath, requestUri)
        && requestMethod.equalsIgnoreCase(sysInterface.getRequestMethod())) {
        // 获取拥有该权限的角色
        List<SysRoleInterfaceEntity> sysInterfaceRoles =
            sysInterfaceRoleRepository.findAllByPid(sysInterface.getId());
        if (sysInterfaceRoles.isEmpty()) {
          // 谁都无法访问
          roleNames.add("ROLE_FORBIDDEN");
          break;
        }
        Long[] roleIds =
            sysInterfaceRoles
                .parallelStream()
                .map(SysRoleInterfaceEntity::getRid)
                .toArray(Long[]::new);
        List<SysRoleEntity> sysRoles = sysRoleRepository.findAllByIdIn(roleIds);
        roleNames =
            sysRoles.stream().map(SysRoleEntity::getName).collect(Collectors.toList());
        break;
      }
    }
    // 如果没有明确指出某个API 需要访问的权限，则默认需要登陆才能访问
    if (roleNames.size() == 0) {
      return SecurityConfig.createList("ROLE_LOGIN");
    }
    return SecurityConfig.createList(roleNames.toArray(new String[0]));
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return FilterInvocation.class.isAssignableFrom(aClass);
  }
}
