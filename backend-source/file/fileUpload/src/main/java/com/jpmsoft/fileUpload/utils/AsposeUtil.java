package com.jpmsoft.fileUpload.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.aspose.pdf.ImageStamp;
import com.aspose.pdf.Page;
import com.aspose.words.*;
import com.aspose.words.Font;
import com.aspose.words.Shape;
import com.jpmsoft.common.model.export.response.GanttTasksProcessModel;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectLibResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.pojo.dto.OutlineWatermarkControlDto;
import com.jpmsoft.fileUpload.pojo.dto.WatermarkControlDto;
import com.jpmsoft.fileUpload.pojo.dto.aspose.ReplaceAndInsertImage;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.pojo.entity.Watermark;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class AsposeUtil {

    /**
     * 加载license 用于破解 不生成水印
     *
     * @author Aaron
     * @date 2021/09/25 13:51
     */
    @SneakyThrows
    private static void getLicense() {
        try (InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("license.xml")) {
            License license = new License();
            license.setLicense(is);
        }
    }

    public static void imgToPdf(String imgPath, String pdfPath){
        imgToPdf(imgPath,pdfPath,100d,100d);
    }

    @SneakyThrows
    public static void imgToPdf(String imgPath, String pdfPath,double w,double h) {
        getLicense();
        // 创建PDF文档
        Document pdfDoc = new Document();
        DocumentBuilder builder = new DocumentBuilder(pdfDoc);
//        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        Shape shape = builder.insertImage(imgPath);
        double imgWidth = shape.getWidth();
        double imgHeight = shape.getHeight();
//        // 定义页面尺寸 (A4纸张尺寸: 595 x 842 磅)
//        double pageWidth = 400;
//        double pageHeight = 540;
        boolean rotate = imgWidth > imgHeight;
//        // 计算缩放比例以适应页面
//        double widthScale = pageWidth / imgWidth;
//        double heightScale = pageHeight / imgHeight;
//        double scale = Math.min(widthScale, heightScale) * 0.9; // 留出一些边距
        // 应用缩放

        if (rotate) {
            // 旋转图片90度
            shape.setRotation(90);
            shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
            shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
            shape.setHorizontalAlignment(HorizontalAlignment.CENTER);
            shape.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        shape.setWidth(w);
        shape.setHeight(h);
        // 确保图片在页面中居中显示
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        shape.setHorizontalAlignment(HorizontalAlignment.CENTER);
        shape.setVerticalAlignment(VerticalAlignment.CENTER);
        pdfDoc.save(pdfPath);
        File file = new File(pdfPath);
        if (file.exists()) {
            log.info("图片转换为PDF成功");
            File img = new File(imgPath);
            if (img.exists()) img.delete();
        } else {
            log.error("图片转换为PDF失败");
        }
    }

    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath, Watermark w, String fontsDirectory, WatermarkControlDto wc, LogUserInfoVo user) {
        try {
            getLicense();
            File file = new File(pdfPath);
            FontSettings fs = new FontSettings();
            if (!ValidateUtil.isTrimEmpty(fontsDirectory)) fs.setFontsFolder(fontsDirectory, true);
            else fs.setFontsFolder(AsposeUtil.class.getResource("/").getPath() + "fonts", true);
            try (FileOutputStream os = new FileOutputStream(file)) {
                Document doc = new Document(wordPath);
                doc.setFontSettings(fs);
                waterMarkImgMore(doc, w, wc);
//            if (wc.isPdfViewEnable()) WaterMarkMore(doc, getWmText(user, wc), wc);
                PdfSaveOptions pdfSO = new PdfSaveOptions();
                pdfSO.setSaveFormat(SaveFormat.PDF);
                pdfSO.setTextCompression(PdfTextCompression.FLATE);
                pdfSO.setImageCompression(PdfImageCompression.JPEG);
//            pdfSO.setUseHighQualityRendering(false);
//            pdfSO.setJpegQuality(100);
                doc.save(os, pdfSO);
            }
        } finally {
            if (w.getDeletePath()) new File(w.getPath()).delete();
        }
    }

    /**
     * word添加水印
     */
    @SneakyThrows
    public static void wordWatermark(String wordPath, String waterPath, Watermark w, String fontsDirectory, WatermarkControlDto wc, LogUserInfoVo user) {
        try {
            getLicense();
            FontSettings fs = new FontSettings();
            if (!ValidateUtil.isTrimEmpty(fontsDirectory)) fs.setFontsFolder(fontsDirectory, true);
            else fs.setFontsFolder(AsposeUtil.class.getResource("/").getPath() + "fonts", true);
            Document doc = new Document(wordPath);
            doc.setFontSettings(fs);
//        Shape watermark = new Shape(doc, ShapeType.IMAGE);
//        watermark.getImageData().setImage(w.getPath());
//        // 设置水印属性
//        watermark.setBehindText(true);         // 置于文字下方
//        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
//        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
//        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
//        watermark.setWrapType(WrapType.NONE);
//        watermark.setWidth(w.getWWidth());               // 宽度
//        watermark.setHeight(w.getWHeight());              // 高度
//        watermark.setRotation(w.getWRotation());            // 旋转角度
//        watermark.setOpacity(w.getWOpacity());             // 透明度
            // 插入到页眉
//        // 确保主页眉存在
//        HeaderFooterCollection headersFooters = doc.getFirstSection().getHeadersFooters();
//        if (headersFooters.getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY) == null) {
//            headersFooters.add(new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY));
//        }
//        HeaderFooter header =headersFooters.getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
            // 创建多行图片水印
            waterMarkImgMore(doc, w, wc, false);
            doc.save(waterPath);
        } finally {
            if (w.getDeletePath()) new File(w.getPath()).delete();
        }
    }

    /**
     * pdf添加水印
     */
    @SneakyThrows
    public static void pdfWatermark(String wordPath, String waterPath, Watermark w, String fontsDirectory, WatermarkControlDto wc, LogUserInfoVo user) {
//        getLicense();
//        FontSettings fs = new FontSettings();
//        if (!ValidateUtil.isTrimEmpty(fontsDirectory)) fs.setFontsFolder(fontsDirectory, true);
//        else fs.setFontsFolder(AsposeUtil.class.getResource("/").getPath() + "fonts", true);
        try {
            com.aspose.pdf.Document pdfDoc = new com.aspose.pdf.Document(wordPath);
//        ImageStamp imageStamp = new ImageStamp(w.getPath());
//        imageStamp.setBackground(true);       // 作为背景
//        imageStamp.setHeight(w.getWHeight());
//        imageStamp.setWidth(w.getWWidth());
//        imageStamp.setOpacity(w.getWOpacity());// 透明度
//        imageStamp.setRotate((int)w.getWOpacity());   // 旋转角度
            // 添加到每一页
            for (Page page : pdfDoc.getPages()) {
                addMultiRowWatermark(page, w);
            }
            pdfDoc.save(waterPath);
        } finally {
            if (w.getDeletePath()) new File(w.getPath()).delete();
        }

    }


    public static void addMultiRowWatermark(Page page, Watermark w) {
        // 加载水印图片
        ImageStamp imageStamp = new ImageStamp(w.getPath());
        imageStamp.setBackground(false);       // 作为背景
        imageStamp.setHeight(w.getWHeight());
        imageStamp.setWidth(w.getWWidth());
        imageStamp.setOpacity(w.getWOpacity());// 透明度
        imageStamp.setRotateAngle(w.getWRotation());   // 旋转角度

        // 计算页面尺寸与水印间距
        double pageWidth = page.getPageInfo().getWidth();
        double pageHeight = page.getPageInfo().getHeight();
        int rows = (int) pageHeight / ((int) w.getWHeight() + 50);
        int cols = (int) pageWidth / ((int) w.getWWidth() + 70);
        double horizontalStep = pageWidth / cols;
        double verticalStep = pageHeight / rows;

        // 循环添加水印
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 计算水印位置（居中偏移）
                double x = horizontalStep * j + horizontalStep / 4;
                double y = verticalStep * i + verticalStep / 4;
                imageStamp.setXIndent(x);
                imageStamp.setYIndent(y);
                page.addStamp(imageStamp);
            }
        }
    }


    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     * @author Aaron
     * @date 2021/09/25 13:51
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath, String fontsDirectory, WatermarkControlDto wc, LogUserInfoVo user, SysUser userEntity) {
        getLicense();
        File file = new File(pdfPath);
        FontSettings fs = new FontSettings();
        if (!ValidateUtil.isTrimEmpty(fontsDirectory)) fs.setFontsFolder(fontsDirectory, true);
        else fs.setFontsFolder(AsposeUtil.class.getResource("/").getPath() + "fonts", true);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordPath);
            doc.setFontSettings(fs);
            if (wc.isPdfViewEnable()) WaterMarkMore(doc, getWmText(user, wc, userEntity), wc);
            PdfSaveOptions pdfSO = new PdfSaveOptions();
            pdfSO.setSaveFormat(SaveFormat.PDF);
            pdfSO.setTextCompression(PdfTextCompression.FLATE);
            pdfSO.setImageCompression(PdfImageCompression.JPEG);
