package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "知识库文件", value = "知识库文件")
@RequestMapping("/OA/KnowledgeFile")
public class OaKnowledgeBaseFileController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改知识库文件")
    @PostMapping("/createOrUpdateOaKnowledgeBaseFile")
    public BaseResponse createOrUpdateOaKnowledgeBaseFile(@RequestBody OaKnowledgeBaseFileModel request) {
        log.info("createOrUpdateOaKnowledgeBaseFile");
        return oaFeign.createOrUpdateOaKnowledgeBaseFile(request);
    }

    @ApiOperation(value = "分页查询知识库文件")
    @PostMapping("/getOaKnowledgeBaseFileList")
    public BaseResponse getOaKnowledgeBaseFileList(@RequestBody OaKnowledgeBaseFileModel request) {
        log.info("getOaKnowledgeBaseFileList");
        return oaFeign.getOaKnowledgeBaseFileList(request);
    }

    @ApiOperation(value = "知识库所有文件")
    @PostMapping("/getOaKnowledgeBaseFileAllList")
    public BaseResponse getOaKnowledgeBaseFileAllList() {
        log.info("getOaKnowledgeBaseFileAllList");
        return oaFeign.getOaKnowledgeBaseFileAllList();
    }

    @ApiOperation(value = "ID删除知识库文件")
    @GetMapping("/deleteOaKnowledgeBaseFileById")
    public BaseResponse deleteOaKnowledgeBaseFileById(@RequestParam String kbfId) {
        log.info("deleteOaKnowledgeBaseFileById");
        return oaFeign.deleteOaKnowledgeBaseFileById(kbfId);
    }
}
