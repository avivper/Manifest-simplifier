package com.manifest;

public class Main extends Data {
    public static final String Xlsx = "xlsx.xlsx";
    public static void main(String[] args) {
        int[] averages = countAverages(Xlsx);
        int retailCount = getRetailCount(Xlsx, "gl_musical_instruments");
        System.out.println(retailCount);
    }
}
