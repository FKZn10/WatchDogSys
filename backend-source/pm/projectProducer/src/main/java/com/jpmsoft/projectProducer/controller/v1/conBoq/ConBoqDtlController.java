package com.jpmsoft.projectProducer.controller.v1.conBoq;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/conbopDtl")
public class ConBoqDtlController {

    @Resource
    private IConBoqDtlService conBoqDtlService;

    @PostMapping("/createOne")
    public   BaseResponse createOne(@RequestBody ConBoqDtlDto request) {
        log.info("createOne");
        return conBoqDtlService.createOne(request);
    }

    @PostMapping("/updateOne")
    public   BaseResponse updateOne(@RequestBody ConBoqDtlDto request) {
        log.info("updateOne");
        return conBoqDtlService.updateOne(request);
    }
    @GetMapping("/deleteOne")
    public BaseResponse deleteOne(@RequestParam String boqdId) {
        log.info("deleteOne");
        return conBoqDtlService.deleteOne(boqdId);
    }

    @PostMapping("/deleteBath")
    public BaseResponse deleteBath(@RequestBody List<String> ids)
    {
        log.info("deleteBath");
        return conBoqDtlService.deleteBath(ids);
    }

    /**
     * 保存
     */
    @PostMapping("/saveBoqDtl")
    public BaseResponse saveBoqDtl(@RequestBody RequestBean requestBean) {
        log.info("saveBoqDtl");
        return conBoqDtlService.saveBoqDtl(requestBean);
    }


    /**
     * 添加临时表
     */
    @PostMapping("/createOrUpdateOrdeleteConBoqDtl")
    public BaseResponse createOrUpdateOrdeleteConBoqDtl(@RequestBody RequestBean requestBean) {
        log.info("createOrUpdateOrdeleteConBoqDtl");
        return conBoqDtlService.createOrUpdateOrdeleteConBoqDtl(requestBean);
    }

    /**
     * 查询临时表
     */
    @PostMapping("/getConBoqDtlTempList")
    public BaseResponse getConBoqDtlTempList(@RequestBody RequestBean requestBean) {
        log.info("getConBoqDtlTempList");
        return conBoqDtlService.getConBoqDtlTempList(requestBean);
    }

    @PostMapping("/importExcel")
    public BaseResponse importExcel(@RequestBody RequestBean requestBean) {
        return conBoqDtlService.importExcel(requestBean);
    }

    @GetMapping("/getConBoqDtlByConId")
    public List<ConBoqDtlDto> getConBoqDtlByConId(@RequestParam String conId) {
        log.info("getConBoqDtlByConId");
        return conBoqDtlService.getConBoqDtlByConId(conId);
    }

    @GetMapping("/getConBoqDtlTreeByConId")
    public BaseResponse getConBoqDtlTreeByConId(@RequestParam String conId,@RequestParam(required = false,name = "boqId") String boqId,@RequestParam(required = false,name = "parentId") String parentId) {
        log.info("getConBoqDtlTreeByConId");
        return conBoqDtlService.getConBoqDtlTreeByConId(conId,boqId,parentId);
    }

    @GetMapping("/getConBoqDtlByConIdAndCode")
    public BaseResponse getConBoqDtlByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode) {
        log.info("getConBoqDtlByConIdAndCode");
        return conBoqDtlService.getConBoqDtlByConIdAndCode(conId, boqdCode);
    }

    @PostMapping("/getConBoqDtlParentByIds")
    public BaseResponse getConBoqDtlParentByIds(@RequestBody List<String> boqdIds) {
        log.info("getConBoqDtlParentByIds");
        return conBoqDtlService.getConBoqDtlParentByIds(boqdIds);
    }

    @TenantId
    @PostMapping("/getConBoqDtlBudgetList")
    public BaseResponse getConBoqDtlBudgetList(@RequestBody ConBoqBudgetDto request) {
        log.info("getConBoqDtlBudgetList");
        return conBoqDtlService.getConBoqDtlBudgetList(request);
    }


    @PostMapping("/getConBoqDtlAndMeasureTree")
    public BaseResponse getConBoqDtlAndMeasureTree(@RequestBody ConBoqDtlMeasureDTO request) {
        log.info("getConBoqDtlAndMeasureTree");
        return conBoqDtlService.getConBoqDtlAndMeasureTree(request);
    }

    @GetMapping("/getConBoqDtlByBoqIdAndParent")
    public BaseResponse getConBoqDtlByBoqIdAndParent(@RequestParam String boqId, @RequestParam String parentId) {
        log.info("getConBoqDtlByBoqIdAndParent");
        return BaseResponse.ok(conBoqDtlService.getConBoqDtlByBoqIdAndParent(boqId, parentId));
    }
}
