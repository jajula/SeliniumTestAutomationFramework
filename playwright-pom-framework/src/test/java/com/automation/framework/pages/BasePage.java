package com.automation.framework.pages;

import com.automation.framework.playwright.PlaywrightManager;
import com.microsoft.playwright.Page;

public abstract class BasePage {

  protected final Page page;

  protected BasePage() {
    this.page = PlaywrightManager.page();
  }
}

