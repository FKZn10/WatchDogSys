package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "材料设备进场报审", value = "材料设备进场报审")
@RequestMapping("/material/v1/meEnterApply")
public class MeEnterApplyController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改材料设备进场报审")
    @PostMapping("/createOrUpdateMeEnterApply")
    public BaseResponse createOrUpdateMeEnterApply(@RequestBody MeEnterApplyRequest request) {
        log.info("createOrUpdateMeEnterApply");
        return materialFeign.createOrUpdateMeEnterApply(request);
    }

    @ApiOperation(value = "分页查询材料设备进场报审")
    @PostMapping("/getMeEnterApplyList")
    public BaseResponse getMeEnterApplyList(@RequestBody MeEnterApplyRequest request, @RequestHeader String token) {
        log.info("getMeEnterApplyList");
        return materialFeign.getMeEnterApplyList(request, token);
    }

    @ApiOperation(value = "ID查询材料设备进场报审")
    @GetMapping("/getMeEnterApplyByEaId")
    public BaseResponse getMeEnterApplyByEaId(@RequestParam String eaId) {
        log.info("getMeEnterApplyByEaId");
        return materialFeign.getMeEnterApplyByEaId(eaId);
    }
}
