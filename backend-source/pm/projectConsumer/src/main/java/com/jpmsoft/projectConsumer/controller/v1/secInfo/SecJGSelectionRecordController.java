package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecJGExpertSelectDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/jgSelectionRecord")
public class SecJGSelectionRecordController {
    @Autowired
    private SecFeign secFeign;
    @ApiOperation(value = "创建或修改专家选取记录")
    @PostMapping("/createOrUpdateSecJGSelectionRecord")
    public BaseResponse createOrUpdateSecJGSelectionRecord(@RequestBody SecJGSelectionRecordModel request) {
        log.info("createOrUpdateSecJGSelectionRecord");
        return secFeign.createOrUpdateSecJGSelectionRecord(request);
    }

    @ApiOperation(value = "分页查询专家选取记录")
    @PostMapping("/getSecJGSelectionRecordList")
    public BaseResponse getSecJGSelectionRecordList(@RequestBody SecJGSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecJGSelectionRecordList");
        return secFeign.getSecJGSelectionRecordList(request,token);
    }

    @PostMapping("/getJGSectionDocumentCount")
    public BaseResponse getJGSectionDocumentCount(@RequestBody SecJGSelectionRecordModel request) {
        log.info("getJGSectionDocumentCount");
        return secFeign.getJGSectionDocumentCount(request);
    }


    @ApiOperation(value = "ID查询专家选取记录")
    @GetMapping("/getSecJGSelectionRecordById")
    public BaseResponse getSecJGSelectionRecordById(@RequestParam String srId) {
        log.info("getSecJGSelectionRecordById");
        return secFeign.getSecJGSelectionRecordById(srId);
    }

    @ApiOperation(value = "ID删除专家选取记录")
    @GetMapping("/deleteSecJGSelectionRecordById")
    public BaseResponse deleteSecJGSelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecJGSelectionRecordById");
        return secFeign.deleteSecJGSelectionRecordById(srId);
    }



    @ApiOperation(value = "查询专家参会次数")
    @GetMapping("/getJGSelectionRecordDtlCount")
    public BaseResponse getJGSelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getJGSelectionRecordDtlCount");
        return secFeign.getJGSelectionRecordDtlCount(exId);
    }

    @ApiOperation(value = "是否回避，是否按时参加")
    @PostMapping("/updateSecJGSelectionRecordDtl")
    public BaseResponse updateSecJGSelectionRecordDtl(@RequestBody SecJGSelectionRecordDtlModel request) {
        log.info("updateSecJGSelectionRecordDtl");
        return secFeign.updateSecJGSelectionRecordDtl(request);
    }




    /****************************************专家抽取***********************************************/
    @ApiOperation("选取时校验专家人数是否满足")
    @PostMapping("/checkJGExPeople")
    public BaseResponse checkJGExPeople(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("checkJGExPeople");
        return secFeign.checkJGExPeople(request, token);
    }



    @ApiOperation(value = "创建新增专家抽取")
    @PostMapping("/createOrUpdateJGExpertExtract")
    public BaseResponse createOrUpdateJGExpertExtract(@RequestBody SecJGSelectionRecordModel request) {
        log.info("createOrUpdateJGExpertExtract");
        return secFeign.createOrUpdateJGExpertExtract(request);
    }

    @ApiOperation(value = "ID查询专家抽取")
    @GetMapping("/getSecJGExpertExtractById")
    public BaseResponse getSecJGExpertExtractById(@RequestParam String srId) {
        log.info("getSecJGExpertExtractById");
        return secFeign.getSecJGExpertExtractById(srId);
    }

    @ApiOperation(value = "抽取申请ID查询专家抽取")
    @GetMapping("/getSecJGExpertExtractByEsId")
    public BaseResponse getSecJGExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecJGExpertExtractByEsId");
        return secFeign.getSecJGExpertExtractByEsId(esId);
    }


    @ApiOperation(value = "分页查询专家抽取")
    @PostMapping("/getSecJGExpertExtractList")
    public BaseResponse getSecJGExpertExtractList(@RequestBody SecJGExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecJGExpertExtractList");
        return secFeign.getSecJGExpertExtractList(request, token);
    }

    @ApiOperation(value = "作废专家抽取同时作废对应专家选取信息")
    @GetMapping("/cancelJGExtractCancelSelect")
    public BaseResponse cancelJGExtractCancelSelect(@RequestParam String srId) {
        log.info("cancelJGExtractCancelSelect");
        return secFeign.cancelJGExtractCancelSelect(srId);
    }

    @ApiOperation(value = "ID查询专家选取记录-jimu打印")
    @GetMapping("/getSecJGSelectionRecordPrintById")
    public BaseResponse getSecJGSelectionRecordPrintById(@RequestParam String srId) {
        log.info("getSecJGSelectionRecordPrintById");
        return secFeign.getSecJGSelectionRecordPrintById(srId);
    }

    @ApiOperation(value = "ID查询专家选取记录-jimu打印")
    @GetMapping("/getSecJGSelectionRecordPrintInsecDtlModelsById")
    public BaseResponse getSecJGSelectionRecordPrintInsecDtlModelsById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintInsecDtlModelsById");
        return secFeign.getSecJGSelectionRecordPrintInsecDtlModelsById(srId,token);
    }
    @ApiOperation(value = "ID查询专家选取记录-jimu打印")
    @GetMapping("/getSecJGSelectionRecordPrintOutsecDtlModelsById")
    public BaseResponse getSecJGSelectionRecordPrintOutsecDtlModelsById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintOutsecDtlModelsById");
        return secFeign.getSecJGSelectionRecordPrintOutsecDtlModelsById(srId,token);
    }

    @ApiOperation(value = "ID查询专家选取记录-jimu打印")
    @GetMapping("/getSecJGSelectionRecordPrintSrInsideExListById")
    public BaseResponse getSecJGSelectionRecordPrintSrInsideExListById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintSrInsideExListById");
        return secFeign.getSecJGSelectionRecordPrintSrInsideExListById(srId,token);
    }
    @ApiOperation(value = "ID查询专家选取记录-jimu打印")
    @GetMapping("/getSecJGSelectionRecordPrintSrOutsideExListById")
    public BaseResponse getSecJGSelectionRecordPrintSrOutsideExListById(@RequestParam String srId, @RequestHeader String token) {
        log.info("getSecJGSelectionRecordPrintSrOutsideExListById");
        return secFeign.getSecJGSelectionRecordPrintSrOutsideExListById(srId,token);
    }
}
