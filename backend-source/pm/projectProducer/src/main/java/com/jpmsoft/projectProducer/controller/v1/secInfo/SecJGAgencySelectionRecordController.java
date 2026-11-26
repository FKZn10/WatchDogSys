package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGAgencyExpertSelectDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGAgencySelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGAgencySelectionRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/jgAgencySelectionRecord")
public class SecJGAgencySelectionRecordController {
    @Resource
    private ISecJGAgencySelectionRecordService secJGAgencySelectionRecordService;
    @Resource
    private ISecJGAgencySelectionRecordDtlService secJGAgencySelectionRecordDtlService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 创建或修改专家选取记录
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateSecJGAgencySelectionRecord")
    public BaseResponse createOrUpdateSecJGAgencySelectionRecord(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecJGAgencySelectionRecord");
        return secJGAgencySelectionRecordService.createOrUpdateSecJGAgencySelectionRecord(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 分页查询专家选取记录
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getSecJGAgencySelectionRecordList")
    public BaseResponse getSecJGAgencySelectionRecordList(@RequestBody SecJGAgencySelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecJGAgencySelectionRecordList");
        return secJGAgencySelectionRecordService.getSecJGAgencySelectionRecordList(request,redisUtils.getUid(token));
    }

    /**
     * 查询同标段的招标文件审批结束记录数
     * @param request
     * @return
     */
    @PostMapping("/getJGAgencySectionDocumentCount")
    public BaseResponse getJGAgencySectionDocumentCount(@RequestBody SecJGAgencySelectionRecordModel request) {
        log.info("getJGAgencySectionDocumentCount");
        return secJGAgencySelectionRecordService.getJGAgencySectionDocumentCount(request);
    }

    /**
     * ID查询专家选取记录
     * @param srId
     * @return
     */
    @GetMapping("/getSecJGAgencySelectionRecordById")
    public BaseResponse getSecJGAgencySelectionRecordById(@RequestParam String srId) {
        log.info("getSecJGAgencySelectionRecordById");
        return secJGAgencySelectionRecordService.getSecJGAgencySelectionRecordById(srId);
    }

    /**
     * ID删除专家选取记录
     * @param srId
     * @return
     */
    @GetMapping("/deleteSecJGAgencySelectionRecordById")
    public BaseResponse deleteSecJGAgencySelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecJGAgencySelectionRecordById");
        return secJGAgencySelectionRecordService.deleteSecJGAgencySelectionRecordById(srId);
    }


    /**
     * 查询专家参会次数
     *
     * @param exId
     * @return
     */
    @GetMapping("/getJGAgencySelectionRecordDtlCount")
    public BaseResponse getJGAgencySelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getJGAgencySelectionRecordDtlCount");
        return secJGAgencySelectionRecordDtlService.getJGAgencySelectionRecordDtlCount(exId);
    }

    /**
     * 是否回避，是否按时参加
     * @param request
     * @return
     */
    @PostMapping("/updateSecJGAgencySelectionRecordDtl")
    public BaseResponse updateSecJGAgencySelectionRecordDtl(@RequestBody SecJGAgencySelectionRecordDtlModel request) {
        log.info("updateSecJGAgencySelectionRecordDtl");
        return secJGAgencySelectionRecordDtlService.updateSecJGAgencySelectionRecordDtl(request);
    }


    /****************************************专家抽取***********************************************/

    @TenantId
    @PostMapping("/checkJGAgencyExPeople")
    public BaseResponse checkJGAgencyExPeople(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token) {
        log.info("checkJGAgencyExPeople");
        return secJGAgencySelectionRecordService.checkJGAgencyExPeople(request, redisUtils.getTeId(token));
    }

    /**
     * 创建新增专家抽取
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateJGAgencyExpertExtract")
    public BaseResponse createOrUpdateJGAgencyExpertExtract(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateJGAgencyExpertExtract");
        return secJGAgencySelectionRecordService.createOrUpdateJGAgencyExpertExtract(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * ID查询专家抽取
     * @param srId
     * @return
     */
    @GetMapping("/getSecJGAgencyExpertExtractById")
    public BaseResponse getSecJGAgencyExpertExtractById(@RequestParam String srId) {
        log.info("getSecJGAgencyExpertExtractById");
        return secJGAgencySelectionRecordService.getSecJGAgencyExpertExtractById(srId);
    }

    /**
     * 抽取申请ID查询专家抽取
     * @param esId
     * @return
     */
    @GetMapping("/getSecJGAgencyExpertExtractByEsId")
    public BaseResponse getSecJGAgencyExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecJGAgencyExpertExtractByEsId");
        return secJGAgencySelectionRecordService.getSecJGAgencyExpertExtractByEsId(esId);
    }

    @GetMapping("/getSecJGAgencySelectionRecordDtlModelById")
    public BaseResponse getSecJGAgencySelectionRecordDtlModelById(@RequestParam String srId) {
        log.info("getSecJGAgencySelectionRecordDtlModelById");
        return secJGAgencySelectionRecordService.getSecJGAgencySelectionRecordDtlModelById(srId);
    }

    @GetMapping("/getSecJGAgencyValueById")
    public BaseResponse getSecJGAgencyValueById(@RequestParam String srId) {
        log.info("getSecJGAgencyValueById");
        return secJGAgencySelectionRecordService.getSecJGAgencyValueById(srId);
    }

    /**
     * 分页查询专家抽取
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getSecJGAgencyExpertExtractList")
    public BaseResponse getSecJGAgencyExpertExtractList(@RequestBody SecJGAgencyExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecJGAgencyExpertExtractList");
        return secJGAgencySelectionRecordService.getSecJGAgencyExpertExtractList(request, redisUtils.getUid(token));
    }

    /**
     * 作废专家抽取同时作废对应专家选取信息
     * @param srId
     * @return
     */
    @GetMapping("/cancelJGAgencyExtractCancelSelect")
    public BaseResponse cancelJGAgencyExtractCancelSelect(@RequestParam String srId) {
        log.info("cancelJGAgencyExtractCancelSelect");
        return secJGAgencySelectionRecordService.cancelJGAgencyExtractCancelSelect(srId);
    }



}
