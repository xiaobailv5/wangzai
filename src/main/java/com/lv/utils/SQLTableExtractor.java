package com.lv.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: SQLTableExtractor
 * @author: dus
 * @description:
 * @date: 2025/1/9 17:53
 * @version: 1.0
 */
public class SQLTableExtractor {


    public static void main(String[] args) {
        // 输入和输出文件路径
        String inputFile = "C:\\Users\\black\\Desktop\\rhkngkm.sql"; // 替换为你的输入文件路径
        String outputFile = "C:\\Users\\black\\Desktop\\machengshun.sql"; // 替换为你的输出文件路径

        // 调用方法处理文件
        try {
            processSQLFile(inputFile, outputFile);
            System.out.println("SQL 文件处理完成！结果已保存到：" + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processSQLFile(String inputFile, String outputFile) throws IOException {
        // 用于存储结果的 StringBuilder
        StringBuilder result = new StringBuilder();

        // 正则表达式匹配 "CREATE TABLE" 语句
        Pattern pattern = Pattern.compile("CREATE TABLE\\s+[`'\"]?([a-zA-Z0-9_]+)[`'\"]?");


        // 读取文件
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 去掉首尾空格
                line = line.trim();
                System.out.println("读取到的行: " + line); // 打印读取的每一行内容

                // 匹配正则表达式
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    // 提取表名并生成 SQL 语句
                    String tableName = matcher.group(1); // 提取括号中的表名
                    String sql = "CREATE TABLE " + tableName + ";"; // 构造新 SQL 语句
                    result.append(sql).append(System.lineSeparator()); // 添加到结果中
                    System.out.println("匹配到: " + sql); // 调试输出
                } else {
                    System.out.println("未匹配到: " + line); // 未匹配的行
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("输入文件未找到: " + inputFile);
            e.printStackTrace();
        }

        // 将结果写入新文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(result.toString());
            System.out.println("写入完成！");
        }
    }




}
