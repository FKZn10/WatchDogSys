package com.jpmsoft.fileUpload.utils;


import com.jpmsoft.common.utils.ValidateUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.regex.Pattern;

public class FileTxtReadUtil {


    // 重复套话（可自行增删）
    private static final String[] STOP_WORDS = {
            "甲方：", "乙方：", "合同编号：", "签订日期：",
            "本合同一式", "页脚", "页眉", "机密等级",
            "第[一二三四五六七八九十0-9]章", "第[一二三四五六七八九十0-9]条"
    };

    /**
     * MultipartFile 上传 → 提取纯净正文（支持 .doc 和 .docx）
     */
    public static String extractCleanText(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        
        // 根据文件扩展名选择解析方式
        if (fileName.toLowerCase().endsWith(".docx")) {
            return extractFromDocx(file);
        } else if (fileName.toLowerCase().endsWith(".doc")) {
            return extractFromDoc(file);
        } else {
            throw new IllegalArgumentException("不支持的文件格式: " + fileName);
        }
    }

    /**
     * 从 .docx 文件中提取文本
     */
    private static String extractFromDocx(MultipartFile file) throws Exception {
        try (InputStream in = file.getInputStream();
             XWPFDocument doc = new XWPFDocument(in)) {
            StringBuilder sb = new StringBuilder();
            // 1. 提取正文段落（自动排除页眉页脚）
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String text = paragraph.getText();
                if (ValidateUtil.isEmpty(text)) continue;
                // 过滤水印
                if (isWatermark(paragraph)) continue;
                // 清洗空格、空行
                String clean = cleanLine(text);
                // 过滤套话
                if (isStopLine(clean)) continue;
                sb.append(clean).append("\n");
            }
            // 2. 提取表格内容（合同必备）
            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String cellText = cell.getText();
                        if (ValidateUtil.isEmpty(cellText)) continue;
                        String clean = cleanLine(cellText);
                        if (!isStopLine(clean)) {
                            sb.append(clean).append(" ");
                        }
                    }
                }
                sb.append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * 从 .doc 文件中提取文本
     */
    private static String extractFromDoc(MultipartFile file) throws Exception {
        try (InputStream in = file.getInputStream();
             POIFSFileSystem fs = new POIFSFileSystem(in);
             HWPFDocument doc = new HWPFDocument(fs)) {
            
            StringBuilder sb = new StringBuilder();
            Range range = doc.getRange();
            
            // 1. 提取段落文本
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph para = range.getParagraph(i);
                String text = para.text();
                if (ValidateUtil.isEmpty(text)) continue;
                
                String clean = cleanLine(text);
                if (isStopLine(clean)) continue;
                sb.append(clean).append("\n");
            }
            
            // 2. 提取表格内容（通过遍历整个文档范围查找表格）
            try {
                // HWPF 中表格嵌入在段落中，需要遍历所有元素
                // 使用 Set 记录已处理的表格，避免重复
                java.util.Set<Integer> processedTables = new java.util.HashSet<>();
                
                for (int i = 0; i < range.numParagraphs(); i++) {
                    Paragraph para = range.getParagraph(i);
                    // 检查段落是否包含表格
                    if (para.isInTable()) {
                        try {
                            // 使用 range.getTable(para) 获取表格对象
                            Table table = range.getTable(para);
                            if (table != null) {
                                // 使用表格的 hashCode 作为唯一标识
                                int tableId = System.identityHashCode(table);
                                if (!processedTables.contains(tableId)) {
                                    processedTables.add(tableId);
                                    
                                    for (int rowNum = 0; rowNum < table.numRows(); rowNum++) {
                                        TableRow row = table.getRow(rowNum);
                                        if (row == null) continue;
                                        
                                        for (int cellNum = 0; cellNum < row.numCells(); cellNum++) {
                                            TableCell cell = row.getCell(cellNum);
                                            if (cell == null) continue;
                                            
                                            String cellText = cell.text();
                                            if (ValidateUtil.isEmpty(cellText)) continue;
                                            
                                            String clean = cleanLine(cellText);
                                            if (!isStopLine(clean)) {
                                                sb.append(clean).append(" ");
                                            }
                                        }
                                    }
                                    sb.append("\n");
                                }
                            }
                        } catch (Exception e) {
                            // 忽略单个表格解析错误（例如：不是表格的第一个段落）
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("提取.doc表格时出错: " + e.getMessage());
            }
            
            return sb.toString().trim();
        }
    }

    // 清洗空格、换行、多余空白
    private static String cleanLine(String text) {
        text = Pattern.compile("\\s+").matcher(text).replaceAll(" ");
        return text.trim();
    }

    // 判断是否水印
    private static boolean isWatermark(XWPFParagraph para) {
        try {
            return para.getCTP().getRList().stream()
                    .anyMatch(r -> r.getDrawingList() != null && !r.getDrawingList().isEmpty());
        } catch (Exception e) {
            return false;
        }
    }

    // 判断是否套话
    private static boolean isStopLine(String line) {
        for (String word : STOP_WORDS) {
            if (line.matches(".*" + word + ".*") || line.contains(word)) {
                return true;
            }
        }
        return false;
    }

}
