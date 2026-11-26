package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecCommitteeCompositionDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@Api(tags = "定标委员会及监督委员会构成", value = "定标委员会及监督委员会构成")
@RequestMapping("/v1/committeeComposition")
public class SecCommitteeCompositionController {

    @Resource
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改定标委员会及监督委员会构成")
    @PostMapping("/createOrUpdateSecCommitteeComposition")
    public BaseResponse createOrUpdateSecCommitteeComposition(@RequestBody SecCommitteeCompositionDTO request) {
        log.info("createOrUpdateSecCommitteeComposition");
        return secFeign.createOrUpdateSecCommitteeComposition(request);
    }


    @ApiOperation(value = "ID查询定标委员会及监督委员会构成")
    @GetMapping("/getSecCommitteeCompositionById")
    public BaseResponse getSecCommitteeCompositionById(@RequestParam String id) {
        log.info("getSecCommitteeCompositionById");
        return secFeign.getSecCommitteeCompositionById(id);
    }
}
