package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaKnowledgeGroupDTO;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "知识库-组和用户权限", value = "知识库-组和用户权限")
@RequestMapping("/v1/OA/KnowledgeGroup")
public class OaKnowledgeGroupController {

    @Resource
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改知识库文件组和用户权限配置")
    @PostMapping("/createOrUpdateOaKnowledgeGroupBath")
    public BaseResponse createOrUpdateOaKnowledgeGroupBath(@RequestBody List<OaKnowledgeGroupDTO> request) {
        return oaFeign.createOrUpdateOaKnowledgeGroupBath(request);
    }

    @ApiOperation(value = "知识库目录ID查询组和用户权限配置")
    @GetMapping("/getOaKnowledgeGroupByKbId")
    public BaseResponse getOaKnowledgeGroupByKbId(@RequestParam String kbId) {
        return oaFeign.getOaKnowledgeGroupByKbId(kbId);
    }
}
