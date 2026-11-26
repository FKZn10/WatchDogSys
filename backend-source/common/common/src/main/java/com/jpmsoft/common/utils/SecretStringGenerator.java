package com.jpmsoft.common.utils;

import com.jpmsoft.common.annotations.SecretField;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SecretStringGenerator {


    public static String toString(Object obj) {
        try {
            return generateFieldString(obj);
        } catch (IllegalAccessException ex) {
            return "";
        }
    }

    public static String generateFieldString(Object entity) throws IllegalAccessException {
        List<FieldEntry> fieldEntries = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SecretField.class)) {
                field.setAccessible(true);
                Object value = field.get(entity);
                fieldEntries.add(new FieldEntry(field.getName(), value));
            }
        }

        fieldEntries.sort(Comparator.comparing(FieldEntry::getFieldName));

        List<String> fieldStrings = fieldEntries.stream()
                .map(entry -> formatField(entry.getFieldName(), entry.getValue()))
                .collect(Collectors.toList());

        return String.join("|", fieldStrings);
    }

    private static String formatField(String fieldName, Object value) {
        return fieldName + ":" + formatValue(value);
    }

    private static String formatValue(Object value) {
        if (value == null) return "null";
        Class<?> valueClass = value.getClass();
        if (valueClass.isArray()) {
            return formatArray(value);
        } else if (value instanceof Collection) {
            return formatCollection((Collection<?>) value);
        } else if (value instanceof Map) {
            return formatMap((Map<?, ?>) value);
        } else if (value instanceof Date) {
            return "date:" + ((Date) value).getTime();
        }
        else if (value instanceof BigDecimal) {
            return "BigDecimal:" + new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
        }
        else if (valueClass.isPrimitive() || valueClass == String.class
                || valueClass == Integer.class || valueClass == Long.class
                || valueClass == Double.class || valueClass == Float.class
                || valueClass == Boolean.class) {
            return value.toString();
        } else {
            return "object:" + value.toString();
        }
    }

    private static String formatArray(Object array) {
        if (array instanceof Object[]) {
            Object[] objArray = (Object[]) array;
            List<String> elements = Arrays.stream(objArray)
                    .map(Object::toString)
                    .sorted()
                    .collect(Collectors.toList());
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof int[]) {
            int[] intArray = (int[]) array;
            List<String> elements = Arrays.stream(intArray)
                    .mapToObj(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof long[]) {
            long[] longArray = (long[]) array;
            List<String> elements = Arrays.stream(longArray)
                    .mapToObj(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof double[]) {
            double[] doubleArray = (double[]) array;
            List<String> elements = Arrays.stream(doubleArray)
                    .mapToObj(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof boolean[]) {
            boolean[] boolArray = (boolean[]) array;
            List<String> elements = new ArrayList<>();
            for (boolean b : boolArray) {
                elements.add(String.valueOf(b));
            }
            Collections.sort(elements);
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof char[]) {
            char[] charArray = (char[]) array;
            List<String> elements = new ArrayList<>();
            for (char c : charArray) {
                elements.add(String.valueOf(c));
            }
            Collections.sort(elements);
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof byte[]) {
            byte[] byteArray = (byte[]) array;
            List<String> elements = new ArrayList<>();
            for (byte b : byteArray) {
                elements.add(String.valueOf(b));
            }
            Collections.sort(elements);
            return "[" + String.join(",", elements) + "]";
        } else if (array instanceof float[]) {
            float[] floatArray = (float[]) array;
            List<String> elements = new ArrayList<>();
            for (float f : floatArray) {
                elements.add(String.valueOf(f));
            }
            Collections.sort(elements);
            return "[" + String.join(",", elements) + "]";
        } else {
            return "unknown_array";
        }
    }

    private static String formatCollection(Collection<?> collection) {
        List<String> elements = collection.stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.toList());
        return "[" + String.join(",", elements) + "]";
    }

    private static String formatMap(Map<?, ?> map) {
        List<String> entries = map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .sorted()
                .collect(Collectors.toList());
        return "{" + String.join(",", entries) + "}";
    }

    private static class FieldEntry {
        private final String fieldName;
        private final Object value;

        public FieldEntry(String fieldName, Object value) {
            this.fieldName = fieldName;
            this.value = value;
        }

        public String getFieldName() {
            return fieldName;
        }

        public Object getValue() {
            return value;
        }
    }

}
