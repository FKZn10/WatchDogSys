package com.jpmsoft.fileUpload.service.impl;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.License;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.PageSetup;
import com.aspose.words.Paragraph;
import com.aspose.words.Range;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.Row;
import com.aspose.words.Table;
import com.aspose.words.WrapType;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.pay.ConPayPrintResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.mapper.PayApplyMapper;
import com.jpmsoft.fileUpload.mapper.TaskProcessinMapper;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.PayApplyApproveDto;
import com.jpmsoft.fileUpload.pojo.dto.aspose.ReplaceAndInsertImage;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.IPayApplyPrintService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.hutool.extra.qrcode.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 付款申请书打印实现
 * <p>
 * 数据: 票据字段复用 PmFeign.getConPayPrintByPayId; 审批链直查 Sys_Task_Processin; 合同签订日期直查 Con_Contract.
 * 模板: SysConfig(Sys_Config) + SysFileAnnex 动态引用.
 * 渲染: 用 Aspose 直接操作 Document —— 加载模板 → 删除/填充工人工资行 → 逐字段 {{key}} 普通文本替换
 *       → 遍历确认表(读左格角色名→匹配审批链→填右格 意见/日期/签名图) → 保存 .docx 下载.
 * <p>
 *
 * @author 付款打印
 */
@Slf4j
@Service
public class PayApplyPrintServiceImpl implements IPayApplyPrintService {

    private static final String CONF_CODE_PAY_PRINT_20W_UP = "payApplyPrint20wUp";
    private static final String CONF_CODE_PAY_PRINT_20W_DOWN = "payApplyPrint20wDown";

    private static final BigDecimal THRESHOLD_20W = new BigDecimal("200000");

    /** 二维码尺寸: 2.5cm ≈ 70.87 磅 */
    private static final double QR_SIZE_PT = 70.87;

    /** 模板中二维码占位符(可选; 未放则默认插到页面右上角) */
    private static final String QR_PLACEHOLDER = "{{printQrCode}}";

    @Resource
    private PmFeign pmFeign;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private PayApplyMapper payApplyMapper;

    @Override
    public void printPayApply(HttpServletResponse response, String payId) {
        if (ValidateUtil.isEmpty(payId)) {
            throw new JPMException("支付ID不能为空");
        }
        Rendered rendered = renderByPayId(payId);
        // 下载 docx
        FileUtil.downFileAndDelFile(response, rendered.docx.getPath(), buildFileName(rendered.pay));
    }

    @Override
    public void previewPayApply(HttpServletResponse response, String payId) {
        if (ValidateUtil.isEmpty(payId)) {
            throw new JPMException("支付ID不能为空");
        }
        Rendered rendered = renderByPayId(payId);
        File docx = rendered.docx;
        File pdf = null;
        try {
            // docx -> pdf
            String root = sysConfigService.getRootDirectory();
            String pdfPath = FileUtil.temporaryPath(root, "pdf");
            String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
            AsposeUtil.wordToPdf(docx.getPath(), pdfPath, fontsDirectory);
            pdf = new File(pdfPath);
            // 输出 HTML 页面(PDF 每页转图片内嵌 base64) —— 微信/浏览器均可直接预览, 不会触发下载
            writePreviewHtml(response, pdf, buildFileName(rendered.pay));
        } catch (Exception ex) {
            log.error("付款申请书预览失败, payId={}", payId, ex);
            throw new JPMException("付款申请书预览失败: " + ex.getMessage());
        } finally {
            if (docx != null && docx.exists()) docx.delete();
            if (pdf != null && pdf.exists()) pdf.delete();
        }
    }

    /** 渲染结果持有(票据数据 + 生成的docx临时文件) */
    private static class Rendered {
        final ConPayPrintResponse pay;
        final File docx;

        Rendered(ConPayPrintResponse pay, File docx) {
            this.pay = pay;
            this.docx = docx;
        }
    }

