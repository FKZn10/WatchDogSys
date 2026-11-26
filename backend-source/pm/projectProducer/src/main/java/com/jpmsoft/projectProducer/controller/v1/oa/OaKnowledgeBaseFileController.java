package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseFileService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/KnowledgeFile")
public class OaKnowledgeBaseFileController {

    @Resource
    private IOaKnowledgeBaseFileService oaKnowledgeBaseFileService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateOaKnowledgeBaseFile")
    public BaseResponse createOrUpdateOaKnowledgeBaseFile(@RequestBody OaKnowledgeBaseFileModel request) {
        log.info("createOrUpdateOaKnowledgeBaseFile");
        return oaKnowledgeBaseFileService.createOrUpdateOaKnowledgeBaseFile(request);
    }

    @TenantId
    @PostMapping("/getOaKnowledgeBaseFileList")
    public BaseResponse getOaKnowledgeBaseFileList(@RequestBody OaKnowledgeBaseFileModel request) {
        log.info("getOaKnowledgeBaseFileList");
        return oaKnowledgeBaseFileService.getOaKnowledgeBaseFileList(request);
    }

    @PostMapping("/getOaKnowledgeBaseFileAllList")
    public BaseResponse getOaKnowledgeBaseFileAllList(@RequestHeader String token) {
        log.info("getOaKnowledgeBaseFileAllList");
        return oaKnowledgeBaseFileService.getOaKnowledgeBaseFileAllList(redisUtils.getTeId(token));
    }

    @GetMapping("/deleteOaKnowledgeBaseFileById")
    public BaseResponse deleteOaKnowledgeBaseFileById(@RequestParam String kbfId) {
        log.info("deleteOaKnowledgeBaseFileById");
        return oaKnowledgeBaseFileService.deleteOaKnowledgeBaseFileById(kbfId);
    }
}
