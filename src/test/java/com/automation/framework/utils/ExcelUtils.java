package com.automation.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExcelUtils {

  private ExcelUtils() {}

  public static Object[][] readSheet(Path file, String sheetName) {
    try (InputStream is = Files.newInputStream(file);
         Workbook wb = WorkbookFactory.create(is)) {

      Sheet sheet = wb.getSheet(sheetName);
      if (sheet == null) {
        throw new IllegalArgumentException("Sheet not found: " + sheetName);
      }

      int lastRow = sheet.getLastRowNum();
      if (lastRow < 1) {
        return new Object[0][0];
      }

      Row header = sheet.getRow(0);
      int cols = header.getLastCellNum();

      // Data rows start at row 1
      Object[][] data = new Object[lastRow][cols];
      DataFormatter fmt = new DataFormatter();

      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        for (int c = 0; c < cols; c++) {
          Cell cell = row == null ? null : row.getCell(c);
          data[r - 1][c] = cell == null ? "" : fmt.formatCellValue(cell);
        }
      }
      return data;

    } catch (Exception e) {
      throw new RuntimeException("Failed reading excel: " + file, e);
    }
  }

  public static void ensureSampleLoginWorkbook(Path file) {
    if (Files.exists(file)) {
      return;
    }

    try {
      Files.createDirectories(file.getParent());

      try (Workbook wb = new XSSFWorkbook()) {
        Sheet sheet = wb.createSheet("Login");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("testName");
        header.createCell(1).setCellValue("username");
        header.createCell(2).setCellValue("password");
        header.createCell(3).setCellValue("expectedContains");
        header.createCell(4).setCellValue("run");

        Row r1 = sheet.createRow(1);
        r1.createCell(0).setCellValue("valid_login");
        r1.createCell(1).setCellValue("tomsmith");
        r1.createCell(2).setCellValue("SuperSecretPassword!");
        r1.createCell(3).setCellValue("You logged into a secure area!");
        r1.createCell(4).setCellValue("Y");

        Row r2 = sheet.createRow(2);
        r2.createCell(0).setCellValue("invalid_login");
        r2.createCell(1).setCellValue("wrong");
        r2.createCell(2).setCellValue("wrong");
        r2.createCell(3).setCellValue("Your username is invalid!");
        r2.createCell(4).setCellValue("Y");

        try (var os = Files.newOutputStream(file)) {
          wb.write(os);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed creating sample workbook: " + file, e);
    }
  }
}
