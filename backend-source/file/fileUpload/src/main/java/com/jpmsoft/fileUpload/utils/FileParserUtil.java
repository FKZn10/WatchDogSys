package com.jpmsoft.fileUpload.utils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;


public class FileParserUtil {

    /**
     * 解析Word文档（.docx），提取所有文字 + 图片OCR文字
     * @param wordFile Word文件
     * @param tempImageDir 临时图片保存目录（用于存放提取的内嵌图片）
     * @return 合并后的文本内容
     * @throws IOException 解析异常
     */
    public static String parseWord(File wordFile, String tempImageDir) throws IOException {
        StringBuilder totalText = new StringBuilder();
        FileInputStream fis = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(fis);

        // 1. 提取Word中的纯文字
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            String paraText = para.getText().trim();
            if (!paraText.isEmpty()) {
                totalText.append(paraText).append("\n");
            }
        }

        // 2. 提取Word中的内嵌图片，并做OCR识别
        List<XWPFPictureData> pictureDatas = document.getAllPictures();
        File tempDir = new File(tempImageDir);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        for (int i = 0; i < pictureDatas.size(); i++) {
            XWPFPictureData picData = pictureDatas.get(i);
            // 【关键修正】替换 writeImageContent 为 write 方法
            String picFileName = tempImageDir + "/word_img_" + i + "." + picData.suggestFileExtension();
            File picFile = new File(picFileName);

            // 手动写入图片字节到文件
            try (FileOutputStream fos = new FileOutputStream(picFile)) {
                fos.write(picData.getData());
            }

            // OCR识别图片文本
            String ocrText = OcrUtil.recognizeImage(picFile);
            totalText.append("[图片").append(i+1).append("识别内容]：").append(ocrText).append("\n");
            // 删除临时图片（可选，避免占用空间）
            picFile.delete();
        }

        document.close();
        fis.close();
        return totalText.toString();
    }


    /**
     * 解析PDF文档，提取所有文字 + 图片/扫描件OCR文字
     * @param pdfFile PDF文件
     * @param tempImageDir 临时图片保存目录
     * @return 合并后的文本内容
     * @throws IOException 解析异常
     */
    public static String parsePdf(File pdfFile, String tempImageDir) throws IOException {
        StringBuilder totalText = new StringBuilder();
        PDDocument document = PDDocument.load(pdfFile);

        // 检查PDF是否加密
        if (document.isEncrypted()) {
            document.close();
            throw new IOException("PDF文件已加密，无法解析");
        }

        // 1. 提取PDF中的纯文字
        PDFTextStripper stripper = new PDFTextStripper();
        String plainText = stripper.getText(document);
        if (!plainText.trim().isEmpty()) {
            totalText.append(plainText).append("\n");
        }

        // 2. 提取PDF中的内嵌图片，并做OCR识别
        // 【关键修正】getPages() 返回 PDPageTree，通过迭代器遍历
        PDPageTree pageTree = document.getPages();
        Iterator<PDPage> pageIterator = pageTree.iterator();

        File tempDir = new File(tempImageDir);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        int imgIndex = 0;
        int pageNum = 0; // 页码计数
        while (pageIterator.hasNext()) {
            PDPage page = pageIterator.next();
            pageNum++;
            PDResources resources = page.getResources();

            // 提取当前页的内嵌图片
            for (COSName name : resources.getXObjectNames()) {
                if (resources.isImageXObject(name)) {
                    PDImageXObject image = (PDImageXObject) resources.getXObject(name);
                    BufferedImage bufferedImage = image.getImage();
                    // OCR识别内嵌图片
                    String ocrText = OcrUtil.recognizeImage(bufferedImage);
                    totalText.append("[PDF内嵌图片").append(++imgIndex).append("识别内容]：").append(ocrText).append("\n");
                }
            }

            // 3. 若PDF是扫描件（纯图片），渲染整页为图片并OCR
            // 判定逻辑：纯文字为空 → 视为扫描件
            if (plainText.trim().isEmpty()) {
                PDFRenderer renderer = new PDFRenderer(document);
                // 【注意】renderImageWithDPI 的第一个参数是 0 开始的页码索引
                BufferedImage pageImage = renderer.renderImageWithDPI(pageNum - 1, 300); // 300DPI保证清晰度
                String ocrText = OcrUtil.recognizeImage(pageImage);
                totalText.append("[PDF扫描页").append(pageNum).append("识别内容]：").append(ocrText).append("\n");
            }
        }

        document.close();
        return totalText.toString();
    }
}
