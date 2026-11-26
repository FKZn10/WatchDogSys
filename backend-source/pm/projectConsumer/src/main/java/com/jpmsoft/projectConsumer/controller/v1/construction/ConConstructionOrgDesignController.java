package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.ConConstructionOrgDesignRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "施工管理-施工组织设计", value = "施工管理-施工组织设计")
@RequestMapping("/v1/conCOD")
public class ConConstructionOrgDesignController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改施工组织设计")
    @PostMapping("/createOrUpdateConConstructionOrgDesign")
    public BaseResponse createOrUpdateConConstructionOrgDesign(@RequestBody ConConstructionOrgDesignRequest request) {
        log.info("createOrUpdateConConstructionOrgDesign");
        return baseFeign.createOrUpdateConConstructionOrgDesign(request);
    }

    @ApiOperation(value = "分页查询施工组织设计")
    @PostMapping("/getConConstructionOrgDesignList")
    public BaseResponse getConConstructionOrgDesignList(@RequestBody ConConstructionOrgDesignRequest request, @RequestHeader String token) {
        log.info("getConConstructionOrgDesignList");
        return baseFeign.getConConstructionOrgDesignList(request, token);
    }

    @ApiOperation(value = "ID查询施工组织设计")
    @GetMapping("/getConConstructionOrgDesignById")
    public BaseResponse getConConstructionOrgDesignById(@RequestParam String codId) {
        log.info("getConConstructionOrgDesignById");
        return baseFeign.getConConstructionOrgDesignById(codId);
    }
}
