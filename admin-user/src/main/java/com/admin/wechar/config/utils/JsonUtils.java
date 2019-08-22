package com.admin.wechar.config.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author fei
 * @date 2018/10/11
 */
public class JsonUtils {

  public static String toJson(Object obj) {
    Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .create();
    return gson.toJson(obj);
  }
}
