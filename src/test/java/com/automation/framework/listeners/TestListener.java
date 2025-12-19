package com.automation.framework.listeners;

import com.automation.framework.reporting.ExtentManager;
import com.automation.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  private static final Logger log = LogManager.getLogger(TestListener.class);
  private static final ExtentReports extent = ExtentManager.getExtent();

  private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

  @Override
  public void onTestStart(ITestResult result) {
    String name = result.getMethod().getMethodName();
    ExtentTest t = extent.createTest(name);
    TEST.set(t);
    log.info("START: {}", name);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    TEST.get().pass("Passed");
    log.info("PASS: {}", result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    Throwable thr = result.getThrowable();
    String base64 = ScreenshotUtils.screenshotBase64();

    if (base64 != null) {
      TEST.get().fail(thr,
          MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Failure Screenshot").build());
    } else {
      TEST.get().fail(thr);
    }

    log.error("FAIL: {}", result.getMethod().getMethodName(), thr);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    TEST.get().skip(result.getThrowable());
    log.warn("SKIP: {}", result.getMethod().getMethodName());
  }

  @Override
  public void onFinish(ITestContext context) {
    ExtentManager.flush();
  }
}
