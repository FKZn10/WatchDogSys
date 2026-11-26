package com.jpmsoft.fileUpload.service.impl;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.License;
import com.aspose.words.Range;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.EnterpriseDeclarationPrintDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.IEnterpriseDeclarationPrintService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 企业申报表打印实现
 * <p>
 * 数据：前端把整条企业申报数据作为对象 POST 过来，<b>不查库</b>；字典译文由前端完成，后端原样渲染。
 * 模板：走配置项（与付款申请书打印一致）——SysConfig 中配置 code
 * {@value #CONF_CODE}，其值是一个 fileRelationId，再按 relationId 从 SYS_FileAnnex 取模板文件。
 * 渲染：Aspose 直接操作 Document —— 加载模板 → 逐字段 {{key}} 普通文本替换 → 兜底清空任何未替换的
 * 占位符 → 保存 docx → AsposeUtil.wordToPdf 转 PDF → 下载。
 * <p>
 * 占位符全部在模板里（文案/标签/表格结构都在 .docx 中），代码只做「占位符名 → 取值」映射，
 * 改模板不改代码。字段为空/为 null 时渲染为空串，且渲染后统一清空残留占位符，保证 PDF 里不出现 {{xxx}}。
 * <p>
 * 模板文件（供运维上传）见 file 模块 docs/企业申报表模板/ 目录。
 * 全部为新增代码，不改动任何原有逻辑。
 *
 * @author 企业申报表打印
 */
@Slf4j
@Service
public class EnterpriseDeclarationPrintServiceImpl implements IEnterpriseDeclarationPrintService {

    /**
     * SysConfig 配置项 code：其值 = 模板的 fileRelationId
     * <p>需在 SysConfig 中配置，并把模板 docx 上传到系统获得 fileRelationId</p>
     */
    private static final String CONF_CODE = "enterpriseDeclarationPrint";

    /** 未替换占位符兜底：匹配任何形如 {{xxx}} 的残留 */
    private static final Pattern LEFT_PLACEHOLDER = Pattern.compile("\\{\\{[^{}]*}}");

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void printEnterpriseDeclaration(HttpServletResponse response, EnterpriseDeclarationPrintDTO data) {
        if (data == null) {
            throw new JPMException("企业申报数据不能为空");
        }
        File docx = null;
        File pdf = null;
        try {
            // 0. 取模板（SysConfig 配 code → fileRelationId → SYS_FileAnnex）
            File templateFile = loadTemplateFile(CONF_CODE);

            // 1. 渲染 docx
            docx = renderDocx(templateFile, data);

            // 2. docx -> pdf
            String root = sysConfigService.getRootDirectory();
            String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
            String pdfPath = FileUtil.temporaryPath(root, "pdf");
            AsposeUtil.wordToPdf(docx.getPath(), pdfPath, fontsDirectory);
            pdf = new File(pdfPath);

            // 3. 下载 PDF（下载后自动删除临时文件）
            FileUtil.downFileAndDelFile(response, pdf.getPath(), buildFileName(data));
        } catch (Exception ex) {
            log.error("企业申报表打印失败, companyName={}", data.getCompanyName(), ex);
            if (ex instanceof JPMException) {
                throw (JPMException) ex;
            }
            throw new JPMException("企业申报表打印失败: " + ex.getMessage());
        } finally {
            if (docx != null && docx.exists()) {
                docx.delete();
            }
            if (pdf != null && pdf.exists()) {
                pdf.delete();
            }
        }
    }

    /**
     * 按配置项取模板文件（与付款申请书打印同一套机制）。
     * <p>SysConfig[confCode] → fileRelationId → SYS_FileAnnex → 本地/归档目录中的物理文件。</p>
     */
    private File loadTemplateFile(String confCode) {
        String relationId = sysConfigService.getSysConfByCode(confCode);
        if (ValidateUtil.isEmpty(relationId)) {
            throw new JPMException("未配置企业申报表模板(" + confCode + "), 请在SysConfig中配置");
        }
        SysFileAnnex file = sysFileAnnexService.getOne(
                Wrappers.<SysFileAnnex>lambdaQuery()
                        .eq(SysFileAnnex::getFileRelationId, relationId)
                        .last(" limit 1 ")
        );
        if (file == null) {
            throw new JPMException("企业申报表模板[" + confCode + "]附件不存在");
        }
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        String basePath = Integer.valueOf(1).equals(file.getArchive()) ? archive : root;
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), file.getFilePath(), basePath, file.getArchive());
        File sourceFile = new File(basePath + "/" + file.getFilePath());
        if (!sourceFile.exists()) {
            throw new JPMException("企业申报表模板[" + confCode + "]源文件不存在");
        }
        return sourceFile;
    }

    /**
     * 渲染模板生成临时 docx 文件。
     */
    private File renderDocx(File templateFile, EnterpriseDeclarationPrintDTO data) throws Exception {
        loadLicense();
        Document doc = new Document(templateFile.getPath());

        // 逐字段普通文本替换（不用 ReportingEngine，该版本 << >> 语法不兼容）
        Range range = doc.getRange();
        for (Map.Entry<String, String> e : buildTextValues(data).entrySet()) {
            try {
                range.replace("{{" + e.getKey() + "}}", e.getValue());
            } catch (Exception ex) {
                log.warn("替换占位符失败: {} -> {}", e.getKey(), ex.getMessage());
            }
        }

        // 兜底：清掉任何不在映射表里的占位符，保证 PDF 里绝不残留 {{xxx}}
        range.replace(LEFT_PLACEHOLDER, "", new FindReplaceOptions());
        if (doc.getRange().getText().contains("{{")) {
            log.warn("企业申报表渲染后仍存在未替换的占位符，已统一置空");
        }

        String root = sysConfigService.getRootDirectory();
        String outPath = FileUtil.temporaryPath(root, "docx");
        doc.save(outPath);
        return new File(outPath);
    }

    /**
     * 占位符名 → 取值映射。
     * 模板里的文案/标签不动，这里只负责取值；空值一律给空串。
     */
    private Map<String, String> buildTextValues(EnterpriseDeclarationPrintDTO data) {
        Map<String, String> vals = new LinkedHashMap<>();
        vals.put("declareYear", nvl(data.getDeclareYear()));
        vals.put("declareType", nvl(data.getDeclareType()));
        vals.put("declarePurpose", nvl(data.getDeclarePurpose()));
        vals.put("declareMajor", nvl(data.getDeclareMajor()));
        vals.put("declareContact", nvl(data.getDeclareContact()));
        vals.put("contactPhone", nvl(data.getContactPhone()));
        vals.put("companyName", nvl(data.getCompanyName()));
        vals.put("creditCode", nvl(data.getCreditCode()));
        vals.put("companyType", nvl(data.getCompanyType()));
        vals.put("companyQualification", nvl(data.getCompanyQualification()));
        vals.put("registeredCapital", nvl(data.getRegisteredCapital()));
        vals.put("legalPerson", nvl(data.getLegalPerson()));
        vals.put("legalPersonPhone", nvl(data.getLegalPersonPhone()));
        vals.put("registeredAddress", nvl(data.getRegisteredAddress()));
        vals.put("officeAddress", nvl(data.getOfficeAddress()));
        vals.put("companyProfile", nvl(data.getCompanyProfile()));
        return vals;
    }

    /**
     * 下载文件名：智能建造企业申报表-{企业名称}.pdf
     */
    private String buildFileName(EnterpriseDeclarationPrintDTO data) {
        String name = nvl(data.getCompanyName());
        return "智能建造企业申报表" + (name.isEmpty() ? "" : "-" + name) + ".pdf";
    }

    /**
     * 取值转字符串：null / 空一律为空串。
     */
    private String nvl(Object o) {
        if (o == null) {
            return "";
        }
        // BigDecimal 用 toPlainString 避免科学计数法
        String s = (o instanceof java.math.BigDecimal)
                ? ((java.math.BigDecimal) o).toPlainString()
                : String.valueOf(o);
        return s.trim();
    }

    private void loadLicense() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("license.xml")) {
            License license = new License();
            license.setLicense(is);
        } catch (Exception e) {
            log.warn("加载Aspose license失败, 可能有水印: {}", e.getMessage());
        }
    }
}
