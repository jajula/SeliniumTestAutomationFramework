package com.automation.framework.pages;

import com.automation.framework.driver.DriverManager;
import com.automation.framework.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

  protected final WebDriverWait wait;

  protected BasePage() {
    int seconds = ConfigReader.getInt("explicitWaitSeconds", 10);
    this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
  }

  protected WebElement visible(By by) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  protected void type(By by, String value) {
    WebElement el = visible(by);
    el.clear();
    el.sendKeys(value);
  }

  protected void click(By by) {
    wait.until(ExpectedConditions.elementToBeClickable(by)).click();
  }

  protected String text(By by) {
    return visible(by).getText();
  }
}
