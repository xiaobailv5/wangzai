package com.lv;

import com.lv.utils.SequenceUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: ExcelToSQL
 * @author: dus
 * @description:
 * @date: 2025/1/9 17:57
 * @version: 1.0
 */
public class ExcelToSQL {


    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\gxjh2\\Desktop\\国际化英文元素.xlsx"; // Excel 文件路径
        String outputSqlFilePath = "C:\\Users\\gxjh2\\Desktop\\sql123.sql"; // SQL 脚本输出路径
        String tableName = "t_km_language_entry_cfg"; // 数据库表名
        Set<Long> uniqueIds = new HashSet<>(); // 存储已生成的唯一 ID

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputSqlFilePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // 假设数据在第一个工作表

            StringBuilder sqlBuilder = new StringBuilder();

            // 遍历每一行数据
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // 跳过表头（假设第一行为表头）
                if (row.getRowNum() == 0) {
                    continue;
                }

                // 获取 B 列和 C 列数据
                Cell sourceWordCell = row.getCell(0); // A 列
                Cell targetWordCell = row.getCell(1); // B 列

                String sourceWord = sourceWordCell != null ? sourceWordCell.toString().trim() : "";
                String targetWord = targetWordCell != null ? targetWordCell.toString().trim() : "";

                // 生成唯一的 13 位 ID
                long id = generateUniqueId(uniqueIds);

                // 构建 SQL 语句
                sqlBuilder.append("INSERT INTO `").append(tableName)
                        .append("` (`ID`, `SOURCE_WORD`, `TARGET_WORD`, `LANGUAGE_TYPE`, `EFFECT_SCOPE`, `WORD_STATE`, `CRT_TIME`, `MODF_TIME`) VALUES (")
                        .append(id).append(", '")
                        .append(sourceWord).append("', '")
                        .append(targetWord).append("', '3', 'all', '1', NOW(), NOW());\n");
            }

            // 将生成的 SQL 写入到文件中
            writer.write(sqlBuilder.toString());
            System.out.println("SQL 脚本已成功生成并保存到文件：" + outputSqlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成唯一的 13 位 ID。
     * @param uniqueIds 存储已生成的唯一 ID 集合
     * @return 唯一的 13 位 ID
     */
    private static long generateUniqueId(Set<Long> uniqueIds) {
        long id;
        do {
            try {
                id = SequenceUtils.getLogicSeq("t_km_language_entry_cfg"); // 基于当前时间戳生成
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (uniqueIds.contains(id));
        uniqueIds.add(id); // 添加到集合中以确保唯一性
        return id;
    }




}
