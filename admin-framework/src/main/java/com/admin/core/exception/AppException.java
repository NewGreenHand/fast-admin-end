package com.admin.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Objects;

/**
 * 自定义异常基类
 *
 * @author fei
 * @date 2017/10/2
 */
public class AppException extends RuntimeException {
  private static final long serialVersionUID = 2404372373182554123L;
  private Logger logger = LoggerFactory.getLogger(AppException.class);

  /** 错误码 */
  private int errorCode;

  /** 异常信息 */
  private String errorMessage;

  /** 格式化错误码时所需参数列表 */
  private String[] params;

  /**
   * 设置错误信息
   *
   * @param errorMessage 错误信息
   */
  public AppException(String errorMessage) {
    super(errorMessage);
    Objects.requireNonNull(errorMessage, "异常信息不能为空");

    this.errorMessage = errorMessage;
  }

  /**
   * 构造函数设置错误信息以及错误参数列表
   *
   * @param errorMessage 异常信息模版
   * @param params 错误参数列表
   */
  public AppException(String errorMessage, String... params) {
    super(String.format(Locale.CHINESE, errorMessage, (Object[]) params));

    Objects.requireNonNull(errorMessage, "异常信息不能为空");
    Objects.requireNonNull(params, "params 参数不能为空");

    this.errorMessage = String.format(Locale.CHINESE, errorMessage, (Object[]) params);
  }

  public AppException(int errorCode, String errorMessage) {
    super(errorMessage);
    Objects.requireNonNull(errorMessage);

    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  /**
   * 构造函数设置错误码、错误信息以及错误参数列表
   *
   * @param errorCode 错误码
   * @param errorMessage 异常信息模版
   * @param params 错误参数列表
   */
  public AppException(int errorCode, String errorMessage, String... params) {
    super(String.format(Locale.CHINESE, errorMessage, (Object[]) params));

    Objects.requireNonNull(errorMessage);
    Objects.requireNonNull(params);

    this.errorCode = errorCode;
    this.params = params;
    // 获取格式化后的异常消息内容
    this.errorMessage = String.format(Locale.CHINESE, errorMessage, (Object[]) params);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
