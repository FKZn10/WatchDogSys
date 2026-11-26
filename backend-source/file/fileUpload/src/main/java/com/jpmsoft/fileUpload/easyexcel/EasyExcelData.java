package com.jpmsoft.fileUpload.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.listener.EasyExcelReadListener;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/2 14:30
 */
public class EasyExcelData<T> {

    /**
     * 解析excel并返回List集合
     *
     * @param file   excel文件
     * @param tClass 模板实体class
     * @return
     */
    public List<T> getExcelData(MultipartFile file, Class<T> tClass) {
        try {
            FileUtil.checkExcelFile(file);
            InputStream is = file.getInputStream();
            EasyExcelReadListener<T> listener = new EasyExcelReadListener<>();
            EasyExcel.read(is, tClass, listener).sheet().doRead();
            return listener.list;
        } catch (IOException ex) {
            throw new JPMException(FileMsg.fileUpError);
        }
    }

    /**
     * 解析excel并返回List集合
     * 指定数据读取起始行
     *
     * @param file       excel文件
     * @param tClass     模板实体class
     * @param hearRowNum 表头行数
     * @return
     */
    public List<T> getExcelData(MultipartFile file, Class<T> tClass, Integer hearRowNum) {
        try {
            FileUtil.checkExcelFile(file);
            InputStream is = file.getInputStream();
            EasyExcelReadListener<T> listener = new EasyExcelReadListener<>();
            EasyExcel.read(is, tClass, listener).sheet().headRowNumber(hearRowNum).doRead();
            return listener.list;
        } catch (IOException ex) {
            throw new JPMException(FileMsg.fileUpError);
        }
    }


    /**
     * 导出方法
     *
     * @param fileName       文件
     * @param writerConsumer consumer
     */
    public static void export(String fileName, Consumer<ExcelWriter> writerConsumer) {
        ExcelWriter writer = EasyExcel.write(fileName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();
        writerConsumer.accept(writer);
        writer.finish();
    }

    /**
     * 导出方法
     *
     * @param outputStream       输出流
     * @param writerConsumer consumer
     */
    public static void exportOutputStream(OutputStream outputStream, Consumer<ExcelWriter> writerConsumer) {
        ExcelWriter writer = EasyExcel.write(outputStream)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();
        writerConsumer.accept(writer);
        writer.finish();
    }
}
