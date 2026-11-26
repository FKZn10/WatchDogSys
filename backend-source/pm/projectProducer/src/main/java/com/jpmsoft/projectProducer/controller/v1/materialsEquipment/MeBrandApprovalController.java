package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandApprovalDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandApprovalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:46
 */
@Slf4j
@RestController
@RequestMapping("/v1/meBrandApproval")
public class MeBrandApprovalController {
    @Resource
    private IMeBrandApprovalService meBrandApprovalService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeBrandApproval")
    public BaseResponse createOrUpdateMeBrandApproval(@RequestBody MeBrandApprovalDto request) {
        log.info("createOrUpdateMeBrandApproval");
        return meBrandApprovalService.createOrUpdateMeBrandApproval(request);
    }

    @TenantId
    @PostMapping("/getMeBrandApprovalList")
    public BaseResponse getMeBrandApprovalList(@RequestBody MeBrandApprovalDto request, @RequestHeader String token) {
        log.info("getMeBrandApprovalList");
        return meBrandApprovalService.getMeBrandApprovalList(request, redisUtils.getUid(token));
    }

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    @GetMapping("/getMeBrandApprovalReviewsCount")
    public BaseResponse getMeBrandApprovalReviewsCount(@RequestParam("pId") String pId,
                                                       @RequestParam("conId") String conId) {
        return meBrandApprovalService.getMeBrandApprovalReviewsCount(pId, conId);
    }

    @GetMapping("/getMeBrandApprovalById")
    public BaseResponse getMeBrandApprovalById(@RequestParam String id) {
        log.info("getMeBrandApprovalById");
        return meBrandApprovalService.getMeBrandApprovalById(id);
    }
    @GetMapping("/getMeBrandApprovalDetailsById")
    public BaseResponse getMeBrandApprovalDetailsById(@RequestParam String id) {
        log.info("getMeBrandApprovalDetailsById");
        return meBrandApprovalService.getMeBrandApprovalDetailsById(id);
    }
}
