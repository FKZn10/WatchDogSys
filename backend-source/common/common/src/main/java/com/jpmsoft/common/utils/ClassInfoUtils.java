package com.jpmsoft.common.utils;

import com.jpmsoft.common.model.ClassInfoAnnotation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 获取类注解信息工具类
 */
public class ClassInfoUtils {

    public static List<ClassInfoAnnotation> getClassInfo(String className) throws ClassNotFoundException {
        List<ClassInfoAnnotation> classInfoAnnotations = new ArrayList<>();
        Field[] fields = Class.forName(className).getDeclaredFields();
        for (Field field:fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(annotation);
                ClassInfoAnnotation classInfoAnnotation = getClassInfoAnnotation(map);
                if (classInfoAnnotation!=null){
                    classInfoAnnotations.add(classInfoAnnotation);
                }
            }
        }
        return classInfoAnnotations;
    }

    private static ClassInfoAnnotation getClassInfoAnnotation(Map<String, Object> map) {
        if (map.get("title")!=null){
            ClassInfoAnnotation classInfoAnnotation = new ClassInfoAnnotation();
            classInfoAnnotation.setTitle(String.valueOf(map.get("title")));
            classInfoAnnotation.setType(String.valueOf(map.get("type")));
            classInfoAnnotation.setValue(String.valueOf(map.get("value")));
            classInfoAnnotation.setDbValue(String.valueOf(map.get("dbValue")));
            if (classInfoAnnotation.getType().equals("dictionary")){
                classInfoAnnotation.setDictionary(String.valueOf(map.get("dictionary")));
            }
            classInfoAnnotation.setNo((Integer) map.get("no"));
            return classInfoAnnotation;
        }else {
            return null;
        }
    }

    /**
     * 构造用于 SQL SELECT 子句的字段映射字符串
     * <p>
     * 本方法会将字段注解列表转换为形如：
     * <pre>
     *     dbField1 AS field1, dbField2 AS field2, ...
     * </pre>
     * 的字符串形式，用于在 SQL 查询中直接拼接 SELECT 语句。
     * <p>
     * 方法会自动跳过以下字段：
     * <ul>
     *     <li>为 null 的注解项</li>
     *     <li>类型为 "date" 的字段（通常在查询中不需要被选出或单独处理）</li>
     * </ul>
     *
     * @param classInfoAnnotationList 从 {@link #getClassInfo(String)} 方法返回的字段注解列表，包含数据库字段名（dbValue）与别名（value）
     * @return SQL SELECT 子句格式的字符串，例如："db_col1 as col1, db_col2 as col2"
     * @author tianhelin
     * @see #getClassInfo(String)
     */
    public static String getSelectClauseWithClassInfo(List<ClassInfoAnnotation> classInfoAnnotationList) {
        return Optional.ofNullable(classInfoAnnotationList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(c -> c != null && !"date".equals(c.getType()))
                .map(c -> c.getDbValue() + " as " + c.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * 处理字段注解对应的原始结果集，为字段注解对象赋值字典项并处理排序号
     * <p>
     * 主要功能包括：
     * <ul>
     *     <li>统计原始结果集中每个字段对应的所有非空值，并填充到对应注解字段的 {@code dictionaryValues} 属性中。</li>
     *     <li>根据排序号（no）对注解字段进行排序处理，规则如下：
     *         <ul>
     *             <li>若所有字段未设置排序号（no <= 0），则按原始顺序从 1 开始依次编号。</li>
     *             <li>若所有字段都已设置排序号，则保持不变。</li>
     *             <li>若部分字段未设置排序号，则为未设置的字段分配当前未使用的最小编号，顺序不变。</li>
     *         </ul>
     *     </li>
     *     <li>最终按排序号升序返回处理后的字段注解列表。</li>
     * </ul>
     *
     * @param rawResult 原始数据查询结果集，每条记录表示为字段名到字段值的映射
     * @param classInfoAnnotationList 从类字段注解中提取的信息列表，每个包含字段名、数据库字段、类型等信息
     * @return 处理后的字段注解列表，已填充字段值集合和排序号，并按排序号升序排列
     * @author tianhelin
     */
    public static List<ClassInfoAnnotation> resultsHandle(List<Map<String, Object>> rawResult, List<ClassInfoAnnotation> classInfoAnnotationList) {
        Map<String, Set<Object>> fieldMap = new HashMap<>(rawResult.size());
        rawResult.stream()
                .filter(Objects::nonNull)
                .forEach(row ->
                        row.entrySet().stream()
                                .filter(entry -> entry.getValue() != null)
                                .forEach(entry -> fieldMap.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).add(entry.getValue()))
                );

        // 处理字段赋值
        classInfoAnnotationList.stream()
                .filter(annotation -> !"date".equals(annotation.getType())) // 过滤掉date类型
                .forEach(annotation -> {
                    Set<Object> values = fieldMap.getOrDefault(annotation.getValue(), Collections.emptySet());

                    // 2.1 处理原始值
                    List<Object> processedValues = values.stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    // 2.2 特殊类型处理：integer/money不添加空白数据
                    if (processedValues.isEmpty() &&
                            !isNumericType(annotation.getType())) {
                        processedValues.add(BLANK_PLACEHOLDER);
                    }

                    // 2.3 标准化处理
                    List<Object> flatValues = processedValues.stream()
                            .flatMap(ClassInfoUtils::normalizeValue)
                            .sorted(ClassInfoUtils::compareValues)
                            .distinct()
                            .collect(Collectors.toList());

                    annotation.setDictionaryValues(flatValues);

                });

        // 处理字段排序
        handleAnnotationOrder(classInfoAnnotationList);
        return classInfoAnnotationList.stream()
                .sorted(Comparator.comparing(ClassInfoAnnotation::getNo))
                .collect(Collectors.toList());
    }

    private static boolean isNumericType(String type) {
        return "integer".equals(type) || "money".equals(type) || "number".equals(type);
    }

    /**
     * 处理注解字段的排序号（no）
     * <p>
     * 根据字段上的排序号（no）来决定字段在列表中的顺序。处理逻辑如下：
     * <ul>
     *     <li>如果所有字段都未设置排序号（即 no <= 0），则按字段原始顺序依次从 1 开始设置排序号。</li>
     *     <li>如果所有字段都已设置有效排序号（即 no > 0），则不做任何修改，保留现有排序号。</li>
     *     <li>如果部分字段已设置排序号、部分未设置，则保留已设置的排序号，未设置的按原顺序补充最小可用的未使用编号。</li>
     * </ul>
     * <p>
     * 注意：该方法假设字段的排序号类型为基本类型 int，默认值为 0，表示未设置排序号。
     *
     * @param list 从类注解中提取的字段注解信息列表，每项为 {@link ClassInfoAnnotation}
     * @author tianhelin
     */
    private static void handleAnnotationOrder(List<ClassInfoAnnotation> list) {
        // 已设置排序号的
        List<ClassInfoAnnotation> withOrder = list.stream()
                .filter(a -> a.getNo() > 0)
                .collect(Collectors.toList());
        // 未设置排序号的
        List<ClassInfoAnnotation> withoutOrder = list.stream()
                .filter(a -> a.getNo() <= 0)
                .collect(Collectors.toList());

        if (withOrder.isEmpty()) {
            // 全部未设置，统一赋值
            AtomicInteger count = new AtomicInteger(1);
            list.forEach(a -> a.setNo(count.getAndIncrement()));
        } else if (!withoutOrder.isEmpty()) {
            // 部分未设置，找出已使用过的编号，按照最小未设置的编号进行排序
            Set<Integer> usedNos = withOrder.stream().map(ClassInfoAnnotation::getNo).collect(Collectors.toSet());
            AtomicInteger next = new AtomicInteger(1);
            for (ClassInfoAnnotation a : withoutOrder) {
                while (usedNos.contains(next.get())) {
                    next.incrementAndGet();
                }
                a.setNo(next.getAndIncrement());
            }
        }
    }


    private static final String BLANK_PLACEHOLDER = "空白数据";

    public static Stream<Object> processDictionaryValues(Collection<Object> values) {
        return Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .flatMap(ClassInfoUtils::normalizeValue)
                .filter(ClassInfoUtils::isNotEmptyValue)
                .sorted(ClassInfoUtils::compareValues)
                .distinct();
    }

    public static Stream<Object> processDictionaryValues(Collection<Object> values, String blankDataName) {
        return Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .flatMap(ClassInfoUtils::normalizeValue)
                .filter(ClassInfoUtils::isNotEmptyValue)
                .sorted(ClassInfoUtils::compareValues)
                .distinct()
                .map(value -> BLANK_PLACEHOLDER.equals(value) ? blankDataName : value);
    }

    // 新增泛型方法（兼容原有调用）
    public static <T> Stream<T> processGenericDictionaryValues(Collection<T> values) {
        return Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .flatMap(ClassInfoUtils::normalizeGenericValue)
                .filter(ClassInfoUtils::isNotEmptyGenericValue)
                .sorted(ClassInfoUtils::compareGenericValues)
                .distinct();
    }

    /**
     * 泛型版本的标准化处理方法
     */
    @SuppressWarnings("unchecked")
    private static <T> Stream<T> normalizeGenericValue(T value) {
        // 处理null
        if (value == null) {
            return Stream.of((T) BLANK_PLACEHOLDER);
        }

        // 处理字符串类型
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.trim().isEmpty()) {
                return Stream.of((T) BLANK_PLACEHOLDER);
            }

        }

        // 其他类型直接返回
        return Stream.of(value);
    }

    /**
     * 泛型版本的非空检查
     */
    private static <T> boolean isNotEmptyGenericValue(T value) {
        if (value == null) return false;
        return !(value instanceof String) || !((String) value).isEmpty();
    }

    /**
     * 泛型版本的比较方法
     */
    private static <T> int compareGenericValues(T o1, T o2) {
        // 空白数据排序逻辑
        if (BLANK_PLACEHOLDER.equals(o1) && BLANK_PLACEHOLDER.equals(o2)) return 0;
        if (BLANK_PLACEHOLDER.equals(o1)) return 1;
        if (BLANK_PLACEHOLDER.equals(o2)) return -1;

        // 获取可比较对象
        Comparable c1 = convertToComparable(o1);
        Comparable c2 = convertToComparable(o2);

        try {
            return c1.compareTo(c2);
        } catch (Exception e) {
            return compareAsString(o1, o2);
        }
    }

    /**
     * 标准化输入值，处理null、空字符串
     * @param value 输入值，可以是任意类型
     * @return 处理后的值流，保证非空
     */
    private static Stream<Object> normalizeValue(Object value) {
        // 处理null或空字符串
        if (value == null) {
            return Stream.of(BLANK_PLACEHOLDER);
        }

        // 处理字符串类型
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.trim().isEmpty()) {
                return Stream.of(BLANK_PLACEHOLDER);
            }

        }

        // 其他类型直接返回
        return Stream.of(value);
    }


    private static boolean isNotEmptyValue(Object value) {
        if (value == null) return false;
        return !(value instanceof String) || !((String) value).isEmpty();
    }

    private static int compareValues(Object o1, Object o2) {
        // 空白数据排序逻辑
        if (BLANK_PLACEHOLDER.equals(o1) && BLANK_PLACEHOLDER.equals(o2)) return 0;
        if (BLANK_PLACEHOLDER.equals(o1)) return 1;
        if (BLANK_PLACEHOLDER.equals(o2)) return -1;

        // 获取可比较对象
        Comparable c1 = convertToComparable(o1);
        Comparable c2 = convertToComparable(o2);

        try {
            return c1.compareTo(c2);
        } catch (Exception e) {
            return compareAsString(o1, o2);
        }
    }

    private static Comparable convertToComparable(Object obj) {
        if (obj == null) return "";

        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj ? 1 : 0;
        }
        if (obj instanceof Date) {
            return ((Date) obj).getTime();
        }
        return obj.toString();
    }

    private static int compareAsString(Object o1, Object o2) {
        String s1 = o1 != null ? o1.toString() : "";
        String s2 = o2 != null ? o2.toString() : "";
        return s1.compareTo(s2);
    }
}
