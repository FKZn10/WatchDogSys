package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.service.ISysTPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "岗位模板", value = "岗位模板")
@RequestMapping("/v1/OrganizationManage")
public class SysTPositionController {

    @Resource
    private ISysTPositionService sysTPositionService;

    @ApiOperation(value = "项目岗位模板列表")
    @PostMapping("/GetT_PositionList")
    public BaseResponse GetT_PositionList(@RequestBody TPositionDto request) {
        log.info("GetT_PositionList");
        return sysTPositionService.GetT_PositionList(request);
    }

    @ApiOperation(value = "创建修改项目岗位模板")
    @PostMapping("/CreateOrUpdateT_Position")
    public BaseResponse CreateOrUpdateT_Position(@RequestBody TPositionDto request) {
        log.info("CreateOrUpdateT_Position");
        return sysTPositionService.CreateOrUpdateT_Position(request);
    }

    /**
     * 删除项目岗位模板
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除项目岗位模板")
    @PostMapping("/DeleteT_Position")
    public BaseResponse DeleteT_Position(@RequestBody @Validated TPositionDto request) {
        log.info("DeleteT_Position");
        return sysTPositionService.DeleteT_Position(request);
    }
}

