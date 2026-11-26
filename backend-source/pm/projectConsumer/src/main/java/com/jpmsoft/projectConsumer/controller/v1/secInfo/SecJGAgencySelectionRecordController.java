package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecJGAgencyExpertSelectDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/jgAgencySelectionRecord")
public class SecJGAgencySelectionRecordController {
    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改专家选取记录")
    @PostMapping("/createOrUpdateSecJGAgencySelectionRecord")
    public BaseResponse createOrUpdateSecJGAgencySelectionRecord(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecJGAgencySelectionRecord");
        return secFeign.createOrUpdateSecJGAgencySelectionRecord(request, token);
    }


    @ApiOperation(value = "分页查询专家选取记录")
    @PostMapping("/getSecJGAgencySelectionRecordList")
    public BaseResponse getSecJGAgencySelectionRecordList(@RequestBody SecJGAgencySelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecJGAgencySelectionRecordList");
        return secFeign.getSecJGAgencySelectionRecordList(request,token);
    }


    @ApiOperation(value = "查询同标段的招标文件审批结束记录数")
    @PostMapping("/getJGAgencySectionDocumentCount")
    public BaseResponse getJGAgencySectionDocumentCount(@RequestBody SecJGAgencySelectionRecordModel request) {
        log.info("getJGAgencySectionDocumentCount");
        return secFeign.getJGAgencySectionDocumentCount(request);
    }


    @ApiOperation(value = "ID查询专家选取记录")
    @GetMapping("/getSecJGAgencySelectionRecordById")
    public BaseResponse getSecJGAgencySelectionRecordById(@RequestParam String srId) {
        log.info("getSecJGAgencySelectionRecordById");
        return secFeign.getSecJGAgencySelectionRecordById(srId);
    }


    @ApiOperation(value = "ID删除专家选取记录")
    @GetMapping("/deleteSecJGAgencySelectionRecordById")
    public BaseResponse deleteSecJGAgencySelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecJGAgencySelectionRecordById");
        return secFeign.deleteSecJGAgencySelectionRecordById(srId);
    }



    @ApiOperation(value = "查询专家参会次数")
    @GetMapping("/getJGAgencySelectionRecordDtlCount")
    public BaseResponse getJGAgencySelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getJGAgencySelectionRecordDtlCount");
        return secFeign.getJGAgencySelectionRecordDtlCount(exId);
    }


    @ApiOperation(value = "是否回避，是否按时参加")
    @PostMapping("/updateSecJGAgencySelectionRecordDtl")
    public BaseResponse updateSecJGAgencySelectionRecordDtl(@RequestBody SecJGAgencySelectionRecordDtlModel request) {
        log.info("updateSecJGAgencySelectionRecordDtl");
        return secFeign.updateSecJGAgencySelectionRecordDtl(request);
    }


    /****************************************专家抽取***********************************************/

    @PostMapping("/checkJGAgencyExPeople")
    public BaseResponse checkJGAgencyExPeople(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token) {
        log.info("checkJGAgencyExPeople");
        return secFeign.checkJGAgencyExPeople(request, token);
    }


    @ApiOperation(value = "创建新增专家抽取")
    @PostMapping("/createOrUpdateJGAgencyExpertExtract")
    public BaseResponse createOrUpdateJGAgencyExpertExtract(@RequestBody SecJGAgencySelectionRecordModel request, @RequestHeader String token) {
        log.info("createOrUpdateJGAgencyExpertExtract");
        return secFeign.createOrUpdateJGAgencyExpertExtract(request, token);
    }


    @ApiOperation(value = "ID查询专家抽取")
    @GetMapping("/getSecJGAgencyExpertExtractById")
    public BaseResponse getSecJGAgencyExpertExtractById(@RequestParam String srId) {
        log.info("getSecJGAgencyExpertExtractById");
        return secFeign.getSecJGAgencyExpertExtractById(srId);
    }


    @ApiOperation(value = "抽取申请ID查询专家抽取")
    @GetMapping("/getSecJGAgencyExpertExtractByEsId")
    public BaseResponse getSecJGAgencyExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecJGAgencyExpertExtractByEsId");
        return secFeign.getSecJGAgencyExpertExtractByEsId(esId);
    }

    @GetMapping("/getSecJGAgencySelectionRecordDtlModelById")
    public BaseResponse getSecJGAgencySelectionRecordDtlModelById(@RequestParam String srId) {
        log.info("getSecJGAgencySelectionRecordDtlModelById");
        return secFeign.getSecJGAgencySelectionRecordDtlModelById(srId);
    }

    @GetMapping("/getSecJGAgencyValueById")
    public BaseResponse getSecJGAgencyValueById(@RequestParam String srId) {
        log.info("getSecJGAgencyValueById");
        return secFeign.getSecJGAgencyValueById(srId);
    }

    @ApiOperation(value = "分页查询专家抽取")
    @PostMapping("/getSecJGAgencyExpertExtractList")
    public BaseResponse getSecJGAgencyExpertExtractList(@RequestBody SecJGAgencyExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecJGAgencyExpertExtractList");
        return secFeign.getSecJGAgencyExpertExtractList(request, token);
    }


    @ApiOperation(value = "作废专家抽取同时作废对应专家选取信息")
    @GetMapping("/cancelJGAgencyExtractCancelSelect")
    public BaseResponse cancelJGAgencyExtractCancelSelect(@RequestParam String srId) {
        log.info("cancelJGAgencyExtractCancelSelect");
        return secFeign.cancelJGAgencyExtractCancelSelect(srId);
    }



}
