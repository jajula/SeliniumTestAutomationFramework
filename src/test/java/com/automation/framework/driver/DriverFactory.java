package com.automation.framework.driver;

import com.automation.framework.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {

  private static final Logger log = LogManager.getLogger(DriverFactory.class);

  private DriverFactory() {}

  public static void initDriver() {
    // ConfigReader already resolves System properties / env vars and ignores blank overrides.
    String browser = ConfigReader.get("browser").trim().toLowerCase();
    boolean headless = ConfigReader.getBoolean("headless", true);

    WebDriver driver;
    switch (browser) {
      case "firefox" -> {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
          options.addArguments("-headless");
        }
        driver = new FirefoxDriver(options);
      }
      case "chrome" -> {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
          options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
      }
      default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    int implicitWait = ConfigReader.getInt("implicitWaitSeconds", 2);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
    driver.manage().window().maximize();

    DriverManager.setDriver(driver);
    log.info("Initialized WebDriver. browser={}, headless={}", browser, headless);
  }

  public static void quitDriver() {
    try {
      WebDriver driver = DriverManager.getDriver();
      driver.quit();
    } catch (Exception ignored) {
      // ignore
    } finally {
      DriverManager.unload();
    }
  }
}
