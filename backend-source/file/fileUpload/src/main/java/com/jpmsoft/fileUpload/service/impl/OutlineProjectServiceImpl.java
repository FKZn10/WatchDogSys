package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.mapper.OutlineProjectStyleDtlMapper;
import com.jpmsoft.fileUpload.mapper.OutlineProjectStyleMapper;
import com.jpmsoft.fileUpload.pojo.dto.DictDto;
import com.jpmsoft.fileUpload.pojo.dto.OutlineWatermarkControlDto;
import com.jpmsoft.fileUpload.pojo.entity.OutlineProjectStyle;
import com.jpmsoft.fileUpload.pojo.entity.OutlineProjectStyleDtl;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysFileDownload;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.DictionaryUtils;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OutlineProjectServiceImpl implements IOutlineProjectService {
    @Resource
    private PmFeign pmFeign;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileDownloadService sysFileDownloadService;
    @Resource
    private OutlineProjectStyleMapper outlineProjectStyleMapper;
    @Resource
    private OutlineProjectStyleDtlMapper outlineProjectStyleDtlMapper;

    /**
     * 导出项目管理大纲 Word 或 PDF 文件。
     * <p>
     * 当 {@code outlineCatalog} 未配置或其中的 {@code enable} 为 {@code true} 时，导出文件会添加目录：
     * 存在封面模板时目录位于封面之后，否则目录位于文档最前面。
     *
     * @param response 文件下载响应
     * @param opmId    项目管理大纲ID
     * @param export   导出格式，传入 {@code pdf} 时导出PDF，否则导出Word
     * @param token    用户令牌
     */
    @Override
    public void exportOutlineProjectWord(HttpServletResponse response, String opmId, String export, String token) {
        if (ValidateUtil.isNull(opmId)) {
            return;
        }
        Map<String, OutlineProjectStyleDtl> styleDtlMap = getProjectStyleDtlMap("01");

        OutlineProjectManageResponse data = pmFeign.selectOutlineProjectManageById(opmId);
        if (ValidateUtil.isNull(data) || ValidateUtil.isEmpty(data.getOutlineProjectLibListFlow())) {
            return;
        }
        String downloadUrl = sysConfigService.getDownloadUrl();
        if (ValidateUtil.isEmpty(downloadUrl)) {
            return;
        }
        OutlineWatermarkControlDto watermarkControlConf = sysConfigService.getOutlineWatermarkControlConf();
        String outlineCatalogConf = sysConfigService.getSysConfByCode("outlineCatalog");
        boolean outlineCatalog = true;
        if (ValidateUtil.isNotEmpty(outlineCatalogConf)) {
            Object enable = JSONUtil.parseObj(outlineCatalogConf).get("enable");
            outlineCatalog = enable == null || Boolean.parseBoolean(enable.toString());
        }
        File template = null;
        File tmpFile = null;
        File tmpPdf = null;
        try {
            String fileRelationId = sysConfigService.getSysConfByCode("outlineCoverTemplate");
            if (ValidateUtil.isNotEmpty(fileRelationId)) {
                List<SysFileAnnex> files = sysFileAnnexService.getFilePojoByFileRelationIds(Collections.singletonList(fileRelationId));
                SysFileAnnex fileAnnex = files.get(0);
                SysFileDownload sysFileDownload = sysFileDownloadService.getSysFileDownload(fileAnnex.getFileId());
                String url = downloadUrl + "/jpm/downloadFile/" + sysFileDownload.getDownId() + "/" + token;
                template = File.createTempFile("outline_template_", ".docx");
                HttpUtil.downloadFile(url, template);
            }

            String wordFileName = data.getPName() + ".docx";
            tmpFile = File.createTempFile("outline_", ".docx");
            String outPath = tmpFile.getAbsolutePath();
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);

            List<DictDto> gldgbm = DictionaryUtils.getDictList("JPMsoft_GLDGBM");
            Map<String, String> gldgbmMap = gldgbm.stream().collect(Collectors.toMap(DictDto::getCode, DictDto::getName));

            AsposeUtil.exportOutlineWord(
                    data,
                    outPath,
                    downloadUrl,
                    token,
                    watermarkControlConf,
                    user,
                    sysUserService.getById(user.getId()),
                    template,
                    styleDtlMap,
                    gldgbmMap,
                    outlineCatalog
            );

            if ("pdf".equalsIgnoreCase(export)) {
                tmpPdf = File.createTempFile("outline_", ".pdf");
                String pdfOutPath = tmpPdf.getAbsolutePath();
                String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
                String pdfFileName = data.getPName() + ".pdf";
                AsposeUtil.wordToPdf(outPath, pdfOutPath, fontsDirectory);
                FileUtil.downFileAndDelFile(response, pdfOutPath, pdfFileName);
            } else {
                FileUtil.downFileAndDelFile(response, outPath, wordFileName);
            }
        } catch (Exception e) {
            log.error("导出失败", e);
        } finally {
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
            if (template != null && template.exists()) {
                template.delete();
            }
            if (tmpPdf != null && tmpPdf.exists()) {
                tmpPdf.delete();
            }
        }
    }

    private Map<String, OutlineProjectStyleDtl> getProjectStyleDtlMap(String category) {
        String key = "OutlineProjectStyle" + category;
        Object o = redisUtils.get(key);
        if (ValidateUtil.isNotNull(o)) {
            JSONObject jsonObject = JSONUtil.parseObj(o.toString());
            Map<String, OutlineProjectStyleDtl> styleDtlMap = new HashMap<>();
            for (String ca : jsonObject.keySet()) {
                JSONObject object = (JSONObject) jsonObject.get(ca);
                OutlineProjectStyleDtl styleDtl = JSONUtil.toBean(object, OutlineProjectStyleDtl.class);
                styleDtlMap.put(ca, styleDtl);
            }
            return styleDtlMap;
        }
        // 样式获取
        OutlineProjectStyle style = outlineProjectStyleMapper.selectOne(Wrappers.<OutlineProjectStyle>lambdaQuery()
                .eq(OutlineProjectStyle::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(OutlineProjectStyle::getStatus, true)
                .eq(OutlineProjectStyle::getCategory, category)
        );
        if (ValidateUtil.isNull(style)) {
            log.error("未找到有效的样式配置");
            throw new JPMException("未找到有效的样式配置");
        }
        // 样式详情获取
        List<OutlineProjectStyleDtl> styleDtlIds = outlineProjectStyleDtlMapper.selectList(Wrappers.<OutlineProjectStyleDtl>lambdaQuery()
                .eq(OutlineProjectStyleDtl::getOpsId, style.getId())
        );
        if (ValidateUtil.isEmpty(styleDtlIds)) {
            log.error("未找到样式详情");
            throw new JPMException("未找到样式详情");
        }
        Map<String, OutlineProjectStyleDtl> styleDtlMap = new HashMap<>();
        for (OutlineProjectStyleDtl styleDtl : styleDtlIds) {
            styleDtlMap.put(styleDtl.getCategory(), styleDtl);
        }
        // 将样式详情映射存入Redis
        redisUtils.set(key, JSONUtil.toJsonStr(styleDtlMap));
        return styleDtlMap;
    }

    @Override
    public BaseResponse importOutlineProjectWord(MultipartFile file, String osmId, String token) {
        if (file.isEmpty()) {
            return BaseResponse.fail("上传的文件为空");
        }
        File tempFile = null;
        try {
            // 创建临时文件
            tempFile = File.createTempFile("upload_", ".docx");
            file.transferTo(tempFile);
            // 校验文件
            if (!AsposeUtil.validateWordFile(tempFile.getAbsolutePath())) {
                return BaseResponse.fail("Word文件无效或已损坏");
            }
            // 解析成jsoup
            Document word = AsposeUtil.parseWordToDocument(tempFile.getAbsolutePath(), file.getOriginalFilename());
            if (ValidateUtil.isNull(word)) {
                return BaseResponse.fail("Word文件解析失败");
            }
            // 提取h1~h6结构内容
            LinkedHashMap<String, String> sectionMap = new LinkedHashMap<>();
            sectionMap.put("title", word.title());
            List<String> headingsTags = Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6");
            Elements headings = word.select(String.join(",", headingsTags));
            if (headings.isEmpty()) {
                return BaseResponse.fail("Word文件中未找到有效的标题");
            }
            for (int i = 0; i < headings.size(); i++) {
                Element currentHeader = headings.get(i);
                String headerText = currentHeader.text();
                String headerTag = currentHeader.tagName();
                String key = headerTag + ":" + headerText;

                // 处理当前header到下一个header之间的HTML
                Element next = (i + 1 < headings.size()) ? headings.get(i + 1) : null;
                StringBuilder content = new StringBuilder();
                for (Element sibling = currentHeader.nextElementSibling();
                     sibling != null && sibling != next;
                     sibling = sibling.nextElementSibling()) {
                    content.append(sibling.outerHtml()).append("\n");
                }
                sectionMap.put(key, content.toString().trim());
            }
            return pmFeign.importOutlineProjectWord(sectionMap, osmId, token);
        } catch (Exception e) {
            log.error("导入Word文件异常", e);
            return BaseResponse.fail("文件处理异常");
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
