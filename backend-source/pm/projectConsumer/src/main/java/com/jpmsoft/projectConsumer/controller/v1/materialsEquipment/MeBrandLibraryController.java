package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MeBrandLibraryDto;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 材料设备品牌库
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/26 11:36
 */
@Slf4j
@RestController
@Api(tags = "材料设备品牌库", value = "材料设备品牌库")
@RequestMapping("/material/v1/meBrandLibrary")
public class MeBrandLibraryController {
    @Resource
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改材料设备品牌库")
    @PostMapping("/createOrUpdateMeBrandLibrary")
    public BaseResponse createOrUpdateMeBrandLibrary(@RequestBody MeBrandLibraryDto request) {
        log.info("createOrUpdateMeBrandLibrary");
        return materialFeign.createOrUpdateMeBrandLibrary(request);
    }

    @ApiOperation(value = "分页查询材料设备品牌库")
    @PostMapping("/getMeBrandLibraryList")
    public BaseResponse getMeBrandLibraryList(@RequestBody MeBrandLibraryDto request, @RequestHeader String token) {
        log.info("getMeBrandLibraryList");
        return materialFeign.getMeBrandLibraryList(request, token);
    }

    @ApiOperation(value = "ID查询材料设备品牌库")
    @GetMapping("/getMeBrandLibraryById")
    public BaseResponse getMeBrandLibraryById(@RequestParam String id) {
        log.info("getMeBrandLibraryById");
        return materialFeign.getMeBrandLibraryById(id);
    }

    @ApiOperation(value = "材料设备品种库ID查询材料设备品牌库")
    @GetMapping("/getMeBrandLibraryByMetId")
    public BaseResponse getMeBrandLibraryByMetId(@RequestParam String metId){
        log.info("getMeBrandLibraryByMetId");
        return materialFeign.getMeBrandLibraryByMetId(metId);
    }

    @ApiOperation(value = "同步材料设备品牌库建立（更新）审批表流程状态")
    @GetMapping("/syncMeBrandLibraryApplyState")
    public BaseResponse syncMeBrandLibraryApplyState(@RequestParam String blaId) {
        log.info("syncMeBrandLibraryApplyState");
        return materialFeign.syncMeBrandLibraryApplyState(blaId);
    }
}