    /**
     * 按 payId 渲染出 docx 临时文件(打印与预览共用).
     */
    private Rendered renderByPayId(String payId) {
        // 1. 票据数据(合同/支付/公司名) —— 复用 projectProducer 现有接口
        ConPayPrintResponse pay = getConPayPrintData(payId);

        // 2. 按合同金额选模板
        String confCode = chooseTemplate(pay);
        File templateFile = loadTemplateFile(confCode);

        // 3. 审批链(支付确认表) —— 直查已办表
        List<PayApplyApproveDto> approves = taskProcessinMapper.listPayApplyApprove(payId);

        // 4. 渲染 docx
        File docx = renderDocx(templateFile, pay, payId, approves);
        return new Rendered(pay, docx);
    }

    /**
     * 输出 HTML 预览页: 把 PDF 每页渲染成 PNG, 以 base64 内嵌到 HTML 中.
     * <p>
     */
    private void writePreviewHtml(HttpServletResponse response, File pdf, String title) throws Exception {
        StringBuilder pages = new StringBuilder();
        try (PDDocument doc = PDDocument.load(pdf)) {
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                // 150 DPI 兼顾清晰度与体积
                BufferedImage img = renderer.renderImageWithDPI(i, 150);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", bos);
                String b64 = Base64.getEncoder().encodeToString(bos.toByteArray());
                pages.append("<div class=\"page\"><img src=\"data:image/png;base64,").append(b64).append("\" alt=\"第")
                     .append(i + 1).append("页\"/></div>");
            }
        }
        String html = "<!DOCTYPE html><html lang=\"zh-CN\"><head>"
                + "<meta charset=\"UTF-8\"/>"
                + "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=3\"/>"
                + "<title>" + escapeHtml(title) + "</title>"
                + "<style>"
                + "html,body{margin:0;padding:0;background:#e9e9e9;-webkit-text-size-adjust:100%;}"
                + ".page{margin:8px auto;max-width:900px;}"
                + ".page img{width:100%;height:auto;display:block;box-shadow:0 1px 4px rgba(0,0,0,.18);background:#fff;}"
                + "</style></head><body>" + pages + "</body></html>";

