package com.manifest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Data {
    // public final String Xlsx = "xlsx.xlsx";
    private static final int ColumnA = 0;
    private static final int ColumnB = 1;
    private static final int ColumnC = 2;

    public static List<String> getCategories(String path) {
        try (
                InputStream file = new FileInputStream(path);
                Workbook workbook = new XSSFWorkbook(file);
                )
        {

            Sheet sheet = workbook.getSheetAt(0);
            List<String> categories = new ArrayList<>();
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (!categories.contains(row.getCell(ColumnA).getStringCellValue())) {
                    categories.add(row.getCell(ColumnA).getStringCellValue());
                }
            }
            return categories;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int countProducts(String path) {
        try(
                InputStream file = new FileInputStream(path);
                Workbook workbook = new XSSFWorkbook(file);
        )
        {
            Sheet sheet = workbook.getSheetAt(0);
            int count = 0;
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (!row.getCell(ColumnA).getStringCellValue().isEmpty()) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int countSingleProduct(String path, String category) {
        try (
                InputStream file = new FileInputStream(path);
                Workbook workbook = new XSSFWorkbook(file);
        )
        {
            Sheet sheet = workbook.getSheetAt(0);
            int count = 0;
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(ColumnA).getStringCellValue().equals(category)) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Calculate percentages per category

    private static double getRatio(String path, String product) {
        double countOfProduct = countSingleProduct(path, product);
        double totalProducts = countProducts(path);
        double ratio = countOfProduct / totalProducts;
        return Math.round(ratio * 100000) / 100000.0;
    }

    private static double getPercent(String path, String product) {
        double ratio = getRatio(path, product);
        double percent = ratio * 100;
        return Math.round(percent * 100000) / 100000.0;
    }

    public static double[] countPercentages(String path) {
        List<String> Categories = getCategories(path);
        double[] percentages = new double[Categories.size()];
        for (int i = 0; i < Categories.size(); i++) {
            percentages[i] = getPercent(path, Categories.get(i));
        }
        return percentages;
    }

    // todo: make average per category
    public static int getRetailCount(String path, String product) {
        int numOfProduct = countSingleProduct(path, product);

        if (numOfProduct == 0) {
            return 0;
        }

        int counter = 0;

        try (
                InputStream file = new FileInputStream(path);
                Workbook workbook = new XSSFWorkbook(file);
                )
        {
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(ColumnA).getStringCellValue().equals(product)) {
                Cell priceCell = row.getCell(ColumnC);
                if (priceCell != null && priceCell.getCellType() == CellType.STRING) {
                    String retailPrice = priceCell.getStringCellValue();
                    if (retailPrice != null && !retailPrice.isEmpty()) {
                        try {
                            int price = Integer.parseInt(retailPrice);
                            counter += price;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price format at row " + i);
                        }
                    }
                } else if (priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
                    double price = priceCell.getNumericCellValue();
                    int retailPrice = (int) price;
                    counter += retailPrice;
                }
            }
        }
        return counter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAverage(String path, String product) {
        int retailCount = getRetailCount(path, product);
        int numOfProduct = countSingleProduct(path, product);

        if (numOfProduct == 0) {
            return 0; // todo: error handling for divided by zero!
        }

        return retailCount / numOfProduct;
    }

    public static int[] countAverages(String path) {
        List<String> Categories = getCategories(path);
        int[] averages = new int[Categories.size()];
        for (int i = 0; i < Categories.size(); i++) {
            averages[i] = getAverage(path, Categories.get(i));
        }
        return averages;
    }

}
