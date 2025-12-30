package com.automation.framework.utils;

import org.testng.annotations.DataProvider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ExcelDataProvider {

  private ExcelDataProvider() {}

  private static Path excelPath() {
    String override = System.getProperty("excelPath");
    if (override != null && !override.isBlank()) {
      return Path.of(override);
    }
    return Path.of("target", "testdata", "LoginTestData.xlsx");
  }

  @DataProvider(name = "loginExcel", parallel = false)
  public static Object[][] loginExcel() {
    Path file = excelPath();
    ExcelUtils.ensureSampleLoginWorkbook(file);

    Object[][] raw = ExcelUtils.readSheet(file, "Login");
    if (raw.length == 0) {
      return raw;
    }

    List<Object[]> rows = new ArrayList<>();
    for (Object[] row : raw) {
      if (row.length < 5) {
        continue;
      }
      String run = String.valueOf(row[4]).trim();
      if ("Y".equalsIgnoreCase(run)) {
        rows.add(row);
      }
    }

    return rows.toArray(new Object[0][]);
  }
}

