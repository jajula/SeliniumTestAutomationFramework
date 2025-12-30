package com.automation.framework.utils;

import com.automation.framework.playwright.PlaywrightManager;
import com.microsoft.playwright.Page;

import java.util.Base64;

public final class ScreenshotUtils {

  private ScreenshotUtils() {}

  public static String screenshotBase64() {
    try {
      Page page = PlaywrightManager.page();
      byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
      return Base64.getEncoder().encodeToString(bytes);
    } catch (Exception e) {
      return null;
    }
  }
}

