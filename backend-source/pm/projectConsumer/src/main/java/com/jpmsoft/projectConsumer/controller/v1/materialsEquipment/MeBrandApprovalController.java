package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MeBrandApprovalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:46
 */
@Slf4j
@RestController
@Api(tags = "项目品牌报审", value = "项目品牌报审")
@RequestMapping("/v1/meBrandApproval")
public class MeBrandApprovalController {

    @Resource
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改项目品牌报审")
    @PostMapping("/createOrUpdateMeBrandApproval")
    public BaseResponse createOrUpdateMeBrandApproval(@RequestBody MeBrandApprovalDto request) {
        log.info("createOrUpdateMeBrandApproval");
        return materialFeign.createOrUpdateMeBrandApproval(request);
    }

    @ApiOperation(value = "分页查询项目品牌报审")
    @PostMapping("/getMeBrandApprovalList")
    public BaseResponse getMeBrandApprovalList(@RequestBody MeBrandApprovalDto request, @RequestHeader String token) {
        log.info("getMeBrandApprovalList");
        return materialFeign.getMeBrandApprovalList(request, token);
    }

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    @ApiOperation(value = "查询项目合同当前报审次数")
    @GetMapping("/getMeBrandApprovalReviewsCount")
    public BaseResponse getMeBrandApprovalReviewsCount(@RequestParam("pId") String pId,
                                                       @RequestParam("conId") String conId) {
        return materialFeign.getMeBrandApprovalReviewsCount(pId, conId);
    }

    @ApiOperation(value = "ID查询项目品牌报审")
    @GetMapping("/getMeBrandApprovalById")
    public BaseResponse getMeBrandApprovalById(@RequestParam String id) {
        log.info("getMeBrandApprovalById");
        return materialFeign.getMeBrandApprovalById(id);
    }

    @ApiOperation(value = "ID查询项目品牌报审(类型拼接)")
    @GetMapping("/getMeBrandApprovalDetailsById")
    public BaseResponse getMeBrandApprovalDetailsById(@RequestParam String id) {
        log.info("getMeBrandApprovalDetailsById");
        return materialFeign.getMeBrandApprovalDetailsById(id);
    }
}
