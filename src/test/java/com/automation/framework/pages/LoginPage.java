package com.automation.framework.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

  private final By username = By.id("username");
  private final By password = By.id("password");
  private final By loginBtn = By.cssSelector("button[type='submit']");
  private final By flash = By.id("flash");

  public LoginPage enterUsername(String value) {
    type(username, value);
    return this;
  }

  public LoginPage enterPassword(String value) {
    type(password, value);
    return this;
  }

  public LoginPage clickLogin() {
    click(loginBtn);
    return this;
  }

  public String getFlashMessage() {
    return text(flash);
  }
}
