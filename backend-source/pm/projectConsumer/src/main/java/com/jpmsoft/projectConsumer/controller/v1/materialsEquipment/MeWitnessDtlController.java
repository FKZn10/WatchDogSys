package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessDtlRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "见证送检清单", value = "见证送检清单")
@RequestMapping("/material/v1/meWitnessDtl")
public class MeWitnessDtlController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "分页查询见证送检清单(项目合同以材料设备进场报审表单链表)")
    @PostMapping("/getMeWitnessDtlList")
    public BaseResponse getMeWitnessDtlList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token) {
        log.info("getMeWitnessDtlList");
        return materialFeign.getMeWitnessDtlList(request, token);
    }

    @ApiOperation(value = "分页查询见证送检清单(项目合同以见证送检表单链表)")
    @PostMapping("/getMeWitnessDtlInspeList")
    public BaseResponse getMeWitnessDtlInspeList(@RequestBody MeWitnessDtlRequest request, @RequestHeader String token) {
        log.info("getMeWitnessDtlInspeList");
        return materialFeign.getMeWitnessDtlInspeList(request, token);
    }
}
