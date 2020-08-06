package com.admin.app.schedule.config;

import com.admin.core.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 定时任务运行类
 *
 * @author fei
 * @since 2019/12/20 16:26
 */
@Slf4j
public class SchedulingRunnable implements Runnable {
  /** Bean 的名称 */
  private String beanName;
  /** 方法的名称 */
  private String methodName;
  /** 方法参数 */
  private Object[] params;

  public SchedulingRunnable(String beanName, String methodName) {
    this(beanName, methodName, null);
  }

  public SchedulingRunnable(String beanName, String methodName, Object... params) {
    this.beanName = beanName;
    this.methodName = methodName;
    this.params = params;
  }

  @Override
  public void run() {
    log.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
    long startTime = System.currentTimeMillis();

    try {
      // 获取任务对象
      Object target = SpringContextUtils.getBean(beanName);

      // 获取具体执行的方法
      Method method;
      if (null != params && params.length > 0) {
        // 方法有参数
        Class<?>[] paramCls = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
          paramCls[i] = params[i].getClass();
        }
        method = target.getClass().getDeclaredMethod(methodName, paramCls);
      } else {
        // 没参数
        method = target.getClass().getDeclaredMethod(methodName);
      }
      // 开启方法的访问权限
      ReflectionUtils.makeAccessible(method);
      // 执行方法
      if (null != params && params.length > 0) {
        method.invoke(target, params);
      } else {
        method.invoke(target);
      }
    } catch (Exception ex) {
      log.error(
          String.format(
              "定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, Arrays.toString(params)),
          ex);
    }

    long times = System.currentTimeMillis() - startTime;
    log.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchedulingRunnable that = (SchedulingRunnable) o;
    if (params == null) {
      return beanName.equals(that.beanName)
          && methodName.equals(that.methodName)
          && that.params == null;
    }

    return beanName.equals(that.beanName)
        && methodName.equals(that.methodName)
        && Arrays.equals(params, that.params);
  }

  @Override
  public int hashCode() {
    if (params == null) {
      return Objects.hash(beanName, methodName);
    }

    return Objects.hash(beanName, methodName) + Objects.hash(params);
  }
}