        byte[] bytes = html.getBytes("UTF-8");
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setContentLength(bytes.length);
        try (OutputStream out = response.getOutputStream()) {
            out.write(bytes);
            out.flush();
        }
    }

    /** HTML 转义(标题里可能有 & < > 等) */
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /**
     * PDF 内联输出(备用): Content-Disposition=inline.
     */
    private void inlineOutputPdf(HttpServletResponse response, File pdf, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        String encoded = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "inline; filename=\"" + encoded + "\"");
        response.setHeader("Content-Length", String.valueOf(pdf.length()));
        try (InputStream in = new FileInputStream(pdf); OutputStream out = response.getOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            out.flush();
        }
    }

    // ------------------------------------------------------------------ 数据获取

    private ConPayPrintResponse getConPayPrintData(String payId) {
        BaseResponse res = pmFeign.getConPayPrintByPayId(payId);
        if (res == null || !res.getSuccess() || ValidateUtil.isNull(res.getData())) {
            throw new JPMException("未获取到付款打印数据, 支付ID=" + payId);
        }
        ConPayPrintResponse data = JacksonUtils.parseObject(
                JacksonUtils.toJSONString(res.getData()), ConPayPrintResponse.class);
        if (data == null) {
            throw new JPMException("付款打印数据转换失败, 支付ID=" + payId);
        }
        return data;
    }

    private String chooseTemplate(ConPayPrintResponse pay) {
        String moneyStr = pay.getConMoneyTotal();
        if (ValidateUtil.isNotEmpty(moneyStr)) {
            try {
                BigDecimal money = new BigDecimal(String.valueOf(moneyStr).replace(",", ""));
                if (money.compareTo(THRESHOLD_20W) >= 0) {
                    return CONF_CODE_PAY_PRINT_20W_UP;
                }
            } catch (NumberFormatException ex) {
                log.warn("合同金额解析失败, 使用20万以下模板: {}", moneyStr);
            }
        }
        return CONF_CODE_PAY_PRINT_20W_DOWN;
    }

    private File loadTemplateFile(String confCode) {
        String relationId = sysConfigService.getSysConfByCode(confCode);
        if (ValidateUtil.isEmpty(relationId)) {
            throw new JPMException("未配置付款申请书模板(" + confCode + "), 请在SysConfig中配置");
        }
        SysFileAnnex file = sysFileAnnexService.getOne(
                Wrappers.<SysFileAnnex>lambdaQuery()
                        .eq(SysFileAnnex::getFileRelationId, relationId)
                        .last(" limit 1 ")
        );
        if (file == null) {
            throw new JPMException("付款申请书模板[" + confCode + "]附件不存在");
        }
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        String basePath = Integer.valueOf(1).equals(file.getArchive()) ? archive : root;
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), file.getFilePath(), basePath, file.getArchive());
        File sourceFile = new File(basePath + "/" + file.getFilePath());
        if (!sourceFile.exists()) {
            throw new JPMException("付款申请书模板[" + confCode + "]源文件不存在");
        }
        return sourceFile;
    }

    // ------------------------------------------------------------------ 渲染

    /**
     * 渲染 docx, 返回生成的临时文件.
     * 步骤: 载模板 → 处理工人工资行 → 文本占位符替换 → 确认表(签名/意见/日期) → 二维码 → 保存.
     */
    private File renderDocx(File templateFile, ConPayPrintResponse pay, String payId,
                            List<PayApplyApproveDto> approves) {
        try {
            loadLicense();
            Document doc = new Document(templateFile.getPath());

            // 1. 工人工资行: payPayeeCaWages 非空 -> 保留并填充; 空 -> 删除模板中的两行
            boolean hasWages = ValidateUtil.isNotEmpty(pay.getPayPayeeCaWages());
            handleWagesLines(doc, hasWages);

            // 2. 票据字段 {{key}} 普通替换
            Map<String, String> textVals = buildTextValues(pay, payId);
            Range range = doc.getRange();
            for (Map.Entry<String, String> e : textVals.entrySet()) {
                String key = e.getKey();
                String val = e.getValue();
                if (key == null || val == null) continue;
                try {
                    range.replace("{{" + key + "}}", val);
                } catch (Exception ex) {
                    log.warn("替换占位符失败: {} -> {}", key, ex.getMessage());
                }
            }

            // 3. 支付确认表: 遍历表格读左格角色名, 匹配审批链, 填右格(签名图+意见+日期)
            fillConfirmTable(doc, approves);

            // 4. 打印二维码(右上角): 扫码后微信预览 PDF
            insertQrCode(doc, payId);

            // 5. 保存
            String root = sysConfigService.getRootDirectory();
            String outPath = FileUtil.temporaryPath(root, "docx");
            doc.save(outPath);
            return new File(outPath);
        } catch (Exception ex) {
            log.error("渲染付款申请书失败, payId={}", payId, ex);
            throw new JPMException("付款申请书渲染失败: " + ex.getMessage());
        }
    }

    /**
     * 在文档右上角插入打印二维码.
     * 扫码指向无token预览接口, 微信扫码后以 PDF 形式预览本文档.
     * 若模板放置了占位符 {{printQrCode}}, 则就地替换; 否则默认插到页面右上角(浮动, 不影响正文排版).
     */
    private void insertQrCode(Document doc, String payId) {
        String qrPath = null;
        try {
            qrPath = generateQrCode(payId);
            if (qrPath == null) {
                return;
            }
            // 优先: 模板占位符定位
            String docText = doc.getRange().getText();
            if (docText != null && docText.contains(QR_PLACEHOLDER)) {
                doc.getRange().replace(
                        Pattern.compile(Pattern.quote(QR_PLACEHOLDER)), "",
                        new FindReplaceOptions(new ReplaceAndInsertImage(qrPath, QR_SIZE_PT, QR_SIZE_PT)));
                return;
            }
            // 默认: 页面右上角浮动图片
            PageSetup ps = doc.getFirstSection().getPageSetup();
            double left = ps.getPageWidth() - ps.getRightMargin() - QR_SIZE_PT;
            double top = ps.getTopMargin();
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.moveToDocumentStart();
            builder.insertImage(qrPath,
                    RelativeHorizontalPosition.PAGE, left,
                    RelativeVerticalPosition.PAGE, top,
                    QR_SIZE_PT, QR_SIZE_PT, WrapType.NONE);
        } catch (Exception ex) {
            log.warn("插入打印二维码失败, payId={}: {}", payId, ex.getMessage(), ex);
        } finally {
            // 图片已嵌入文档, 删除临时二维码文件
            if (qrPath != null) {
                File qf = new File(qrPath);
                if (qf.exists()) {
                    qf.delete();
                }
            }
        }
    }

    /**
     * 生成二维码图片(内容为无token的预览地址), 返回本地文件路径.
     */
    private String generateQrCode(String payId) {
        try {
            String downloadUrl = sysConfigService.getSysConfByCode("downloadUrl");
            if (ValidateUtil.isEmpty(downloadUrl)) {
                log.warn("downloadUrl 未配置, 跳过二维码生成");
                return null;
            }
            String url = downloadUrl + "/jpm/payApplyPreview/" + payId;
            String root = sysConfigService.getRootDirectory();
            File qrFile = new File(FileUtil.temporaryPath(root, "png"));
            File parent = qrFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            // 高分辨率生成, 插入 Word 时缩放到 2.5cm
            QrCodeUtil.generate(url, 300, 300, qrFile);
            log.info("付款打印二维码: {} -> {}", url, qrFile.getAbsolutePath());
            return qrFile.getAbsolutePath();
        } catch (Exception ex) {
            log.warn("生成二维码失败, payId={}: {}", payId, ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 处理工人工资两行: payPayeeCaWages 非空则保留(占位符会被替换); 空则把这两行从模板删除.
     */
    private void handleWagesLines(Document doc, boolean hasWages) {
        if (hasWages) {
            return;
        }
        try {
            Node[] nodes = doc.getChildNodes(NodeType.PARAGRAPH, true).toArray();
            for (Node n : nodes) {
                if (n instanceof Paragraph) {
                    String txt = n.getText();
                    if (txt.contains("{{payPayeeCaNameWages}}") || txt.contains("{{payPayeeBankWages}}")
                            || txt.contains("{{payPayeeCaWages}}")) {
                        n.remove();
                    }
                }
            }
        } catch (Exception ex) {
            log.warn("处理工人工资行失败: {}", ex.getMessage());
        }
    }

    /**
     * 票据字段占位符映射(主体固定字段).
     * 文案/标签在模板里, 这里只做「占位符名 → 取值」, 不硬编码模板文案.
     */
    private Map<String, String> buildTextValues(ConPayPrintResponse pay, String payId) {
        Map<String, String> vals = new HashMap<>();
        vals.put("conCode", nvl(pay.getConCode()));
        vals.put("conDate", formatConDate(payId));
        vals.put("conName", nvl(pay.getConName()));
        vals.put("conMoneyTotal", nvl(pay.getConMoneyTotal()));
        vals.put("payTotalLast", nvl(pay.getPayTotalLast()));
        vals.put("payApplyAmount", computeCurrentPay(pay));
        // 收款单位 = 账户名称(payPayeeCaName), 不是公司名(payPayeeName)
        vals.put("payPayeeCaName", nvl(pay.getPayPayeeCaName()));
        vals.put("payContactNumber", nvl(pay.getPayContactNumber()));
        vals.put("payPayeeBank", nvl(pay.getPayPayeeBank()));
        vals.put("payPayeeCa", nvl(pay.getPayPayeeCa()));

        // 工人工资(有值时才有意义; 无值行已被删除)
        // 工人工资收款单位 = 账户名称(payPayeeCaNameWages), 不是 payPayeeNamePro
        vals.put("payPayeeCaNameWages", nvl(pay.getPayPayeeCaNameWages()));
        vals.put("payPayeeBankWages", nvl(pay.getPayPayeeBankWages()));
        vals.put("payPayeeCaWages", nvl(pay.getPayPayeeCaWages()));
        return vals;
    }

    /**
     * 填充支付确认表: 遍历表格每一行, 读左格文字作为审批节点名(模板自带, 不硬编码),
     * 去审批链中容错匹配(去空格/全半角), 命中则在右格填 电子签名图 + 审批意见 + 审批时间.
     */
    private void fillConfirmTable(Document doc, List<PayApplyApproveDto> approves) {
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        for (int r = 0; r < table.getRows().getCount(); r++) {
            Row row = table.getRows().get(r);
            String role = row.getCells().get(0).getText().trim();
            PayApplyApproveDto hit = matchApprove(approves, role);
            if (hit == null) {
                continue;
            }
            Cell cell = row.getCells().get(1);
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.moveTo(cell.getFirstParagraph());

            // 电子签名图: U_Signature 存的是附件 file_relation_id(与 downloadOpenFile 一致)
            if (ValidateUtil.isNotEmpty(hit.getUSignature())) {
                SysFileAnnex annex = sysFileAnnexService.getOneFileAnnexByRelationId(hit.getUSignature());
                log.info("付款打印签名: role={}, uSignature={}, 附件={}", role, hit.getUSignature(),
                        annex == null ? "null" : annex.getFilePath());
                if (annex != null) {
                    String basePath = Integer.valueOf(1).equals(annex.getFileLocation()) ? archive : root;
                    try {
                        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), annex.getFilePath(), basePath, annex.getFileLocation());
                        File img = new File(basePath + "/" + annex.getFilePath());
                        log.info("付款打印签名图路径: {}, exists={}", img.getAbsolutePath(), img.exists());
                        if (img.exists()) {
                            builder.insertImage(img.getAbsolutePath(), 40D, 18D);
                            builder.write(" ");
                        } else {
                            log.warn("付款打印签名图文件不存在: {}", img.getAbsolutePath());
                        }
                    } catch (Exception ex) {
                        log.warn("插入签名图失败 node={}: {}", role, ex.getMessage(), ex);
                    }
                }
            }

            // 审批意见 + 审批时间
            builder.write(nvl(hit.getTpExplain()));
            if (hit.getTpDotime() != null) {
                builder.write("  " + sdf.format(hit.getTpDotime()));
            }
        }
    }

    /**
     * 审批记录匹配(去空格/全半角): 先精确匹配, 再包含匹配兜底.
     * 兜底解决模板列名与流程节点名的后缀/前缀差异(如"甲方代表意见" vs "甲方代表").
     */
    private PayApplyApproveDto matchApprove(List<PayApplyApproveDto> approves, String nodeName) {
        if (approves == null || approves.isEmpty()) return null;
        String norm = normalize(nodeName);
        // 1. 精确匹配
        for (PayApplyApproveDto a : approves) {
            if (norm.equals(normalize(a.getActName()))) {
                return a;
            }
        }
        // 2. 包含匹配兜底(模板列名包含流程节点名 或 反之)
        for (PayApplyApproveDto a : approves) {
            String actNorm = normalize(a.getActName());
            if (norm.isEmpty() || actNorm.isEmpty()) continue;
            if (norm.contains(actNorm) || actNorm.contains(norm)) {
                return a;
            }
        }
        return null;
    }

    private String normalize(String s) {
        if (s == null) return "";
        String r = s.replaceAll("\\s+", "");
        StringBuilder sb = new StringBuilder();
        for (char c : r.toCharArray()) {
            if (c >= 0xFF01 && c <= 0xFF5E) {
                sb.append((char) (c - 0xFEE0));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String computeCurrentPay(ConPayPrintResponse pay) {
        String payType = pay.getPayType();
        if (ValidateUtil.isEmpty(payType)) return "";
        if ("01".equals(payType) || "02".equals(payType)) {
            return nvl(pay.getPayPayable());
        }
        return nvl(pay.getPayApproval());
    }

    private String buildFileName(ConPayPrintResponse pay) {
        String moneyStr = pay.getConMoneyTotal();
        String level = "20万以下";
        if (ValidateUtil.isNotEmpty(moneyStr)) {
            try {
                BigDecimal money = new BigDecimal(String.valueOf(moneyStr).replace(",", ""));
                level = money.compareTo(THRESHOLD_20W) >= 0 ? "20万以上" : "20万以下";
            } catch (NumberFormatException ignored) {
            }
        }
        String conName = pay.getConName() == null ? "" : pay.getConName();
        return "付款申请书(" + level + ")-" + conName + ".docx";
    }

    private void loadLicense() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("license.xml")) {
            License license = new License();
            license.setLicense(is);
        } catch (Exception e) {
            log.warn("加载Aspose license失败, 可能有水印: {}", e.getMessage());
        }
    }

    private String nvl(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    private String formatConDate(String payId) {
        try {
            Date conDate = payApplyMapper.getConDateByPayId(payId);
            if (conDate == null) return "";
            return new SimpleDateFormat("yyyy年MM月dd日").format(conDate);
        } catch (Exception ex) {
            log.warn("查询合同签订日期失败, payId={}", payId, ex);
            return "";
        }
    }
}