package com.jpmsoft.fileUpload.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OcrUtil {

    private static final ITesseract TESSERACT;

    static {
        try {
            initTesseractDataPath();
            TESSERACT = new Tesseract();
            // 设置物理文件系统中的 tessdata 路径
            TESSERACT.setDatapath(getTesseractDataPath());
            // 设置识别语言（根据你的需求调整，如 chi_sim 是简体中文）
            TESSERACT.setLanguage("chi_sim");
        } catch (Exception e) {
            throw new RuntimeException("Tesseract 初始化失败", e);
        }
    }

    // 获取 Tesseract 数据目录的物理路径
    private static String getTesseractDataPath() {
        // 推荐使用系统临时目录，也可以自定义固定路径（如 /home/tesseract/）
        return System.getProperty("java.io.tmpdir") + File.separator + "tesseract";
    }

    // 初始化 Tesseract 数据路径：从 JAR 包复制资源到物理文件系统
    private static void initTesseractDataPath() throws IOException {
        String targetPath = getTesseractDataPath();
        File targetDir = new File(targetPath);
        // 如果目标目录不存在，创建并复制 JAR 内的资源
        if (!targetDir.exists()) {
            targetDir.mkdirs();
            // 读取 JAR 包内的 tesseract 目录下的所有文件（以 .traineddata 为例）
            ClassPathResource resource = new ClassPathResource("tesseract/");
            // 遍历 JAR 内的资源文件（这里假设你需要复制所有 .traineddata 文件）
            String[] dataFiles = {"chi_sim.traineddata"}; // 根据你的实际文件调整
            for (String fileName : dataFiles) {
                try (InputStream is = new ClassPathResource("tesseract/" + fileName).getInputStream()) {
                    Path targetFile = Paths.get(targetPath, fileName);
                    FileCopyUtils.copy(is, Files.newOutputStream(targetFile));
                }
            }
        }
    }

    /**
     * 将 resources 下的语言包复制到临时目录
     */
    private static void copyResourceToTemp(ClassPathResource resource, File targetDir) throws IOException {
        String fileName = resource.getFilename();
        File targetFile = new File(targetDir, fileName);
        // 已存在则跳过复制
        if (targetFile.exists()) {
            return;
        }
        // 通过流复制（适配 JAR 包内资源）
        try (InputStream is = resource.getInputStream();
             FileOutputStream fos = new FileOutputStream(targetFile)) {
            FileCopyUtils.copy(is, fos);
        }
    }

    /**
     * 识别图片文本（支持BufferedImage/File）
     *
     * @param image 图片对象
     * @return 识别后的文本
     */
    public static String recognizeImage(BufferedImage image) {
        try {
            return TESSERACT.doOCR(image).replaceAll("\\s+", " ").trim();
        } catch (TesseractException e) {
            return "OCR识别失败：" + e.getMessage();
        }
    }

    public static String recognizeImage(File imageFile) {
        try {
            return TESSERACT.doOCR(imageFile).replaceAll("\\s+", " ").trim();
        } catch (TesseractException e) {
            return "OCR识别失败：" + e.getMessage();
        }
    }
}
