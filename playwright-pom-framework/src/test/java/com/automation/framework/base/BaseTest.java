package com.automation.framework.base;

import com.automation.framework.playwright.PlaywrightFactory;
import com.automation.framework.playwright.PlaywrightManager;
import com.automation.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

  protected final Logger log = LogManager.getLogger(getClass());

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    PlaywrightFactory.init();
    PlaywrightManager.page().navigate(ConfigReader.get("baseUrl"));
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    PlaywrightFactory.shutdown();
  }
}

