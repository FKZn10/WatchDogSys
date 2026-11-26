package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessInspectionRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "见证送检", value = "见证送检")
@RequestMapping("/material/v1/meWitnessInspection")
public class MeWitnessInspectionController {
    @Resource
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增修改见证送检")
    @PostMapping("/createOrUpdateMeWitnessInspection")
    public BaseResponse createOrUpdateMeWitnessInspection(@RequestBody MeWitnessInspectionRequest request) {
        log.info("createOrUpdateMeWitnessInspection");
        return materialFeign.createOrUpdateMeWitnessInspection(request);
    }

    @ApiOperation(value = "修改见证送检详情")
    @PostMapping("/updateMeWitnessInspectionDtl")
    public BaseResponse updateMeWitnessInspectionDtl(@RequestBody MeWitnessInspectionRequest request) {
        log.info("updateMeWitnessInspectionDtl");
        return materialFeign.updateMeWitnessInspectionDtl(request);
    }


    @ApiOperation(value = "退回发起人初始化详情部分值为null")
    @GetMapping("/return2InitWitnessInspectionDtl")
    public BaseResponse return2InitWitnessInspectionDtl(@RequestParam String wiId) {
        log.info("return2InitWitnessInspectionDtl");
        return materialFeign.return2InitWitnessInspectionDtl(wiId);
    }

    @ApiOperation(value = "分页查询见证送检")
    @PostMapping("/getMeWitnessInspectionList")
    public BaseResponse getMeWitnessInspectionList(@RequestBody MeWitnessInspectionRequest request, @RequestHeader String token) {
        log.info("getMeWitnessInspectionList");
        return materialFeign.getMeWitnessInspectionList(request, token);
    }

    @ApiOperation(value = "ID查见证送检")
    @GetMapping("/getMeWitnessInspectionByWiId")
    public BaseResponse getMeWitnessInspectionByWiId(@RequestParam String wiId) {
        log.info("getMeWitnessInspectionByWiId");
        return materialFeign.getMeWitnessInspectionByWiId(wiId);
    }

    @ApiOperation(value = "合同ID查审批通过的见证送检")
    @GetMapping("/getMeWitnessInspectionByConId")
    public BaseResponse getMeWitnessInspectionByConId(@RequestParam String conId) {
        log.info("getMeWitnessInspectionByConId");
        return materialFeign.getMeWitnessInspectionByConId(conId);
    }

    @ApiOperation(value = "委托单编号查审批通过的见证送检")
    @GetMapping("/getMeWitnessInspectionByOrderNo")
    public BaseResponse getMeWitnessInspectionByOrderNo(@RequestParam String wiOrderNo) {
        log.info("getMeWitnessInspectionByOrderNo");
        return materialFeign.getMeWitnessInspectionByOrderNo(wiOrderNo);
    }
}
