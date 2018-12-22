package com.admin.core.config.http;

import com.admin.core.exception.AppException;

/**
 * httpClient 请求响应错误处理
 * @author fei
 * @date 2018/6/24
 */
public class HttpResponseException extends AppException {
  public HttpResponseException(String errorMessage) {
    super(errorMessage);
  }

  public HttpResponseException(String errorMessage, String... params) {
    super(errorMessage, params);
  }

  public HttpResponseException(int errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

  public HttpResponseException(int errorCode, String errorMessage, String... params) {
    super(errorCode, errorMessage, params);
  }
}
