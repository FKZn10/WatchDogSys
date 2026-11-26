package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.service.contract.IConBoqEcchangeDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 变更事项申请-合同工程量清单变更
 * @Author: 李文涛 aaron
 * @date: 2024/10/24 10:16
 */
@Slf4j
@RestController
@RequestMapping("/v1/boqEcChange")
public class ConBoqEcchangeDtlController {


    @Resource
    private IConBoqEcchangeDtlService conBoqEcchangeDtlService;

    /**
     * 创建修改工程量清单变更
     *
     * @param request
     * @param formPriKey 低代码主表ID 这里是变更事项id
     * @return
     */
    @PostMapping("/createOrUpdateBoqEcChange")
    public  BaseResponse createOrUpdateBoqEcChange(@RequestBody List<ConBoqDtlDto> request, @RequestParam String formPriKey){
        return conBoqEcchangeDtlService.createOrUpdateBoqEcChange(request,formPriKey);
    }

    @GetMapping("/getBoqEcChangeDtlByEcId")
    public BaseResponse getBoqEcChangeDtlByEcId(@RequestParam String ecId)
    {
        return conBoqEcchangeDtlService.getBoqEcChangeDtlByEcId(ecId);
    }

    @GetMapping("/approved2RefreshConBoqDtl")
    public BaseResponse approved2RefreshConBoqDtl(@RequestParam String ecId)
    {
        conBoqEcchangeDtlService.approved2RefreshConBoqDtl(ecId);
        return  BaseResponse.ok();
    }

    @PostMapping("/can2Change")
    public  BaseResponse can2Change(@RequestBody ConBoqDtlDto request){
        return conBoqEcchangeDtlService.can2Change(request);
    }

    @GetMapping("/getBoqEcChangeDtlByBoqdId")
    public BaseResponse getBoqEcChangeDtlByBoqdId(@RequestParam String boqdId)
    {
        return conBoqEcchangeDtlService.getBoqEcChangeDtlByBoqdId(boqdId);
    }

}
