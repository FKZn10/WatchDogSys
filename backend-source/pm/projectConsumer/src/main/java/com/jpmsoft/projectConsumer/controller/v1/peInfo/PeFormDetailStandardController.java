package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "评价标准", value = "评价标准")
@RequestMapping("/v1/peFormDetail")
public class PeFormDetailStandardController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改评价标准", httpMethod = "POST")
    @PostMapping("/createOrUpdatePeFormStandard")
    public BaseResponse createOrUpdatePeFormStandard(@RequestBody PeFormDetailStandardRequest request) {
        log.info("createOrUpdatePeFormStandard");
        return peInfoFeign.createOrUpdatePeFormStandard(request);
    }

    @ApiOperation(value = "评价标准列表", httpMethod = "POST")
    @PostMapping("/getPeFormStandardList")
    public BaseResponse getPeFormStandardList(@RequestBody PeFormDetailStandardRequest request) {
        log.info("getPeFormStandardList");
        return peInfoFeign.getPeFormStandardList(request);
    }

    @ApiOperation(value = "ID删除评价标准")
    @GetMapping("/deletePeFormStandardById")
    public BaseResponse deletePeFormStandardById(@RequestParam String sId) {
        log.info("deletePeFormStandardById");
        return peInfoFeign.deletePeFormStandardById(sId);
    }
}
