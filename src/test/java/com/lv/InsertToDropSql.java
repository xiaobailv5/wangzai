package com.lv;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.expression.Expression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: Test001
 * @author: dus
 * @description: 根据数据库insert脚本文件生成回滚脚本
 * @date: 2024/12/30 8:53
 * @version: 1.0
 */
public class InsertToDropSql {


    public static void main(String[] args) {
        // 输入的 INSERT 脚本文件路径
        String inputFilePath = "D:\\个人\\2025文件\\香港初始化脚本\\第三批\\rhkngkm\\香港融合客服系统-统一知识库-生产-rhkngkm-20250115-1-1-INSERT-sql.txt";
        // 输出的 DELETE 回滚脚本文件路径
        String outputFilePath = "C:\\Users\\gxjh2\\Desktop\\rollback_script.sql";

        try {
            // 读取 INSERT 脚本文件
            List<String> insertStatements = readSqlFile(inputFilePath);

            // 生成 DELETE 回滚脚本
            List<String> deleteStatements = generateDeleteStatements(insertStatements);

            // 将生成的 DELETE 语句写入文件
            writeSqlFile(outputFilePath, deleteStatements);

            System.out.println("回滚脚本生成成功！");
        } catch (IOException | JSQLParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取 SQL 文件并返回所有 INSERT 语句
     */
    private static List<String> readSqlFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .filter(line -> line.trim().toUpperCase().startsWith("INSERT"))
                    .collect(Collectors.toList());
        }
    }

    /**
     * 生成 DELETE 回滚语句
     */
    private static List<String> generateDeleteStatements(List<String> insertStatements) throws JSQLParserException {
        return insertStatements.stream()
                .map(insertSql -> {
                    try {
                        // 解析 INSERT 语句
                        Statement statement = CCJSqlParserUtil.parse(insertSql);
                        if (statement instanceof Insert) {
                            Insert insert = (Insert) statement;
                            Table table = insert.getTable();
                            String tableName = table.getName();
                            //字段
                            List<Column> columns = insert.getColumns();

                            // 假设插入的是单行数据
                            ItemsList itemsList = insert.getItemsList();

                            List<Expression> expressions = ((ExpressionList) itemsList).getExpressions();

                            // 假设第一列是主键（可以根据实际情况调整）
                            Expression primaryKeyExpression = expressions.get(0);

                            // 构建 DELETE 语句
                            Delete delete = new Delete();
                            delete.setTable(table);
                            delete.setWhere(new EqualsTo(columns.get(0),primaryKeyExpression));

                            return delete.toString();
                        }
                    } catch (JSQLParserException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 将生成的 SQL 语句写入文件
     */
    private static void writeSqlFile(String filePath, List<String> sqlStatements) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String sql : sqlStatements) {
                writer.write(sql + ";\n");
            }
        }
    }




}
