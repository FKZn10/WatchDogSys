package com.jpmsoft.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取log文件倒数指定多少行
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 14:34
 */
public class LogLastNLine {

    public static List<String> getLastNLines(String filePath, int n) {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = tail(filePath, n)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (ValidateUtil.isNotEmpty(line))
                    data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static BufferedReader tail(String filePath, int lines) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");

        // 获取文件长度
        long fileLength = raf.length();

        // 计算开始读取的位置
        long start = fileLength;
        if (lines > 0) {
            // 如果要读取的行数大于0，则向前移动到文件末尾并计算开始读取的位置
            start -= (lines * 16); // 假设每行平均16字节，这个值可以根据实际情况调整

            // 移动到开始读取的位置
            raf.seek(start);

            // 读取内容直到文件末尾
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = raf.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }

            // 将读取的内容转换为字符串并分割成行
            String content = sb.toString();
            String[] linesArray = content.split("\n", -1);

            // 计算实际的行数
            int actualLines = linesArray.length;

            // 如果文件行数少于要读取的行数，则返回全部内容
            if (actualLines < lines) {
                return new BufferedReader(new StringReader(content));
            } else {
                // 否则返回最后几行
                StringBuilder lastLines = new StringBuilder();
                for (int i = actualLines - lines; i < actualLines; i++) {
                    if (i >= 0) {
                        lastLines.append(linesArray[i]).append("\n");
                    }
                }
                return new BufferedReader(new StringReader(lastLines.toString()));
            }
        } else {
            // 如果要读取的行数为0或负数，则返回空BufferedReader
            return new BufferedReader(new StringReader(""));
        }
    }

}
