package com.automation.framework.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class PlaywrightManager {

  private static final ThreadLocal<Playwright> PW = new ThreadLocal<>();
  private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
  private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
  private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

  private PlaywrightManager() {}

  static void set(Playwright playwright, Browser browser, BrowserContext context, Page page) {
    PW.set(playwright);
    BROWSER.set(browser);
    CONTEXT.set(context);
    PAGE.set(page);
  }

  public static Page page() {
    Page p = PAGE.get();
    if (p == null) {
      throw new IllegalStateException("Playwright Page is not initialized for this thread.");
    }
    return p;
  }

  static void unload() {
    PAGE.remove();
    CONTEXT.remove();
    BROWSER.remove();
    PW.remove();
  }

  static Playwright playwrightOrNull() {
    return PW.get();
  }

  static Browser browserOrNull() {
    return BROWSER.get();
  }

  static BrowserContext contextOrNull() {
    return CONTEXT.get();
  }
}

