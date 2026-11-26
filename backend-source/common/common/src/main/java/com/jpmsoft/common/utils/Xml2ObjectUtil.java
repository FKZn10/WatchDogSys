package com.jpmsoft.common.utils;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * XML与实体类互转工具类（基于Jackson XML）
 */
public class Xml2ObjectUtil {

    /**
     * XML 文件 转 实体类
     * @param xmlFile XML 文件对象
     * @param clazz 目标实体类
     * @return 转换后的实体
     * @throws Exception 解析异常
     */
    public static <T> T xmlFile2Object(File xmlFile, Class<T> clazz) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(clazz, xmlFile);
    }

    /**
     * 实体类 转 XML 输出流（浏览器下载核心方法）
     * 等价于原来的 XML_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputStream, obj)
     */
    public static void object2XmlStream(Object obj, OutputStream outputStream) throws IOException {
        try {
            // 创建 SimpleXml 序列化器（自带格式化缩进，和 writerWithDefaultPrettyPrinter 效果一致）
            Serializer serializer = new Persister();

            // 写入输出流（指定 UTF-8 编码，防止中文乱码）
            serializer.write(
                    obj,
                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            throw new IOException("XML 序列化失败", e);
        }
    }

    public static <T> T xmlToBean(String xml, Class<T> clazz) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(clazz, xml);
    }

//    // 全局XmlMapper实例（线程安全，可复用）
//    private static final XmlMapper XML_MAPPER = new XmlMapper();
//
//    /**
//     * 读取XML文件转换为实体类对象
//     * @param xmlFile XML文件对象
//     * @param clazz 目标实体类的Class对象
//     * @return 实体类对象
//     * @throws IOException 文件读取/解析异常
//     */
//    public static <T> T xmlFile2Object(File xmlFile, Class<T> clazz) throws IOException {
//        return XML_MAPPER.readValue(xmlFile, clazz);
//    }
//
//    /**
//     * XML字符串转换为实体类对象（拓展场景）
//     * @param xmlStr XML字符串
//     * @param clazz 目标实体类的Class对象
//     * @return 实体类对象
//     * @throws IOException 解析异常
//     */
//    public static <T> T xmlStr2Object(String xmlStr, Class<T> clazz) throws IOException {
//        return XML_MAPPER.readValue(xmlStr, clazz);
//    }
//
//    // 拓展：实体类转XML字符串/文件
//    public static String object2XmlStr(Object obj) throws IOException {
//        return XML_MAPPER.writeValueAsString(obj);
//    }
//
//
//    // 【新增核心方法2】实体类写入输出流（下载场景核心，支持任意OutputStream）
//    public static void object2XmlStream(Object obj, OutputStream outputStream) throws IOException {
//        // 将实体类转换为XML并写入输出流，浏览器下载的核心方法
//        XML_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputStream, obj);
//    }
}
