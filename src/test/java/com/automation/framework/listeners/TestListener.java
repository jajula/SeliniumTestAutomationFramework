package com.automation.framework.listeners;

import com.automation.framework.reporting.ExtentManager;
import com.automation.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IConfigurationListener {

  private static final Logger log = LogManager.getLogger(TestListener.class);
  private static final ExtentReports extent = ExtentManager.getExtent();

  private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

  private static ExtentTest getOrCreateTest(ITestResult result) {
    ExtentTest t = TEST.get();
    if (t != null) {
      return t;
    }

    String className = result.getTestClass() != null ? result.getTestClass().getName() : "UnknownClass";
    String methodName = result.getMethod() != null ? result.getMethod().getMethodName() : "unknownMethod";
    t = extent.createTest(className + "." + methodName);
    TEST.set(t);
    return t;
  }

  private static void clearTest() {
    TEST.remove();
  }

  @Override
  public void onTestStart(ITestResult result) {
    String className = result.getTestClass() != null ? result.getTestClass().getName() : "UnknownClass";
    String methodName = result.getMethod().getMethodName();
    String name = className + "." + methodName;
    ExtentTest t = extent.createTest(name);
    TEST.set(t);
    log.info("START: {}", name);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    getOrCreateTest(result).pass("Passed");
    log.info("PASS: {}", result.getMethod().getMethodName());
    clearTest();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    Throwable thr = result.getThrowable();
    String base64 = ScreenshotUtils.screenshotBase64();
    ExtentTest t = getOrCreateTest(result);

    if (base64 != null) {
      t.fail(thr,
          MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Failure Screenshot").build());
    } else {
      t.fail(thr);
    }

    log.error("FAIL: {}", result.getMethod().getMethodName(), thr);
    clearTest();
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    getOrCreateTest(result).skip(result.getThrowable());
    log.warn("SKIP: {}", result.getMethod().getMethodName());
    clearTest();
  }

  @Override
  public void onConfigurationFailure(ITestResult result) {
    Throwable thr = result.getThrowable();
    getOrCreateTest(result).fail(thr);
    log.error("CONFIG FAIL: {}", result.getMethod().getMethodName(), thr);
    clearTest();
  }

  @Override
  public void onConfigurationSkip(ITestResult result) {
    getOrCreateTest(result).skip(result.getThrowable());
    log.warn("CONFIG SKIP: {}", result.getMethod().getMethodName());
    clearTest();
  }

  @Override
  public void onConfigurationSuccess(ITestResult result) {
    log.info("CONFIG PASS: {}", result.getMethod().getMethodName());
  }

  @Override
  public void onFinish(ITestContext context) {
    ExtentManager.flush();
  }
}
