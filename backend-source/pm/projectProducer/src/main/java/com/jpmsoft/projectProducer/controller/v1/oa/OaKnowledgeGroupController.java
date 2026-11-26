package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeGroupDTO;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeGroupService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 14:00
 */
@Slf4j
@RestController
@RequestMapping("/v1/OA/KnowledgeGroup")
public class OaKnowledgeGroupController {

    @Resource
    private IOaKnowledgeGroupService oaKnowledgeGroupService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrUpdateOaKnowledgeGroupBath")
    public BaseResponse createOrUpdateOaKnowledgeGroupBath(@RequestBody List<OaKnowledgeGroupDTO> request, @RequestHeader String token) {
        return oaKnowledgeGroupService.createOrUpdateOaKnowledgeGroupBath(request,redisUtils.getTeId(token));
    }

    @GetMapping("/getOaKnowledgeGroupByKbId")
    public BaseResponse getOaKnowledgeGroupByKbId(@RequestParam String kbId) {
        return oaKnowledgeGroupService.getOaKnowledgeGroupByKbId(kbId);
    }
}
