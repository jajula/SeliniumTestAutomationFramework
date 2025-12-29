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

  private static ExtentTest getOrCreateTest(ITestResult result) {
    ExtentTest t = TEST.get();
    if (t != null) {
      return t;
    }

    String className =
        result.getTestClass() != null ? result.getTestClass().getRealClass().getSimpleName() : "UnknownClass";
    String methodName = result.getMethod() != null ? result.getMethod().getMethodName() : "unknownMethod";
    String name = className + "." + methodName;

    t = extent.createTest(name);
    TEST.set(t);
    return t;
  }

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest t = getOrCreateTest(result);
    log.info("START: {}", t.getModel().getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ExtentTest t = getOrCreateTest(result);
    t.pass("Passed");
    log.info("PASS: {}", t.getModel().getName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    Throwable thr = result.getThrowable();
    ExtentTest t = getOrCreateTest(result);

    String base64 = null;
    try {
      base64 = ScreenshotUtils.screenshotBase64();
    } catch (Exception e) {
      log.warn("Failed to capture screenshot for failure", e);
    }

    if (base64 != null) {
      t.fail(thr,
          MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Failure Screenshot").build());
    } else {
      t.fail(thr);
    }

    log.error("FAIL: {}", t.getModel().getName(), thr);
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ExtentTest t = getOrCreateTest(result);
    t.skip(result.getThrowable());
    log.warn("SKIP: {}", t.getModel().getName());
  }

  @Override
  public void onFinish(ITestContext context) {
    ExtentManager.flush();
  }
}
