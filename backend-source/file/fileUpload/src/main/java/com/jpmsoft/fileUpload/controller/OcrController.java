package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.pojo.dto.AnswerDTO;
import com.jpmsoft.fileUpload.pojo.dto.OcrConfigDTO;
import com.jpmsoft.fileUpload.pojo.dto.OcrFormDTO;
import com.jpmsoft.fileUpload.service.IFileUploadService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileService;
import com.jpmsoft.fileUpload.utils.AliyunOcrUtil;
import com.jpmsoft.fileUpload.utils.FileParserUtil;
import com.jpmsoft.fileUpload.utils.PdfToImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/14 9:11
 */
@Slf4j
@RestController
@Api(tags = "OCR图片识别", value = "OCR图片识别")
@RequestMapping("/ocr")
public class OcrController {

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private ISysFileService sysFileService;

    private static final String TEMP_IMAGE_DIR = System.getProperty("java.io.tmpdir") + "/ocr_temp";

    @PostMapping("/parseAttachment")
    public BaseResponse parseAttachment(@RequestParam MultipartFile file) throws IOException {
        String root = sysConfigService.getRootDirectory();
        File f = fileUploadService.temporaryStorageFile(root, file, ".*\\.(png|jpg|jpeg|bmp|gif|tiff|WebP|pdf)$", FileMsg.ocrFileType, null);
        String result = null;
        try {
            if (f.getName().toLowerCase(Locale.ENGLISH).endsWith("docx")) {
                result = FileParserUtil.parseWord(f, TEMP_IMAGE_DIR);
            } else if (f.getName().toLowerCase(Locale.ENGLISH).endsWith("pdf")) {
                result = FileParserUtil.parsePdf(f, TEMP_IMAGE_DIR);
            }
        } finally {
            if (f.exists() && f.isFile()) f.delete();
        }
        return BaseResponse.ok(result);
    }


    @ApiOperation(value = "阿里云OCR统一识别")
    @GetMapping("/ocr")
    public BaseResponse ocr(@RequestParam String path) throws Exception {
        OcrConfigDTO conf = sysConfigService.getOcrConfig();
        AliyunOcrUtil.ocr(conf.getAccessKey(), conf.getSecretKey(), path);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "阿里云OCR票据识别")
    @PostMapping("/ocrForm")
    public BaseResponse ocrForm(@RequestParam MultipartFile file, String json) throws Exception {
        List<OcrFormDTO> data = JacksonUtils.parseArray(json, OcrFormDTO.class);
        if (CollectionUtils.isEmpty(data)) return BaseResponse.fail(FileMsg.noReqParam);
        String root = sysConfigService.getRootDirectory();
        File f = fileUploadService.temporaryStorageFile(root, file, ".*\\.(png|jpg|jpeg|bmp|gif|tiff|WebP|pdf)$", FileMsg.ocrFileType, null);
        Boolean pdf = false;//判断是否是PDF
        if (f.getName().toLowerCase(Locale.ENGLISH).endsWith("pdf")) pdf = true;
        try {
            List<String> keys = data.stream().map(OcrFormDTO::getKey).distinct().collect(Collectors.toList());
            OcrConfigDTO conf = sysConfigService.getOcrConfig();
            //ocr检索结果
            Map<String, String> result;
            if (!pdf) result = AliyunOcrUtil.ocrForm(conf.getAccessKey(), conf.getSecretKey(), f.getPath(), keys);
            else result = pdfOcr(root, f, keys, conf);
            if (result != null && result.size() > 0) {
                data.forEach(d -> {
                    if (result.containsKey(d.getKey()))
                        d.setContent(result.get(d.getKey()));
                    //传入参数没有检索相关信息 无法检索数据
                    if (d.getSearchPrimary() && !ValidateUtil.isAnObjEmpty(d.getTable(), d.getField(), d.getIdKey())) {//支持检索
                        String sql = "select " + d.getIdKey() + " from " + d.getTable() + " where " + d.getField() + "='" + d.getContent() + "' limit 1 ;";
                        try {
                            d.setIdValue(sysFileService.executeSql(sql));
                        } catch (Exception ex) {
                            log.error("查询失败！忽略结果" + ex.getMessage());
                        }
                    }
                });
                return BaseResponse.ok(data);
            } else return BaseResponse.ok();
        } finally {
            f.delete();
        }
    }


