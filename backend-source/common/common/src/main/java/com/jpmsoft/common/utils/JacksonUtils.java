package com.jpmsoft.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class JacksonUtils {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    static {
        // 初始化JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        //处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        //处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //对象的所有字段全部列入
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //防止出现pName转换json时变成pname
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
//        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
//        mapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        mapper.registerModule(javaTimeModule);
    }

    /**
     * 获取ObjectMapper实例
     *
     * @return ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * 替换fastjson
     * JSONObject.toJSONString 方法
     *
     * @param o
     * @return
     */
    public static String toJSONString(Object o) {
        if (ValidateUtil.isNull(o)) return null;
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            System.out.println("jackson toJSONString error!");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 替换fastjson
     * JSONObject.parseObject 方法
     * JSON转List<Obj>
     *
     * @param str
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String str, TypeReference<T> valueTypeRef) {
        if (ValidateUtil.isEmpty(str)) return null;
        try {
            return mapper.readValue(str, valueTypeRef);
        } catch (JsonProcessingException ex) {
            System.out.println("jackson parseObject(TypeReference) error!");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 替换fastjson
     * JSONObject.parseObject 方法
     * JSON转bean
     *
     * @param str
     * @param valueTypef
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String str, Class<T> valueTypef) {
        try {
            return mapper.readValue(str, valueTypef);
        } catch (JsonProcessingException ex) {
            System.out.println("jackson parseObject(Class) error!");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 替换fastjson
     * JSONObject.parseArray 方法
     * JSON转List<T>
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> cls) {
        if (ValidateUtil.isEmpty(json)) {
            return null;
        }
        try {
            JavaType jt = mapper.getTypeFactory().constructParametricType(List.class, cls);
            return mapper.readValue(json, jt);
        } catch (Exception ex) {
            System.out.println("jackson parseArray(Class) error!");
            ex.printStackTrace();
        }
        return null;
    }


}
