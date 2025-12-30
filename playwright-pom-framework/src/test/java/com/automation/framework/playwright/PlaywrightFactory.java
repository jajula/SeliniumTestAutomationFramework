package com.automation.framework.playwright;

import com.automation.framework.utils.ConfigReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public final class PlaywrightFactory {

  private static final Logger log = LogManager.getLogger(PlaywrightFactory.class);

  private PlaywrightFactory() {}

  public static void init() {
    String browserName = ConfigReader.get("browser").trim().toLowerCase(Locale.ROOT);
    boolean headless = ConfigReader.getBoolean("headless", true);

    Playwright playwright = Playwright.create();
    BrowserType type = switch (browserName) {
      case "chromium", "chrome" -> playwright.chromium();
      case "firefox" -> playwright.firefox();
      case "webkit" -> playwright.webkit();
      default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
    };

    BrowserType.LaunchOptions launch = new BrowserType.LaunchOptions().setHeadless(headless);
    // If user asked for chrome explicitly, try using the installed Chrome channel.
    if ("chrome".equals(browserName)) {
      launch.setChannel("chrome");
    }

    Browser browser = type.launch(launch);
    BrowserContext context = browser.newContext(new Browser.NewContextOptions());
    Page page = context.newPage();

    int explicitSeconds = ConfigReader.getInt("explicitWaitSeconds", 10);
    page.setDefaultTimeout(explicitSeconds * 1000L);

    PlaywrightManager.set(playwright, browser, context, page);
    log.info("Initialized Playwright. browser={}, headless={}", browserName, headless);
  }

  public static void shutdown() {
    try {
      Page page = null;
      BrowserContext context = null;
      Browser browser = null;
      Playwright pw = null;

      try {
        page = PlaywrightManager.page();
      } catch (Exception ignored) {
        // ignore
      }
      context = PlaywrightManager.contextOrNull();
      browser = PlaywrightManager.browserOrNull();
      pw = PlaywrightManager.playwrightOrNull();

      if (page != null) {
        page.close();
      }
      if (context != null) {
        context.close();
      }
      if (browser != null) {
        browser.close();
      }
      if (pw != null) {
        pw.close();
      }
    } catch (Exception ignored) {
      // ignore
    } finally {
      PlaywrightManager.unload();
    }
  }
}

