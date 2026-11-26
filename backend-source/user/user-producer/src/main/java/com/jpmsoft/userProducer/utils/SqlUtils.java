package com.jpmsoft.userProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;

import java.util.List;
import java.util.Locale;

/**
 * SQL工具类
 *
 * @author tianhelin
 * @date 2026-01-23
 */
public class SqlUtils {
    private SqlUtils() {
    }

    /**
     * 指定类型生成sql时对长度进行处理
     * <p>
     * varchar、char、nvarchar、varbinary类型，默认255长度；
     * decimal、dec、numeric、number类型，默认18,2长度；
     * 其他类型即使有长度也不处理。
     *
     * @param columnType
     * @param columnLength
     * @return
     */
    public static String handleColumnLength(String columnType, Integer columnLength) {
        /*
        达梦、mysql的：
        类型有数值数据类型NUMERIC、NUMBER、DECIMAL、DEC、BIT、INTEGER、INT、TINYINT、SMALLINT、BIGINT、BINARY、VARBINARY、REAL、FLOAT、DOUBLE
        字符数据类型CHAR、CHARACTER、VARCHAR
        多媒体数据类型TEXT、LONGVARCHAR、IMAGE、LONGVARBINARY、BLOB、CLOB、BFILE
        一般日期时间数据类型DATE、TIME、TIMESTAMP、DATETIME
        postgre的：
        double precision、smallserial、serial、bigserial、interval、boolean
         */
        String type = columnType.toLowerCase(Locale.ENGLISH).trim();
        switch (type) {
            case "varchar":
            case "char":
            case "nvarchar":
            case "varbinary":
                if (columnLength == null || columnLength <= 0) {
                    columnLength = 255;
                }
                return type + "(" + columnLength + ")";
            case "decimal":
            case "dec":
            case "numeric":
            case "number":
                if (columnLength == null || columnLength <= 0) {
                    columnLength = 18;
                }
                return type + "(" + columnLength + ", 2)";
            default:
                return type;
        }
    }

    /**
     * 根据主键标识和非空标识，生成 null 或 not null 的 SQL 片段
     *
     * @param columnPk   主键标识 (通常 "1" 为主键)
     * @param columnCode 字段编码 (用于特殊字段排除，如 State)
     * @param columnNull 是否非空标识 (通常 "是" 代表非空)
     * @return " not null" 或 " null"
     */
    public static String handleNullConstraint(String columnPk, String columnCode, String columnNull) {
        boolean isPk = ValidateUtil.isNotEmpty(columnPk) && "1".equals(columnPk) && !"State".equalsIgnoreCase(columnCode);
        boolean isNotNull = ValidateUtil.isNotEmpty(columnNull) && "是".equals(columnNull);

        if (isPk || isNotNull) {
            return " not null";
        } else {
            return " null";
        }
    }

    /**
     * 生成表字段备注的SQL语句
     *
     * @param dbType
     * @param table
     * @param list
     * @return
     */
    public static String generateTableAndColumnRemarks(String dbType, SysTable table, List<SysTableColumn> list) {
        if (ValidateUtil.isEmpty(dbType) || ValidateUtil.isEmpty(table) || ValidateUtil.isEmpty(list)) {
            return null;
        }
        StringBuilder remarksSql = new StringBuilder();
        String normalizedDbType = normalizeDbType(dbType);
        if ("mysql".equals(normalizedDbType)) {
            // 表备注
            if (ValidateUtil.isNotEmpty(table.getTableName())) {
                remarksSql.append(String.format("alter table %s comment = '%s';%n",
                        table.getTableCode(),
                        escapeSqlComment(table.getTableName())));
            }
            // 字段备注
            for (SysTableColumn column : list) {
                if (ValidateUtil.isNotEmpty(column.getColumnName())) {
                    remarksSql.append(String.format("alter table %s modify column %s %s comment '%s';%n",
                            table.getTableCode(),
                            column.getColumnCode(),
                            handleColumnLength(column.getColumnType(), column.getColumnLength()) +
                                    handleNullConstraint(column.getColumnPk(), column.getColumnCode(), column.getColumnNull()),
                            escapeSqlComment(column.getColumnName())));
                }
            }
            return remarksSql.toString();
        }
        // 通用sql
        // 表备注
        if (ValidateUtil.isNotEmpty(table.getTableName())) {
            remarksSql.append(String.format("comment on table %s is '%s';%n",
                    table.getTableCode(),
                    escapeSqlComment(table.getTableName())));
        }
        // 字段备注
        for (SysTableColumn column : list) {
            if (ValidateUtil.isNotEmpty(column.getColumnName())) {
                remarksSql.append(String.format("comment on column %s.%s is '%s';%n",
                        table.getTableCode(),
                        column.getColumnCode(),
                        escapeSqlComment(column.getColumnName())));
            }
        }
        return remarksSql.toString();
    }

    private static String normalizeDbType(String dbType) {
        String type = dbType.trim().toLowerCase(Locale.ENGLISH);
        if (type.contains("mysql")) {
            return "mysql";
        }
        if (type.contains("dm") || type.contains("达梦")) {
            return "dm";
        }
        return type;
    }

    private static String escapeSqlComment(String comment) {
        return comment.replace("'", "''");
    }
}
