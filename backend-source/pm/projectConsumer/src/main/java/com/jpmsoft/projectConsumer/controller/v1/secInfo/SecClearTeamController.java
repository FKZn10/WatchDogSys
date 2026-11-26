package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecClearTeamDTO;
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
@Api(tags = "清标小组", value = "清标小组")
@RequestMapping("/v1/clearTeam")
public class SecClearTeamController {

    @Resource
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改清标小组")
    @PostMapping("/createOrUpdateSecClearTeam")
    public BaseResponse createOrUpdateSecClearTeam(@RequestBody SecClearTeamDTO request) {
        log.info("createOrUpdateSecClearTeam");
        return secFeign.createOrUpdateSecClearTeam(request);
    }


    @ApiOperation(value = "ID查询清标小组")
    @GetMapping("/getSecClearTeamById")
    public BaseResponse getSecClearTeamById(@RequestParam String id) {
        log.info("getSecClearTeamById");
        return secFeign.getSecClearTeamById(id);
    }
}
