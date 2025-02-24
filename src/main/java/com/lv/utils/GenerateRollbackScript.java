package com.lv.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: GenerateRollbackScript
 * @author: dus
 * @description: 根据建表语句文件生成回滚脚本
 * @date: 2025/1/13 10:54
 * @version: 1.0
 */
public class GenerateRollbackScript {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\gxjh2\\Desktop\\香港初始化脚本\\建表\\已建\\香港融合客服系统-统一知识库-生产-rhkngkm-20250113-9-9-CREATE-sql.txt"; // 替换为你的输入文件路径
        String outputFilePath = "C:\\Users\\gxjh2\\Desktop\\rollback_script.sql"; // 替换为你的输出文件路径

        try {
            generateRollbackScript(inputFilePath, outputFilePath);
            System.out.println("Rollback script has been generated at: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateRollbackScript(String inputFilePath, String outputFilePath) throws IOException {
        StringBuilder sqlContentBuilder = new StringBuilder();
        Files.lines(Paths.get(inputFilePath)).forEach(line -> sqlContentBuilder.append(line).append("\n"));

        String sqlContent = sqlContentBuilder.toString();
        Pattern createTablePattern = Pattern.compile("(?i)create\\s+table\\s+`(\\w+)`", Pattern.CASE_INSENSITIVE);

        StringBuilder rollbackScript = new StringBuilder();

        Matcher matcher = createTablePattern.matcher(sqlContent);
        while (matcher.find()) {
            String tableName = matcher.group(1);
            rollbackScript.append("DROP TABLE `").append(tableName).append("`;\n");
        }

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(rollbackScript.toString());
        }
    }

}
