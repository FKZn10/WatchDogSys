package com.jpmsoft.fileUpload.easyexcel;

import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CustomColumnWidthStyleStrategy  extends AbstractSheetWriteHandler {

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        super.beforeSheetCreate(writeWorkbookHolder, writeSheetHolder);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 15 * 256); 
        sheet.setColumnWidth(2, 80 * 256);
        sheet.setColumnWidth(3, 40 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 25 * 256);
        sheet.setColumnWidth(9, 35 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 200 * 256);
        sheet.setColumnWidth(12, 50 * 256);
        sheet.setColumnWidth(13, 20 * 256);
        sheet.setColumnWidth(14, 20 * 256);
        sheet.setColumnWidth(15, 20 * 256);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);

        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            if (sheet.getRow(rowIndex) != null) {
                for (int cellIndex = 0; cellIndex < sheet.getRow(rowIndex).getPhysicalNumberOfCells(); cellIndex++) {
                    sheet.getRow(rowIndex).getCell(cellIndex).setCellStyle(cellStyle);
                }
            }
        }

        super.afterSheetCreate(writeWorkbookHolder, writeSheetHolder);
    }
}
