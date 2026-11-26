package com.jpmsoft.projectProducer.controller.v1.secInfo;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordMultipleModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertMultipleDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordMultipleService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/selectionRecordMultiple")
public class SecSelectionRecordMultipleController {

    @Resource
    private ISecSelectionRecordMultipleService secSelectionRecordMultipleService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecSelectionRecordMultiple")
    public BaseResponse createOrUpdateSecSelectionRecordMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecSelectionRecordMultiple");
        return secSelectionRecordMultipleService.createOrUpdateSecSelectionRecordMultiple(request, redisUtils.getUserInfoByToken(token));
    }


    @TenantId
    @PostMapping("/getSecSelectionRecordMultipleList")
    public BaseResponse getSecSelectionRecordMultipleList(@RequestBody SecSelectionRecordMultipleModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordMultipleList");
        return secSelectionRecordMultipleService.getSecSelectionRecordMultipleList(request,redisUtils.getUid(token));
    }

    @GetMapping("/getSecSelectionRecordMultipleById")
    public BaseResponse getSecSelectionRecordMultipleById(@RequestParam String id) {
        log.info("getSecSelectionRecordMultipleById");
        return secSelectionRecordMultipleService.getSecSelectionRecordMultipleById(id);
    }

    @GetMapping("/deleteSecSelectionRecordMultipleById")
    public BaseResponse deleteSecSelectionRecordMultipleById(@RequestParam String id) {
        log.info("deleteSecSelectionRecordMultipleById");
        return secSelectionRecordMultipleService.deleteSecSelectionRecordMultipleById(id);
    }

   /* @PostMapping("/updateSecSelectionRecordDtl")
    public BaseResponse updateSecSelectionRecordDtl(@RequestBody SecSelectionRecordDtlModel request) {
        log.info("updateSecSelectionRecordDtl");
        return secSelectionRecordMultipleService.updateSecSelectionRecordDtl(request);
    }
*/

    /****************************************专家抽取***********************************************/

    @TenantId
    @PostMapping("/createOrUpdateExpertExtractMultiple")
    public BaseResponse createOrUpdateExpertExtractMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token) {
        log.info("createOrUpdateExpertExtractMultiple");
        return secSelectionRecordMultipleService.createOrUpdateExpertExtractMultiple(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertExtractMultipleById")
    public BaseResponse getSecExpertExtractMultipleById(@RequestParam String id) {
        log.info("getSecExpertExtractMultipleById");
        return secSelectionRecordMultipleService.getSecExpertExtractMultipleById(id);
    }

    @PostMapping("/extractJGExpertMultiple")
    public BaseResponse extractJGExpertMultiple(@RequestBody ExtractExpertMultipleDto request, @RequestHeader String token) {
        log.info("extractJGExpertMultiple");
        return secSelectionRecordMultipleService.extractJGExpertMultiple(request, redisUtils.getTeId(token));
    }


    @PostMapping("/approvalDataExpertMultiple")
    public BaseResponse approvalDataExpertMultiple(@RequestBody SecSelectionRecordMultipleModel request) {
        log.info("approvalDataExpertMultiple");
        return secSelectionRecordMultipleService.approvalData(request);
    }


    @GetMapping("/getOtherExpertSecInfo")
    public BaseResponse getOtherExpertSecInfo(@RequestParam String secId) {
        log.info("getOtherExpertSecInfo");
        return secSelectionRecordMultipleService.getOtherExpertSecInfo(secId);
    }



}
