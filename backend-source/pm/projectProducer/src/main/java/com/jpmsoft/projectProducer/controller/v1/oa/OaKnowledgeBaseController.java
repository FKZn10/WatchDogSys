package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseModel;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/Knowledge")
public class OaKnowledgeBaseController {

    @Resource
    private IOaKnowledgeBaseService oaKnowledgeBaseService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateOaKnowledgeBase")
    public BaseResponse createOrUpdateOaKnowledgeBase(@RequestBody OaKnowledgeBaseModel request,@RequestHeader String token) {
        log.info("createOrUpdateOaKnowledgeBase");
        return oaKnowledgeBaseService.createOrUpdateOaKnowledgeBase(request,redisUtils.getTeId(token));
    }

    @PostMapping("/getOaKnowledgeBaseTree")
    public BaseResponse getOaKnowledgeBaseTree(@RequestBody OaKnowledgeBaseModel request,@RequestHeader String token) {
        log.info("getOaKnowledgeBaseTree");
        return oaKnowledgeBaseService.getOaKnowledgeBaseTree(request,redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/deleteOaKnowledgeBaseById")
    public BaseResponse deleteOaKnowledgeBaseById(@RequestParam String kbId) {
        log.info("deleteOaKnowledgeBaseById");
        return oaKnowledgeBaseService.deleteOaKnowledgeBaseById(kbId);
    }

    @PostMapping("/onDragOaKnowledgeBase")
    public BaseResponse onDragOaKnowledgeBase(@RequestBody OnDragDto request) {
        log.info("onDragOaKnowledgeBase");
        return oaKnowledgeBaseService.onDragOaKnowledgeBase(request);
    }
}
