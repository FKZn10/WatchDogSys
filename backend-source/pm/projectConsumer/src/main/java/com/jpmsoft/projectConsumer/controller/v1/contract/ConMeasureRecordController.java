package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/27 17:11
 */
@Slf4j
@RestController
@Api(tags = "计量单", value = "计量单")
@RequestMapping("/v1/conMeasureRecord")
public class ConMeasureRecordController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "合同ID+清单号查询历史审批通过的计量单")
    @GetMapping("/getAllConMearByConIdAndCode")
    public BaseResponse getAllConMearByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode) {
        return conFeign.getAllConMearByConIdAndCode(conId, boqdCode);
    }

    @ApiOperation(value = "web自动保存计量单(计量单单条新增修改)")
    @PostMapping("/webAutoSaveConMeasureRecord")
    public BaseResponse webAutoSaveConMeasureRecord(@RequestBody ConMeasureRecordDto request, @RequestHeader String token) {
        return conFeign.webAutoSaveConMeasureRecord(request, token);
    }

    @ApiOperation(value = "根据计量单判断是否可删除合同工程量清单列表")
    @PostMapping("/can2deleteBoqd")
    public BaseResponse can2deleteBoqd(@RequestBody List<String> boqdIds) {
        return conFeign.can2deleteBoqd(boqdIds);
    }

}
