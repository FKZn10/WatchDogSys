package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "变更事项申请-合同工程量清单变更", value = "变更事项申请-合同工程量清单变更")
@RequestMapping("/v1/boqEcChange")
public class ConBoqEcchangeDtlController {


    @Resource
    private ConFeign conFeign;

    /**
     * 创建修改工程量清单变更
     *
     * @param request
     * @param formPriKey 低代码主表ID 这里是变更事项id
     * @return
     */
    @ApiOperation(value = "新增或修改工程量清单变更  formPriKey(默认拼接到url) 低代码主表ID 这里是变更事项id")
    @PostMapping("/createOrUpdateBoqEcChange")
    public  BaseResponse createOrUpdateBoqEcChange(@RequestBody List<ConBoqDtlDto> request, @RequestParam String formPriKey){
        return conFeign.createOrUpdateBoqEcChange(request,formPriKey);
    }



    @ApiOperation(value = "变更事项ID查询所有工程量清单变更")
    @GetMapping("/getBoqEcChangeDtlByEcId")
    public BaseResponse getBoqEcChangeDtlByEcId(@RequestParam String ecId)
    {
        return conFeign.getBoqEcChangeDtlByEcId(ecId);
    }

    @ApiOperation(value = "审批通过回调反写工程量清单详情")
    @GetMapping("/approved2RefreshConBoqDtl")
    public BaseResponse approved2RefreshConBoqDtl(@RequestParam String ecId)
    {
        return  conFeign.approved2RefreshConBoqDtl(ecId);
    }


    @ApiOperation(value = "判断是否可变更（数量变少）或删除")
    @PostMapping("/can2Change")
    public  BaseResponse can2Change(@RequestBody ConBoqDtlDto request){
        return conFeign.can2Change(request);
    }

    @ApiOperation(value = "工程量清单详情ID查询变更记录")
    @GetMapping("/getBoqEcChangeDtlByBoqdId")
    public BaseResponse getBoqEcChangeDtlByBoqdId(@RequestParam String boqdId)
    {
        return conFeign.getBoqEcChangeDtlByBoqdId(boqdId);
    }
}
