package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "履约评价表", value = "履约评价表")
@RequestMapping("/v1/peForm")
public class PeFormController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改履约评价表", httpMethod = "POST")
    @PostMapping("/createOrUpdatePeForm")
    public BaseResponse createOrUpdatePeForm(@RequestBody PeFormRequest request) {
        log.info("createOrUpdatePeForm");
        return peInfoFeign.createOrUpdatePeForm(request);
    }

    @ApiOperation(value = "履约评价表列表", httpMethod = "POST")
    @PostMapping("/getPeFormList")
    public BaseResponse getPeFormList(@RequestBody PeFormRequest request, @RequestHeader String token) {
        log.info("getPeFormList");
        return peInfoFeign.getPeFormList(request, token);
    }

    @ApiOperation(value = "id删除履约评价表", httpMethod = "GET")
    @GetMapping("/deletePeFormByFId")
    public BaseResponse deletePeFormByFId(@RequestParam String fId) {
        log.info("deletePeFormByFId");
        return peInfoFeign.deletePeFormByFId(fId);
    }


    @ApiOperation(value = "id获取履约评价表级联信息", httpMethod = "GET")
    @GetMapping("/getPeFormCascadeInfoByFId")
    public BaseResponse getPeFormCascadeInfoByFId(@RequestParam String fId) {
        log.info("getPeFormCascadeInfoByFId");
        return peInfoFeign.getPeFormCascadeInfoByFId(fId);
    }
}
