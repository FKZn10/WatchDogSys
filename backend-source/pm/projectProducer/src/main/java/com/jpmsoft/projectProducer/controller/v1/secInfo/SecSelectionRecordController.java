package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/selectionRecord")
public class SecSelectionRecordController {

    @Resource
    private ISecSelectionRecordService secSelectionRecordService;

    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecSelectionRecord")
    public BaseResponse createOrUpdateSecSelectionRecord(@RequestBody SecSelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecSelectionRecord");
        return secSelectionRecordService.createOrUpdateSecSelectionRecord(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecSelectionRecordClassInfo")
    public BaseResponse getSecSelectionRecordClassInfo(@RequestBody SecSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordClassInfo");
        return secSelectionRecordService.getSecSelectionRecordClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecSelectionRecordList")
    public BaseResponse getSecSelectionRecordList(@RequestBody SecSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordList");
        return secSelectionRecordService.getSecSelectionRecordList(request, redisUtils.getUserInfoByToken(token));
    }

    @PostMapping("/getSectionDocumentCount")
    public BaseResponse getSectionDocumentCount(@RequestBody SecSelectionRecordModel request) {
        log.info("getSectionDocumentCount");
        return secSelectionRecordService.getSectionDocumentCount(request);
    }

    @GetMapping("/getTenderingMethodByProjectAndSection")
    public BaseResponse getTenderingMethodByProjectAndSection(@RequestParam("pId") String pId,
                                                              @RequestParam("secId") String secId) {
        log.info("getTenderingMethodByProjectAndSection");
        return secSelectionRecordService.getTenderingMethodByProjectAndSection(pId, secId);
    }

    @GetMapping("/getSecSelectionRecordById")
    public BaseResponse getSecSelectionRecordById(@RequestParam String srId) {
        log.info("getSecSelectionRecordById");
        return secSelectionRecordService.getSecSelectionRecordById(srId);
    }

    @GetMapping("/deleteSecSelectionRecordById")
    public BaseResponse deleteSecSelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecSelectionRecordById");
        return secSelectionRecordService.deleteSecSelectionRecordById(srId);
    }


    /**
     * 查询专家参会次数
     *
     * @param exId
     * @return
     */
    @GetMapping("/getSelectionRecordDtlCount")
    public BaseResponse getSelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getSelectionRecordDtlCount");
        return secSelectionRecordDtlService.getSelectionRecordDtlCount(exId);
    }

    @PostMapping("/updateSecSelectionRecordDtl")
    public BaseResponse updateSecSelectionRecordDtl(@RequestBody SecSelectionRecordDtlModel request) {
        log.info("updateSecSelectionRecordDtl");
        return secSelectionRecordDtlService.updateSecSelectionRecordDtl(request);
    }

    @PostMapping("/getSelectionRecordDtlListByExId")
    public BaseResponse getSelectionRecordDtlListByExId(@RequestBody SecSelectionRecordDtlDto request) {
        log.info("getSelectionRecordDtlListByExId");
        return secSelectionRecordDtlService.getSelectionRecordDtlListByExId(request);
    }

    /****************************************专家抽取***********************************************/

    @TenantId
    @PostMapping("/createOrUpdateExpertExtract")
    public BaseResponse createOrUpdateExpertExtract(@RequestBody SecSelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateExpertExtract");
        return secSelectionRecordService.createOrUpdateExpertExtract(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertExtractById")
    public BaseResponse getSecExpertExtractById(@RequestParam String srId) {
        log.info("getSecExpertExtractById");
        return secSelectionRecordService.getSecExpertExtractById(srId);
    }

    @GetMapping("/getSecExpertExtractByEsId")
    public BaseResponse getSecExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecExpertExtractByEsId");
        return secSelectionRecordService.getSecExpertExtractByEsId(esId);
    }

    @GetMapping("/getSecSelectionRecordDtlModelById")
    public BaseResponse getSecSelectionRecordDtlModelById(@RequestParam String srId) {
        log.info("getSecSelectionRecordDtlModelById");
        return secSelectionRecordService.getSecSelectionRecordDtlModelById(srId);
    }
    @TenantId
    @PostMapping("/getSecExpertExtractList")
    public BaseResponse getSecExpertExtractList(@RequestBody SecExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecExpertExtractList");
        return secSelectionRecordService.getSecExpertExtractList(request, redisUtils.getUid(token));
    }

    @GetMapping("/cancelExtract2cancelSelect")
    public BaseResponse cancelExtract2cancelSelect(@RequestParam String srId) {
        log.info("cancelExtract2cancelSelect");
        return secSelectionRecordService.cancelExtract2cancelSelect(srId);
    }

    @PostMapping("/getSecSelectionRecordInfoBySecId")
    public BaseResponse getSecSelectionRecordInfoBySecId(@RequestBody SecSelectionRecordModel request) {
        log.info("getSecSelectionRecordInfoBySecId");
        return secSelectionRecordService.getSecSelectionRecordInfoBySecId(request);
    }
}
