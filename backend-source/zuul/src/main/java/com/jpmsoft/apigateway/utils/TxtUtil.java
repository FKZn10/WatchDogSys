package com.jpmsoft.apigateway.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
public class TxtUtil {


    /**
     * Java读取txt文件的内容,返回一个每行为一个String[]的ArrayList集合
     *
     * @param filePath 文件路径
     * @param encoding 编码格式
     * @return 每行为一个String[]的ArrayList集合
     */
    public static ArrayList readTxtFile(String filePath, String encoding) {
        ArrayList res = new ArrayList();
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            //  InputStream inputStream = classPathResource.getInputStream();
//            if (classPathResource.isFile() && classPathResource.exists()) { // 判断文件是否存在
            {
                InputStreamReader read = new InputStreamReader(inputStream, encoding);// 编码格式必须和文件的一致
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    res.add(lineTxt);
                }
                read.close();
            }
//            else {
//                System.out.println("指定的文件不存在");
//            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return res;
    }
//
//    /**
//     * Java读取txt文件的内容,返回一个每行为一个String[]的ArrayList集合
//     *
//     * @param filePath 文件路径
//     * @param encoding 编码格式
//     * @return 每行为一个String[]的ArrayList集合
//     */
//    public static ArrayList readTxtFile(String filePath, String encoding) {
//        ArrayList res = new ArrayList();
//        try {
//            ClassPathResource classPathResource = new ClassPathResource(filePath);
//            InputStream inputStream = classPathResource.getInputStream();
//            if (classPathResource.isFile() && classPathResource.exists()) { // 判断文件是否存在
//                InputStreamReader read = new InputStreamReader(inputStream, encoding);// 编码格式必须和文件的一致
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//                    res.add(lineTxt);
//                }
//                read.close();
//            } else {
//                System.out.println("指定的文件不存在");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//        return res;
//    }
}
