package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "材料设备进场报审清单", value = "材料设备进场报审清单")
@RequestMapping("/material/v1/meEnterApplyDtl")
public class MeEnterApplyDtlController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改材料设备进场报审清单")
    @PostMapping("/createOrUpdateMeEnterApplyDtl")
    public BaseResponse createOrUpdateMeEnterApplyDtl(@RequestBody MeEnterApplyDtlRequest request) {
        log.info("createOrUpdateMeEnterApplyDtl");
        return materialFeign.createOrUpdateMeEnterApplyDtl(request);
    }

    @ApiOperation(value = "分页查询材料设备进场报审清单")
    @PostMapping("/getMeEnterApplyDtlList")
    public BaseResponse getMeEnterApplyDtlList(@RequestBody MeEnterApplyDtlRequest request, @RequestHeader String token) {
        log.info("getMeEnterApplyDtlList");
        return materialFeign.getMeEnterApplyDtlList(request, token);
    }
}
