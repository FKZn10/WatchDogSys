package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "履约评价表详细", value = "履约评价表详细")
@RequestMapping("/v1/peFormDetail")
public class PeFormDetailController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改履约评价表详细", httpMethod = "POST")
    @PostMapping("/createOrUpdatePeFormDetail")
    public BaseResponse createOrUpdatePeFormDetail(@RequestBody PeFormDetailRequest request) {
        log.info("createOrUpdatePeFormDetail");
        return peInfoFeign.createOrUpdatePeFormDetail(request);
    }

    @ApiOperation(value = "履约评价表详细列表", httpMethod = "POST")
    @PostMapping("/getPeFormDetailList")
    public BaseResponse getPeFormDetailList(@RequestBody PeFormDetailRequest request, @RequestHeader String token) {
        log.info("getPeFormDetailList");
        return peInfoFeign.getPeFormDetailList(request, token);
    }

    @ApiOperation(value = "ID获取履约评价表详细", httpMethod = "GET")
    @GetMapping("/deletePeFormDetailByFdId")
    public BaseResponse deletePeFormDetailByFdId(@RequestParam String fdId) {
        log.info("deletePeFormDetailByFdId");
        return peInfoFeign.deletePeFormDetailByFdId(fdId);
    }
}
