package com.lv.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: FilterSqlByExcel
 * @author: dus
 * @description: 按照excle表格里的表名  删除sql文件里在excle表里不存在的建表语句
 * @date: 2025/1/10 17:27
 * @version: 1.0
 */
public class FilterSqlByExcel {

    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\gxjh2\\Desktop\\香港初始化脚本\\第二批\\香港\\建表\\剩余表.xlsx"; // 替换为你的Excel文件路径
        String sqlFilePath = "C:\\Users\\gxjh2\\Desktop\\香港初始化脚本\\第二批\\香港\\建表\\2501101708300113218.sql"; // 替换为你的SQL文件路径
        String outputFilePath = "C:\\Users\\gxjh2\\Desktop\\file.sql"; // 输出文件路径

        Set<String> validTableNames = readTableNamesFromExcel(excelFilePath);
        if (validTableNames == null || validTableNames.isEmpty()) {
            System.out.println("No table names found in Excel.");
            return;
        }

        try {
            filterAndWriteSqlStatements(sqlFilePath, validTableNames, outputFilePath);
            System.out.println("Filtered SQL statements have been written to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> readTableNamesFromExcel(String filePath) {
        Set<String> tableNames = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // 假设表名位于第一列
                if (cell != null) {
                    tableNames.add(cell.getStringCellValue().trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    private static void filterAndWriteSqlStatements(String sqlFilePath, Set<String> validTableNames, String outputFilePath) throws IOException {
        Pattern createTablePattern = Pattern.compile("(?i)create\\s+table\\s+`(\\w+)`", Pattern.CASE_INSENSITIVE);

        StringBuilder sqlContentBuilder = new StringBuilder();
        Files.lines(Paths.get(sqlFilePath)).forEach(line -> sqlContentBuilder.append(line).append("\n"));

        String sqlContent = sqlContentBuilder.toString();
        String[] lines = sqlContent.split("\n");

        StringBuilder currentStatement = new StringBuilder();
        boolean insideCreateTable = false;

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (String line : lines) {
                if (!insideCreateTable && line.trim().toLowerCase().startsWith("create table")) {
                    insideCreateTable = true;
                }

                currentStatement.append(line).append("\n");

                if (line.trim().endsWith(";")) {
                    Matcher matcher = createTablePattern.matcher(currentStatement.toString());
                    if (matcher.find()) {
                        String tableName = matcher.group(1);
                        if (validTableNames.contains(tableName)) {
                            writer.write(currentStatement.toString());
                        }
                    }
                    currentStatement.setLength(0);
                    insideCreateTable = false;
                }
            }
        }
    }

}
