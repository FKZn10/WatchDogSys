package com.jpmsoft.fileUpload.utils;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.pojo.dto.WatermarkControlDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

public class ItextPdfUtil {

    /**
     * 图像的乘法因子
     */
    public static float FACTOR = 0.5f;

    /**
     * pdf水印
     *
     * @param pdfPath
     * @param text
     * @param wc
     * @param outputStream
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static OutputStream addWaterMark(String pdfPath, String text, WatermarkControlDto wc, OutputStream outputStream) throws IOException, DocumentException {
        File pdf = new File(pdfPath);
        InputStream inputStream = new FileInputStream(pdf);
        // 待加水印的文件
        PdfReader reader = new PdfReader(inputStream);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        try {


        // 设置字体
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED); // 华文宋体
        // 水印透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(wc.getWebAlpha());
        gs.setStrokeOpacity(wc.getWebAlpha());
        // PDF总页数
        int total = reader.getNumberOfPages() + 1;
        // 循环对每页插入水印
        PdfContentByte content;
        BaseColor fontColor = new BaseColor(255, 248, 220);
        if (ValidateUtil.isNotEmpty(wc.getColorRgb())) {
            String[] rgb = wc.getColorRgb().split(",");
            fontColor = new BaseColor(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
        }
        for (int i = 1; i < total; i++) {
            // 水印在之前文本之上
            content = stamper.getOverContent(i);
            // 透明度
            content.setGState(gs);
            // 文字水印
            if (text != null) {
                content.beginText();
                // 设置颜色
//                content.setColorFill(new BaseColor(Color.lightGray.getRGB()));
                content.setColorFill(fontColor);
                // 设置字体及字号
                content.setFontAndSize(font, wc.getPdfViewFontSize());
                // 设置起始位置
                content.setTextMatrix(0, 0);
                // 字符间距
                content.setCharacterSpacing(1);
                String[] allText = text.split("\\n");
                String str = AsposeUtil.getLongestString(allText);
                int fontWidth = str.length() * wc.getPdfViewFontSize();
                int fontHight = allText.length * wc.getPdfViewFontSize();
                for (int j = 0; j < 500; j = j + 70 + fontWidth) {
                    for (int k = 0; k < 700; k = k + 50 + fontHight) {
                        content.showTextAligned(Element.ALIGN_LEFT, text, j, k, wc.getRotation());
                    }
                }
                content.endText();
            }
        }
        }finally {
            stamper.close();
            reader.close();
            inputStream.close();
        }
        return outputStream;
    }


    /**
     * @param src  源文件
     * @param dest 目标文件
     * @throws IOException
     * @throws DocumentException
     */
    public static void manipulatePdf(InputStream src, String dest) throws IOException, DocumentException {
        PdfName key = new PdfName("ITXT_SpecialId");
        PdfName value = new PdfName("123456789");
        // 读取pdf文件
        PdfReader reader = new PdfReader(src);
        int n = reader.getXrefSize();
        PdfObject object;
        PRStream stream;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {

            object = reader.getPdfObject(i);
            PdfObject pdfObject = reader.getPdfObject(i);
            if (object == null || !object.isStream())
                continue;
            stream = (PRStream) object;
            PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
            System.out.println(stream.type());
            if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                PdfImageObject image = new PdfImageObject(stream);
                BufferedImage bi = image.getBufferedImage();
                if (bi == null) continue;
                int width = (int) (bi.getWidth() * FACTOR);
                int height = (int) (bi.getHeight() * FACTOR);
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
                Graphics2D g = img.createGraphics();
                g.drawRenderedImage(bi, at);
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                //判断文件流的大小，超过500k的才进行压缩，否则不进行压缩
                if (img.getData().getDataBuffer().getSize() > 512000) {
                    ImageIO.write(img, "JPG", imgBytes);
                    stream.clear();
                    stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
                    stream.put(PdfName.TYPE, PdfName.XOBJECT);
                    stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
                    stream.put(key, value);
                    stream.put(PdfName.FILTER, PdfName.DCTDECODE);
                    stream.put(PdfName.WIDTH, new PdfNumber(width));
                    stream.put(PdfName.HEIGHT, new PdfNumber(height));
                    stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                    stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
                } else {
                    ImageIO.write(img, "JPG", imgBytes);
                }
            }
        }
        // Save altered PDF
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }

}
