package com.jpmsoft.fileUpload.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/11 9:44
 */
public class XlsxMerge {

    private static int targetIndex = 0;

    public static void merge(File dir, String outputFile) throws Exception {
        targetIndex = 0;
        File[] files = dir.listFiles();
        // 创建目标工作簿
        Workbook targetWorkbook = new SXSSFWorkbook();
        Sheet targetSheet = targetWorkbook.createSheet("Merged Sheet");
        // 获取线程池
        ExecutorService executorService = Executors.newFixedThreadPool(files.length);
        int rowNum = 0;
        for (int i = 0; i < files.length; i++) {
            final int index = i;
            final String filePath = files[index].getAbsolutePath();
            executorService.submit(() -> {
                try {
                    // 读取源文件
                    FileInputStream fis = new FileInputStream(filePath);
                    Workbook workbook = WorkbookFactory.create(fis);
                    Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
                    fis.close();
                    // 将源文件的数据复制到目标工作簿的同一个工作表上
                    copyDataToTargetWorkbook(targetWorkbook, targetSheet, sheet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        // 等待所有任务完成
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        // 保存目标工作簿到文件
        FileOutputStream fos = new FileOutputStream(outputFile);
        targetWorkbook.write(fos);
        fos.close();
        targetWorkbook.close();
        System.out.println("合并完成！");
    }


    private static void copyDataToTargetWorkbook(Workbook targetWorkbook, Sheet targetSheet, Sheet sheet) {
        // 遍历原始Sheet的每一行
        for (Row row : sheet) {
            Row targetRow = targetSheet.createRow(targetIndex);
            System.out.println(targetIndex);
            targetIndex += 1;
            // 遍历原始行的每一单元格
            for (Cell cell : row) {
                CellStyle style = targetWorkbook.createCellStyle();
                style.cloneStyleFrom(cell.getCellStyle());
                Cell targetCell = targetRow.createCell(cell.getColumnIndex());
                //targetRow.createCell(cell.getColumnIndex(), cell.getCellType());
                copyCell(cell, targetCell);
            }
        }
    }

    // 辅助方法：复制单元格数据（根据实际情况实现）
    private static void copyCell(Cell inputCell, Cell outputCell) {
//        outputCell.setCellType(inputCell.getCellType());
        switch (inputCell.getCellType()) {
            case STRING:
                outputCell.setCellValue(inputCell.getStringCellValue());
                break;
            case NUMERIC:
                outputCell.setCellValue(inputCell.getNumericCellValue());
                break;
            case BOOLEAN:
                outputCell.setCellValue(inputCell.getBooleanCellValue());
                break;
            case FORMULA:
                outputCell.setCellFormula(inputCell.getCellFormula());
                break;
            default:
                outputCell.setCellValue(inputCell.getStringCellValue());
                break;
        }
    }
}
