package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.InvestmentSecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoTZRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoAmountDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/secInfo")
public class SecInfoController {

    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecInfo")
    public BaseResponse createOrUpdateSecInfo(@RequestBody SecInfoRequest request) {
        log.info("createOrUpdateSecInfo");
        return secInfoService.createOrUpdateSecInfo(request);
    }

    @TenantId
    @PostMapping("/getSecInfoList")
    public BaseResponse getSecInfoList(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoList");
        return secInfoService.getSecInfoList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSecInfoListGroupByProject")
    public BaseResponse getSecInfoListGroupByProject(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoListGroupByProject");
        return secInfoService.getSecInfoListGroupByProject(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSecInfoListOne2Many")
    public BaseResponse getSecInfoListOne2Many(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoListOne2Many");
        return secInfoService.getSecInfoListOne2Many(request);
    }

    @TenantId
    @PostMapping("/getSecInfoListNoAuth")
    public BaseResponse getSecInfoListNoAuth(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoListNoAuth");
        return secInfoService.getSecInfoListNoAuth(request);
    }


    @GetMapping("/deleteSecInfoById")
    public BaseResponse deleteSecInfoById(@RequestParam String secId) {
        log.info("deleteSecInfoById");
        return secInfoService.deleteSecInfoById(secId);
    }

    @GetMapping("/getSecInfoById")
    public BaseResponse getSecInfoById(@RequestParam String secId) {
        log.info("getSecInfoById");
        return secInfoService.getSecInfoById(secId);
    }

    @TenantId
    @PostMapping("/getSecInfoByPId")
    public BaseResponse getSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getSecInfoByPId");
        return secInfoService.getSecInfoByPId(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getCjSecInfoByPId")
    public BaseResponse getCjSecInfoByPId(@RequestBody SecInfoRequest request, @RequestHeader String token) {
        log.info("getCjSecInfoByPId");
        return secInfoService.getCjSecInfoByPId(request, redisUtils.getUid(token));
    }

    @PostMapping("/updateSecBiddingTypeById")
    public BaseResponse updateSecBiddingTypeById(@RequestBody SecInfoRequest request) {
        log.info("updateSecBiddingTypeById");
        return secInfoService.updateSecBiddingTypeById(request);
    }

    @TenantId
    @PostMapping("/getSecProjectList")
    public BaseResponse getSecProjectList(@RequestBody SecProjectModel request, @RequestHeader String token) {
        log.info("getSecProjectList");
        return secInfoService.getSecProjectList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSecProjectStatisticsList")
    public BaseResponse getSecProjectStatisticsList(@RequestBody SecProjectModel request, @RequestHeader String token) {
        log.info("getSecProjectStatisticsList");
        return secInfoService.getSecProjectStatisticsList(request, redisUtils.getUid(token));
    }


    @PostMapping("/updateApproveStatusSecInfo")
    public BaseResponse updateApproveStatusSecInfo(@RequestBody SecInfoRequest request) {
        log.info("updateApproveStatusSecInfo");
        return secInfoService.updateApproveStatusSecInfo(request);
    }


    @GetMapping("/secInfoCanHandle")
    public BaseResponse secInfoCanHandle(@RequestParam String secId) {
        log.info("secInfoCanHandle");
        return secInfoService.secInfoCanHandle(secId);
    }

    /**
     * 判断名称重复
     * 排除作废
     *
     * @param name
     * @param secId
     * @return
     */
    @GetMapping("/existDuplicateName")
    public BaseResponse existDuplicateName(@RequestParam String name, @RequestParam(required = false) String secId) {
        log.info("existDuplicateName");
        return BaseResponse.ok(secInfoService.existDuplicateName(name, secId));
    }

    /**
     * 获取项目概算树（项目概算）
     *
     * @param
     * @return
     */
    @PostMapping("/getSecInfoAmountTree")
    public BaseResponse getSecInfoAmountTree(@RequestBody SecInfoAmountDto planAmountDto) {
        return secInfoService.getSecInfoAmountTree(planAmountDto);
    }

    @PostMapping("/getSecInfoTZClassInfo")
    public BaseResponse getSecInfoTZClassInfo(@RequestBody SecInfoTZRequest request, @RequestHeader String token) {
        log.info("getSecInfoTZClassInfo");
        return secInfoService.getSecInfoTZClassInfo(request.getProjectIds(), redisUtils.getUid(token));
    }

    @PostMapping("/getInvestmentSecInfo")
    public BaseResponse getInvestmentSecInfo(@RequestBody InvestmentSecInfoRequest request) {
        log.info("getInvestmentSecInfo");
        return secInfoService.getInvestmentSecInfo(request);
    }




    @GetMapping("/getSecBiddingTypeByIds")
    public BaseResponse getSecBiddingTypeById(@RequestParam String secIds) {
        log.info("getSecBiddingTypeById");
        return secInfoService.getSecBiddingTypeByIds(secIds);
    }



    @PostMapping("/getSecInfoGDGY")
    public BaseResponse getSecInfoGDGY(@RequestBody SecInfoRequest request) {
        log.info("getSecInfoGDGY");
        return secInfoService.getSecInfoGDGY(request);
    }

    @GetMapping("/getConSecInfoGgById")
    public BaseResponse getConSecInfoGgById(@RequestParam("secId") String secId) {
        log.info("getConSecInfoGgById");
        return secInfoService.getConSecInfoGgById(secId);
    }
}
