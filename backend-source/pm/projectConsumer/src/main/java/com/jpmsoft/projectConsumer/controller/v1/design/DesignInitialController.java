package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignInitialRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "初步设计", value = "初步设计")
@RequestMapping("/design/v1/designInitial")
public class DesignInitialController {
    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改初步设计")
    @PostMapping("/createOrUpdateDesignInitial")
    public BaseResponse createOrUpdateDesignInitial(@RequestBody DesignInitialRequest request) {
        log.info("createOrUpdateDesignInitial");
        return baseFeign.createOrUpdateDesignInitial(request);
    }

    @ApiOperation(value = "分页查询初步设计")
    @PostMapping("/getDesignInitialList")
    public BaseResponse getDesignInitialList(@RequestBody DesignInitialRequest request, @RequestHeader String token) {
        log.info("getDesignInitialList");
        return baseFeign.getDesignInitialList(request, token);
    }


    @ApiOperation(value = "分页查询初步设计(统计+列表)")
    @PostMapping("/getDesignInitialStatisticsList")
    public BaseResponse getDesignInitialStatisticsList(@RequestBody DesignInitialRequest request, @RequestHeader String token) {
        log.info("getDesignInitialStatisticsList");
        return baseFeign.getDesignInitialStatisticsList(request, token);
    }

    @ApiOperation(value = "分页查询初步设计-不鉴权")
    @PostMapping("/getDesignInitialListNoAuth")
    public BaseResponse getDesignInitialListNoAuth(@RequestBody DesignInitialRequest request) {
        log.info("getDesignInitialListNoAuth");
        return baseFeign.getDesignInitialListNoAuth(request);
    }

    @ApiOperation(value = "id删除初步设计")
    @GetMapping("/deleteDesignInitialBydiId")
    public BaseResponse deleteDesignInitialBydiId(@RequestParam String diId) {
        log.info("deleteDesignInitialBydiId");
        return baseFeign.deleteDesignInitialBydiId(diId);
    }

    @ApiOperation(value = "id查询初步设计")
    @GetMapping("/getDesignInitialBydiId")
    public BaseResponse getDesignInitialBydiId(@RequestParam String diId) {
        log.info("getDesignInitialBydiId");
        return baseFeign.getDesignInitialBydiId(diId);
    }


    @ApiOperation(value = "获取初步设计流程状态")
    @PostMapping("/updateDesignInitialApproveStatus")
    public BaseResponse updateDesignInitialApproveStatus(@RequestBody DesignInitialRequest request) {
        log.info("updateDesignInitialApproveStatus");
        return baseFeign.updateDesignInitialApproveStatus(request);
    }

}
