package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignConstructionRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "施工图设计", value = "施工图设计")
@RequestMapping("/design/v1/designConstruction")
public class DesignConstructionController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改施工图设计")
    @PostMapping("/createOrUpdateDesignConstruction")
    public BaseResponse createOrUpdateDesignConstruction(@RequestBody DesignConstructionRequest request) {
        log.info("createOrUpdateDesignConstruction");
        return baseFeign.createOrUpdateDesignConstruction(request);
    }

    @ApiOperation(value = "分页查询施工图设计")
    @PostMapping("/getDesignConstructionList")
    public BaseResponse getDesignConstructionList(@RequestBody DesignConstructionRequest request, @RequestHeader String token) {
        log.info("getDesignConstructionList");
        return baseFeign.getDesignConstructionList(request, token);
    }

    @ApiOperation(value = "分页查询施工图设计（统计+列表）")
    @PostMapping("/getDesignConstructionStatisticsList")
    public BaseResponse getDesignConstructionStatisticsList(@RequestBody DesignConstructionRequest request, @RequestHeader String token) {
        log.info("getDesignConstructionStatisticsList");
        return baseFeign.getDesignConstructionStatisticsList(request, token);
    }


    @ApiOperation(value = "分页查询施工图设计-不鉴权")
    @PostMapping("/getDesignConstructionListNoAuth")
    public BaseResponse getDesignConstructionListNoAuth(@RequestBody DesignConstructionRequest request) {
        log.info("getDesignConstructionListNoAuth");
        return baseFeign.getDesignConstructionListNoAuth(request);
    }


    @ApiOperation(value = "删除施工图设计")
    @GetMapping("/deleteDesignConstructionBydcId")
    public BaseResponse deleteDesignConstructionBydcId(@RequestParam String dcId) {
        log.info("deleteDesignConstructionBydcId");
        return baseFeign.deleteDesignConstructionBydcId(dcId);
    }

    @ApiOperation(value = "ID查询施工图设计")
    @GetMapping("/getDesignConstructionBydcId")
    public BaseResponse getDesignConstructionBydcId(@RequestParam String dcId) {
        log.info("getDesignConstructionBydcId");
        return baseFeign.getDesignConstructionBydcId(dcId);
    }

    @ApiOperation(value = "获取施工图设计流程状态")
    @PostMapping("/updateDesignConstructionApproveStatus")
    public BaseResponse updateDesignConstructionApproveStatus(@RequestBody DesignConstructionRequest request) {
        log.info("updateDesignConstructionApproveStatus");
        return baseFeign.updateDesignConstructionApproveStatus(request);
    }

    /**
     * 下发状态切换
     *
     * @param dcId
     * @param issued
     * @return
     */
    @ApiOperation("下发状态切换")
    @GetMapping("/updateDesignConstructionIssued")
    public BaseResponse updateDesignConstructionIssued(@RequestParam String dcId, @RequestParam(required = false) String issued) {
        log.info("updateDesignConstructionIssued");
        return baseFeign.updateDesignConstructionIssued(dcId, issued);
    }
}
