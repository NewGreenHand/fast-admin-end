package com.admin.user.config.security;

import com.admin.user.entity.SysPermission;
import com.admin.user.entity.SysPermissionRole;
import com.admin.user.entity.SysRole;
import com.admin.user.service.PermissionRoleService;
import com.admin.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
  private final RoleService roleService;
  private final PermissionRoleService permissionRoleService;

  @Value("${server.servlet.context-path}")
  private String serverName;

  @Autowired
  public CustomMetadataSource(RoleService roleService, PermissionRoleService permissionRoleService) {
    this.roleService = roleService;
    this.permissionRoleService = permissionRoleService;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    HttpServletRequest request = ((FilterInvocation) o).getRequest();
    // 获取请求路径
    String requestUri = request.getRequestURI();
    // 获取请求方式
    String requestMethod = request.getMethod();

    // 获取所有有效的角色
    List<SysRole> roles = roleService.findAllValid();
    List<String> roleNames = new LinkedList<>();
    role: for (SysRole role : roles) {
      // 获取该角色所拥有的权限
      List<SysPermission> permissions = permissionRoleService.findAllByRoleId(role.getId());
      // 判断当前角色是否拥有当前路径的访问权限
      for (SysPermission permission : permissions) {
        // 组装全路径(配置权限的时候就不用写死项目名了)
        String fullPath = serverName.concat(permission.getPath());
        if (antPathMatcher.match(fullPath, requestUri)
            && requestMethod.equalsIgnoreCase(permission.getRequestMethod())) {

          roleNames.add(role.getName());
          continue role;
        }
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
