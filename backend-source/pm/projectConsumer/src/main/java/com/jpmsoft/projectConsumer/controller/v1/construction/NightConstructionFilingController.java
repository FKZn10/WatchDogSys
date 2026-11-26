package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "夜间施工备案", value = "夜间施工备案")
@RequestMapping("/v1/nightConstructionFiling")
public class NightConstructionFilingController {
    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改夜间施工备案")
    @PostMapping("/createOrUpdateNightConstructionFiling")
    public BaseResponse createOrUpdateNightConstructionFiling(@RequestBody NightConstructionFilingDTO request) {
        log.info("createOrUpdateNightConstructionFiling");
        return baseFeign.createOrUpdateNightConstructionFiling(request);
    }

    @ApiOperation(value = "分页查询夜间施工备案")
    @PostMapping("/getNightConstructionFilingList")
    public BaseResponse getNightConstructionFilingList(@RequestBody NightConstructionFilingDTO request, @RequestHeader String token) {
        log.info("getNightConstructionFilingList");
        return baseFeign.getNightConstructionFilingList(request, token);
    }

    @ApiOperation(value = "ID查询夜间施工备案")
    @GetMapping("/getNightConstructionFilingById")
    public BaseResponse getNightConstructionFilingById(@RequestParam String id) {
        log.info("getNightConstructionFilingById");
        return baseFeign.getNightConstructionFilingById(id);
    }
}
