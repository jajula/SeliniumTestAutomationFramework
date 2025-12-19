package com.automation.framework.base;

import com.automation.framework.driver.DriverFactory;
import com.automation.framework.driver.DriverManager;
import com.automation.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

  protected final Logger log = LogManager.getLogger(getClass());

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    DriverFactory.initDriver();
    DriverManager.getDriver().get(ConfigReader.get("baseUrl"));
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    DriverFactory.quitDriver();
  }
}
