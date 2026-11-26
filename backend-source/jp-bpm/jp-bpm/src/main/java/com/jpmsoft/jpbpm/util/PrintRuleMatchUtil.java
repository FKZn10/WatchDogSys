package com.jpmsoft.jpbpm.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jpmsoft.common.utils.ValidateUtil;

import java.util.*;

/**
 * 打印规则匹配工具类。
 * <p>
 * 规则 JSON 的 key 表示要读取的表单字段，value 表示期望值。字段 key 支持数据库字段名和 JavaBean 驼峰字段名，
 * 例如 {@code Sec_Id}、{@code sec_id}、{@code secId} 会按同一个字段处理。规则中的多个字段条件为 AND 关系；
 * 如果传入多条表单记录，只要任意一条记录满足全部条件即为匹配。
 * <p>
 * 普通等值规则示例：
 * <pre>
 * {"Sec_Id":"123","is_new":"1"}
 * {"secId":"123","isNew":"1"}
 * </pre>
 * 不等于规则示例：
 * <pre>
 * {"Sec_Id!=":"123"}
 * {"secId":{"!=":"123"}}
 * {"secId":{"operator":"!=","value":"123"}}
 * </pre>
 * 链式关联规则示例：
 * <pre>
 * {"Sec_Id@Sec_Info.Sec_Id.Bp_Id@Sec_Bidding_Plan.Bp_Id.is_new":"1"}
 * {"Sec_Id@Sec_Info.Sec_Id.Bp_Id@Sec_Bidding_Plan.Bp_Id.is_new!=":"1"}
 * </pre>
 */
public class PrintRuleMatchUtil {
    private static final String NOT_EQUALS = "!=";
    private static final String OPERATOR = "operator";
    private static final String VALUE = "value";

    private PrintRuleMatchUtil() {
    }

    /**
     * 链式关联记录加载器。
     * <p>
     * 当规则 key 中包含 {@code @} 时，工具类会按 {@code 表名.关联字段名.目标字段名} 的配置逐级查询关联记录。
     * 具体的数据访问由调用方实现，通常直接调用 mapper 查询关联表的第一条记录。
     * <pre>
     * RelationRecordLoader loader = (tableName, fieldName, fieldValue) -> {
     *     List&lt;Map&lt;String, Object&gt;&gt; list = mapper.getTableDataByField(tableName, fieldName, fieldValue);
     *     return ValidateUtil.isNotEmpty(list) ? list.get(0) : null;
     * };
     * </pre>
     */
    public interface RelationRecordLoader {
        /**
         * 加载一条关联记录。
         *
         * @param tableName  关联表名，如 {@code Sec_Info}
         * @param fieldName  关联查询字段名，如 {@code Sec_Id}
         * @param fieldValue 关联查询字段值
         * @return 关联记录；未查询到时返回 {@code null}
         */
        Map<String, Object> load(String tableName, String fieldName, Object fieldValue);
    }

    /**
     * 解析打印规则 JSON。
     * <p>
     * 该方法用于解析打印模板配置中的规则字段，例如 {@code tpRemark}。只有合法 JSON 才会转换为 Map，
     * 空字符串或非法 JSON 会返回 {@code null}，调用方可以据此跳过规则过滤。
     * <pre>
     * Map&lt;String, Object&gt; ruleMap = PrintRuleMatchUtil.parseRuleJson("{\"secId\":\"123\"}");
     * Map&lt;String, Object&gt; notEqualsRule = PrintRuleMatchUtil.parseRuleJson("{\"secId!=\":\"123\"}");
     * </pre>
     * 注意：不等于运算符应写为 {@code !=}，完整示例为：
     * <pre>
     * {"secId!=":"123"}
     * {"secId":{"!=":"123"}}
     * {"secId":{"operator":"!=","value":"123"}}
     * </pre>
     *
     * @param ruleJson 打印规则 JSON 字符串
     * @return 打印规则 Map；空或非法 JSON 返回 {@code null}
     */
    public static Map<String, Object> parseRuleJson(String ruleJson) {
        if (ValidateUtil.isEmpty(ruleJson) || !JSONUtil.isTypeJSON(ruleJson)) {
            return null;
        }
        return JSONUtil.toBean(ruleJson, Map.class);
    }

