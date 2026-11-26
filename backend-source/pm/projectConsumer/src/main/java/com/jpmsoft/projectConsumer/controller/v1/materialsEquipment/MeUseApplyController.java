package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeUseApplyReuqest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "使用报审", value = "使用报审")
@RequestMapping("/material/v1/meUseApply")
public class MeUseApplyController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改使用报审")
    @PostMapping("/createOrUpdateMeUseApply")
    public BaseResponse createOrUpdateMeUseApply(@RequestBody MeUseApplyReuqest request) {
        log.info("createOrUpdateMeUseApply");
        return materialFeign.createOrUpdateMeUseApply(request);
    }

    @ApiOperation(value = "分页查使用报审")
    @PostMapping("/getMeUseApplyList")
    public BaseResponse getMeUseApplyList(@RequestBody MeUseApplyReuqest request, @RequestHeader String token) {
        log.info("getMeUseApplyList");
        return materialFeign.getMeUseApplyList(request, token);
    }

    @ApiOperation(value = "ID查使用报审")
    @GetMapping("/getMeUseApplyByUaId")
    public BaseResponse getMeUseApplyByUaId(@RequestParam String uaId) {
        log.info("getMeUseApplyByUaId");
        return materialFeign.getMeUseApplyByUaId(uaId);
    }
}