    /**
     * PDF转图片后OCR识别
     */
    private Map<String, String> pdfOcr(String root, File f, List<String> keys, OcrConfigDTO conf) throws Exception {
        Map<String, String> result = new HashMap<>();
        String dirPath = root + "/pdftoimg/" + GenerateUUID.getUUID() + "/";
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdirs();
        try {

            PdfToImageConverter.convertToImages(f.getPath(), dirPath, "png", 400);
            //获取所有png
            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null && files.length > 0) {
                int i = 1;
                Tesseract tesseract = new Tesseract();
                //tesseract.setDatapath(AsposeUtil.class.getResource("/").getPath() + "tesseract");
                tesseract.setDatapath("D:/Java/SelfCode/file/fileUpload/src/main/resources/tesseract/");
                tesseract.setLanguage("chi_sim");
                StringBuilder sb = new StringBuilder();
                for (File file : files) { //遍历图片识别
                    sb.append(tesseract.doOCR(file));
//                if (result.size() < keys.size()) { //所有key都获取到则暂停
//                    if (i<=5) {
//                        if (i == 1)
//                            pdfOcr(result, file, keys, conf);
//                        else {
//                            Thread.sleep(2000);
//                            pdfOcr(result, file, keys, conf);
//                        }
//                        i++;
//                    }
//                }
                }
                String anser = "请告诉我上述内容中" + String.join("、", keys) + "是什么？" +
                        "如果上述需要提取的内容格式是:" +
                        "请返回json格式数据" +
                        ",格式案例:{\"项目名称\":\"测试一下\"},其中金额字段请给我转换成单位元数值不要带单位," +
                        "日期转成yyyy-MM-dd格式,时间转成yyyy-MM-dd HH:mm:ss格式," +
                        "返回JSON格式中每个key对应value格式均为字符串,请严格按照格式案例返回,前后不要包裹说明或者其他内容";
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setSystemPrompt(sb.toString());
                answerDTO.setUserPromp(anser);
                BaseResponse res = userFeign.chatJson(answerDTO);
                if (res == null || !res.getSuccess()) throw new JPMException("AI模型响应超时或失败！");
                String json = res.getData().toString();
                if (json.startsWith("```json")) json = json.replaceAll("```json", "");
                if (json.endsWith("```")) json = json.replaceAll("```", "");
                Map<String, String> data = JacksonUtils.parseObject(json, Map.class);
                return data;
            }
            return result;
        } finally {
            dir.delete();
        }
    }

    @Resource
    private UserFeign userFeign;

    public BaseResponse ocr() throws TesseractException {
        Tesseract tesseract = new Tesseract();
        //tesseract.setDatapath(AsposeUtil.class.getResource("/").getPath() + "tesseract");
        tesseract.setDatapath("E:/Java/SelfCode/file/fileUpload/target/classes/tesseract/");
        tesseract.setLanguage("chi_sim");
        String ocr = tesseract.doOCR(new File("F://tesseract/data/images/1741310609653.png"));
        String anser = "请告诉我上述内容中项目名称是什么？请返回json格式数据,格式案例{\"项目名称\":\"测试一下\"},不要返回其他描述信息";
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setSystemPrompt(ocr);
        answerDTO.setUserPromp(anser);
        return BaseResponse.ok(userFeign.chatJson(answerDTO));
    }

    private void pdfOcr(Map<String, String> result, File png, List<String> keys, OcrConfigDTO conf) throws Exception {
        if (!CollectionUtils.isEmpty(keys)) {
            Map<String, String> res = AliyunOcrUtil.ocrForm(conf.getAccessKey(), conf.getSecretKey(), png.getPath(), keys);
            for (Map.Entry<String, String> entry : res.entrySet()) {
                if (!result.containsKey(entry.getKey()) && ValidateUtil.isNotEmpty(entry.getValue())) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }


    /**
     * 文件转 Base64 字符串
     */
    public static String fileToBase64(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }
}
