package com.admin.core.basice;


import org.springframework.beans.BeanUtils;

/**
 * dto 转换工具
 * @author fei
 * @date 2017/10/11
 */
public interface DtoConvert {
  /**
   * dto -> entity
   *
   * @param tClass entity class
   * @param <T> entity
   * @return entity
   */
  default  <T> T convert(Class<T> tClass) {
    T t = BeanUtils.instantiateClass(tClass);
    this.pathProperties(t);
    return t;
  }

  /**
   * dto -> entity
   *
   * @param tClass entity class
   * @param <T> entity
   * @return entity
   */
  default  <T> T convert(Class<T> tClass, String... ignoreProperties) {
    T t = BeanUtils.instantiateClass(tClass);
    this.pathProperties(t, ignoreProperties);

    return t;
  }

  /**
   * dto -> entity
   *
   * @param t entity
   * @param <T> entity
   */
  default  <T> T pathProperties(T t) {
    BeanUtils.copyProperties(this, t);

    return t;
  }

  /**
   * dto -> entity
   *
   * @param t entity
   * @param <T> entity
   * @param ignoreProperties 要忽略的属性
   * @return 源对象
   */
  default  <T> T pathProperties(T t, String... ignoreProperties) {
    BeanUtils.copyProperties(this, t, ignoreProperties);

    return t;
  }
}
