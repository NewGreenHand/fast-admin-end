package com.admin.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * @author fei
 * @since 2018-12-22
 */
@Slf4j
public class UrlTest {

  @Test
  public void test() {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    String url = "/admin/user/list?name=fei";
//    UrlUtils.buildRequestUrl()
    log.info("匹配结果: {}", antPathMatcher.matchStart("/admin/user/list", url));
  }

  @Test
  public void test2() {
    String a = "AaBb";
    String b = "aabb";

    log.info("匹配结果: {}", null instanceof String);
  }
}


