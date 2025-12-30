package com.automation.framework.pages;

import com.microsoft.playwright.Locator;

public class LoginPage extends BasePage {

  private final Locator username = page.locator("#username");
  private final Locator password = page.locator("#password");
  private final Locator loginButton = page.locator("button[type='submit']");
  private final Locator flash = page.locator("#flash");

  public LoginPage enterUsername(String value) {
    username.fill(value);
    return this;
  }

  public LoginPage enterPassword(String value) {
    password.fill(value);
    return this;
  }

  public LoginPage clickLogin() {
    loginButton.click();
    return this;
  }

  public String getFlashMessage() {
    return flash.innerText().trim();
  }
}

