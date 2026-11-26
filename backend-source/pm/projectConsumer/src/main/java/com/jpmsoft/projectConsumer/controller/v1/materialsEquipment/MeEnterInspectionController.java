package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterInspectionRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "进场验货", value = "进场验货")
@RequestMapping("/material/v1/meEnterInspection")
public class MeEnterInspectionController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改进场验货")
    @PostMapping("/createOrUpdateMeEnterInspection")
    public BaseResponse createOrUpdateMeEnterInspection(@RequestBody MeEnterInspectionRequest request) {
        log.info("createOrUpdateMeEnterInspection");
        return materialFeign.createOrUpdateMeEnterInspection(request);
    }

    @ApiOperation(value = "分页查询进场验货")
    @PostMapping("/getMeEnterInspectionList")
    public BaseResponse getMeEnterInspectionList(@RequestBody MeEnterInspectionRequest request, @RequestHeader String token) {
        log.info("getMeEnterInspectionList");
        return materialFeign.getMeEnterInspectionList(request, token);
    }

    @ApiOperation(value = "ID查询进场验货")
    @GetMapping("/getMeEnterInspectionByEiId")
    public BaseResponse getMeEnterInspectionByEiId(@RequestParam String eiId) {
        log.info("getMeEnterInspectionByEiId");
        return materialFeign.getMeEnterInspectionByEiId(eiId);
    }
}
