package com.automation.framework.tests;

import com.automation.framework.base.BaseTest;
import com.automation.framework.pages.LoginPage;
import com.automation.framework.utils.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  @Test(dataProvider = "loginExcel", dataProviderClass = ExcelDataProvider.class)
  public void login_from_excel(String testName,
                              String username,
                              String password,
                              String expectedContains,
                              String run) {
    log.info("Running testCase={}", testName);

    LoginPage page = new LoginPage();
    page.enterUsername(username)
        .enterPassword(password)
        .clickLogin();

    String msg = page.getFlashMessage();
    Assert.assertTrue(msg.contains(expectedContains),
        "Expected message to contain: " + expectedContains + " but was: " + msg);
  }
}

