package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MeBrandLibraryApplyDto;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:29
 */
@Slf4j
@RestController
@Api(tags = "材料设备品牌库建立（更新）审批", value = "材料设备品牌库建立（更新）审批")
@RequestMapping("/v1/meBrandLibraryApply")
public class MeBrandLibraryApplyController {

    @Resource
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改")
    @PostMapping("/createOrUpdateMeBrandLibraryApply")
    public BaseResponse createOrUpdateMeBrandLibraryApply(@RequestBody MeBrandLibraryApplyDto request) {
        log.info("createOrUpdateMeBrandLibraryApply");
        return materialFeign.createOrUpdateMeBrandLibraryApply(request);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/getMeBrandLibraryApplyList")
    public BaseResponse getMeBrandLibraryApplyList(@RequestBody MeBrandLibraryApplyDto request, @RequestHeader String token) {
        log.info("getMeBrandLibraryApplyList");
        return materialFeign.getMeBrandLibraryApplyList(request, token);
    }

    @ApiOperation(value = "ID查询")
    @GetMapping("/getMeBrandLibraryApplyById")
    public BaseResponse getMeBrandLibraryApplyById(@RequestParam String id) {
        log.info("getMeBrandLibraryApplyById");
        return materialFeign.getMeBrandLibraryApplyById(id);
    }
}
