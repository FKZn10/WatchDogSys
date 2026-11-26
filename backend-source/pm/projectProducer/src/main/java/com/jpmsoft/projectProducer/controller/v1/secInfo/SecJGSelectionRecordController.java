package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertSelectDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGSelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGSelectionRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/jgSelectionRecord")
public class SecJGSelectionRecordController {
    @Resource
    private ISecJGSelectionRecordService secJGSelectionRecordService;
    @Resource
    private ISecJGSelectionRecordDtlService secJGSelectionRecordDtlService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 创建或修改专家选取记录
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateSecJGSelectionRecord")
    public BaseResponse createOrUpdateSecJGSelectionRecord(@RequestBody SecJGSelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecJGSelectionRecord");
        return secJGSelectionRecordService.createOrUpdateSecJGSelectionRecord(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 分页查询专家选取记录
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getSecJGSelectionRecordList")
    public BaseResponse getSecJGSelectionRecordList(@RequestBody SecJGSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecJGSelectionRecordList");
        return secJGSelectionRecordService.getSecJGSelectionRecordList(request,redisUtils.getUid(token));
    }

    /**
     * 查询同标段的招标文件审批结束记录数
     * @param request
     * @return
     */
    @PostMapping("/getJGSectionDocumentCount")
    public BaseResponse getJGSectionDocumentCount(@RequestBody SecJGSelectionRecordModel request) {
        log.info("getJGSectionDocumentCount");
        return secJGSelectionRecordService.getJGSectionDocumentCount(request);
    }

    /**
     * ID查询专家选取记录
     * @param srId
     * @return
     */
    @GetMapping("/getSecJGSelectionRecordById")
    public BaseResponse getSecJGSelectionRecordById(@RequestParam String srId) {
        log.info("getSecJGSelectionRecordById");
        return secJGSelectionRecordService.getSecJGSelectionRecordById(srId);
    }

    /**
     * ID删除专家选取记录
     * @param srId
     * @return
     */
    @GetMapping("/deleteSecJGSelectionRecordById")
    public BaseResponse deleteSecJGSelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecJGSelectionRecordById");
        return secJGSelectionRecordService.deleteSecJGSelectionRecordById(srId);
    }


    /**
     * 查询专家参会次数
     *
     * @param exId
     * @return
     */
    @GetMapping("/getJGSelectionRecordDtlCount")
    public BaseResponse getJGSelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getJGSelectionRecordDtlCount");
        return secJGSelectionRecordDtlService.getJGSelectionRecordDtlCount(exId);
    }

    /**
     * 是否回避，是否按时参加
     * @param request
     * @return
     */
    @PostMapping("/updateSecJGSelectionRecordDtl")
    public BaseResponse updateSecJGSelectionRecordDtl(@RequestBody SecJGSelectionRecordDtlModel request) {
        log.info("updateSecJGSelectionRecordDtl");
        return secJGSelectionRecordDtlService.updateSecJGSelectionRecordDtl(request);
    }


    /****************************************专家抽取***********************************************/

    @TenantId
    @PostMapping("/checkJGExPeople")
    public BaseResponse checkJGExPeople(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("checkJGExPeople");
        return secJGSelectionRecordService.checkJGExPeople(request, redisUtils.getTeId(token));
    }

    /**
     * 创建新增专家抽取
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateJGExpertExtract")
    public BaseResponse createOrUpdateJGExpertExtract(@RequestBody SecJGSelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateJGExpertExtract");
        return secJGSelectionRecordService.createOrUpdateJGExpertExtract(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * ID查询专家抽取
     * @param srId
     * @return
     */
    @GetMapping("/getSecJGExpertExtractById")
    public BaseResponse getSecJGExpertExtractById(@RequestParam String srId) {
        log.info("getSecJGExpertExtractById");
        return secJGSelectionRecordService.getSecJGExpertExtractById(srId);
    }

    /**
     * 抽取申请ID查询专家抽取
     * @param esId
     * @return
     */
    @GetMapping("/getSecJGExpertExtractByEsId")
    public BaseResponse getSecJGExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecJGExpertExtractByEsId");
        return secJGSelectionRecordService.getSecJGExpertExtractByEsId(esId);
    }

    @GetMapping("/getSecJGSelectionRecordDtlModelById")
    public BaseResponse getSecJGSelectionRecordDtlModelById(@RequestParam String srId) {
        log.info("getSecJGSelectionRecordDtlModelById");
        return secJGSelectionRecordService.getSecJGSelectionRecordDtlModelById(srId);
    }

    /**
     * 分页查询专家抽取
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getSecJGExpertExtractList")
    public BaseResponse getSecJGExpertExtractList(@RequestBody SecJGExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecJGExpertExtractList");
        return secJGSelectionRecordService.getSecJGExpertExtractList(request, redisUtils.getUid(token));
    }

    /**
     * 作废专家抽取同时作废对应专家选取信息
     * @param srId
     * @return
     */
    @GetMapping("/cancelJGExtractCancelSelect")
    public BaseResponse cancelJGExtractCancelSelect(@RequestParam String srId) {
        log.info("cancelJGExtractCancelSelect");
        return secJGSelectionRecordService.cancelJGExtractCancelSelect(srId);
    }

    @GetMapping("/getSecJGSelectionRecordPrintById")
    public BaseResponse getSecJGSelectionRecordPrintById(@RequestParam String srId) {
        log.info("getSecJGSelectionRecordPrintById");
        return secJGSelectionRecordService.getSecJGSelectionRecordPrintById(srId);
    }
    @GetMapping("/getSecJGSelectionRecordPrintInsecDtlModelsById")
    public BaseResponse getSecJGSelectionRecordPrintInsecDtlModelsById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintInsecDtlModelsById");
        return secJGSelectionRecordService.getSecJGSelectionRecordPrintInsecDtlModelsById(srId,token);
    }
    @GetMapping("/getSecJGSelectionRecordPrintOutsecDtlModelsById")
    public BaseResponse getSecJGSelectionRecordPrintOutsecDtlModelsById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintOutsecDtlModelsById");
        return secJGSelectionRecordService.getSecJGSelectionRecordPrintOutsecDtlModelsById(srId,token);
    }

    @GetMapping("/getSecJGSelectionRecordPrintSrInsideExListById")
    public BaseResponse getSecJGSelectionRecordPrintSrInsideExListById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintSrInsideExListById");
        return secJGSelectionRecordService.getSecJGSelectionRecordPrintSrInsideExListById(srId,token);
    }
    @GetMapping("/getSecJGSelectionRecordPrintSrOutsideExListById")
    public BaseResponse getSecJGSelectionRecordPrintSrOutsideExListById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintSrOutsideExListById");
        return secJGSelectionRecordService.getSecJGSelectionRecordPrintSrOutsideExListById(srId,token);
    }
}