    /**
     * 解析打印表单数据 JSON。
     * <p>
     * 该方法用于把外部传入的表单数据 JSON 转为规则匹配需要的记录列表。支持单对象和对象数组两种格式：
     * <pre>
     * {"Sec_Id":"123","is_new":"1"}
     * [{"Sec_Id":"123"},{"Sec_Id":"456"}]
     * </pre>
     * JavaBean 转换后的驼峰 key 也可以直接使用：
     * <pre>
     * {"secId":"123","isNew":"1"}
     * </pre>
     *
     * @param formDataJson 表单数据 JSON 字符串
     * @return 表单记录列表；空或非法 JSON 返回空列表
     */
    public static List<Map<String, Object>> parseFormDataJson(String formDataJson) {
        if (ValidateUtil.isEmpty(formDataJson) || !JSONUtil.isTypeJSON(formDataJson)) {
            return new ArrayList<>();
        }
        Object json = JSONUtil.parse(formDataJson);
        List<Map<String, Object>> records = new ArrayList<>();
        if (json instanceof JSONArray) {
            JSONArray array = (JSONArray) json;
            for (Object item : array) {
                if (item != null) {
                    records.add(JSONUtil.toBean(JSONUtil.parseObj(item), Map.class));
                }
            }
        } else {
            records.add(JSONUtil.toBean(JSONUtil.parseObj(json), Map.class));
        }
        return records;
    }

    /**
     * 判断表单数据 JSON 中是否存在任意一条记录匹配打印规则。
     * <p>
     * 适用于调用方已经拿到 JSON 格式表单数据的场景。普通字段规则不需要 {@code relationRecordLoader}；
     * 链式关联规则必须传入加载器，否则无法继续查询关联表。
     * <pre>
     * Map&lt;String, Object&gt; ruleMap = PrintRuleMatchUtil.parseRuleJson("{\"secId\":\"123\"}");
     * boolean match = PrintRuleMatchUtil.matchAny("{\"Sec_Id\":\"123\"}", ruleMap, null);
     * </pre>
     * 链式关联示例：
     * <pre>
     * Map&lt;String, Object&gt; ruleMap = PrintRuleMatchUtil.parseRuleJson(
     *     "{\"Sec_Id@Sec_Info.Sec_Id.Bp_Id@Sec_Bidding_Plan.Bp_Id.is_new\":\"1\"}"
     * );
     * boolean match = PrintRuleMatchUtil.matchAny(formDataJson, ruleMap, loader);
     * </pre>
     *
     * @param formDataJson         表单数据 JSON，可以是对象或对象数组
     * @param ruleMap              打印规则 Map，可由 {@link #parseRuleJson(String)} 得到
     * @param relationRecordLoader 链式关联记录加载器；没有链式规则时可传 {@code null}
     * @return 任意一条记录满足全部规则时返回 {@code true}
     */
    public static boolean matchAny(String formDataJson, Map<String, Object> ruleMap, RelationRecordLoader relationRecordLoader) {
        return matchAny(parseFormDataJson(formDataJson), ruleMap, relationRecordLoader);
    }

    /**
     * 判断记录集合中是否存在任意一条记录匹配打印规则。
     * <p>
     * 适用于调用方已经从数据库或 JSON 中拿到 {@code List<Map<String, Object>>} 的场景。
     * 规则 key 同时支持数据库字段名和驼峰字段名：
     * <pre>
     * Map&lt;String, Object&gt; record = new HashMap&lt;&gt;();
     * record.put("Sec_Id", "123");
     *
     * Map&lt;String, Object&gt; ruleMap = PrintRuleMatchUtil.parseRuleJson("{\"secId\":\"123\"}");
     * boolean match = PrintRuleMatchUtil.matchAny(Collections.singletonList(record), ruleMap, null);
     * </pre>
     *
     * @param records              表单记录集合
     * @param ruleMap              打印规则 Map
     * @param relationRecordLoader 链式关联记录加载器；没有链式规则时可传 {@code null}
     * @return 任意一条记录满足全部规则时返回 {@code true}
     */
    public static boolean matchAny(Collection<Map<String, Object>> records, Map<String, Object> ruleMap, RelationRecordLoader relationRecordLoader) {
        return matchAny(records, ruleMap, relationRecordLoader, new HashMap<>());
    }

