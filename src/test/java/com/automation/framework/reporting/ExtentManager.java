package com.automation.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {

  private static ExtentReports extent;

  private ExtentManager() {}

  public static synchronized ExtentReports getExtent() {
    if (extent == null) {
      extent = new ExtentReports();

      Path outDir = Path.of("target", "extent");
      try {
        Files.createDirectories(outDir);
      } catch (Exception ignored) {
        // ignore
      }

      ExtentSparkReporter spark = new ExtentSparkReporter(outDir.resolve("extent-report.html").toString());
      spark.config().setTheme(Theme.STANDARD);
      spark.config().setDocumentTitle("Selenium Automation Report");
      spark.config().setReportName("Test Execution");

      extent.attachReporter(spark);
      extent.setSystemInfo("Framework", "Selenium POM + TestNG");
    }
    return extent;
  }

  public static synchronized void flush() {
    if (extent != null) {
      extent.flush();
    }
  }
}
