package com.automation.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

  private static final Properties PROPS = new Properties();

  static {
    try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (is == null) {
        throw new IllegalStateException("config.properties not found on classpath");
      }
      PROPS.load(is);
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private ConfigReader() {}

  public static String get(String key) {
    String sys = System.getProperty(key);
    if (sys != null && !sys.isBlank()) {
      return sys;
    }
    String env = System.getenv(key);
    if (env != null && !env.isBlank()) {
      return env;
    }
    String value = PROPS.getProperty(key);
    if (value == null) {
      throw new IllegalArgumentException("Missing config key: " + key);
    }
    return value;
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    String v;
    try {
      v = get(key);
    } catch (Exception ignored) {
      return defaultValue;
    }
    return Boolean.parseBoolean(v.trim());
  }

  public static int getInt(String key, int defaultValue) {
    String v;
    try {
      v = get(key);
    } catch (Exception ignored) {
      return defaultValue;
    }
    return Integer.parseInt(v.trim());
  }
}

