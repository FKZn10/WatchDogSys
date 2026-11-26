package com.jpmsoft.fileUpload.utils;


import com.aspose.cells.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * aspose-cells
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/28 16:07
 */
@Slf4j
public class CellsUtil {

    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = CellsUtil.class.getClassLoader().getResourceAsStream("\\license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 支持DOC, DOCX, OOXML, RTF, HTML, OpenDocument, PDF, EPUB, XPS, SWF等相互转换
     *
     * @param excelfilePath [原始excel路径]
     * @param pdfFilePath   [输出路径]
     */
    public static Boolean excelConvertToPdf(String excelfilePath, String pdfFilePath) {
        try {
            // 原始excel路径
            Workbook wb = new Workbook(excelfilePath);
            // 输出路径
            File pdfFile = new File(pdfFilePath);
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * excel加密，增加水印
     *
     * @param path
     * @param pwd
     * @return
     */
    public static Boolean encrySyExcel(String path, String pwd) {
        // 验证License
        if (!getLicense())
            return false;
        try {
            // 原始excel路径
            Workbook wb = new Workbook(path);
            //加密
            wb.getSettings().setPassword(pwd);
            wb.setEncryptionOptions(EncryptionType.XOR, 40);
            wb.setEncryptionOptions(EncryptionType.STRONG_CRYPTOGRAPHIC_PROVIDER, 128);
            //水印
            wb.save(path);
        } catch (Exception ex) {
            log.error("excel加密，增加水印操作失败:" + ex.getMessage(),ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }


}
