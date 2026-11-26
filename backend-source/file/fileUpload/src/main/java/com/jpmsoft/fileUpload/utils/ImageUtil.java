package com.jpmsoft.fileUpload.utils;

// import org.opencv.core.*;
// import org.opencv.imgcodecs.Imgcodecs;
// import org.opencv.imgproc.CLAHE;
// import org.opencv.imgproc.Imgproc;
// import org.opencv.photo.Photo;
//
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;

/**
 * 图片处理优化
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/19 11:37
 */
public class ImageUtil {

//    static {
//        System.load("D:\\opencv\\opencv\\build\\java\\x64\\opencv_java490.dll");
//    }
//
//    public static void processWithColor(String inputPath, String outputPath) {
//        // 1. 读取图像
//        Mat src = Imgcodecs.imread(inputPath);
//        Mat gray = new Mat();
//        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
//        // 2. 边缘检测
//        Mat edges = new Mat();
//        Imgproc.Canny(gray, edges, 50, 150);
//        // 3. 霍夫线变换检测直线
//        Mat lines = new Mat();
//        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 100, 100, 10);
//        // 4. 计算倾斜角度（取所有直线角度的中位数）
//        List<Double> angles = new ArrayList<>();
//        for (int i = 0; i < lines.rows(); i++) {
//            double[] line = lines.get(i, 0);
//            double x1 = line[0], y1 = line[1], x2 = line[2], y2 = line[3];
//            double angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
//            if (Math.abs(angle) < 45) { // 过滤接近垂直的线
//                angles.add(angle);
//            }
//        }
//        // 5. 计算中位数角度
//        angles.sort(Double::compare);
//        double medianAngle = angles.get(angles.size() / 2);
//        // 6. 旋转矫正
//        Mat corrected = rotateImageWithPadding(src, medianAngle);
//        // 7. 保存结果
//        Imgcodecs.imwrite(outputPath, enhanceImage(corrected));
//    }
//
//    public static Mat enhanceImage(Mat src) {
//        // 1. 灰度化与去噪
//        Mat gray = new Mat();
//        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.GaussianBlur(gray, gray, new Size(5,5), 0);
//        // 2. 非局部均值去噪
//        Mat denoised = new Mat();
//        Photo.fastNlMeansDenoising(gray, denoised, 15, 7, 21);
//        // 3. CLAHE对比度增强
//        Mat enhanced = new Mat();
//        CLAHE clahe = Imgproc.createCLAHE(2.5, new Size(8,8));
//        clahe.apply(denoised, enhanced);
//        // 4. 边缘锐化
//        Mat sharpened = new Mat();
//        Imgproc.Laplacian(enhanced, sharpened, CvType.CV_16S, 5);
//        Core.convertScaleAbs(sharpened, sharpened);
//        Core.addWeighted(enhanced, 1.5, sharpened, -0.5, 0, enhanced);
//        // 5. 颜色校正（LAB空间归一化）
//        Mat lab = new Mat();
//        Imgproc.cvtColor(src, lab, Imgproc.COLOR_BGR2Lab);
//        Core.normalize(lab, lab, 0, 255, Core.NORM_MINMAX);
//        Imgproc.cvtColor(lab, lab, Imgproc.COLOR_Lab2BGR);
//        // 6. 融合结果
//        Mat finalResult = new Mat();
//        Mat enhancedColor = new Mat();
//        Imgproc.cvtColor(enhanced, enhancedColor, Imgproc.COLOR_GRAY2BGR);
//        if (!lab.size().equals(enhancedColor.size())) {
//            Imgproc.resize(enhancedColor, enhancedColor, lab.size());
//        }
//        Core.addWeighted(lab, 0.7, enhancedColor, 0.3, 0, finalResult);
//        return finalResult;
//    }
//
//    public static Mat textZq(Mat src) {
//        // 1. 转换到LAB颜色空间
//        Mat labImg = new Mat();
//        Imgproc.cvtColor(src, labImg, Imgproc.COLOR_BGR2Lab);
//        List<Mat> labChannels = new ArrayList<>();
//        Core.split(labImg, labChannels);
//        Mat lightness = labChannels.get(0);
//        // 2. 亮度通道处理
//        Mat denoised = new Mat();
//        Photo.fastNlMeansDenoising(lightness, denoised, 10, 7, 21);
//        Mat blurred = new Mat();
//        Imgproc.GaussianBlur(denoised, blurred, new Size(0, 0), 3);
//        Mat sharpened = new Mat();
//        Core.addWeighted(denoised, 1.8, blurred, -0.8, 0, sharpened);
//        // 3. CLAHE增强
//        CLAHE clahe = Imgproc.createCLAHE(2.0, new Size(8, 8));
//        Mat claheMat = new Mat();
//        clahe.apply(sharpened, claheMat);
//        // 4. 合并通道并还原颜色
//        labChannels.set(0, claheMat);
//        Core.merge(labChannels, labImg);
//        Mat result = new Mat();
//        Imgproc.cvtColor(labImg, result, Imgproc.COLOR_Lab2BGR);
//        // 可选：Gamma校正
//        float gamma = 0.9f;
//        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
//        byte[] lutData = new byte[256];
//        for (int i = 0; i < 256; i++) {
//            lutData[i] = (byte) Math.min(255, Math.pow(i / 255.0, gamma) * 255);
//        }
//        lut.put(0, 0, lutData);
//        Core.LUT(result, lut, result);
//        return result;
//    }
//
//    //region 轮廓检测将图片旋转修正
//    // 核心校正方法
//    public static Mat autoCorrectTextOrientation(Mat src) {
//        // 1. 预处理增强文本特征
//        Mat processed = preprocessImage(src);
//        // 2. 检测主要文本区域角度
//        double skewAngle = detectTextSkewAngle(processed);
//        System.out.println("主要文本区域旋转角度" + skewAngle);
//        // 3. 执行旋转校正
//        return rotateImageWithPadding(src, skewAngle);
//    }
//
//    // 图像预处理（优化文本边缘检测）
//    private static Mat preprocessImage(Mat src) {
//        Mat gray = new Mat();
//        Mat blurred = new Mat();
//        Mat edges = new Mat();
//        // 多步骤预处理流程
//        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 1.2);
//        Imgproc.Canny(blurred, edges, 50, 150);
//        // 形态学闭合操作增强文本区域
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
//        Imgproc.morphologyEx(edges, edges, Imgproc.MORPH_CLOSE, kernel);
//        return edges;
//    }
//
//    // 文本倾斜角度检测（改进版）
//    private static double detectTextSkewAngle(Mat edges) {
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//        Imgproc.findContours(edges, contours, hierarchy,
//                Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        // 按面积降序排序轮廓
//        contours.sort(Comparator.comparingDouble(c ->
//                -Imgproc.contourArea(c)
//        ));
//
//        // 分析前3大轮廓获取稳定角度
//        List<Double> angles = new ArrayList<>();
//        for (int i = 0; i < Math.min(3, contours.size()); i++) {
//            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
//            RotatedRect rect = Imgproc.minAreaRect(contour2f);
//            angles.add(calculateValidAngle(rect));
//        }
//
//        // 取角度中值作为最终结果
//        return angles.stream()
//                .sorted()
//                .skip(angles.size() / 2)
//                .findFirst()
//                .orElse(0.0);
//    }
//
//    // 有效角度计算逻辑（修正OpenCV 4.9.0方向定义）
//    private static double calculateValidAngle(RotatedRect rect) {
//        double angle = rect.angle;
//        Size size = rect.size;
//        // 根据OpenCV 4.9.0的新定义调整角度
//        if (size.width < size.height) {
//            angle = (angle > 0) ? angle - 90 : angle + 90;
//        }
//        return (angle > 45) ? angle - 180 : (angle < -45) ? angle + 180 : angle;
//    }
//
//    // 智能旋转带边界保护
//    private static Mat rotateImageWithPadding(Mat src, double angle) {
//        if (angle == 0) return src.clone();
//        Point center = new Point(src.cols() / 2.0, src.rows() / 2.0);
//        Mat rotMat = Imgproc.getRotationMatrix2D(center, angle, 1.0);
//        // 计算新边界尺寸
//        Rect bbox = computeNewBoundingBox(src.size(), angle);
//        // 调整旋转矩阵的平移分量
//        adjustTranslation(rotMat, bbox, center);
//        // 执行仿射变换
//        Mat dst = new Mat();
//        Imgproc.warpAffine(src, dst, rotMat, bbox.size(),
//                Imgproc.INTER_LINEAR,
//                Core.BORDER_CONSTANT,
//                new Scalar(255, 255, 255));
//        return dst;
//    }
//
//    // 计算旋转后的新边界框
//    private static Rect computeNewBoundingBox(Size srcSize, double angle) {
//        double radians = Math.toRadians(angle);
//        double width = srcSize.width;
//        double height = srcSize.height;
//
//        // 计算新画布尺寸
//        double newWidth = Math.abs(width * Math.cos(radians))
//                + Math.abs(height * Math.sin(radians));
//        double newHeight = Math.abs(width * Math.sin(radians))
//                + Math.abs(height * Math.cos(radians));
//
//        return new Rect(0, 0, (int) Math.ceil(newWidth), (int) Math.ceil(newHeight));
//    }
//
//    // 调整平移分量（解决Java API类型问题）
//    private static void adjustTranslation(Mat rotMat, Rect bbox, Point center) {
//        double[] m02 = rotMat.get(0, 2);
//        rotMat.put(0, 2, m02[0] + (bbox.width / 2.0 - center.x));
//        double[] m12 = rotMat.get(1, 2);
//        rotMat.put(1, 2, m12[0] + (bbox.height / 2.0 - center.y));
//    }
//
//    //endregion

}
