package com.automation.framework.utils;

import com.automation.framework.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ScreenshotUtils {

  private ScreenshotUtils() {}

  public static String screenshotBase64() {
    try {
      return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    } catch (Exception e) {
      return null;
    }
  }
}
