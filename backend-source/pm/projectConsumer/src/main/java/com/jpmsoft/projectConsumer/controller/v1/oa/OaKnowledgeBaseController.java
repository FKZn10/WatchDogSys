package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseModel;
import com.jpmsoft.projectConsumer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "知识库目录", value = "知识库目录")
@RequestMapping("/OA/Knowledge")
public class OaKnowledgeBaseController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改知识库目录")
    @PostMapping("/createOrUpdateOaKnowledgeBase")
    public BaseResponse createOrUpdateOaKnowledgeBase(@RequestBody OaKnowledgeBaseModel request) {
        log.info("createOrUpdateOaKnowledgeBase");
        return oaFeign.createOrUpdateOaKnowledgeBase(request);
    }

    @ApiOperation(value = "查询知识库目录树")
    @PostMapping("/getOaKnowledgeBaseTree")
    public BaseResponse getOaKnowledgeBaseTree(@RequestBody OaKnowledgeBaseModel request,@RequestHeader String token) {
        log.info("getOaKnowledgeBaseTree");
        return oaFeign.getOaKnowledgeBaseTree(request,token);
    }

    @ApiOperation(value = "ID删除知识库目录")
    @GetMapping("/deleteOaKnowledgeBaseById")
    public BaseResponse deleteOaKnowledgeBaseById(@RequestParam String kbId) {
        log.info("deleteOaKnowledgeBaseById");
        return oaFeign.deleteOaKnowledgeBaseById(kbId);
    }

    @ApiOperation(value = "拖拽移动（仅限同级）")
    @PostMapping("/onDragOaKnowledgeBase")
    public BaseResponse onDragOaKnowledgeBase(@RequestBody OnDragDto request) {
        log.info("onDragOaKnowledgeBase");
        return oaFeign.onDragOaKnowledgeBase(request);
    }
}
