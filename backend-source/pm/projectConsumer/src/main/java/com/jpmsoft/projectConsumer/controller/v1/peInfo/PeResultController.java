package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "履约评价结果送达", value = "履约评价结果送达")
@RequestMapping("/v1/peResult")
public class PeResultController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "履约评价结果送达签收")
    @PostMapping("/signPeResult")
    public BaseResponse signPeResult(@RequestBody PeResultRequest request) {
        log.info("signPeResult");
        return peInfoFeign.signPeResult(request);
    }

    @ApiOperation(value = "获取履约评价结果送达列表")
    @PostMapping("/getPeResultList")
    public BaseResponse getPeResultList(@RequestBody PeResultRequest request, @RequestHeader String token) {
        log.info("getPeResultList");
        return peInfoFeign.getPeResultList(request, token);
    }

    @ApiOperation(value = "ID获取履约评价结果送达")
    @PostMapping("/getPeResultByRid")
    public BaseResponse getPeResultByRid(@RequestBody PeResultRequest request) {
        log.info("getPeResultByRid");
        return peInfoFeign.getPeResultByRid(request);
    }
}
