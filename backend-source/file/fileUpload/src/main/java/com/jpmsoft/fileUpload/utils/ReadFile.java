package com.jpmsoft.fileUpload.utils;

import com.google.common.collect.Lists;
import com.jpmsoft.fileUpload.utils.EncodingDetect;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.sl.extractor.SlideShowExtractor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 读取文件内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/24 9:01
 */
@Slf4j
public class ReadFile {


    /**
     * IK分词
     * @param target
     * @return
     */
    public static List<String> iKSegmenterToList(String target) throws Exception {
        if (StringUtils.isEmpty(target)){
            return Lists.newArrayList();
        }
        List<String> result = new ArrayList<>();
        StringReader sr = new StringReader(target);
        // 关闭智能分词 (对分词的精度影响较大)
        IKSegmenter ik = new IKSegmenter(sr, false);
        Lexeme lex;
        while((lex=ik.next())!=null) {
            String lexemeText = lex.getLexemeText();
            result.add(lexemeText);
        }
        return result;
    }


    /**
     * 读取文件内容
     *
     * @param path 文件绝对路径
     * @return
     */
    public static String read(String path) {
        String suffix = path.substring(path.lastIndexOf('.') + 1);
        if (suffix.equalsIgnoreCase("doc")) return readDoc(path);
        else if (suffix.equalsIgnoreCase("docx")) return readDocx(path);
        else if (suffix.equalsIgnoreCase("pdf")) return readPdf(path);
        else if (suffix.equalsIgnoreCase("xls")) return readXls(path);
        else if (suffix.equalsIgnoreCase("xlsx")) return readXlsx(path);
        else if (suffix.equalsIgnoreCase("pptx")) return readPPTX(path);
        else if (suffix.equalsIgnoreCase("ppt")) return readPPT(path);
        else return readFile(path);
    }


    /**
     * 读取文本内容（自动获取编码格式）
     */
    public static String readFile(String path) {
        File file = new File(path);
        try {
            String fileEncode = EncodingDetect.getJavaEncode(path);
            return FileUtils.readFileToString(file, fileEncode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 读取doc文件
     */
    public static String readDoc(String path) {
        FileInputStream in;
        String text = null;
        try {
            in = new FileInputStream(path);
            WordExtractor extractor = new WordExtractor(in);
            text = extractor.getText();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 读取 PDF文本内容
     */
    public static String readPdf(String path) {
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        try {
            // 读取文本内容
            PDDocument document = PDDocument.load(file);
            // 获取页码
            int pages = document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            content.append(stripper.getText(document));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 读取xls文件
     *
     * @param path
     * @return
     */
    public static String readXls(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) return "";
        StringBuilder content = new StringBuilder();
        try {
            HSSFWorkbook excel = new HSSFWorkbook(Files.newInputStream(file.toPath()));
            //获取第一个sheet
            HSSFSheet sheet0 = excel.getSheetAt(0);
            for (Iterator rowIterator = sheet0.iterator(); rowIterator.hasNext(); ) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                for (Iterator iterator = row.cellIterator(); iterator.hasNext(); ) {
                    HSSFCell cell = (HSSFCell) iterator.next();
                    //根据单元的的类型 读取相应的结果
                    if (cell.getCellType() == CellType.STRING)
                        content.append(cell.getStringCellValue() + "\t");
                    else if (cell.getCellType() == CellType.NUMERIC
                            || cell.getCellType() == CellType.FORMULA)
                        content.append(cell.getNumericCellValue() + "\t");
                    else
                        content.append("" + "\t");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.warn(e.toString());
        }
        return content.toString();
    }

    /**
     * 读取xlsx文件
     */
    public static String readXlsx(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) return "";
        StringBuilder content = new StringBuilder();
        try {
            XSSFWorkbook excel = new XSSFWorkbook(Files.newInputStream(file.toPath()));
            //获取第一个sheet
            XSSFSheet sheet0 = excel.getSheetAt(0);
            for (Iterator rowIterator = sheet0.iterator(); rowIterator.hasNext(); ) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                for (Iterator iterator = row.cellIterator(); iterator.hasNext(); ) {
                    XSSFCell cell = (XSSFCell) iterator.next();
                    //根据单元格的类型 读取相应的结果
                    if (cell.getCellType() == CellType.STRING)
                        content.append(cell.getStringCellValue() + "\t");
                    else if (cell.getCellType() == CellType.NUMERIC
                            || cell.getCellType() == CellType.FORMULA)
                        content.append(cell.getNumericCellValue() + "\t");
                    else
                        content.append("" + "\t");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e.toString());
        }
        return content.toString();
    }


    /**
     * 读取pptx文件
     */
    public static String readPPTX(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) return "";
        StringBuffer content = new StringBuffer();
        try {
            XMLSlideShow xmlSlideShow = new XMLSlideShow(Files.newInputStream(file.toPath()));
            List<XSLFSlide> slides = xmlSlideShow.getSlides();            //获得每一张幻灯片
            for (XSLFSlide slide : slides) {
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape spTree = rawSlide.getCSld().getSpTree();
                List<CTShape> spList = spTree.getSpList();
                for (CTShape shape : spList) {
                    CTTextBody txBody = shape.getTxBody();
                    if (null == txBody) {
                        continue;
                    }
                    List<CTTextParagraph> pList = txBody.getPList();
                    for (CTTextParagraph textParagraph : pList) {
                        List<CTRegularTextRun> textRuns = textParagraph.getRList();
                        for (CTRegularTextRun textRun : textRuns) {
                            content.append(textRun.getT());
                        }
                    }
                }
            }
            xmlSlideShow.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 读取ppt文件
     */
    public static String readPPT(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) return "";
        StringBuilder content = new StringBuilder();
        try {
            HSLFSlideShow hslfSlideShow = new HSLFSlideShow(Files.newInputStream(file.toPath()));
            List<HSLFSlide> slides = hslfSlideShow.getSlides();
            SlideShowExtractor slideShowExtractor = new SlideShowExtractor(hslfSlideShow);

            for (HSLFSlide slide : slides) {
                content.append(slideShowExtractor.getText(slide));
            }
            slideShowExtractor.close();
        } catch (IOException e) {
            log.warn(e.toString());
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 读取docx文件
     */
    public static String readDocx(String path) {
        String buffer = "";
        try {
            OPCPackage opcPackage = POIXMLDocument.openPackage(path);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            buffer = extractor.getText();
            extractor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
