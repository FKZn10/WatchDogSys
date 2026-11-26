package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.AiPromptGenerateDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.pojo.dto.OcrFormDTO;
import com.jpmsoft.fileUpload.pojo.entity.AIInfo;
import com.jpmsoft.fileUpload.service.IAIInfoService;
import com.jpmsoft.fileUpload.service.IAiConversationLogService;
import com.jpmsoft.fileUpload.service.IAiInfoExtractRecordService;
import com.jpmsoft.fileUpload.utils.FileToImageUtil;
import com.jpmsoft.fileUpload.utils.FileTxtReadUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.AiClient;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI信息提取
 * 从附件中提取信息 用于表单填充
 */
@RestController
@RequestMapping("/ai/uploadFile")
public class AiInfoExtractController {

    @Resource
    private AiClient aiClient;
    @Resource
    private UserFeign userFeign;
    @Resource
    private IAiInfoExtractRecordService aiInfoExtractRecordService;
    @Resource
    private IAIInfoService aiInfoService;
    @Resource
    private IAiConversationLogService aiConversationLogService;

    @PostMapping("/read")
    public BaseResponse read(@RequestParam("file") MultipartFile file) throws Exception {
        return BaseResponse.ok(FileTxtReadUtil.extractCleanText(file));
    }

    /**
     * 提取文件信息用于表单填充
     */
    @PostMapping("/recognize-multi")
    public BaseResponse recognizePdfMulti(@RequestParam("file") MultipartFile file, @RequestParam String json
            , @RequestParam String tCode, @RequestHeader String token) {
        // 先读取文件字节数组，避免 transferTo 后临时文件被移动
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new JPMException("文件读取失败: " + e.getMessage());
        }
        // 使用字节数组计算MD5
        String fileMd5 = MD5File.getFileKeyFromBytes(fileBytes);
        List<OcrFormDTO> data = JacksonUtils.parseArray(json, OcrFormDTO.class);
        List<String> keys = data.stream().map(OcrFormDTO::getKey).distinct().collect(Collectors.toList());
        String questionStr = String.join("、", keys);
        String questionMd5 = MD5File.md5Hash(questionStr);
        String result = aiInfoExtractRecordService.getAiResult("infoExtract", fileMd5, questionMd5, tCode);
        if (ValidateUtil.isNotEmpty(result)) return BaseResponse.ok(result); // 缓存命中
        AIInfo conf = aiInfoService.selectByType("infoExtract");
        String question = getPrompt("infoExtract", "请告诉我在文件中," + questionStr + "分别是什么?");
        List<String> base64List = new ArrayList<>();
        try {
            base64List.add(FileToImageUtil.convertToBase64(file, 100d));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JPMException("附件转换失败！" + ex.getMessage());
        }
        //ai提取内容
        result = aiClient.simpleMultiModalConversationCall(conf, base64List, question);
        aiInfoExtractRecordService.insertOne(fileMd5, questionMd5, "infoExtract", result, tCode, token);
        aiConversationLogService.createLog(question, result, "infoExtract", token);
        return BaseResponse.ok(result);
    }

    /**
     * 生成 prompt
     *
     * @param type    操作类型
     * @param problem 主体问题
     * @return
     */
    private String getPrompt(String type, String problem) {
        BaseResponse res = userFeign.getAiPromptXmlByBody(new AiPromptGenerateDTO().setType(type).setProblem(problem));
        if (res != null && res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK)
            return res.getData().toString();
        throw new JPMException("无法获取prompt!");
    }

}