    /**
     * 判断记录集合中是否存在任意一条记录匹配打印规则，并复用外部传入的链式关联缓存。
     * <p>
     * 当同一次列表过滤需要对多个打印模板使用相同表单数据和相同关联表时，建议传入同一个
     * {@code relationRecordCache}，避免重复查询关联记录。
     * <pre>
     * Map&lt;String, Map&lt;String, Object&gt;&gt; cache = new HashMap&lt;&gt;();
     * boolean firstMatch = PrintRuleMatchUtil.matchAny(records, firstRule, loader, cache);
     * boolean secondMatch = PrintRuleMatchUtil.matchAny(records, secondRule, loader, cache);
     * </pre>
     *
     * @param records              表单记录集合
     * @param ruleMap              打印规则 Map
     * @param relationRecordLoader 链式关联记录加载器；没有链式规则时可传 {@code null}
     * @param relationRecordCache  链式关联记录缓存；传 {@code null} 时工具类会自动创建临时缓存
     * @return 任意一条记录满足全部规则时返回 {@code true}
     */
    public static boolean matchAny(Collection<Map<String, Object>> records, Map<String, Object> ruleMap,
                                   RelationRecordLoader relationRecordLoader,
                                   Map<String, Map<String, Object>> relationRecordCache) {
        if (ValidateUtil.isEmpty(records) || ValidateUtil.isEmpty(ruleMap)) {
            return false;
        }
        if (relationRecordCache == null) {
            relationRecordCache = new HashMap<>();
        }
        for (Map<String, Object> record : records) {
            if (match(record, ruleMap, relationRecordLoader, relationRecordCache)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断单条记录是否匹配打印规则。
     * <p>
     * 规则中的所有条件都必须满足才返回 {@code true}。等值与不等值、普通字段与链式字段均支持：
     * <pre>
     * {"secId":"123"}
     * {"secId!=":"123"}
     * {"Sec_Id@Sec_Info.Sec_Id.Bp_Id@Sec_Bidding_Plan.Bp_Id.is_new!=":"0"}
     * </pre>
     *
     * @param record               单条表单记录
     * @param ruleMap              打印规则 Map
     * @param relationRecordLoader 链式关联记录加载器；没有链式规则时可传 {@code null}
     * @return 单条记录满足全部规则时返回 {@code true}
     */
    public static boolean match(Map<String, Object> record, Map<String, Object> ruleMap, RelationRecordLoader relationRecordLoader) {
        return match(record, ruleMap, relationRecordLoader, new HashMap<>());
    }

    /**
     * 判断打印表单数据是否匹配打印规则
     *
     * @param record               打印表单数据map
     * @param ruleMap              打印规则map
     * @param relationRecordLoader 关联记录加载器
     * @param relationRecordCache  关联记录缓存
     * @return 是否匹配
     */
    private static boolean match(Map<String, Object> record, Map<String, Object> ruleMap,
                                 RelationRecordLoader relationRecordLoader,
                                 Map<String, Map<String, Object>> relationRecordCache) {
        if (ValidateUtil.isEmpty(record) || ValidateUtil.isEmpty(ruleMap)) {
            return false;
        }
        for (Map.Entry<String, Object> entry : ruleMap.entrySet()) {
            RuleCondition condition = RuleCondition.of(entry.getKey(), entry.getValue());
            Object recordVal = getRuleRecordValue(record, condition.getRuleKey(), relationRecordLoader, relationRecordCache);
            if (!condition.match(recordVal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据给定的规则键从当前记录或链式关联记录中获取字段值。
     * 规则键支持字段名和 JavaBean 驼峰名，如 Sec_Id 与 secId 均可匹配。
     * <p>
     * 普通字段示例：
     * <pre>
     * Object value = PrintRuleMatchUtil.getRuleRecordValue(record, "secId", null, new HashMap&lt;&gt;());
     * </pre>
     * 链式关联示例：
     * <pre>
     * Object value = PrintRuleMatchUtil.getRuleRecordValue(
     *     record,
     *     "Sec_Id@Sec_Info.Sec_Id.Bp_Id@Sec_Bidding_Plan.Bp_Id.is_new",
     *     loader,
     *     cache
     * );
     * </pre>
     *
     * @param record               当前记录
     * @param ruleKey              规则键，支持普通字段和 {@code @} 分隔的链式关联字段
     * @param relationRecordLoader 链式关联记录加载器；普通字段可传 {@code null}
     * @param relationRecordCache  链式关联记录缓存
     * @return 规则键对应的字段值；无法获取时返回 {@code null}
     */
    public static Object getRuleRecordValue(Map<String, Object> record, String ruleKey,
                                            RelationRecordLoader relationRecordLoader,
                                            Map<String, Map<String, Object>> relationRecordCache) {
        if (ValidateUtil.isEmpty(ruleKey)) {
            return null;
        }
        if (!ruleKey.contains("@")) {
            return getByFieldName(record, ruleKey);
        }
        String[] ruleParts = ruleKey.split("@");
        if (ruleParts.length < 2 || ValidateUtil.isEmpty(ruleParts[0])) {
            return null;
        }
        String localFkName = ruleParts[0];
        if (!isSqlIdentifier(localFkName)) {
            return null;
        }
        Object relationValue = getByFieldName(record, localFkName);
        for (int i = 1; i < ruleParts.length; i++) {
            String[] relationParts = ruleParts[i].split("\\.", 3);
            if (relationParts.length != 3) {
                return null;
            }
            String relationTable = relationParts[0];
            String relationPkName = relationParts[1];
            String relationFieldName = relationParts[2];
            if (!isSqlIdentifier(relationTable) || !isSqlIdentifier(relationPkName)
                    || !isSqlIdentifier(relationFieldName) || relationValue == null
                    || relationRecordLoader == null) {
                return null;
            }
            String cacheKey = relationTable + "." + relationPkName + "." + relationValue;
            Map<String, Object> relationRecord = relationRecordCache.get(cacheKey);
            if (!relationRecordCache.containsKey(cacheKey)) {
                relationRecord = relationRecordLoader.load(relationTable, relationPkName, relationValue);
                relationRecordCache.put(cacheKey, relationRecord);
            }
            if (relationRecord == null) {
                return null;
            }
            relationValue = getByFieldName(relationRecord, relationFieldName);
        }
        return relationValue;
    }

    /**
     * 根据给定的字段名从当前记录中获取字段值。
     * <p>
     * 字段匹配会忽略大小写，并兼容数据库下划线字段和 JavaBean 驼峰字段：
     * <pre>
     * record.put("Sec_Id", "123");
     * PrintRuleMatchUtil.getByFieldName(record, "secId"); // 返回 "123"
     *
     * record.put("isNew", "1");
     * PrintRuleMatchUtil.getByFieldName(record, "is_new"); // 返回 "1"
     * </pre>
     *
     * @param record    当前记录
     * @param fieldName 要读取的字段名，可以是数据库字段名或驼峰字段名
     * @return 字段值；未找到时返回 {@code null}
     */
    public static Object getByFieldName(Map<String, Object> record, String fieldName) {
        if (ValidateUtil.isEmpty(record) || ValidateUtil.isEmpty(fieldName)) {
            return null;
        }
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            if (sameFieldName(entry.getKey(), fieldName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 判断两个字段名是否相同，支持字段名和 JavaBean 驼峰名，如 Sec_Id 与 secId 均可匹配。
     *
     * @param source 字段名1
     * @param target 字段名2
     * @return 是否相同
     */
    private static boolean sameFieldName(String source, String target) {
        if (source == null || target == null) {
            return false;
        }
        return source.equalsIgnoreCase(target) || normalizeFieldName(source).equals(normalizeFieldName(target));
    }

    /**
     * 标准化字段名，将下划线替换为无下划线，并将所有字母转换为小写。
     *
     * @param fieldName 字段名
     * @return 标准化后的字段名
     */
    private static String normalizeFieldName(String fieldName) {
        return fieldName.replace("_", "").toLowerCase();
    }

    /**
     * 判断给定的名称是否为 SQL 标识符，即是否符合 SQL 标识符的命名规则。
     *
     * @param name 名称
     * @return 是否为 SQL 标识符
     */
    private static boolean isSqlIdentifier(String name) {
        return ValidateUtil.isNotEmpty(name) && name.matches("[A-Za-z_][A-Za-z0-9_]*");
    }

    /**
     * 判断两个值是否相等，支持 null 值和字符串比较。
     *
     * @param recordVal 当前记录的字段值
     * @param ruleVal   规则值
     * @return 是否相等
     */
    private static boolean valueEquals(Object recordVal, Object ruleVal) {
        if (recordVal == null && ruleVal == null) {
            return true;
        }
        if (recordVal == null || ruleVal == null) {
            return false;
        }
        return recordVal.toString().equals(ruleVal.toString());
    }

    private static class RuleCondition {
        private final String ruleKey;
        private final Object ruleVal;
        private final boolean notEquals;

        private RuleCondition(String ruleKey, Object ruleVal, boolean notEquals) {
            this.ruleKey = ruleKey;
            this.ruleVal = ruleVal;
            this.notEquals = notEquals;
        }

        private static RuleCondition of(String rawKey, Object rawVal) {
            String ruleKey = rawKey;
            boolean notEquals = false;
            Object ruleVal = rawVal;

            if (ValidateUtil.isNotEmpty(ruleKey) && ruleKey.endsWith(NOT_EQUALS)) {
                ruleKey = ruleKey.substring(0, ruleKey.length() - NOT_EQUALS.length());
                notEquals = true;
            }
            if (rawVal instanceof Map) {
                Map valueMap = (Map) rawVal;
                Object operator = valueMap.get(OPERATOR);
                if (operator == null) {
                    operator = valueMap.get("op");
                }
                if (NOT_EQUALS.equals(operator)) {
                    notEquals = true;
                    if (valueMap.containsKey(VALUE)) {
                        ruleVal = valueMap.get(VALUE);
                    }
                }
                if (valueMap.containsKey(NOT_EQUALS)) {
                    notEquals = true;
                    ruleVal = valueMap.get(NOT_EQUALS);
                }
            }
            return new RuleCondition(ruleKey, ruleVal, notEquals);
        }

        private boolean match(Object recordVal) {
            boolean equals = valueEquals(recordVal, ruleVal);
            return notEquals ? !equals : equals;
        }

        private String getRuleKey() {
            return ruleKey;
        }
    }
}
