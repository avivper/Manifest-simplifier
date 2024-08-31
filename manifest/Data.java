package com.manifest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Data {
    // public final String Xlsx = "xlsx.xlsx";
    private static final int ColumnA = 0;

    public static List<String> Categories(String path) {
        try (
                InputStream file = new FileInputStream(path);
                Workbook workbook = new XSSFWorkbook(file);
                )
        {

            Sheet sheet = workbook.getSheetAt(0);
            List<String> categories = new ArrayList<>();
            for (Row row : sheet) {
                Cell cell = row.getCell(ColumnA);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String category = cell.getStringCellValue();
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
            return categories;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
