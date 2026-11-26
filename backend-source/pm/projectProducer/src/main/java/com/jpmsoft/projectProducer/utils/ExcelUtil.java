package com.jpmsoft.projectProducer.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.FileMsg;
import com.jpmsoft.projectProducer.pojo.entity.other.SysLoggerInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelUtil {


    /**
     * 处理导入小数点
     */
    private static String StringFormat(Cell cell) {
        if (ValidateUtil.isNull(cell)) return null;
        cell.setCellType(CellType.STRING);
        return cell.toString();
    }

    private static Float FloatFormat(Cell cell) {
        return Float.valueOf(cellIsEmpty(cell) ? "0" : cell.toString());
    }

    private static Date TimeFormat(Cell cell, String format) {
        if (ValidateUtil.isNull(cell)) return null;
        Date date = DateUtil.String2Date(cell.getLocalDateTimeCellValue().toString(), format);
        return date;
    }

    /**
     * 处理导入身份证成科学计数
     *
     * @param cell
     * @return
     */
    private static String IntFormat(Cell cell) {
        if (ValidateUtil.isNull(cell)) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            return df.format(cell.getNumericCellValue());
        }
        return cell.toString();
    }


    /**
     * 导出接口日志到Excel
     *
     * @param sheet       页数
     * @param excelWriter
     * @param datas       数据
     */
    public static Boolean exportInterfaceLog2Excel(int sheet, ExcelWriter excelWriter, List<SysLoggerInfo> datas) {
        boolean flag = true;
        try {
            WriteSheet writeSheet = EasyExcel.writerSheet(sheet, "sheet" + sheet).build();
            excelWriter.write(datas, writeSheet);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("迭代删除接口日志,生产日志Excel失败！" + ex.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 判断单元格是否为空
     *
     * @param cell
     * @return
     */
    public static boolean cellIsEmpty(Cell cell) {
        if (ValidateUtil.isNull(cell)) return Boolean.TRUE;
        cell.setCellType(CellType.STRING);
        String str = cell.toString();
        return str == null || "".equals(str + "");
    }

    private static String getStr(Cell cell) {
        return cellIsEmpty(cell) ? "" : StringFormat(cell);
    }

    private static BigDecimal getDec(Cell cell) {
        return new BigDecimal(cellIsEmpty(cell) ? "0" : StringFormat(cell));
    }

    private static Integer getInt(Cell cell) {
        return ValidateUtil.isEmpty(StringFormat(cell)) ? null : Integer.parseInt(StringFormat(cell));
    }

    /**
     * 根据列数计算列号
     *
     * @param num 列数
     * @return
     */
    public static String numCovertLetter(int num) {
        if (num <= 0) {
            return num + "";
        }
        String str = "";
        while (num > 0) {
            int res = num % 26;
            if (res == 0) {
                res = 26;
            }
            str = (char) (res + 64) + str;
            num = (num - res) / 26;
        }
        return str;
    }

    private static void checkDictionary(List<String> cons, String value, int row, int index, String dicCode) {
        if (cons != null && !CollectionUtils.isEmpty(cons)) {
            if (ValidateUtil.isNotEmpty(value) && !cons.contains(value))
                throw new JPMException(String.format(FileMsg.unknownFormat, row, numCovertLetter(index + 1)));
        } else
            throw new JPMException(String.format(FileMsg.notFoundDictionary, dicCode));
    }
}