//            pdfSO.setUseHighQualityRendering(false);
//            pdfSO.setJpegQuality(100);
            doc.save(os, pdfSO);
        }
    }

    /**
     * word转pdf
     *
     * @param wordPath
     * @param pdfPath
     * @param fontsDirectory
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath, String fontsDirectory) {
        getLicense();
        List<String> fontDirs = new ArrayList<>();
        Collections.addAll(fontDirs,
                "C:/Windows/Fonts",
                System.getProperty("user.home") + "/AppData/Local/Microsoft/Windows/Fonts",
                "/usr/share/fonts", "/usr/local/share/fonts", "/app/fonts"
        );
        if (fontsDirectory != null && !fontsDirectory.trim().isEmpty()) {
            fontDirs.add(fontsDirectory.trim());
        }
        String appFonts = AsposeUtil.class.getResource("/") != null
                ? AsposeUtil.class.getResource("/").getPath() + "fonts" : null;
        if (appFonts != null && !appFonts.trim().isEmpty()) {
            fontDirs.add(appFonts);
        }
        fontDirs = fontDirs.stream()
                .filter(p -> p != null && new File(p).exists())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        FontSettings fonts = FontSettings.getDefaultInstance();
        fonts.setFontsFolders(fontDirs.toArray(new String[0]), true);

        // 设置默认字体
        fonts.getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("SimSun");

        Document doc = new Document(wordPath);
        doc.setFontSettings(fonts);

        for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true)) {
            table.autoFit(AutoFitBehavior.FIXED_COLUMN_WIDTHS);
            table.setAllowAutoFit(false);
            table.setLeftIndent(0);

            Section sec = (Section) table.getAncestor(NodeType.SECTION);
            if (sec != null) {
                PageSetup ps = sec.getPageSetup();
                double pageWidth = ps.getPageWidth() - ps.getLeftMargin() - ps.getRightMargin();
                table.setPreferredWidth(PreferredWidth.fromPoints(pageWidth));
            }

            if (table.getTextWrapping() != TextWrapping.NONE) {
                table.setTextWrapping(TextWrapping.NONE);
                table.setAlignment(TableAlignment.CENTER);
            }
        }

        // 刷新字段和页面布局
        doc.updateFields();
        doc.updatePageLayout();

        // 设置警告回调
        doc.setWarningCallback(info -> log.warn("[Aspose Warning] {}", info.getDescription()));

        PdfSaveOptions opts = new PdfSaveOptions();
        opts.setCompliance(PdfCompliance.PDF_A_2_U);
        opts.setEmbedFullFonts(false);
        opts.setJpegQuality(100);
        opts.setUseHighQualityRendering(true);
        opts.getOutlineOptions().setHeadingsOutlineLevels(6);
        opts.getOutlineOptions().setExpandedOutlineLevels(3);

        try (FileOutputStream os = new FileOutputStream(pdfPath)) {
            doc.save(os, opts);
        }
    }

    /**
     * 根据模板填充数据生成word
     *
     * @param path       生成word保存的绝对路径
     * @param tempPath   模板文件路径
     * @param dataSource 数据源
     * @param sourceName 数据源别名
     * @param imgs       需要替换的文件 key标记value图片地址
     * @param w          图像的宽度
     * @param h          图像的高度
     */
    @SneakyThrows
    public static void tempExportWord(String tempPath, String path, Object dataSource, String sourceName, Map<String, String> imgs, Double w, Double h) {
        getLicense();
        //ClassPathResource classPathResource = new ClassPathResource("template/" + tempName);
        Document doc = new Document(tempPath);
        ReportingEngine engine = new ReportingEngine();
        engine.buildReport(doc, dataSource, sourceName);
        replaceText2Image(imgs, doc, w, h);
        doc.save(path);
    }

    @SneakyThrows
    public static void tempExportWord(String tempPath, String path, Object[] dataSources, String[] sourceNames, Map<String, String> imgs, Double w, Double h) {
        getLicense();
        Document doc = new Document(tempPath);
        ReportingEngine engine = new ReportingEngine();
        engine.buildReport(doc, dataSources, sourceNames);
        replaceText2Image(imgs, doc, w, h);
        doc.save(path);
    }

    /**
     * 批量替换图片
     *
     * @param imgs key标记 value 图片地址
     * @param doc
     * @throws Exception
     */
    private static void replaceText2Image(Map<String, String> imgs, Document doc, Double w, Double h) throws Exception {
        if (imgs == null) return;
        Range range = doc.getRange();
        for (Map.Entry<String, String> entry : imgs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            key = key.replace("\\", "\\\\");
            key = key.replace("$", "\\$");
            key = key.replace("[", "\\[");
            key = key.replace("]", "\\]");
            key = key.replace("(", "\\(");
            key = key.replace(")", "\\)");
            key = key.replace("|", "\\|");
            key = key.replace("+", "\\+");
            key = key.replace("?", "\\?");
            key = key.replace("*", "\\*");
            boolean bool = false;
            if (value.toLowerCase(Locale.ENGLISH).endsWith(".webp")) {
                String webp = value.toLowerCase(Locale.ENGLISH);
                value = webp.replace(".webap", ".jpg");
                Webp2Jpeg.webp2jpg(webp, value);
                bool = true;
            }
            ReplaceAndInsertImage rm;
            if (w != null && h != null) rm = new ReplaceAndInsertImage(value, w, h);
            else rm = new ReplaceAndInsertImage(value);
//            range.replace(Pattern.compile(key), rm, false);
            range.replace(Pattern.compile(key), "", new FindReplaceOptions(rm));
            if (bool) {
                File f = new File(value);
                if (f.exists() && f.isFile()) f.delete();
            }
        }
    }

    /**
     * 组装水印正文
     *
     * @param user
     * @param wc
     * @return
     */
    private static String getWmText(LogUserInfoVo user, WatermarkControlDto wc, SysUser userEntity) {
        return getWmText(user, userEntity, wc.getContents());
    }

    private static String getWmText(LogUserInfoVo user, OutlineWatermarkControlDto owc, SysUser userEntity) {
        return getWmText(user, userEntity, owc.getContents());
    }

    private static String getWmText(LogUserInfoVo user, SysUser userEntity, List<Map<String, Object>> contents) {
        StringBuilder sb = new StringBuilder();
        Date now = new Date();
        user.setConTables(null);
        Map<String, Object> userInfo = JacksonUtils.parseObject(JacksonUtils.toJSONString(user), Map.class);
        for (Map<String, Object> content : contents) {
            String str = "";
            try {
                if (content.containsKey("constant") && ValidateUtil.isNotEmpty(content.get("constant").toString())) {
                    //常量值 固定文本
                    str = content.get("constant").toString();
                } else if (content.containsKey("variable") && ValidateUtil.isNotEmpty(content.get("variable").toString())) {
                    //变量值 暂时仅适配登录返回user信息
                    StringSubstitutor sub = new StringSubstitutor(userInfo);
                    str = sub.replace(content.get("variable").toString());
                } else if (content.containsKey("currentTime") && ValidateUtil.isNotEmpty(content.get("currentTime").toString())) {
                    //格式化当前时间
                    str = DateUtil.Date2String(now, content.get("currentTime").toString());
                } else if (content.containsKey("phone") && ValidateUtil.isNotEmpty(content.get("phone").toString())
                        && ValidateUtil.isNotEmpty(userEntity.getuMtel())) {//用户手机号
                    String[] num = content.get("phone").toString().split(",");//截取索引位置
                    str = userEntity.getuMtel().substring(Integer.parseInt(num[0]), Integer.parseInt(num[1]));
                }
            } catch (Exception ex) {
                log.error("格式化水印参数异常:" + content + "", ex);
            }
            if (ValidateUtil.isNotEmpty(str)) {
                sb.append(str);
                if (content.containsKey("wrap") && content.get("wrap").toString().equalsIgnoreCase("true"))
                    sb.append("\n");
            }
        }
        return sb.toString();
    }

    @SneakyThrows
    public static void exportGanttTasksProcessWord(String docPath, List<GanttTasksProcessModel> models, String root, List<SysFileAnnex> photos, String title, String actionUser, List<String> dNameList) {
        getLicense();
        List<String> dNames = models.stream().filter(n -> !ValidateUtil.isEmpty(n.getdName())).map(GanttTasksProcessModel::getdName).distinct().collect(Collectors.toList());
        //项目序号
        if (dNameList != null && dNameList.size() > 0) {
            dNames = dNameList;
        }
        int pNum = 1;
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertHtml("<h1 style='text-align:center;'><font face='宋体' colo='black' style='font-size:40px;' >" + title + "</font></h1>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        for (int i = 0; i < dNames.size(); i++) {
            //地区统建室的序号
            int dNum = i + 1;
            String dName = dNames.get(i);
            //地区统建室下的项目名
            List<String> pNames = models.stream().filter(n -> !ValidateUtil.isEmpty(n.getpName()) && !ValidateUtil.isEmpty(n.getdName()) && n.getdName().equals(dName)).map(GanttTasksProcessModel::getpName).distinct().collect(Collectors.toList());
            //word中插入 地区统建室标题
            generateWordTitle(builder, 20, true, Color.BLACK, NumberUtil.int2chineseNum(dNum) + "、" + dName, true, 0d);
            for (String pName : pNames) {
                List<GanttTasksProcessModel> remarkProgress = models.stream().filter(n -> !ValidateUtil.isEmpty(n.getpName()) && !ValidateUtil.isEmpty(n.getdName()) && n.getdName().equals(dName) && n.getpName().equals(pName)).collect(Collectors.toList());
                //word中插入 项目标题
                generateWordTitle(builder, 16, true, Color.BLACK, "(" + NumberUtil.int2chineseNum(pNum) + ") " + pName, true, 20d);
                pNum += 1;
                for (int j = 0; j < remarkProgress.size(); j++) {
                    GanttTasksProcessModel progress = remarkProgress.get(j);
                    //word中插入 说明
                    String context = ValidateUtil.isEmpty(progress.getGtpImageProgress()) ? progress.getGtpRemark() : progress.getGtpImageProgress();
                    DecimalFormat decimalFormat = new DecimalFormat("0");
                    int rate = Integer.parseInt(decimalFormat.format(progress.getTaskProgress()));
                    String str = DateUtil.Date2String(progress.getGtpDate(), "MM月dd日") + "，" + progress.getTaskText() + "完成" + rate + "% (累计)";
                    if (!ValidateUtil.isEmpty(context)) {
                        builder.getParagraphFormat().setLineSpacing(18d);
                        generateWordTitle(builder, 14, false, Color.BLACK, "▲ " + str + "，" + context, false, 35d);
                    }
                    if (j == remarkProgress.size() - 1) builder.insertBreak(BreakType.LINE_BREAK);
                }
                builder.getParagraphFormat().setLineSpacing(12d);
            }
        }

        int p = 1;
        String imgOneMsg = "";
        //图片
        if (photos != null && photos.size() > 0) {
            //换页
            builder.insertBreak(BreakType.SECTION_BREAK_ODD_PAGE);
            Table table = builder.startTable();
            for (SysFileAnnex photo : photos) {
                File file = new File(root + photo.getFilePath());
                if (file.isFile() && file.exists()) {
                    if (p == 1) {
                        imgOneMsg = photo.getFileName();
                    }
                    builder.insertCell();
                    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
                    builder.insertImage(root + photo.getFilePath(), 170d, 170d);
//                Shape shape = new Shape(doc, ShapeType.IMAGE);
//                ImageData img = shape.getImageData();
//                img.setImage(root + photo.getFilePath());
//                shape.setWidth(170d);
//                shape.setHeight(170d);
//                if (p == 1)
//                    shape.setHorizontalAlignment(HorizontalAlignment.LEFT);
//                if (p == 2)
//                    shape.setHorizontalAlignment(HorizontalAlignment.RIGHT);
//                CompositeNode node = shape.getParentNode();
//                builder.insertNode(shape);
                    if (p == 2) {
                        p = 1;
                        builder.endRow();
                        builder.insertCell();
                        builder.write(imgOneMsg);
                        builder.insertCell();
                        builder.write(photo.getFileName());
                        builder.endRow();
                    } else p += 1;
                }
                table.setAlignment(TableAlignment.CENTER);
            }
            table.clearBorders();
            table.clearShading();
            builder.endTable();
        }
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        String actionStr = "导出时间：" + DateUtil.Date2String(new Date(), "yyyy年MM月dd日") + "  导出人员：" + actionUser;
        builder.insertHtml("<p style='text-align:right;'><font face='宋体' colo='black' style='font-size:18px;' >" + actionStr + "</font></p>");
        doc.save(docPath);
    }

    /**
     * 导出专家选取记录word
     * 线上
     *
     * @param ep
     * @param entity
     * @param docPath
     */
    @SneakyThrows
    public static void exportSecExpertSelectionRecordWord(SecExpert ep, SecSelectionRecord entity, String docPath, String title) {
        getLicense();
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHtml("<h1 style='text-align:center;'><font face='宋体' colo='black' style='font-size:40px;' >" + title + "</font></h1>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' >" + ep.getExName() + "：" + "</font></p>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' >&nbsp;&nbsp;" + "您好！根据工作需要，现在就以下事项向您进行专业咨询：" + "</font></p>");
        builder.insertHtml("<br/>");


        Table table = builder.startTable();
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.insertCell();
        builder.getCellFormat().setWidth(60.0);
        builder.write("咨询事项");
        builder.insertCell();
        //单元格垂直对齐方向
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        //单元格水平对齐方向
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
        builder.getCellFormat().setWidth(380.0);
        builder.write(ValidateUtil.isNotEmpty(entity.getSrItems()) ? entity.getSrItems() : "");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setWidth(60.0);
        builder.write("资料清单");
        builder.insertCell();
        //单元格垂直对齐方向
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        //单元格水平对齐方向
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
        builder.getCellFormat().setWidth(380.0);
        builder.write(ValidateUtil.isNotEmpty(entity.getSrDataLists()) ? entity.getSrDataLists() : "");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setWidth(60.0);
        builder.write("注意事项");
        builder.insertCell();
        //单元格垂直对齐方向
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        //单元格水平对齐方向
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
        builder.getCellFormat().setWidth(380.0);
        builder.write(ValidateUtil.isNotEmpty(entity.getSrNotes()) ? entity.getSrNotes() : "");
        builder.endRow();
        table.setAllowAutoFit(true);
        table.autoFit(AutoFitBehavior.FIXED_COLUMN_WIDTHS);
        builder.endTable();

        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' > " + "咨询单位（盖章）：" + "</font></p>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:right;'><font face='宋体' colo='black' style='font-size:18px;' > " + "年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;" + "</font></p>");
        builder.insertHtml("<br/>");
        doc.save(docPath);
    }


    /**
     * 导出专家选取记录word
     * 线下
     *
     * @param eps
     * @param entity
     * @param docPath
     */
    @SneakyThrows
    public static void exportExpertSelectionRecordWordOffline(List<SecExpert> eps, SecSelectionRecord entity, String docPath, String title, List<SysDictionary> dictionarys) {
        getLicense();
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHtml("<h1 style='text-align:center;'><font face='宋体' colo='black' style='font-size:40px;' >" + title + "</font></h1>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' > 会议开始时间：" + DateUtil.Date2String(entity.getSrStart(), "yyyy-MM-dd") + "&nbsp;&nbsp;&nbsp;选取时间：" + DateUtil.Date2String(entity.getSrAccessTime(), "yyyy-MM-dd") + "</font></p>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' > 选取人所属主管机构:" + entity.getSrCompetent() + "</font></p>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:left;'><font face='宋体' colo='black' style='font-size:18px;' > 会议事项简介:" + entity.getSrAbout() + "</font></p>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<br/>");
        builder.insertHtml("<p style='text-align:center;'><font face='宋体' colo='black' style='font-size:18px;' > 专家选取清单 </font></p>");
        Table table = builder.startTable();
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        //表头
        builder.insertCell();
        builder.write("序号");
        builder.insertCell();
        builder.write("姓名");
        builder.insertCell();
        builder.write("性别");
        builder.insertCell();
        builder.write("专家类别");
        builder.insertCell();
        builder.write("专家类型");
        builder.insertCell();
        builder.write("联系方式");
        builder.insertCell();
        builder.write("从事专业");
        builder.endRow();
        Integer index = 1;

        for (SecExpert ep : eps) {
            builder.insertCell();
            builder.write(index.toString());
            builder.insertCell();
            builder.write(ValidateUtil.isEmpty(ep.getExName()) ? "" : ep.getExName());
            builder.insertCell();
            builder.write(ValidateUtil.isEmpty(ep.getExSex()) ? "" : ep.getExSex());
            builder.insertCell();
            builder.write(ValidateUtil.isEmpty(ep.getExType()) ? "" : ep.getExType());
            builder.insertCell();
            String exCate = "";
            if (ValidateUtil.isNotEmpty(dictionarys) && ValidateUtil.isNotEmpty(ep.getExCategory())) {
                List<SysDictionary> dp = dictionarys.stream().filter(n -> n.getDCode().equals(ep.getExCategory())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(dp)) exCate = dp.stream().findFirst().get().getDName();
            }
            builder.write(exCate);
            builder.insertCell();
            String num2 = ValidateUtil.isEmpty(ep.getExCellphoneNumber2()) ? "" : ep.getExCellphoneNumber2();
            builder.write(ValidateUtil.isEmpty(ep.getExCellphoneNumber1()) ? num2 : ep.getExCellphoneNumber1());
            builder.insertCell();
            builder.write(ValidateUtil.isEmpty(ep.getExProfessionalCategory()) ? "" : ep.getExProfessionalCategory());
            builder.endRow();
            index++;
        }
        builder.endTable();
        doc.save(docPath);
    }


    /**
     * 文字
     */
    @SneakyThrows
    private static void generateWordTitle(DocumentBuilder builder, int fontSize, Boolean bold, Color color, String text, Boolean inBreak, Double leftIndent) {
        //文本格式
        Font font = builder.getFont();
        font.setSize(fontSize);
        font.setBold(bold);
        font.setColor(color);
        font.setName("宋体");
        //段落格式
        ParagraphFormat paragraphFormat = builder.getParagraphFormat();
        paragraphFormat.setLeftIndent(leftIndent);
        builder.writeln(text);
        if (inBreak) builder.insertBreak(BreakType.LINE_BREAK);
    }
    //region 水印添加方法

    /**
     * 为word文档添加水印
     * 单个水印
     *
     * @param doc           word文档模型
     * @param watermarkText 需要添加的水印字段
     * @throws Exception
     */
    public static void insertWatermarkText(Document doc, String watermarkText) throws Exception {
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //水印内容
        watermark.getTextPath().setText(watermarkText);
        //水印字体
        watermark.getTextPath().setFontFamily("宋体");
        //水印宽度
        watermark.setWidth(100);
        //水印高度
        watermark.setHeight(20);
        //旋转水印
        watermark.setRotation(-40);
        //水印颜色 浅灰色
        watermark.getFill().setColor(Color.lightGray);
        watermark.setStrokeColor(Color.lightGray);
        //设置相对水平位置
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        //设置相对垂直位置
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        //设置包装类型
        watermark.setWrapType(WrapType.NONE);
        //设置垂直对齐
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置文本水平对齐方式
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);

        for (Section sect : doc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
        //System.out.println("Watermark Set");
    }

    /**
     * 在页眉中插入水印
     *
     * @param watermarkPara
     * @param sect
     * @param headerType
     * @throws Exception
     */
    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null) {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        header.appendChild(watermarkPara.deepClone(true));
    }


    @SneakyThrows
    public static Shape shapeImgMore(Document doc, Watermark w, double left, double top, Boolean behidText) {
        Shape watermark = new Shape(doc, ShapeType.IMAGE);
        watermark.getImageData().setImage(w.getPath());
        // 设置水印属性
        watermark.setBehindText(behidText);         // 置于文字下方
        if (behidText == java.lang.Boolean.FALSE) watermark.setZOrder(Integer.MAX_VALUE);
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        watermark.setWrapType(WrapType.NONE);
        watermark.setWidth(w.getWWidth());               // 宽度
        watermark.setHeight(w.getWHeight());              // 高度
        watermark.setRotation(w.getWRotation());            // 旋转角度
        watermark.setOpacity(w.getWOpacity());
        // 调整位置（基于百分比）
        watermark.setLeft(left);
        watermark.setTop(top);
        return watermark;
    }


    /**
     * 设置水印属性
     *
     * @param doc
     * @param waterMark
     * @param left
     * @param top
     * @return
     * @throws Exception
     */
    public static Shape ShapeMore(Document doc, String waterMark, double left, double top, int w, int h, WatermarkControlDto wc) throws Exception {
        return ShapeMore(doc, waterMark, left, top, w, h, wc.getRotation(), 1, wc.getColorRgb(), false, "Arial");
    }

    public static Shape ShapeMore(Document doc, String waterMark, double left, double top, double w, double h, OutlineWatermarkControlDto owc) throws Exception {
        return ShapeMore(doc, waterMark, left, top, w, h, owc.getRotation(), owc.getAlpha(), owc.getColorRgb(), true, owc.getFont());
    }

    public static Shape ShapeMore(Document doc, String waterMark, double left, double top, double w, double h, float rotation, float alpha, String colorRgb, Boolean behindText, String font) throws Exception {
        Shape waterShape = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
//        Shape waterShape = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
//        Shape waterShape = new Shape(doc, ShapeType.IMAGE);
//        waterShape.getImageData().setImage(wmText);
        waterShape.getTextPath().setText(waterMark);
        waterShape.getTextPath().setFontFamily(font);
        waterShape.setWidth(w);
        waterShape.setHeight(h);
        waterShape.setRotation(rotation);
        waterShape.setFilled(true);
        Color color = new Color(255, 248, 220);
        if (ValidateUtil.isNotEmpty(colorRgb)) {
            String[] rgb = colorRgb.split(",");
            color = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
        }
        waterShape.setFillColor(color);
        waterShape.getFill().setTransparency(1 - alpha);
        waterShape.setStrokeColor(color);
        waterShape.getStroke().setTransparency(1 - alpha);
//        waterShape.setStrokeColor(new Color(210,210,210));
        //将水印放置的坐标
        waterShape.setLeft(left);
        waterShape.setTop(top);
//        //设置垂直对齐
//        waterShape.setVerticalAlignment(VerticalAlignment.CENTER);
//        //设置文本水平对齐方式
//        waterShape.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置包装类型
        waterShape.setWrapType(WrapType.NONE);
        waterShape.setBehindText(behindText);
        return waterShape;
    }


    /**
     * 插入多个水印
     *
     * @param mdoc
     * @param wmText
     * @throws Exception
     */
    public static void WaterMarkMore(Document mdoc, String wmText, WatermarkControlDto wc) throws Exception {
        //最长一行字符串
        String[] allText = wmText.split("\\n");
        String str = getLongestString(allText);
        int fontWidth = str.length() * wc.getPdfViewFontSize();
        int fontHight = allText.length * wc.getPdfViewFontSize();
        Paragraph watermarkPara = new Paragraph(mdoc);
        for (int j = 0; j < 500; j = j + 70 + fontWidth) {
            for (int i = 0; i < 700; i = i + 50 + fontHight) {
                Shape waterShape = ShapeMore(mdoc, wmText, j, i, fontWidth, fontHight, wc);
                watermarkPara.appendChild(waterShape);
            }
        }
        //Shape waterShape = ShapeMore(mdoc, wmText, 155, 300);
        //watermarkPara.appendChild(waterShape);
        for (Section sect : mdoc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    public static void WaterMarkMore(Document mdoc, String wmText, OutlineWatermarkControlDto owc) throws Exception {
        //最长一行字符串
        String[] allText = wmText.split("\\n");
        String str = getLongestString(allText);
        int fontWidth = str.length() * owc.getFontSize();
        int fontHight = owc.getFontSize() * 2;
        Paragraph watermarkPara = new Paragraph(mdoc);
        // 获取当前页尺寸
        double pageWidth = mdoc.getFirstSection().getPageSetup().getPageWidth();
        double pageHeight = mdoc.getFirstSection().getPageSetup().getPageHeight();
        // 根据需求调整步长：水平步长和垂直步长
        int horizontalIncrement = 120 + fontWidth;
        int verticalIncrement = 100 + fontHight;

        for (int j = 0; j < pageWidth; j = j + horizontalIncrement) {
            for (int i = 0; i < pageHeight; i = i + verticalIncrement) {
                Shape waterShape = ShapeMore(mdoc, wmText, j, i, fontWidth, fontHight, owc);
                watermarkPara.appendChild(waterShape);
            }
        }
        for (Section sect : mdoc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    private static List<Shape> WaterMarkMore(Document doc, String watermark, OutlineWatermarkControlDto owc, Section sec) throws Exception {
        List<Shape> result = new ArrayList<>();
        // 获取当前页尺寸
        double pageWidth = sec.getPageSetup().getPageWidth();
        double pageHeight = sec.getPageSetup().getPageHeight();
        String[] allText = watermark.split("\\n");
        String str = getLongestString(allText);
        double fontWidth = str.length() * owc.getFontSize();
        double fontHight = owc.getFontSize() * 1.5;
        double horizontalIncrement = 120 + fontWidth;
        double verticalIncrement = 100 + fontHight;

        for (int j = 0; j < pageWidth; j += (int) horizontalIncrement) {
            for (int i = 0; i < pageHeight; i += (int) verticalIncrement) {
                Shape shape = ShapeMore(doc, watermark, j, i, fontWidth, fontHight, owc);
                result.add(shape);
            }
        }
        return result;
    }

    public static void waterMarkImgMore(Document mdoc, Watermark w, WatermarkControlDto wc) throws Exception {
        //最长一行字符串
        int fontWidth = (int) w.getWWidth();
        int fontHight = (int) w.getWHeight();
        Paragraph watermarkPara = new Paragraph(mdoc);
        for (int j = 0; j < mdoc.getFirstSection().getPageSetup().getPageWidth(); j = j + 70 + fontWidth) {
            for (int i = 0; i < mdoc.getFirstSection().getPageSetup().getPageHeight(); i = i + 50 + fontHight) {
                Shape waterShape = shapeImgMore(mdoc, w, j, i, false);
                watermarkPara.appendChild(waterShape);
            }
        }
        for (Section sect : mdoc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }

    }

    public static void waterMarkImgMore(Document mdoc, Watermark w, WatermarkControlDto wc, Boolean behidText) throws Exception {
        //最长一行字符串
        int fontWidth = (int) w.getWWidth();
        int fontHight = (int) w.getWHeight();
        LayoutCollector collector = new LayoutCollector(mdoc);
        mdoc.updatePageLayout();  // 确保布局信息已更新‌:ml-citation{ref="8" data="citationList"}

        // 遍历所有段落节点（可替换为其他节点类型）
        NodeCollection paragraphs = mdoc.getChildNodes(NodeType.PARAGRAPH, true);
        Node[] nodes = paragraphs.toArray();
        Map<Integer, Node> pageStartNodes = new HashMap<>();
        for (Node node : nodes) {
            int pageIndex = collector.getStartPageIndex(node);
            if (!pageStartNodes.containsKey(pageIndex)) {
                pageStartNodes.put(pageIndex, node);
            }
        }
        for (Map.Entry<Integer, Node> entry : pageStartNodes.entrySet()) {
            DocumentBuilder builder = new DocumentBuilder(mdoc);
            builder.moveTo(entry.getValue());
            Section currentSection = (Section) entry.getValue().getAncestor(NodeType.SECTION);
            for (int j = 0; j < currentSection.getPageSetup().getPageWidth(); j = j + 70 + fontWidth) {
                for (int i = 0; i < currentSection.getPageSetup().getPageHeight(); i = i + 50 + fontHight) {
                    Shape waterShape = shapeImgMore(mdoc, w, j, i, behidText);
                    builder.insertNode(waterShape);
                }
            }
        }
    }
    //endregion

    public static String getLongestString(String[] animalNames) {
        String str = "";
        int j = 0;
        for (j = 0; j < animalNames.length; j++) {
            if (str.length() < animalNames[j].length()) str = animalNames[j];
        }
        return str;
    }

    //region word加密
    public static void encryWord(Document doc, String pwd) {
        /**
         * AllowOnlyComments——仅允许修改注释。
         * AllowOnlyFormFields——仅允许将数据输入到表单字段中。
         * AllowOnlyRevisions——仅允许添加修订标记。
         * ReadOnly——完全只读（不允许对文档进行任何更改）。
         * NoProtection——完全没有保护。
         */
        doc.protect(ProtectionType.READ_ONLY, pwd);
    }

    public static void unprotect(Document doc) {
        doc.unprotect();
    }
    //endregion


    @SneakyThrows
    public static void encrySyWord(String in, String out, String pwd) {
        getLicense();
//        Document doc = new Document(new FileInputStream(new File(in)));
//        AsposeUtil.WaterMarkMore(doc, "机密文件");
//        AsposeUtil.encryWord(doc, pwd);
//        doc.save(out);
    }

    // 磅 -> 厘米 换算系数
    static final double cmToPt = 28.3464567;

    /**
     * 导出项目管理大纲Word
     *
     * @param data
     * @param outPath
     * @param downloadUrl
     * @param token
     * @param owc
     * @param user
     * @param userEntity
     * @param template
     * @param styleDtlMap
     * @param gldgbmMap      项目编码对应的封面内容
     * @param outlineCatalog 是否添加目录
     */
    @SneakyThrows
    public static void exportOutlineWord(
            OutlineProjectManageResponse data,
            String outPath,
            String downloadUrl,
            String token,
            OutlineWatermarkControlDto owc,
            LogUserInfoVo user,
            SysUser userEntity,
            File template,
            Map<String, OutlineProjectStyleDtl> styleDtlMap,
            Map<String, String> gldgbmMap,
            boolean outlineCatalog
    ) {
        List<OutlineProjectLibResponse> listFlow = data.getOutlineProjectLibListFlow();
        if (listFlow == null || listFlow.isEmpty()) {
            throw new IllegalArgumentException("导出内容为空");
        }
        String title = data.getPName();
        getLicense();
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // 样式定义
        OutlineProjectStyleDtl titleStyle = styleDtlMap.get("10");
        OutlineProjectStyleDtl level1Style = styleDtlMap.get("11");
        OutlineProjectStyleDtl level2Style = styleDtlMap.get("12");
        OutlineProjectStyleDtl level3Style = styleDtlMap.get("13");
        OutlineProjectStyleDtl level4Style = styleDtlMap.get("14");
        OutlineProjectStyleDtl level5Style = styleDtlMap.get("15");
        OutlineProjectStyleDtl level6Style = styleDtlMap.get("16");
        OutlineProjectStyleDtl textStyle = styleDtlMap.get("01");
        OutlineProjectStyleDtl tableStyle = styleDtlMap.get("02");
        OutlineProjectStyleDtl imgStyle = styleDtlMap.get("03");
        OutlineProjectStyleDtl pageStyle = styleDtlMap.get("04");
        OutlineProjectStyleDtl catalogStyle = styleDtlMap.get("17");

        // 页面设置
        Section section = doc.getFirstSection();
        PageSetup pageSetup = section.getPageSetup();
        pageSetup.setLeftMargin(pageStyle.getMarginsLeft() * cmToPt);
        pageSetup.setRightMargin(pageStyle.getMarginsRight() * cmToPt);
        pageSetup.setTopMargin(pageStyle.getMarginsTop() * cmToPt);
        pageSetup.setBottomMargin(pageStyle.getMarginsBottom() * cmToPt);
        pageSetup.setPaperSize(PaperSize.A4);
        pageSetup.setOrientation(Orientation.PORTRAIT);

        // 渲染每个章节
        for (int idx = 0; idx < listFlow.size(); idx++) {
            OutlineProjectLibResponse node = listFlow.get(idx);
            String catalog = node.getOplCatalogName();
            String html = Optional.ofNullable(node.getOplContent())
                    .filter(content -> !isInvalidHtml(content))
                    .map(content -> convertImgToBase64(content, downloadUrl, token))
                    .map(content -> downgradeHtmlHeadings(content, node.getOplLevel()))
                    .orElse("");
            // 各级标题
            int level = node.getOplLevel();
            if (level == 1) {
                if (idx != 0) {
                    builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
                }
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
                setTitleStyle(builder, level1Style);
            } else if (level == 2) {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);
                setTitleStyle(builder, level2Style);
            } else if (level == 3) {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_3);
                setTitleStyle(builder, level3Style);
            } else if (level == 4) {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_4);
                setTitleStyle(builder, level4Style);
            } else if (level == 5) {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_5);
                setTitleStyle(builder, level5Style);
            } else if (level == 6) {
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_6);
                setTitleStyle(builder, level6Style);
            } else {
                builder.getParagraphFormat().setStyleIdentifier(getHeadingStyle(level));
                builder.getParagraphFormat().setSpaceBefore(0);
                builder.getParagraphFormat().setSpaceAfter(0);
                builder.getParagraphFormat().setLineSpacingRule(LineSpacingRule.EXACTLY);
                builder.getParagraphFormat().setLineSpacing(28);
                builder.getFont().setBold(false);
                builder.getFont().setItalic(false);
                builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
                builder.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
                builder.getParagraphFormat().setFirstLineIndent(0);
                builder.getFont().setName("方正仿宋_GBK");
                builder.getFont().setSize(16);
                builder.getFont().setSizeBi(16);
                builder.getFont().setNameAscii("Times New Roman");
                builder.getFont().setNameOther("Times New Roman");
                builder.getFont().setNameBi("Times New Roman");
            }
            builder.getFont().setColor(Color.BLACK);
            builder.writeln(catalog);

            // 正文内容
            builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.NORMAL);
            builder.getParagraphFormat().setAlignment(getAlignFormat(textStyle.getAlign()));
            builder.getParagraphFormat().setSpaceBefore(textStyle.getParagraphBefore());
            builder.getParagraphFormat().setSpaceAfter(textStyle.getParagraphAfter());
            setLineSpacing(builder, textStyle);
            builder.getFont().setBold(textStyle.getBold());
            builder.getFont().setItalic(textStyle.getItalic());
            builder.getFont().setName(textStyle.getFont());
            builder.getFont().setNameAscii(textStyle.getFontEng());
            builder.getFont().setNameOther(textStyle.getFontEng());
            builder.getFont().setNameBi(textStyle.getFontEng());
            builder.getFont().setColor(Color.BLACK);
            builder.getFont().setSize(textStyle.getFontSize());
            builder.getFont().setSizeBi(textStyle.getFontSize());
            int before = doc.getChildNodes(NodeType.PARAGRAPH, true).getCount();
            builder.insertHtml(tableFormat(html, tableStyle));

            // 处理段落的其他格式
            NodeCollection allParas = doc.getChildNodes(NodeType.PARAGRAPH, true);
            for (int i = before - 1; i < allParas.getCount(); i++) {
                Paragraph para = (Paragraph) allParas.get(i);
                boolean hasImage = false;
                for (Object no : para.getChildNodes(NodeType.SHAPE, true)) {
                    if (no instanceof Shape && ((Shape) no).hasImage()) {
                        hasImage = true;
                        break;
                    }
                }
                boolean isInTable = para.getAncestor(NodeType.TABLE) != null;
                boolean isCentered = para.getParagraphFormat().getAlignment() == ParagraphAlignment.CENTER;

                if (!hasImage) {
                    trimParagraphSpaces(para);
                    if (!isInTable && !isCentered) {
                        para.getParagraphFormat().setAlignment(getAlignFormat(textStyle.getAlign()));
                        setFirstLineIndent(para, textStyle);
                    } else if (isCentered) {
                        para.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
                        para.getParagraphFormat().setFirstLineIndent(0);
                        para.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
                    } else {
                        // 这里的格式之后会覆盖
                        para.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
                        para.getParagraphFormat().setFirstLineIndent(0);
                        para.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
                    }
                    setLineSpacing(para, textStyle);
                    para.getParagraphFormat().setSpaceBefore(textStyle.getParagraphBefore());
                    para.getParagraphFormat().setSpaceAfter(textStyle.getParagraphAfter());
                    for (Run run : para.getRuns()) {
                        run.getFont().setName(textStyle.getFont());
                        run.getFont().setNameAscii(textStyle.getFontEng());
                        run.getFont().setNameOther(textStyle.getFontEng());
                        run.getFont().setNameBi(textStyle.getFontEng());
                        run.getFont().setSize(textStyle.getFontSize());
                        run.getFont().setSizeBi(textStyle.getFontSize());
                        run.getFont().setBold(textStyle.getBold());
                        run.getFont().setItalic(textStyle.getItalic());
                        run.getFont().setColor(Color.BLACK);
                    }
                }
            }
        }

        // 删除最后一个空行
        if (doc.getLastSection().getBody().getParagraphs().getCount() > 0) {
            Paragraph lastParagraph = doc.getLastSection().getBody().getParagraphs().get(doc.getLastSection().getBody().getParagraphs().getCount() - 1);
            if (lastParagraph.getText().trim().isEmpty()) {
                lastParagraph.remove();
            }
        }

        // 处理所有图片，确保宽高都不超出页边距，并居中对齐
        for (Shape imgShape : (Iterable<Shape>) doc.getChildNodes(NodeType.SHAPE, true)) {
            if (imgShape.getAncestor(NodeType.CELL) != null) {
                continue;
            }
            if (imgShape.getShapeType() == ShapeType.IMAGE && imgShape.hasImage()) {
                Section sec = (Section) imgShape.getAncestor(NodeType.SECTION);
                if (sec == null) {
                    sec = doc.getFirstSection();
                }
                double pageWidth = sec.getPageSetup().getPageWidth();
                double pageHeight = sec.getPageSetup().getPageHeight();
                double availableWidth = pageWidth - sec.getPageSetup().getLeftMargin() - sec.getPageSetup().getRightMargin();
                double availableHeight = pageHeight - sec.getPageSetup().getTopMargin() - sec.getPageSetup().getBottomMargin();
                imgShape.setWidth(imgShape.getImageData().getImageSize().getWidthPoints());
                imgShape.setHeight(imgShape.getImageData().getImageSize().getHeightPoints());
                double imgWidth = imgShape.getWidth(), imgHeight = imgShape.getHeight();
                double widthScale = availableWidth / imgWidth, heightScale = availableHeight / imgHeight;
                double scale = Math.min(1.0, Math.min(widthScale, heightScale));
                if (scale < 1.0) {
                    imgShape.setWidth(imgWidth * scale);
                    imgShape.setHeight(imgHeight * scale);
                }
                imgShape.setRotation(0);
                imgShape.setHorizontalAlignment(getAlignFormat(imgStyle.getAlign()));
                imgShape.setWrapType(WrapType.INLINE);
                Paragraph para = imgShape.getParentParagraph();
                if (para != null) {
                    para.getParagraphFormat().setAlignment(getAlignFormat(imgStyle.getAlign()));
                    setFirstLineIndent(para, imgStyle);
                    setLineSpacing(para, imgStyle);
                    para.getParagraphFormat().setSpaceBefore(imgStyle.getParagraphBefore());
                    para.getParagraphFormat().setSpaceAfter(imgStyle.getParagraphAfter());
                }
            }
        }

        // 处理所有的表格
        for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true)) {
            // 设置表格边框
            table.setBorders(LineStyle.SINGLE, tableStyle.getTableBorderWidth(), Color.BLACK);
            table.setAlignment(TableAlignment.CENTER);

            // 表格指定宽度百分比
            table.setPreferredWidth(PreferredWidth.fromPercent(100));

            for (Row row : table.getRows()) {
                for (Cell cell : row.getCells()) {
                    // 单元格垂直居中
                    cell.getCellFormat().setVerticalAlignment(getAlignFormat(tableStyle.getTableAlign()));
                    // 设置单元格边框
                    cell.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
                    cell.getCellFormat().getBorders().setColor(Color.BLACK);
                    cell.getCellFormat().getBorders().setLineWidth(tableStyle.getTableBorderWidth());

                    for (Paragraph para : cell.getParagraphs()) {
                        // 段落居中
                        para.getParagraphFormat().setAlignment(getAlignFormat(tableStyle.getAlign()));
                        setLineSpacing(para, tableStyle);
                        setFirstLineIndent(para, tableStyle);
                        para.getParagraphFormat().setSpaceBefore(tableStyle.getParagraphBefore());
                        para.getParagraphFormat().setSpaceAfter(tableStyle.getParagraphAfter());
                        // 设置字体
                        for (Run run : para.getRuns()) {
                            run.getFont().setName(tableStyle.getFont());
                            run.getFont().setNameAscii(tableStyle.getFontEng());
                            run.getFont().setNameOther(tableStyle.getFontEng());
                            run.getFont().setNameBi(tableStyle.getFontEng());
                            run.getFont().setSize(tableStyle.getFontSize());
                            run.getFont().setSizeBi(tableStyle.getFontSize());
                            run.getFont().setBold(tableStyle.getBold());
                            run.getFont().setItalic(tableStyle.getItalic());
                            run.getFont().setColor(Color.BLACK);
                        }

                        NodeCollection shapes = para.getChildNodes(NodeType.SHAPE, true);
                        if (shapes.getCount() > 0) {
                            // 段落单倍行距
                            para.getParagraphFormat().setLineSpacingRule(LineSpacingRule.MULTIPLE);
                            para.getParagraphFormat().setFirstLineIndent(0);
                            para.getParagraphFormat().setCharacterUnitFirstLineIndent(0);

                            // Word可视宽度
                            Section sec = (Section) (table.getAncestor(NodeType.SECTION) != null
                                    ? table.getAncestor(NodeType.SECTION) : doc.getFirstSection());
                            double visibleWidth = sec.getPageSetup().getPageWidth()
                                    - sec.getPageSetup().getLeftMargin()
                                    - sec.getPageSetup().getRightMargin();

                            // 目标宽度为可视宽度的1/5
                            double targetW = visibleWidth * 0.20;

                            // 尽量不超过单元格可用宽度（保守估算，避免溢出）
                            double cellW = cell.getCellFormat().getWidth();
                            if (cellW <= 0) {
                                // 拿不到固定列宽就按本行列数均分一个估算值
                                int cols = cell.getParentRow().getCells().getCount();
                                cellW = Math.max(1, visibleWidth / Math.max(1, cols));
                            }
                            double innerW = Math.max(1, cellW - cell.getCellFormat().getLeftPadding() - cell.getCellFormat().getRightPadding());
                            targetW = Math.min(targetW, innerW);

                            for (Object o : shapes) {
                                Shape s = (Shape) o;
                                if (s.getShapeType() == ShapeType.IMAGE && s.hasImage()) {
                                    // 按等比缩放
                                    double ow = s.getImageData().getImageSize().getWidthPoints();
                                    double oh = s.getImageData().getImageSize().getHeightPoints();
                                    if (ow > 0 && oh > 0) {
                                        double scale = targetW / ow;
                                        s.setWidth(ow * scale);
                                        s.setHeight(oh * scale);
                                    } else {
                                        // 拿不到原始宽就直接设定
                                        s.setWidth(targetW);
                                    }
                                    s.setWrapType(WrapType.INLINE);
                                    para.getParagraphFormat().setAlignment(getAlignFormat(tableStyle.getAlign()));
                                }
                            }
                        }
                    }
                }
            }
        }

        Document resultDoc;
        int catalogSectionIndex = -1;
        boolean withTemplate = template != null && template.exists();
        Document catalogDoc = outlineCatalog ? createOutlineCatalogDocument(pageStyle, titleStyle) : null;
        if (withTemplate) {
            Document templateDoc = new Document(template.getAbsolutePath());
            excludePlaceholderFromOutline(templateDoc, "{{title}}");
            templateDoc.getRange().replace("{{title}}", title);
            String content = gldgbmMap.get(data.getOpmCode());
            if (ValidateUtil.isNotEmpty(gldgbmMap) && ValidateUtil.isNotEmpty(content)) {
                templateDoc.getRange().replace("{{content}}", content);
            } else {
                templateDoc.getRange().replace("{{content}}", "项目管理大纲");
            }
            String opmUnit = data.getOpmUnit();
            if (ValidateUtil.isNotEmpty(opmUnit)) {
                templateDoc.getRange().replace("{{company}}", opmUnit);
            } else {
                templateDoc.getRange().replace("{{company}}", "南京城市建设管理集团有限公司");
            }
            templateDoc.getRange().replace("{{date}}", formatChineseDate(new Date()));
            // 分节换页
            // DocumentBuilder tmplBuilder = new DocumentBuilder(templateDoc);
            // tmplBuilder.moveToDocumentEnd();
            // tmplBuilder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            // 合并目录和正文
            if (catalogDoc != null) {
                catalogSectionIndex = templateDoc.getSections().getCount();
                templateDoc.appendDocument(catalogDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            }
            templateDoc.appendDocument(doc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            resultDoc = templateDoc;
        } else {
            // 没模板，加个标题
            builder.moveToDocumentStart();
            builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.TITLE);
            setTitleStyle(builder, titleStyle);
            builder.getParagraphFormat().setOutlineLevel(OutlineLevel.BODY_TEXT);
            builder.writeln(title);
            builder.getParagraphFormat().setAlignment(getAlignFormat(textStyle.getAlign()));
            if (catalogDoc != null) {
                catalogSectionIndex = 0;
                catalogDoc.appendDocument(doc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
                resultDoc = catalogDoc;
            } else {
                resultDoc = doc;
            }
        }

        // 添加页眉页脚
        setHeaderAndFooter(resultDoc, title, getWmText(user, owc, userEntity), owc, withTemplate, catalogSectionIndex, styleDtlMap);

        if (outlineCatalog) {
            applyOutlineCatalogStyle(resultDoc, catalogStyle);
            resultDoc.updateFields();
        }
        resultDoc.updatePageLayout();
        resultDoc.save(outPath);
    }

    /**
     * 将模板占位符所在段落排除在目录大纲之外。
     *
     * @param doc         模板文档
     * @param placeholder 占位符
     */
    private static void excludePlaceholderFromOutline(Document doc, String placeholder) {
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true)) {
            if (paragraph.getText().contains(placeholder)) {
                paragraph.getParagraphFormat().setOutlineLevel(OutlineLevel.BODY_TEXT);
            }
        }
    }

    /**
     * 创建项目管理大纲目录文档。
     *
     * @param pageStyle  页面样式
     * @param titleStyle 标题样式
     * @return 包含目录域的独立文档
     */
    @SneakyThrows
    private static Document createOutlineCatalogDocument(OutlineProjectStyleDtl pageStyle, OutlineProjectStyleDtl titleStyle) {
        Document catalogDoc = new Document();
        Section catalogSection = catalogDoc.getFirstSection();
        PageSetup catalogPageSetup = catalogSection.getPageSetup();
        catalogPageSetup.setLeftMargin(pageStyle.getMarginsLeft() * cmToPt);
        catalogPageSetup.setRightMargin(pageStyle.getMarginsRight() * cmToPt);
        catalogPageSetup.setTopMargin(pageStyle.getMarginsTop() * cmToPt);
        catalogPageSetup.setBottomMargin(pageStyle.getMarginsBottom() * cmToPt);
        catalogPageSetup.setPaperSize(PaperSize.A4);
        catalogPageSetup.setOrientation(Orientation.PORTRAIT);

        DocumentBuilder catalogBuilder = new DocumentBuilder(catalogDoc);
        catalogBuilder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.TITLE);
        setTitleStyle(catalogBuilder, titleStyle);
        catalogBuilder.getParagraphFormat().setOutlineLevel(OutlineLevel.BODY_TEXT);
        catalogBuilder.writeln("目录");
        catalogBuilder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.NORMAL);
        catalogBuilder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
        catalogBuilder.getFont().setBold(false);
        catalogBuilder.insertTableOfContents("\\o \"1-9\" \\h \\z \\u");
        return catalogDoc;
    }

    /**
     * 将分类17的配置应用到Word内置目录条目样式。
     *
     * @param doc          目标文档
     * @param catalogStyle 目录条目样式（分类17）
     */
    private static void applyOutlineCatalogStyle(Document doc, OutlineProjectStyleDtl catalogStyle) {
        for (int styleIdentifier = StyleIdentifier.TOC_1;
             styleIdentifier <= StyleIdentifier.TOC_9;
             styleIdentifier++) {
            Style tocStyle = doc.getStyles().getByStyleIdentifier(styleIdentifier);
            if (tocStyle == null) {
                continue;
            }

            ParagraphFormat paragraphFormat = tocStyle.getParagraphFormat();
            paragraphFormat.setSpaceBefore(catalogStyle.getParagraphBefore());
            paragraphFormat.setSpaceAfter(catalogStyle.getParagraphAfter());
            paragraphFormat.setAlignment(getAlignFormat(catalogStyle.getAlign()));
            if ("01".equals(catalogStyle.getLineSpacingType())) {
                paragraphFormat.setLineSpacingRule(LineSpacingRule.MULTIPLE);
                double lineSpacing = catalogStyle.getLineSpacingValue();
                paragraphFormat.setLineSpacing(lineSpacing <= 5 ? lineSpacing * 12 : lineSpacing);
            } else if ("02".equals(catalogStyle.getLineSpacingType())) {
                paragraphFormat.setLineSpacingRule(LineSpacingRule.EXACTLY);
                paragraphFormat.setLineSpacing(catalogStyle.getLineSpacingValue());
            }
            if ("01".equals(catalogStyle.getIndentSpecial())) {
                paragraphFormat.setCharacterUnitFirstLineIndent(catalogStyle.getIndentSpecialValue());
            } else if ("02".equals(catalogStyle.getIndentSpecial())) {
                paragraphFormat.setCharacterUnitFirstLineIndent(catalogStyle.getIndentSpecialValue() * -1);
            } else {
                paragraphFormat.setFirstLineIndent(0);
                paragraphFormat.setCharacterUnitFirstLineIndent(0);
            }

            Font font = tocStyle.getFont();
            font.setName(catalogStyle.getFont());
            font.setNameAscii(catalogStyle.getFontEng());
            font.setNameOther(catalogStyle.getFontEng());
            font.setNameBi(catalogStyle.getFontEng());
            font.setSize(catalogStyle.getFontSize());
            font.setSizeBi(catalogStyle.getFontSize());
            font.setBold(catalogStyle.getBold());
            font.setItalic(catalogStyle.getItalic());
            font.setColor(Color.BLACK);
        }
    }

    private static void setTitleStyle(DocumentBuilder builder, OutlineProjectStyleDtl levelStyle) {
        builder.getParagraphFormat().setSpaceBefore(levelStyle.getParagraphBefore());
        builder.getParagraphFormat().setSpaceAfter(levelStyle.getParagraphAfter());
        setLineSpacing(builder, levelStyle);
        builder.getFont().setName(levelStyle.getFont());
        builder.getFont().setNameAscii(levelStyle.getFontEng());
        builder.getFont().setNameOther(levelStyle.getFontEng());
        builder.getFont().setNameBi(levelStyle.getFontEng());
        builder.getFont().setSize(levelStyle.getFontSize());
        builder.getFont().setSizeBi(levelStyle.getFontSize());
        builder.getFont().setBold(levelStyle.getBold());
        builder.getFont().setItalic(levelStyle.getItalic());
        builder.getParagraphFormat().setAlignment(getAlignFormat(levelStyle.getAlign()));
        builder.getFont().setColor(Color.BLACK);
        setFirstLineIndent(builder, levelStyle);
    }

    public static int getAlignFormat(String type) {
        switch (type) {
            case "02":
                return ParagraphAlignment.CENTER;
            case "03":
                return ParagraphAlignment.RIGHT;
            case "04":
                return ParagraphAlignment.JUSTIFY;
            case "01":
            default:
                return ParagraphAlignment.LEFT;
        }
    }

    public static int getTableAlignFormat(String type) {
        switch (type) {
            case "02":
                return TableAlignment.CENTER;
            case "03":
                return TableAlignment.RIGHT;
            case "01":
            default:
                return TableAlignment.LEFT;
        }
    }

    public static void setLineSpacing(DocumentBuilder builder, OutlineProjectStyleDtl style) {
        if ("01".equals(style.getLineSpacingType())) {
            builder.getParagraphFormat().setLineSpacingRule(LineSpacingRule.MULTIPLE);
            double v = style.getLineSpacingValue();
            builder.getParagraphFormat().setLineSpacing(v <= 5 ? v * 12 : v);
        } else if ("02".equals(style.getLineSpacingType())) {
            builder.getParagraphFormat().setLineSpacingRule(LineSpacingRule.EXACTLY);
            builder.getParagraphFormat().setLineSpacing(style.getLineSpacingValue());
        }
    }

    public static void setLineSpacing(Paragraph para, OutlineProjectStyleDtl style) {
        if ("01".equals(style.getLineSpacingType())) {
            para.getParagraphFormat().setLineSpacingRule(LineSpacingRule.MULTIPLE);
            double v = style.getLineSpacingValue();
            para.getParagraphFormat().setLineSpacing(v <= 5 ? v * 12 : v);
        } else if ("02".equals(style.getLineSpacingType())) {
            para.getParagraphFormat().setLineSpacingRule(LineSpacingRule.EXACTLY);
            para.getParagraphFormat().setLineSpacing(style.getLineSpacingValue());
        }
    }

    public static void setFirstLineIndent(DocumentBuilder builder, OutlineProjectStyleDtl style) {
        if ("01".equals(style.getIndentSpecial())) {
            builder.getParagraphFormat().setCharacterUnitFirstLineIndent(style.getIndentSpecialValue());
        } else if ("02".equals(style.getIndentSpecial())) {
            builder.getParagraphFormat().setCharacterUnitFirstLineIndent(style.getIndentSpecialValue() * -1);
        } else {
            builder.getParagraphFormat().setFirstLineIndent(0);
            builder.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
        }
    }

    public static void setFirstLineIndent(Paragraph para, OutlineProjectStyleDtl style) {
        if ("01".equals(style.getIndentSpecial())) {
            para.getParagraphFormat().setCharacterUnitFirstLineIndent(style.getIndentSpecialValue());
        } else if ("02".equals(style.getIndentSpecial())) {
            para.getParagraphFormat().setCharacterUnitFirstLineIndent(style.getIndentSpecialValue() * -1);
        } else {
            para.getParagraphFormat().setFirstLineIndent(0);
            para.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
        }
    }

    /**
     * 规范化表格里图片：按单元格可用宽度等比缩放 + 段落单倍行距 + 对齐 + 段前后
     */
    @SneakyThrows
    private static void normalizeImageInTableCell(
            Shape img,
            Cell cell,
            Section sec,
            OutlineProjectStyleDtl tableStyle,
            OutlineProjectStyleDtl imgStyle
    ) {
        // 计算单元格内部可用宽度
        double cellInnerWidth = getCellInnerWidth(cell, sec);

        // 先把图片设为原始点尺寸
        img.setWidth(img.getImageData().getImageSize().getWidthPoints());
        img.setHeight(img.getImageData().getImageSize().getHeightPoints());

        // 等比缩放到不超过 cellInnerWidth
        double w = img.getWidth(), h = img.getHeight();
        if (w > cellInnerWidth && w > 0) {
            double scale = cellInnerWidth / w;
            img.setWidth(w * scale);
            img.setHeight(h * scale);
        }

        // 高度：通常让行高自适应；如果行高被设置为 EXACTLY 则限制图片高度不超过行高
        Row row = cell.getParentRow();
        if (row != null && row.getRowFormat().getHeightRule() == HeightRule.EXACTLY) {
            double rowH = row.getRowFormat().getHeight();
            if (img.getHeight() > rowH && rowH > 0) {
                double scale = rowH / img.getHeight();
                img.setHeight(img.getHeight() * scale);
                img.setWidth(img.getWidth() * scale);
            }
        }

        img.setRotation(0);
        img.setWrapType(WrapType.INLINE);

        Paragraph para = img.getParentParagraph();
        if (para != null) {
            if (isParagraphOnlyImage(para)) {
                para.getParagraphFormat().setLineSpacingRule(LineSpacingRule.EXACTLY);
                para.getParagraphFormat().setFirstLineIndent(0);
                para.getParagraphFormat().setCharacterUnitFirstLineIndent(0);
                para.getParagraphFormat().setAlignment(getAlignFormat(imgStyle.getAlign()));
                para.getParagraphFormat().setSpaceBefore(imgStyle.getParagraphBefore());
                para.getParagraphFormat().setSpaceAfter(imgStyle.getParagraphAfter());
            } else {
                para.getParagraphFormat().setLineSpacingRule(LineSpacingRule.EXACTLY);
            }
        }
    }

    private static double getCellInnerWidth(Cell cell, Section sec) {
        Table table = cell.getParentRow().getParentTable();

        double contentWidth = sec.getPageSetup().getPageWidth()
                - sec.getPageSetup().getLeftMargin()
                - sec.getPageSetup().getRightMargin();

        double tableInnerWidth = getTableInnerWidth(table, contentWidth);

        double cellWidth = cell.getCellFormat().getWidth();
        if (cellWidth <= 0) {
            PreferredWidth pw = cell.getCellFormat().getPreferredWidth();
            if (pw != null) {
                if (pw.getType() == PreferredWidthType.POINTS) {
                    cellWidth = pw.getValue();
                } else if (pw.getType() == PreferredWidthType.PERCENT) {
                    cellWidth = tableInnerWidth * (pw.getValue() / 100.0);
                }
            }
        }

        if (cell.getCellFormat().getHorizontalMerge() == CellMerge.FIRST) {
            Cell cur = cell;
            while (true) {
                Cell next = (Cell) cur.getNextSibling();
                if (next == null) {
                    break;
                }
                if (next.getCellFormat().getHorizontalMerge() != CellMerge.PREVIOUS) {
                    break;
                }
                double w = next.getCellFormat().getWidth();
                if (w <= 0) {
                    PreferredWidth npw = next.getCellFormat().getPreferredWidth();
                    if (npw != null) {
                        if (npw.getType() == PreferredWidthType.POINTS) {
                            w = npw.getValue();
                        } else if (npw.getType() == PreferredWidthType.PERCENT) {
                            w = tableInnerWidth * (npw.getValue() / 100.0);
                        }
                    }
                }
                if (w > 0) {
                    cellWidth += w;
                }
                cur = next;
            }
        }

        // 回退
        if (cellWidth <= 0) {
            int cols = cell.getParentRow().getCells().getCount();
            cellWidth = tableInnerWidth / Math.max(1, cols);
        }

        // 扣掉单元格内边距
        double leftPad = cell.getCellFormat().getLeftPadding();
        double rightPad = cell.getCellFormat().getRightPadding();

        // 防止负数
        double inner = Math.max(1, cellWidth - leftPad - rightPad);
        return inner;
    }

    private static double getTableInnerWidth(Table table, double contentWidth) {
        double tableWidth = 0;
        PreferredWidth tpw = table.getPreferredWidth();
        if (tpw != null) {
            if (tpw.getType() == PreferredWidthType.POINTS) {
                tableWidth = tpw.getValue();
            } else if (tpw.getType() == PreferredWidthType.PERCENT) {
                tableWidth = contentWidth * (tpw.getValue() / 100.0);
            }
        }
        if (tableWidth <= 0) {
            // 允许自动
            tableWidth = contentWidth;
        }
        return Math.max(1, tableWidth);
    }

    @SneakyThrows
    private static boolean isParagraphOnlyImage(Paragraph p) {
        int imageCount = 0;
        int nonTrivialRuns = 0;

        for (Node n : (Iterable<Node>) p.getChildNodes()) {
            if (n.getNodeType() == NodeType.SHAPE) {
                Shape s = (Shape) n;
                if (s.hasImage()) {
                    imageCount++;
                }
            } else if (n.getNodeType() == NodeType.RUN) {
                String txt = n.getText();
                if (txt != null && !txt.trim().isEmpty()) {
                    nonTrivialRuns++;
                }
            } else if (n.getNodeType() == NodeType.FIELD_START
                    || n.getNodeType() == NodeType.FIELD_END
                    || n.getNodeType() == NodeType.BOOKMARK_START
                    || n.getNodeType() == NodeType.BOOKMARK_END) {
                // 忽略字段、书签等
            } else {
                // 其他节点类型
                nonTrivialRuns++;
            }
        }
        return imageCount == 1 && nonTrivialRuns == 0;
    }

    /**
     * 格式化中文日期
     *
     * @param date
     * @return
     */
    public static String formatChineseDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String year = String.valueOf(cal.get(Calendar.YEAR))
                .chars()
                .mapToObj(c -> "〇一二三四五六七八九".charAt(c - '0'))
                .map(String::valueOf)
                .collect(Collectors.joining());
        String month = NumberUtil.int2chineseNum(cal.get(Calendar.MONTH) + 1) + "月";
        return year + "年" + month;
    }

    /**
     * 设置页眉页脚
     *
     * @param doc                 目标文档
     * @param title               文档标题
     * @param watermark           水印文字
     * @param owc                 大纲水印配置
     * @param withTemplate        是否包含封面模板
     * @param catalogSectionIndex 目录所在节索引，不包含目录时为-1
     * @param styleDtlMap         大纲样式配置
     */
    @SneakyThrows
    private static void setHeaderAndFooter(
            Document doc, String title,
            String watermark,
            OutlineWatermarkControlDto owc,
            boolean withTemplate,
            int catalogSectionIndex,
            Map<String, OutlineProjectStyleDtl> styleDtlMap
    ) {
        int sectionCount = doc.getSections().getCount();

        // 获取样式
        OutlineProjectStyleDtl headerStyle = styleDtlMap.get("05");
        OutlineProjectStyleDtl footerStyle = styleDtlMap.get("06");
        OutlineProjectStyleDtl catalogStyle = styleDtlMap.get("17");

        for (int i = 0; i < sectionCount; i++) {
            Section sec = doc.getSections().get(i);
            // 页眉页脚的上下边距
            sec.getPageSetup().setHeaderDistance(headerStyle.getHeaderUpperMargin() * cmToPt);
            sec.getPageSetup().setFooterDistance(footerStyle.getFooterBottomMargin() * cmToPt);

            // 页眉
            HeaderFooter header = sec.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
            if (header == null) {
                header = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
                sec.getHeadersFooters().add(header);
            } else {
                header.removeAllChildren();
            }
            Paragraph para = new Paragraph(doc);
            para.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADER);
            para.getParagraphFormat().setAlignment(getAlignFormat(headerStyle.getAlign()));
            // 设置段前段后
            para.getParagraphFormat().setSpaceBeforeAuto(false);
            para.getParagraphFormat().setSpaceAfterAuto(false);
            para.getParagraphFormat().setSpaceBefore(headerStyle.getParagraphBefore());
            para.getParagraphFormat().setSpaceAfter(headerStyle.getParagraphAfter());
            setFirstLineIndent(para, headerStyle);
            setLineSpacing(para, headerStyle);
            Run run = new Run(doc, title);
            run.getFont().setName(headerStyle.getFont());
            run.getFont().setSize(headerStyle.getFontSize());
            run.getFont().setNameAscii(headerStyle.getFontEng());
            run.getFont().setNameOther(headerStyle.getFontEng());
            run.getFont().setNameBi(headerStyle.getFontEng());
            run.getFont().setSizeBi(headerStyle.getFontSize());
            run.getFont().setColor(Color.BLACK);
            run.getFont().setBold(headerStyle.getBold());
            run.getFont().setItalic(headerStyle.getItalic());
            para.appendChild(run);
            // 加下边框
            if (headerStyle.getHeaderUnderline()) {
                Border border = para.getParagraphFormat().getBorders().getByBorderType(BorderType.BOTTOM);
                border.setLineStyle(LineStyle.SINGLE);
                border.setLineWidth(headerStyle.getTableBorderWidth());
            }
            // 添加水印
            if (owc != null && owc.isEnable()) {
                List<Shape> shapes = WaterMarkMore(doc, watermark, owc, sec);
                for (Shape s : shapes) {
                    para.appendChild(s);
                }
            }
            header.appendChild(para);
            while (header.getParagraphs().getCount() > 1) {
                for (int j = header.getParagraphs().getCount() - 1; j >= 0; j--) {
                    Paragraph p = header.getParagraphs().get(j);
                    if (p.getText().trim().isEmpty()) {
                        p.remove();
                    }
                }
            }

            // 页脚
            boolean first = (i == 0);
            boolean coverSection = withTemplate && first;
            boolean catalogSection = i == catalogSectionIndex;
            boolean cover = Boolean.TRUE.equals(footerStyle.getFooterPageNumberCover());
            boolean addPageNumber = catalogSection
                    ? Boolean.TRUE.equals(catalogStyle.getFooterPageNumber())
                    : Boolean.TRUE.equals(footerStyle.getFooterPageNumber());
            if (coverSection && !cover) {
                addPageNumber = false;
            }

            if (!cover && withTemplate && first) {
                sec.getPageSetup().setDifferentFirstPageHeaderFooter(true);

                HeaderFooter headerFirst = sec.getHeadersFooters()
                        .getByHeaderFooterType(HeaderFooterType.HEADER_FIRST);
                if (headerFirst == null) {
                    headerFirst = new HeaderFooter(doc, HeaderFooterType.HEADER_FIRST);
                    sec.getHeadersFooters().add(headerFirst);
                } else {
                    headerFirst.removeAllChildren();
                }
                for (Object n : header.getChildNodes()) {
                    headerFirst.appendChild(((Node) n).deepClone(true));
                }
            }

            if (addPageNumber) {
                boolean second = (i == 1);
                boolean firstAfterCatalogWithoutPageNumber = catalogSectionIndex >= 0
                        && !Boolean.TRUE.equals(catalogStyle.getFooterPageNumber())
                        && i == catalogSectionIndex + 1;

                if (!cover) {
                    if (withTemplate && first) {
                        sec.getPageSetup().setDifferentFirstPageHeaderFooter(true);

                        HeaderFooter firstFooter = sec.getHeadersFooters()
                                .getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST);
                        if (firstFooter == null) {
                            firstFooter = new HeaderFooter(doc, HeaderFooterType.FOOTER_FIRST);
                            sec.getHeadersFooters().add(firstFooter);
                        } else {
                            firstFooter.removeAllChildren();
                        }

                        sec.getPageSetup().setRestartPageNumbering(true);
                        sec.getPageSetup().setPageStartingNumber(1);
                    } else if (!withTemplate && first) {
                        sec.getPageSetup().setDifferentFirstPageHeaderFooter(true);
                        sec.getPageSetup().setRestartPageNumbering(true);
                        sec.getPageSetup().setPageStartingNumber(0);
                    } else if (firstAfterCatalogWithoutPageNumber || second) {
                        sec.getPageSetup().setRestartPageNumbering(true);
                        sec.getPageSetup().setPageStartingNumber(1);
                    } else {
                        sec.getPageSetup().setRestartPageNumbering(false);
                    }
                } else {
                    if (first) {
                        sec.getPageSetup().setRestartPageNumbering(true);
                        sec.getPageSetup().setPageStartingNumber(1);
                        sec.getPageSetup().setDifferentFirstPageHeaderFooter(false);
                    } else {
                        sec.getPageSetup().setRestartPageNumbering(false);
                    }
                }

                DocumentBuilder builder = new DocumentBuilder(doc);
                builder.moveToSection(i);
                builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
                builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.FOOTER);
                builder.getParagraphFormat().setAlignment(getAlignFormat(footerStyle.getAlign()));
                builder.getParagraphFormat().setSpaceBeforeAuto(false);
                builder.getParagraphFormat().setSpaceAfterAuto(false);
                builder.getParagraphFormat().setSpaceBefore(footerStyle.getParagraphBefore());
                builder.getParagraphFormat().setSpaceAfter(footerStyle.getParagraphAfter());
                setFirstLineIndent(builder, footerStyle);
                setLineSpacing(builder, footerStyle);
                builder.getFont().setName(footerStyle.getFont());
                builder.getFont().setSize(footerStyle.getFontSize());
                builder.getFont().setNameAscii(footerStyle.getFontEng());
                builder.getFont().setNameOther(footerStyle.getFontEng());
                builder.getFont().setNameBi(footerStyle.getFontEng());
                builder.getFont().setSizeBi(footerStyle.getFontSize());
                builder.getFont().setColor(Color.BLACK);
                builder.getFont().setBold(footerStyle.getBold());
                builder.getFont().setItalic(footerStyle.getItalic());
                builder.insertField("PAGE", "");

                // 统一处理该节页脚段落
                HeaderFooter footer = sec.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
                if (footer != null) {
                    for (Paragraph paragraph : footer.getParagraphs()) {
                        setFirstLineIndent(paragraph, footerStyle);
                        paragraph.getParagraphFormat().setSpaceBeforeAuto(false);
                        paragraph.getParagraphFormat().setSpaceAfterAuto(false);
                        paragraph.getParagraphFormat().setSpaceBefore(footerStyle.getParagraphBefore());
                        paragraph.getParagraphFormat().setSpaceAfter(footerStyle.getParagraphAfter());
                    }
                }
            } else if (catalogSection || (coverSection && !cover)) {
                sec.getHeadersFooters().linkToPrevious(false);
                removeFooter(sec);
            }
        }
    }

    /**
     * 移除页脚
     *
     * @param section
     */
    private static void removeFooter(Section section) {
        HeaderFooterCollection headersFooters = section.getHeadersFooters();
        for (int i = headersFooters.getCount() - 1; i >= 0; i--) {
            HeaderFooter hf = headersFooters.get(i);
            if (hf.getHeaderFooterType() == HeaderFooterType.FOOTER_PRIMARY
                    || hf.getHeaderFooterType() == HeaderFooterType.FOOTER_FIRST
                    || hf.getHeaderFooterType() == HeaderFooterType.FOOTER_EVEN) {
                headersFooters.removeAt(i);
            }
        }
    }

    /**
     * 获取标题样式
     *
     * @param level 标题级别
     * @return 对应的样式标识符
     */
    private static int getHeadingStyle(int level) {
        switch (level) {
            case 1:
                return StyleIdentifier.HEADING_1;
            case 2:
                return StyleIdentifier.HEADING_2;
            case 3:
                return StyleIdentifier.HEADING_3;
            case 4:
                return StyleIdentifier.HEADING_4;
            case 5:
                return StyleIdentifier.HEADING_5;
            case 6:
                return StyleIdentifier.HEADING_6;
            case 7:
                return StyleIdentifier.HEADING_7;
            case 8:
                return StyleIdentifier.HEADING_8;
            default:
                return StyleIdentifier.HEADING_9;
        }
    }

    /**
     * 将HTML中的图片链接转换为Base64
     *
     * @param html HTML字符串
     * @return 转换后的HTML字符串
     */
    public static String convertImgToBase64(String html, String prefix, String token) {
        if (html == null || !html.contains("<img")) {
            return html;
        }
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
        Elements imgElements = doc.select("img");
        for (Element img : imgElements) {
            img.removeAttr("width");
            img.removeAttr("height");
            img.removeAttr("style");
            try {
                String src = img.attr("src");
                String fileId = extractFileId(src);
                if (fileId != null) {
                    String fullUrl = String.format("%s/jpm/downloadAloneFile/%s/%s", prefix, fileId, token);
                    String base64Src = imageUrlToBase64(fullUrl);
                    img.attr("src", base64Src);
                }
            } catch (Exception e) {
                log.error("图片转换失败: {}", img.attr("src"), e);
            }
        }
        return doc.body().html();
    }

    /**
     * 将图片URL转换为Base64编码
     *
     * @param imageUrl 图片URL
     * @return Base64编码的字符串
     */
    public static String imageUrlToBase64(String imageUrl) {
        try (HttpResponse response = HttpRequest.get(imageUrl).timeout(10000).execute()) {
            byte[] bytes = IoUtil.readBytes(response.bodyStream());
            String contentType = response.header(Header.CONTENT_TYPE) != null
                    ? response.header(Header.CONTENT_TYPE)
                    : "image/png";
            if (!contentType.startsWith("image/")) {
                throw new IllegalArgumentException("URL 不是图片类型: " + contentType);
            }
            return "data:" + contentType + ";base64," + Base64Encoder.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException("图片转Base64失败: " + imageUrl, e);
        }
    }

    /**
     * 提取图片链接中的文件ID
     *
     * @param src 图片链接
     * @return 文件ID
     */
    private static String extractFileId(String src) {
        Pattern idPattern = Pattern.compile("/jpm/downloadAloneFile/([a-fA-F0-9\\-]{32,36})/");
        Matcher matcher = idPattern.matcher(src);
        if (matcher.find()) {
            return matcher.group(1);
        }
        Pattern fallbackPattern = Pattern.compile("/jpm/downloadAloneFile/([^/{}]+)");
        Matcher fallbackMatcher = fallbackPattern.matcher(src);
        if (fallbackMatcher.find()) {
            return fallbackMatcher.group(1);
        }
        return null;
    }

    /**
     * 清理HTML中的图片标签样式
     *
     * @param html
     * @return
     */
    public static String cleanImgHtml(String html) {
        if (html == null || !html.contains("<img")) {
            return html;
        }
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
        Elements imgElements = doc.select("img");
        for (Element img : imgElements) {
            img.removeAttr("width");
            img.removeAttr("height");
            img.removeAttr("style");
        }
        return doc.body().html();
    }

    /**
     * 表格样式处理
     *
     * @param table      HTML表格字符串
     * @param tableStyle 表格样式
     * @return 处理后的HTML表格字符串
     */
    private static String tableFormat(String table, OutlineProjectStyleDtl tableStyle) {
        if (ValidateUtil.isEmpty(table)) {
            return "";
        }
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(table);

        Double tableBorderWidth = tableStyle.getTableBorderWidth();
        // 磅转px
        int borderPx = tableBorderWidth != null ? (int) Math.round(tableBorderWidth * 1.333) : 1;

        // 设置表格样式
        Elements tables = doc.select("table");
        for (Element tableElement : tables) {
            tableElement.attr("style", "border-collapse:collapse; border:" + borderPx + "px solid #000;");
        }
        // 设置表头样式
        Elements ths = doc.select("th");
        for (Element th : ths) {
            th.attr("style", "border:" + borderPx + "px solid #000;");
        }
        // 设置表格单元格样式
        Elements tds = doc.select("td");
        for (Element td : tds) {
            td.attr("style", "border:" + borderPx + "px solid #000;");
        }
        return doc.body().html();
    }

    /**
     * 去除段落首尾空格
     *
     * @param para
     */
    private static void trimParagraphSpaces(Paragraph para) {
        StringBuilder sb = new StringBuilder();
        for (Run run : para.getRuns()) {
            sb.append(run.getText());
        }
        String trimmed = sb.toString()
                .replaceAll("^[\\s\\u00A0\\u3000]+", "")
                .replaceAll("[\\s\\u00A0\\u3000]+$", "");
        para.removeAllChildren();
        if (!trimmed.isEmpty()) {
            Run newRun = new Run(para.getDocument(), trimmed);
            para.appendChild(newRun);
        }
    }

    /**
     * 将HTML中的标题标签降级
     *
     * @param html      HTML字符串
     * @param baseLevel 基准级别
     * @return 降级后的HTML字符串
     */
    public static String downgradeHtmlHeadings(String html, int baseLevel) {
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
        Elements headings = doc.select("h1, h2, h3, h4, h5, h6");
        for (Element heading : headings) {
            int originalLevel = Integer.parseInt(heading.tagName().substring(1));
            int newLevel = Math.min(baseLevel + originalLevel, 9);
            heading.tagName("h" + newLevel);
        }
        return doc.body().html();
    }

    /**
     * 校验Word文件是否有效
     *
     * @param filePath 文件路径
     * @return 是否有效
     */
    public static boolean validateWordFile(String filePath) {
        try {
            getLicense();
            Document doc = new Document(filePath);
            return doc.getPageCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将Word文件解析为Document
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static org.jsoup.nodes.Document parseWordToDocument(String filePath, String fileName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            getLicense();
            Document doc = new Document(filePath);
            HtmlSaveOptions options = new HtmlSaveOptions();
            options.setExportHeadersFootersMode(ExportHeadersFootersMode.NONE);
            options.setExportListLabels(ExportListLabels.AUTO);
            options.setEncoding(StandardCharsets.UTF_8);
            options.setExportImagesAsBase64(true);
            options.setDocumentSplitHeadingLevel(0);
            doc.save(outputStream, options);
            String html = outputStream.toString(StandardCharsets.UTF_8.name());
            String docTitle = doc.getBuiltInDocumentProperties().getTitle();
            if (docTitle == null || docTitle.trim().isEmpty()) {
                // 用文件名作为标题
                if (fileName != null && !fileName.trim().isEmpty()) {
                    docTitle = fileName.replaceAll("\\.[^.]*$", "");
                } else {
                    String baseName = new File(filePath).getName();
                    docTitle = baseName.replaceAll("\\.[^.]*$", "");
                }
            }
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(html);
            jsoupDoc.title(docTitle);
            return jsoupDoc;
        } catch (Exception e) {
            log.error("Word转HTML失败: {}", filePath, e);
            return null;
        }
    }

    /**
     * 判断HTML内容是否为空
     * 规则：
     * 1. 没有任何文本内容
     * 2. 所有段落、表格、列表等结构均为空（只包含空白字符或单个<br>标签）
     * 3. 图片、占位符算有效内容
     */
    public static boolean isInvalidHtml(String html) {
        if (html == null || html.trim().isEmpty()) {
            return true;
        }
        org.jsoup.nodes.Document doc = Jsoup.parse(html);

        if (html.contains("${")) {
            return false;
        }

        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String src = img.attr("src");
            if (src != null && !src.trim().isEmpty()) {
                return false;
            }
        }

        Elements cells = doc.select("td, th");
        for (Element cell : cells) {
            String text = cell.text().trim();
            boolean onlyBr = cell.children().size() == 1 && "br".equalsIgnoreCase(cell.child(0).tagName());
            if (!text.isEmpty() || (!cell.children().isEmpty() && !onlyBr)) {
                return false;
            }
        }

        Elements ps = doc.select("p");
        for (Element p : ps) {
            String text = p.text().trim();
            boolean onlyBr = p.children().size() == 1 && "br".equalsIgnoreCase(p.child(0).tagName());
            if (!text.isEmpty() || (!p.children().isEmpty() && !onlyBr)) {
                return false;
            }
        }

        Elements lis = doc.select("li");
        for (Element li : lis) {
            if (!li.text().trim().isEmpty()) {
                return false;
            }
        }

        if (!doc.text().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
