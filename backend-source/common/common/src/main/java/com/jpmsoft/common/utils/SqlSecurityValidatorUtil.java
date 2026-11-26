package com.jpmsoft.common.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * SQL安全校验工具类
 */
public final class SqlSecurityValidatorUtil {
    private static final String OK = "OK";

    private static final Pattern LINE_COMMENT = Pattern.compile("(?m)\\s*(--|#).*$", Pattern.CASE_INSENSITIVE);
    private static final Pattern BLOCK_COMMENT = Pattern.compile("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern HAS_SUSPICIOUS_COMMENT = Pattern.compile(
            "(?is)\\b(or|and)\\b\\s*(?:\\(?\\s*)?(?:(\\d+)\\s*=\\s*\\2|'x'\\s*=\\s*'x'|true\\b)\\s*(--|#|/\\*)"
    );

    private static final Pattern SINGLE_QUOTE = Pattern.compile("'(?:''|\\\\.|[^'\\\\])*'", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE_QUOTE = Pattern.compile("\"(?:\"\"|\\\\.|[^\"\\\\])*\"", Pattern.CASE_INSENSITIVE);
    private static final Pattern BACKTICK = Pattern.compile("`[^`]*`", Pattern.CASE_INSENSITIVE);

    private static final Pattern ALLOW_START = Pattern.compile("^(with|select)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SQL_START = Pattern.compile("(?i)\\b(with|select|insert|update|delete|merge|create|alter|drop|truncate)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern MARKDOWN_SQL_BLOCK = Pattern.compile("(?is)```(?:sql)?\\s*(.*?)\\s*```", Pattern.CASE_INSENSITIVE);

    private static final Pattern HAS_SEMICOLON = Pattern.compile(";", Pattern.CASE_INSENSITIVE);
    private static final Pattern HAS_STACKED_STATEMENT = Pattern.compile(";\\s*\\S", Pattern.CASE_INSENSITIVE);

    private static final Pattern DANGEROUS = Pattern.compile(
            "(?i)\\b(grant|revoke|merge|call|exec(?:ute)?|insert|update|delete|create|alter|drop|truncate|rename)\\b"
                    + "|(?i)\\b(replace\\s+into|load\\s+data|copy|into\\s+out(?:file|dumpfile))\\b"
                    + "|(?i)\\bset\\s+(?:global|session)?\\b"
                    + "|(?i)\\buse\\b"
                    + "|(?i)\\battach\\b|\\bvacuum\\b|\\bpragma\\b"
    );

    private static final Pattern DELETE_DATA = Pattern.compile("(?i)\\b(delete\\s+from|truncate\\s+table|drop\\s+table)\\b");
    private static final Pattern SELECT_START = Pattern.compile("(?i)^\\s*(with|select)\\b");
    private static final Pattern CREATE_TABLE_START = Pattern.compile(
            "(?i)^\\s*create\\s+(?:(?:global|local)\\s+)?(?:temporary\\s+)?table\\b"
    );
    private static final Pattern CREATE_TABLE_FROM_QUERY = Pattern.compile(
            "(?i)\\b(?:as\\s+select|like)\\b"
    );
    private static final Pattern UPDATE_START = Pattern.compile("(?i)^\\s*update\\b");
    private static final Pattern DELETE_START = Pattern.compile("(?i)^\\s*delete\\b");
    private static final Pattern DELETE_FROM_START = Pattern.compile("(?i)^\\s*delete\\s+from\\b");
    private static final Pattern INSERT_START = Pattern.compile("(?i)^\\s*insert\\b");
    private static final Pattern INSERT_INTO_START = Pattern.compile("(?i)^\\s*insert\\s+into\\b");
    private static final Pattern QUERY_IN_WRITE_STATEMENT = Pattern.compile("(?i)\\bselect\\b");
    private static final Pattern TAUTOLOGY = Pattern.compile(
            "(?i)\\bor\\b\\s*(?:\\(?\\s*)?(?:(\\d+)\\s*=\\s*\\1|'x'\\s*=\\s*'x'|true\\b)"
    );
    private static final Pattern TAUTOLOGY_WHERE_START = Pattern.compile(
            "(?i)\\bwhere\\b\\s*(?:\\(?\\s*)?(?:(\\d+)\\s*=\\s*\\1|'x'\\s*=\\s*'x'|true\\b)"
    );
    private static final Pattern INJECTION_FUNCTION = Pattern.compile(
            "(?i)\\b(sleep|benchmark|pg_sleep|waitfor\\s+delay|extractvalue|updatexml|load_file|xp_cmdshell)\\b"
    );
    private static final Pattern SYSTEM_SCHEMA = Pattern.compile(
            "(?i)\\b(information_schema|mysql|performance_schema|pg_catalog)\\."
    );

    private static final Pattern BAN_UNION = Pattern.compile("(?i)\\bunion\\b");

    private SqlSecurityValidatorUtil() {
    }

    /**
     * 校验给定的 SQL 语句是否仅包含允许的 SELECT 语句，并符合相关安全约束
     *
     * @param rawSql       原始的 SQL 语句。若为空或空字符串，则返回默认的校验通过表示。
     * @param banUnion     是否禁止使用 UNION 语句。若为 true，则检测到 UNION 即判定为非法。
     * @param hasSemicolon 是否禁止 SQL 语句中存在分号。若为 true，则检测到分号即判定为非法。
     * @return 若校验通过，则返回 "OK"；若校验不通过，则返回具体的拒绝理由信息。
     */
    public static String validateSelectOnly(String rawSql, boolean banUnion, boolean hasSemicolon) {
        if (rawSql == null || rawSql.isEmpty()) {
            return OK;
        }

        String sql = prepareSql(rawSql, true);

        if (hasSemicolon && HAS_SEMICOLON.matcher(sql).find()) {
            return "不允许多语句执行";
        }
        if (!ALLOW_START.matcher(sql).find()) {
            return "只允许WITH/SELECT语句";
        }
        if (DANGEROUS.matcher(sql).find()) {
            return "SQL包含危险关键字或子句";
        }
        if (banUnion && BAN_UNION.matcher(sql).find()) {
            return "不允许使用UNION";
        }

        return OK;
    }

    /**
     * 校验给定的 SQL 语句是否仅包含允许的 SELECT 语句，并符合相关安全约束
     *
     * @param rawSql 原始的 SQL 语句。若为空或空字符串，则返回默认的校验通过表示。
     * @return 若校验通过，则返回 "OK"；若校验不通过，则返回具体的拒绝理由信息。
     */
    public static String validateSelectOnly(String rawSql) {
        return validateSelectOnly(rawSql, false, false);
    }

    /**
     * 校验给定的 SQL 语句是否符合混合安全性要求（读写混合验证）。
     * 校验内容包括：
     * 1. 仅允许 SELECT/WITH、CREATE TABLE、INSERT INTO、UPDATE、DELETE FROM 语句；
     * 2. CREATE 仅允许结构化建表，禁止 CREATE AS SELECT、CREATE TABLE LIKE 等复制建表方式；
     * 3. INSERT 仅允许直接写入值，禁止 INSERT SELECT 等从查询结果批量写入的方式；
     * 4. UPDATE/DELETE 必须包含顶层 WHERE 条件，禁止全表修改或删除；
     * 5. 常见的 SQL 注入特征：堆叠语句、注释截断、永真式、延时/错误/系统函数及系统表使用。
     * <p>
     * 支持选择是否允许多语句 SQL。当允许时，多语句会以分号分隔并分别进行校验。
     *
     * @param rawSql 原始的 SQL 语句。若为 null 或空字符串，则返回默认的校验通过结果。
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由的信息。
     */
    public static String validateMixedSecurity(String rawSql) {
        return validateMixedSecurity(rawSql, false);
    }

    /**
     * 校验给定的 SQL 语句是否符合混合安全性要求（读写混合验证）
     * 校验内容包括：
     * 1. 是否存在多语句堆叠的风险；
     * 2. 是否存在 SQL 注释截断的风险；
     * 3. 每段 SQL 是否属于允许的查询、建表、插入、更新或删除语句，并满足对应的安全约束。
     * <p>
     * 当允许多语句时，会以分号（;）分隔 SQL 并分别进行校验；
     * 当不允许多语句时，若检测到多语句堆叠，则直接返回具体的拒绝理由信息。
     *
     * @param rawSql              原始的 SQL 语句。若为 null 或空字符串，则返回默认的校验通过结果。
     * @param allowMultiStatement 是否允许多语句执行。true 表示允许，false 表示不允许。
     * @return 若校验通过，则返回 "OK"；若校验不通过，则返回具体的拒绝理由信息。
     */
    public static String validateMixedSecurity(String rawSql, boolean allowMultiStatement) {
        if (rawSql == null || rawSql.isEmpty()) {
            return OK;
        }

        String sqlWithComment = prepareSql(rawSql, false);

        if (!allowMultiStatement && HAS_STACKED_STATEMENT.matcher(sqlWithComment).find()) {
            return "检测到多语句堆叠，不允许多语句执行";
        }
        if (HAS_SUSPICIOUS_COMMENT.matcher(sqlWithComment).find()) {
            return "疑似SQL注入：不允许使用SQL注释截断";
        }

        List<String> statements = splitStatements(sqlWithComment);
        for (int i = 0; i < statements.size(); i++) {
            String result = validateSingleMixedStatement(statements.get(i));
            if (!OK.equals(result)) {
                if (allowMultiStatement && statements.size() > 1) {
                    return "第" + (i + 1) + "段SQL校验失败：" + result;
                }
                return result;
            }
        }

        return OK;
    }

    /**
     * 对给定的 SQL 语句进行安全性校验，验证其是否符合混合安全性要求
     * 调用内部方法完成详细校验，包括数据修改风险和注入风险的检查
     *
     * @param rawSql 输入的原始 SQL 语句。若为空或空字符串，则返回默认的校验通过结果。
     * @return 校验通过时返回 "OK"；若校验不通过，则返回具体拒绝理由的信息。
     */
    public static String validateSqlSecurity(String rawSql) {
        return validateMixedSecurity(rawSql);
    }

    /**
     * 对给定的 SQL 语句进行安全性校验，验证其是否符合混合安全性要求
     * 调用内部方法完成详细校验，包括数据修改风险和注入风险的检查
     *
     * @param rawSql              输入的原始 SQL 语句。若为空或空字符串，则返回默认的校验通过结果。
     * @param allowMultiStatement 是否允许多语句执行。true 表示允许，false 表示不允许。
     * @return 校验通过时返回 "OK"；若校验不通过，则返回具体拒绝理由的信息。
     */
    public static String validateSqlSecurity(String rawSql, boolean allowMultiStatement) {
        return validateMixedSecurity(rawSql, allowMultiStatement);
    }

    /**
     * 检查给定的 SQL 语句是否包含数据删除风险
     *
     * @param rawSql 输入的原始 SQL 语句。
     * @return 若存在数据删除风险，则返回 true；否则返回 false。
     */
    public static boolean hasDeleteDataRisk(String rawSql) {
        if (rawSql == null || rawSql.isEmpty()) {
            return false;
        }
        List<String> statements = splitStatements(prepareSql(rawSql, false));
        for (String statement : statements) {
            if (DELETE_DATA.matcher(removeCommentsAndNormalizeSpace(statement)).find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查给定的 SQL 语句是否包含全表更新风险
     *
     * @param rawSql 输入的原始 SQL 语句。
     * @return 若存在全表更新风险，则返回 true；否则返回 false。
     */
    public static boolean hasFullUpdateRisk(String rawSql) {
        if (rawSql == null || rawSql.isEmpty()) {
            return false;
        }
        List<String> statements = splitStatements(prepareSql(rawSql, false));
        for (String statement : statements) {
            String sql = removeCommentsAndNormalizeSpace(statement);
            if (UPDATE_START.matcher(sql).find() && !hasTopLevelWhere(sql)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查给定的 SQL 语句是否包含常见 SQL 注入风险
     *
     * @param rawSql 输入的原始 SQL 语句。
     * @return 若存在常见 SQL 注入风险，则返回 true；否则返回 false。
     */
    public static boolean hasSqlInjectionRisk(String rawSql) {
        return hasSqlInjectionRisk(rawSql, false);
    }

    /**
     * 检查给定的 SQL 语句是否包含常见 SQL 注入风险
     *
     * @param rawSql              输入的原始 SQL 语句。若为空或空字符串，则返回 false。
     * @param allowMultiStatement 是否允许多语句执行。若为 true，则不会将分号分隔的 SQL 视为注入风险。
     * @return 若存在常见 SQL 注入风险，则返回 true；否则返回 false。
     */
    public static boolean hasSqlInjectionRisk(String rawSql, boolean allowMultiStatement) {
        if (rawSql == null || rawSql.isEmpty()) {
            return false;
        }
        String sqlWithComment = prepareSql(rawSql, false);
        if (!allowMultiStatement && HAS_STACKED_STATEMENT.matcher(sqlWithComment).find()) {
            return true;
        }
        if (HAS_SUSPICIOUS_COMMENT.matcher(sqlWithComment).find()) {
            return true;
        }
        for (String statement : splitStatements(sqlWithComment)) {
            String sql = removeCommentsAndNormalizeSpace(statement);
            if (hasSingleStatementInjectionRisk(sql)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对单个 SQL 语句进行安全性校验，验证其是否符合混合安全性要求。
     * 查询语句沿用只读安全规则；CREATE、INSERT、UPDATE、DELETE 分别执行语句类型及影响范围校验。
     *
     * @param statement 输入的单个 SQL 语句。若为空或空字符串，则返回默认的校验通过结果。
     * @return 校验通过时返回 "OK"；若校验不通过，则返回具体拒绝理由的信息。
     */
    private static String validateSingleMixedStatement(String statement) {
        String sql = removeCommentsAndNormalizeSpace(statement);
        if (sql.isEmpty()) {
            return OK;
        }
        if (hasSingleStatementInjectionRisk(sql)) {
            return getSingleStatementInjectionReason(sql);
        }
        if (SELECT_START.matcher(sql).find()) {
            return validateReadStatement(sql);
        }
        if (CREATE_TABLE_START.matcher(sql).find()) {
            return validateCreateTableStatement(sql);
        }
        if (INSERT_START.matcher(sql).find()) {
            return validateInsertStatement(sql);
        }
        if (UPDATE_START.matcher(sql).find()) {
            return validateUpdateStatement(sql);
        }
        if (DELETE_START.matcher(sql).find()) {
            return validateDeleteStatement(sql);
        }
        if (sql.startsWith("create")) {
            return "CREATE仅允许创建数据表";
        }
        return "仅允许SELECT/WITH、CREATE TABLE、INSERT INTO、UPDATE、DELETE FROM语句";
    }

    /**
     * 校验查询语句，禁止在查询中混入数据修改、DDL、管理命令或高风险导出子句。
     *
     * @param sql 已完成预处理的单条 SQL 语句
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由
     */
    private static String validateReadStatement(String sql) {
        if (DANGEROUS.matcher(sql).find()) {
            return "查询SQL包含危险关键字或子句";
        }
        return OK;
    }

    /**
     * 校验 CREATE TABLE 语句，仅允许直接定义表结构，禁止通过查询结果或已有表复制建表。
     *
     * @param sql 已完成预处理的单条 CREATE TABLE 语句
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由
     */
    private static String validateCreateTableStatement(String sql) {
        if (CREATE_TABLE_FROM_QUERY.matcher(sql).find() || QUERY_IN_WRITE_STATEMENT.matcher(sql).find()) {
            return "CREATE TABLE不允许通过查询结果或已有表复制建表";
        }
        return OK;
    }

    /**
     * 校验 INSERT 语句，仅允许 INSERT INTO 直接写入，禁止从查询结果批量写入。
     *
     * @param sql 已完成预处理的单条 INSERT 语句
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由
     */
    private static String validateInsertStatement(String sql) {
        if (!INSERT_INTO_START.matcher(sql).find()) {
            return "INSERT仅允许INSERT INTO语句";
        }
        if (QUERY_IN_WRITE_STATEMENT.matcher(sql).find()) {
            return "INSERT不允许使用查询结果写入";
        }
        return OK;
    }

    /**
     * 校验 UPDATE 语句，禁止缺少顶层 WHERE 条件或使用明显恒真条件的全表更新。
     *
     * @param sql 已完成预处理的单条 UPDATE 语句
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由
     */
    private static String validateUpdateStatement(String sql) {
        if (!hasTopLevelWhere(sql)) {
            return "SQL存在全量更新风险：UPDATE缺少WHERE条件";
        }
        if (TAUTOLOGY_WHERE_START.matcher(sql).find()) {
            return "SQL存在全量更新风险：UPDATE的WHERE条件疑似恒真";
        }
        return OK;
    }

    /**
     * 校验 DELETE 语句，仅允许 DELETE FROM，并禁止缺少顶层 WHERE 条件或使用明显恒真条件的全表删除。
     *
     * @param sql 已完成预处理的单条 DELETE 语句
     * @return 校验通过返回 "OK"，否则返回具体拒绝理由
     */
    private static String validateDeleteStatement(String sql) {
        if (!DELETE_FROM_START.matcher(sql).find()) {
            return "DELETE仅允许DELETE FROM语句";
        }
        if (!hasTopLevelWhere(sql)) {
            return "SQL存在全量删除风险：DELETE缺少WHERE条件";
        }
        if (TAUTOLOGY_WHERE_START.matcher(sql).find()) {
            return "SQL存在全量删除风险：DELETE的WHERE条件疑似恒真";
        }
        return OK;
    }

    /**
     * 检查单个 SQL 语句是否包含常见 SQL 注入风险
     *
     * @param sql 输入的单个 SQL 语句。若为空或空字符串，则返回 false。
     * @return 若存在常见 SQL 注入风险，则返回 true；否则返回 false。
     */
    private static boolean hasSingleStatementInjectionRisk(String sql) {
        return TAUTOLOGY.matcher(sql).find()
                || INJECTION_FUNCTION.matcher(sql).find()
                || SYSTEM_SCHEMA.matcher(sql).find();
    }

    /**
     * 获取单个 SQL 语句的常见 SQL 注入风险拒绝理由
     *
     * @param sql 输入的单个 SQL 语句。若为空或空字符串，则返回默认的拒绝理由。
     * @return 若存在常见 SQL 注入风险，则返回具体拒绝理由的信息；否则返回默认的拒绝理由。
     */
    private static String getSingleStatementInjectionReason(String sql) {
        if (TAUTOLOGY.matcher(sql).find()) {
            return "疑似SQL注入：包含恒真/恒假条件";
        }
        if (INJECTION_FUNCTION.matcher(sql).find()) {
            return "疑似SQL注入：包含高风险函数或系统命令";
        }
        if (SYSTEM_SCHEMA.matcher(sql).find()) {
            return "疑似SQL注入：不允许访问系统元数据表";
        }
        return "疑似SQL注入";
    }

    /**
     * 对单个 SQL 语句进行预处理，包括提取 SQL、规范化字符、去除字面量、移除注释或仅修剪空白等操作
     *
     * @param rawSql         输入的原始 SQL 语句。若为空或空字符串，则返回空字符串。
     * @param removeComments 是否移除注释。若为 true，则移除注释并进行规范化；否则仅修剪空白。
     * @return 处理后的 SQL 语句。若输入为空或空字符串，则返回空字符串。
     */
    private static String prepareSql(String rawSql, boolean removeComments) {
        String sql = extractSql(rawSql);
        sql = Normalizer.normalize(sql, Normalizer.Form.NFKC).toLowerCase(Locale.ENGLISH);
        sql = stripInvisibleChars(sql);
        sql = stripLiterals(sql);
        if (removeComments) {
            sql = removeCommentsAndNormalizeSpace(sql);
        } else {
            sql = sql.trim();
        }
        return sql;
    }

    /**
     * 移除 SQL 字面量，包括单引号、双引号和反引号包裹的内容
     *
     * @param s 输入的 SQL 语句。若为空或空字符串，则返回空字符串。
     * @return 处理后的 SQL 语句，其中字面量已被替换为占位符。若输入为空或空字符串，则返回空字符串。
     */
    private static String stripLiterals(String s) {
        s = SINGLE_QUOTE.matcher(s).replaceAll("'x'");
        s = DOUBLE_QUOTE.matcher(s).replaceAll("\"x\"");
        s = BACKTICK.matcher(s).replaceAll("`x`");
        return s;
    }

    /**
     * 提取原始 SQL 语句中的有效 SQL 部分，去除 Markdown 格式和 SQL 块的起始标记
     *
     * @param rawSql 输入的原始 SQL 语句。若为空或空字符串，则返回空字符串。
     * @return 处理后的 SQL 语句，其中无效部分已被去除。若输入为空或空字符串，则返回空字符串。
     */
    private static String extractSql(String rawSql) {
        String sql = stripInvisibleChars(rawSql).trim();
        java.util.regex.Matcher markdownMatcher = MARKDOWN_SQL_BLOCK.matcher(sql);
        if (markdownMatcher.find()) {
            sql = markdownMatcher.group(1).trim();
        }
        java.util.regex.Matcher startMatcher = SQL_START.matcher(sql);
        if (startMatcher.find() && startMatcher.start() > 0) {
            sql = sql.substring(startMatcher.start()).trim();
        }
        return sql;
    }

    /**
     * 移除 SQL 中的不可见字符，包括零宽空格、零宽非连接符、零宽连接符、零宽窄字符和零宽窄字符连接符
     *
     * @param value 输入的 SQL 语句。若为空或空字符串，则返回空字符串。
     * @return 处理后的 SQL 语句，其中不可见字符已被去除。若输入为空或空字符串，则返回空字符串。
     */
    private static String stripInvisibleChars(String value) {
        return value == null ? "" : value
                .replace("\uFEFF", "")
                .replace("\u200B", "")
                .replace("\u200C", "")
                .replace("\u200D", "")
                .replace("\u2060", "");
    }

    /**
     * 移除 SQL 中的注释和规范化空白字符
     *
     * @param sql 输入的 SQL 语句。若为空或空字符串，则返回空字符串。
     * @return 处理后的 SQL 语句，其中注释已被移除，空白字符已被规范化。若输入为空或空字符串，则返回空字符串。
     */
    private static String removeCommentsAndNormalizeSpace(String sql) {
        sql = LINE_COMMENT.matcher(sql).replaceAll(" ");
        sql = BLOCK_COMMENT.matcher(sql).replaceAll(" ");
        return sql.replaceAll("\\s+", " ").trim();
    }

    /**
     * 将 SQL 语句按分号分割为多个独立的语句
     *
     * @param sql 输入的 SQL 语句。若为空或空字符串，则返回空列表。
     * @return 分割后的 SQL 语句列表。若输入为空或空字符串，则返回空列表。
     */
    private static List<String> splitStatements(String sql) {
        String[] parts = sql.split(";");
        List<String> statements = new ArrayList<>();
        for (String part : parts) {
            String statement = part.trim();
            if (!statement.isEmpty()) {
                statements.add(statement);
            }
        }
        return statements;
    }

    /**
     * 判断 SQL 语句中是否包含顶级 WHERE 子句。
     * 通过逐字符跟踪括号层级，兼容 {@code WHERE(...)} 及嵌套子查询等写法。
     *
     * @param statement 输入的 SQL 语句。若为空或空字符串，则返回 false。
     * @return 若 SQL 语句中包含顶级 WHERE 子句，则返回 true；否则返回 false。
     */
    private static boolean hasTopLevelWhere(String statement) {
        int depth = 0;
        for (int i = 0; i < statement.length(); i++) {
            char ch = statement.charAt(i);
            if (ch == '(') {
                depth++;
                continue;
            }
            if (ch == ')') {
                if (depth > 0) {
                    depth--;
                }
                continue;
            }
            if (depth == 0
                    && i + 5 <= statement.length()
                    && statement.regionMatches(true, i, "where", 0, 5)
                    && (i == 0 || !isIdentifierPart(statement.charAt(i - 1)))
                    && (i + 5 == statement.length() || !isIdentifierPart(statement.charAt(i + 5)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符是否可作为未加引号 SQL 标识符的一部分。
     *
     * @param ch 待判断字符
     * @return 字母、数字、下划线或美元符号返回 true，否则返回 false
     */
    private static boolean isIdentifierPart(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '$';
    }
}
