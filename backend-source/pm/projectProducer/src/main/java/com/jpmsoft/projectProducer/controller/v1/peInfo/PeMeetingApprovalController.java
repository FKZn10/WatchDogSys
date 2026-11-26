package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingApprovalRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.common.model.response.peInfo.PeMeetingApprovalRes;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingApprovalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 11:15
 */
@RestController
@RequestMapping("/v1/peMeetingApproval")
public class PeMeetingApprovalController {

    @Resource
    private IPeMeetingApprovalService peMeetingApprovalService;
    @Resource
    private IPeInfoService peInfoService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePeMeetingApproval")
    public BaseResponse createOrUpdatePeMeetingApproval(@RequestBody PeMeetingApprovalRequest request) {
        return peMeetingApprovalService.createOrUpdatePeMeetingApproval(request);
    }

    @TenantId
    @PostMapping("/getPeMeetingApprovalList")
    public BaseResponse getPeMeetingApprovalList(@RequestBody PeMeetingApprovalRequest request, @RequestHeader String token) {
        List<PeMeetingApprovalRes> datas = peMeetingApprovalService.getPeMeetingApprovalList(request, redisUtils.getUid(token));
        Integer total = peMeetingApprovalService.getPeMeetingApprovalCount(request, redisUtils.getUid(token));
        return BaseResponse.ok(datas, total);
    }

    @GetMapping("/getPeMeetingApprovalById")
    public BaseResponse getPeMeetingApprovalById(@RequestParam String maId) {
        PeMeetingApprovalRes data = peMeetingApprovalService.getPeMeetingApprovalById(maId);
        List<PeInfoModel> peInfoModels = peInfoService.getPeInfoByMaId(maId);
        data.setPeInfoModels(peInfoModels);
        return BaseResponse.ok(data);
    }


    @GetMapping("/updatePeStateByMaId")
    public BaseResponse updatePeStateByMaId(@RequestParam String peState, @RequestParam String maId) {
        return peMeetingApprovalService.updatePeStateByMaId(peState, maId);
    }
}
