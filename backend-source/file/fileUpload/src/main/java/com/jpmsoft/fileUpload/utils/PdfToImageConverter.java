package com.jpmsoft.fileUpload.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * PDF转为一张张图片
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/3 18:08
 */
public class PdfToImageConverter {


    /**
     * 将PDF逐页转换为独立图片
     * @param pdfFilePath  PDF文件路径
     * @param outputDir    输出目录
     * @param imageFormat  图片格式（png/jpg）
     * @param dpi          分辨率（推荐300以上‌:ml-citation{ref="3,4" data="citationList"}）
     */
    public static void convertToImages(String pdfFilePath, String outputDir,
                                       String imageFormat, int dpi) throws Exception {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = renderer.renderImageWithDPI(page, dpi);
                String outputPath = String.format("%s/page_%d.%s",
                        outputDir, page+1, imageFormat);
                ImageIO.write(image, imageFormat, new File(outputPath));
            }
        }
    }
}
