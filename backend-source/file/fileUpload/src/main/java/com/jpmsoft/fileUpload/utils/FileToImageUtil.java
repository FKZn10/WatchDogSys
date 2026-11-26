package com.jpmsoft.fileUpload.utils;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.PageSet;
import com.aspose.words.SaveFormat;
import com.jpmsoft.common.exception.JPMException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

/**
 * 附件统一处理
 * 转换成拼接成图片用于AI识别
 */
public class FileToImageUtil {


    /**
     * 最大支持的页数，防止内存溢出
     */
    private static final int MAX_PAGE_COUNT = 500;


    /***********************************************统一入口**************************************************/

    /**
     * 统一入口: 自动识别文件类型并转换为 Base64 图片
     * 支持格式: PDF, DOC, DOCX, PPT, PPTX, JPG, JPEG, PNG, GIF, BMP, WEBP
     *
     * @param file 上传的文件
     * @return data:image/png;base64,xxx 或对应格式的 base64 字符串
     * @throws IOException IO异常
     * @throws Exception   其他异常
     */
    public static String convertToBase64(MultipartFile file) throws IOException, Exception {
        return convertToBase64(file, 100);
    }

    /**
     * 统一入口: 自动识别文件类型并转换为 Base64 图片(可自定义参数)
     *
     * @param file    上传的文件
     * @param quality 质量参数(PDF/Word的DPI, PPT的缩放比例)
     * @return data:image/png;base64,xxx 或对应格式的 base64 字符串
     * @throws IOException IO异常
     * @throws Exception   其他异常
     */
    public static String convertToBase64(MultipartFile file, double quality) throws IOException, Exception {
        if (file == null || file.isEmpty()) {
            throw new IOException("文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("文件名不能为空");
        }
        String extension = getFileExtension(fileName).toLowerCase();
        switch (extension) {
            case "pdf":
                return pdfToSingleImageBase64(file, (int) quality);
            case "doc":
            case "docx":
                return wordToSingleImageBase64(file, (int) quality);
            case "ppt":
            case "pptx":
                return pptToSingleImageBase64(file, quality / 100d);
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
            case "webp":
                return imageToBase64(file);
            default:
                throw new JPMException("仅支持PDF, DOC, DOCX, PPT, PPTX, JPG, JPEG, PNG, GIF, BMP, WEBP格式！");
        }
    }


    /**
     * PDF 所有页面合并为 1 张纵向大图 → 转 Base64（可直接传给AI）
     *
     * @param pdfFile 上传的PDF文件
     * @param dpi     清晰度（推荐 150 / 200 / 300）
     * @return data:image/png;base64,xxx
     */
    public static String pdfToSingleImageBase64(MultipartFile pdfFile, int dpi) throws IOException {
        // 先将文件内容读入字节数组，避免临时文件被删除的问题
        byte[] pdfBytes = pdfFile.getBytes();
        
        PDDocument document = null;
        try {
            // 使用字节数组创建输入流
            document = PDDocument.load(pdfBytes);
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();

            if (pageCount > MAX_PAGE_COUNT) {
                throw new IOException("PDF页数超过限制(" + MAX_PAGE_COUNT + "页)，可能引起内存溢出");
            }

            BufferedImage[] pageImages = new BufferedImage[pageCount];
            int totalWidth = 0;
            int totalHeight = 0;

            try {
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage img = renderer.renderImageWithDPI(i, dpi);
                    pageImages[i] = img;
                    totalWidth = Math.max(totalWidth, img.getWidth());
                    totalHeight += img.getHeight();
                }

                BufferedImage combinedImage = null;
                ByteArrayOutputStream baos = null;
                try {
                    combinedImage = new BufferedImage(
                            totalWidth,
                            totalHeight,
                            BufferedImage.TYPE_INT_RGB
                    );
                    Graphics2D g2d = combinedImage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, totalWidth, totalHeight);

                    int currentY = 0;
                    for (BufferedImage img : pageImages) {
                        g2d.drawImage(img, 0, currentY, null);
                        currentY += img.getHeight();
                    }
                    g2d.dispose();

                    baos = new ByteArrayOutputStream();
                    ImageIO.write(combinedImage, "png", baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                    return "data:image/png;base64," + base64;
                } finally {
                    if (baos != null) {
                        try {
                            baos.close();
                        } catch (IOException e) {
                        }
                    }
                    if (combinedImage != null) {
                        combinedImage.flush();
                    }
                }
            } finally {
                for (BufferedImage img : pageImages) {
                    if (img != null) {
                        img.flush();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new JPMException("PDF转换失败！");
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * DOC/DOCX 所有页面合并为 1 张纵向大图 → 转 Base64(可直接传给AI)
     *
     * @param wordFile 上传的Word文件(支持.doc和.docx)
     * @param dpi      清晰度(推荐 150 / 200 / 300)
     * @return data:image/png;base64,xxx
     * @throws IOException IO异常
     * @throws Exception   其他异常
     */
    public static String wordToSingleImageBase64(MultipartFile wordFile, int dpi) throws IOException, Exception {
        InputStream inputStream = null;
        try {
            inputStream = wordFile.getInputStream();
            Document doc = new Document(inputStream);

            int pageCount = doc.getPageCount();

            if (pageCount > MAX_PAGE_COUNT) {
                throw new IOException("Word文档页数超过限制(" + MAX_PAGE_COUNT + "页)，可能引起内存溢出");
            }

            BufferedImage[] pageImages = new BufferedImage[pageCount];
            int totalWidth = 0;
            int totalHeight = 0;

            try {
                for (int i = 0; i < pageCount; i++) {
                    ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
                    options.setPageSet(new PageSet(i));
                    options.setResolution(dpi);

                    ByteArrayOutputStream pageBaos = null;
                    ByteArrayInputStream pageBais = null;
                    try {
                        pageBaos = new ByteArrayOutputStream();
                        doc.save(pageBaos, options);

                        pageBais = new ByteArrayInputStream(pageBaos.toByteArray());
                        BufferedImage img = ImageIO.read(pageBais);
                        pageImages[i] = img;
                        totalWidth = Math.max(totalWidth, img.getWidth());
                        totalHeight += img.getHeight();
                    } finally {
                        if (pageBaos != null) {
                            try {
                                pageBaos.close();
                            } catch (IOException e) {
                            }
                        }
                        if (pageBais != null) {
                            try {
                                pageBais.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }

                BufferedImage combinedImage = null;
                ByteArrayOutputStream baos = null;
                try {
                    combinedImage = new BufferedImage(
                            totalWidth,
                            totalHeight,
                            BufferedImage.TYPE_INT_RGB
                    );
                    Graphics2D g2d = combinedImage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, totalWidth, totalHeight);

                    int currentY = 0;
                    for (BufferedImage img : pageImages) {
                        g2d.drawImage(img, 0, currentY, null);
                        currentY += img.getHeight();
                    }
                    g2d.dispose();

                    baos = new ByteArrayOutputStream();
                    ImageIO.write(combinedImage, "png", baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                    return "data:image/png;base64," + base64;
                } finally {
                    if (baos != null) {
                        try {
                            baos.close();
                        } catch (IOException e) {
                        }
                    }
                    if (combinedImage != null) {
                        combinedImage.flush();
                    }
                }
            } finally {
                for (BufferedImage img : pageImages) {
                    if (img != null) {
                        img.flush();
                    }
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


    /**
     * PPT/PPTX 所有页面合并为 1 张纵向大图 → 转 Base64(可直接传给AI)
     *
     * @param pptFile 上传的PPT文件(支持.ppt和.pptx)
     * @param scale   缩放比例(推荐 2.0 / 3.0,数值越大越清晰)
     * @return data:image/png;base64,xxx
     * @throws IOException IO异常
     */
    public static String pptToSingleImageBase64(MultipartFile pptFile, double scale) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = pptFile.getInputStream();
            String fileName = pptFile.getOriginalFilename();

            BufferedImage[] pageImages;

            if (fileName != null && fileName.toLowerCase().endsWith(".pptx")) {
                pageImages = convertPptxToImages(inputStream, scale);
            } else {
                pageImages = convertPptToImages(inputStream, scale);
            }

            if (pageImages == null || pageImages.length == 0) {
                throw new IOException("无法解析PPT文件");
            }

            if (pageImages.length > MAX_PAGE_COUNT) {
                throw new IOException("PPT幻灯片数量超过限制(" + MAX_PAGE_COUNT + "页)，可能引起内存溢出");
            }

            int totalWidth = 0;
            int totalHeight = 0;
            for (BufferedImage img : pageImages) {
                totalWidth = Math.max(totalWidth, img.getWidth());
                totalHeight += img.getHeight();
            }

            BufferedImage combinedImage = null;
            ByteArrayOutputStream baos = null;
            try {
                combinedImage = new BufferedImage(
                        totalWidth,
                        totalHeight,
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = combinedImage.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, totalWidth, totalHeight);

                int currentY = 0;
                for (BufferedImage img : pageImages) {
                    g2d.drawImage(img, 0, currentY, null);
                    currentY += img.getHeight();
                }
                g2d.dispose();

                baos = new ByteArrayOutputStream();
                ImageIO.write(combinedImage, "png", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                return "data:image/png;base64," + base64;
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                    }
                }
                if (combinedImage != null) {
                    combinedImage.flush();
                }
                for (BufferedImage img : pageImages) {
                    if (img != null) {
                        img.flush();
                    }
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 将 PPTX 文件转换为图片数组
     */
    private static BufferedImage[] convertPptxToImages(InputStream inputStream, double scale) throws IOException {
        XMLSlideShow ppt = null;
        try {
            ppt = new XMLSlideShow(inputStream);
            Dimension pageSize = ppt.getPageSize();

            int slideCount = ppt.getSlides().size();
            BufferedImage[] images = new BufferedImage[slideCount];

            for (int i = 0; i < slideCount; i++) {
                XSLFSlide slide = ppt.getSlides().get(i);

                int width = (int) (pageSize.width * scale);
                int height = (int) (pageSize.height * scale);

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = null;
                try {
                    graphics = img.createGraphics();

                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                    graphics.setColor(Color.WHITE);
                    graphics.fill(new Rectangle2D.Double(0, 0, width, height));

                    graphics.scale(scale, scale);

                    slide.draw(graphics);
                } finally {
                    if (graphics != null) {
                        graphics.dispose();
                    }
                }

                images[i] = img;
            }

            return images;
        } finally {
            if (ppt != null) {
                try {
                    ppt.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 将 PPT 文件转换为图片数组
     */
    private static BufferedImage[] convertPptToImages(InputStream inputStream, double scale) throws IOException {
        HSLFSlideShow ppt = null;
        try {
            ppt = new HSLFSlideShow(inputStream);
            Dimension pageSize = ppt.getPageSize();

            List<HSLFSlide> slides = ppt.getSlides();
            BufferedImage[] images = new BufferedImage[slides.size()];

            for (int i = 0; i < slides.size(); i++) {
                HSLFSlide slide = slides.get(i);

                int width = (int) (pageSize.width * scale);
                int height = (int) (pageSize.height * scale);

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = null;
                try {
                    graphics = img.createGraphics();

                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                    graphics.setColor(Color.WHITE);
                    graphics.fill(new Rectangle2D.Double(0, 0, width, height));

                    graphics.scale(scale, scale);

                    slide.draw(graphics);
                } finally {
                    if (graphics != null) {
                        graphics.dispose();
                    }
                }

                images[i] = img;
            }

            return images;
        } finally {
            if (ppt != null) {
                try {
                    ppt.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /***********************************************处理图片**************************************************/


    /**
     * 图片文件转 Base64(可直接传给AI)
     * 支持格式: jpg, jpeg, png, gif, bmp, webp
     *
     * @param imageFile 上传的图片文件
     * @return data:image/png;base64,xxx 或对应格式的 base64 字符串
     * @throws IOException IO异常
     */
    public static String imageToBase64(MultipartFile imageFile) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = imageFile.getInputStream();
            String fileName = imageFile.getOriginalFilename();

            if (fileName == null || fileName.isEmpty()) {
                throw new IOException("文件名不能为空");
            }

            String extension = getFileExtension(fileName).toLowerCase();
            String mimeType = getMimeTypeByExtension(extension);

            if (mimeType == null) {
                throw new IOException("不支持的图片格式: " + extension);
            }

            BufferedImage image = null;
            try {
                image = ImageIO.read(inputStream);
                if (image == null) {
                    throw new IOException("无法读取图片文件");
                }

                ByteArrayOutputStream baos = null;
                try {
                    baos = new ByteArrayOutputStream();

                    String outputFormat = extension;
                    if ("jpg".equals(extension)) {
                        outputFormat = "jpeg";
                    } else if (!isSupportedOutputFormat(extension)) {
                        outputFormat = "png";
                        mimeType = "image/png";
                    }

                    ImageIO.write(image, outputFormat, baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

                    return mimeType + ";base64," + base64;
                } finally {
                    if (baos != null) {
                        baos.close();
                    }
                }
            } finally {
                if (image != null) {
                    image.flush();
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 图片文件转 Base64(可指定输出格式)
     *
     * @param imageFile    上传的图片文件
     * @param outputFormat 输出格式(jpg, png, gif等)
     * @return data:image/png;base64,xxx 或对应格式的 base64 字符串
     * @throws IOException IO异常
     */
    public static String imageToBase64WithFormat(MultipartFile imageFile, String outputFormat) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = imageFile.getInputStream();

            BufferedImage image = null;
            try {
                image = ImageIO.read(inputStream);
                if (image == null) {
                    throw new IOException("无法读取图片文件");
                }

                String format = outputFormat.toLowerCase();
                if ("jpg".equals(format)) {
                    format = "jpeg";
                }

                String mimeType = getMimeTypeByExtension(format);
                if (mimeType == null) {
                    mimeType = "image/png";
                    format = "png";
                }

                ByteArrayOutputStream baos = null;
                try {
                    baos = new ByteArrayOutputStream();
                    ImageIO.write(image, format, baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

                    return mimeType + ";base64," + base64;
                } finally {
                    if (baos != null) {
                        baos.close();
                    }
                }
            } finally {
                if (image != null) {
                    image.flush();
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获取文件扩展名
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    /**
     * 根据扩展名获取 MIME 类型
     */
    private static String getMimeTypeByExtension(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            default:
                return null;
        }
    }

    /**
     * 检查是否为支持的输出格式
     */
    private static boolean isSupportedOutputFormat(String format) {
        String lowerFormat = format.toLowerCase();
        return "jpeg".equals(lowerFormat) || "png".equals(lowerFormat)
                || "gif".equals(lowerFormat) || "bmp".equals(lowerFormat)
                || "webp".equals(lowerFormat) || "jpg".equals(lowerFormat);
    }
}
