package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计量单
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/14 8:58
 */
@Slf4j
@RestController
@RequestMapping("/v1/conMeasureRecord")
public class ConMeasureRecordController {

    @Resource
    private IConMeasureRecordService conMeasureRecordService;

    @GetMapping("/getConMearDeclareByConId")
    public List<ConMearDeclareDto> getConMearDeclareByConId(@RequestParam String conId) {
        return conMeasureRecordService.getConMearDeclareByConId(conId);
    }

    @GetMapping("/getAllConMearByConIdAndCode")
    public BaseResponse getAllConMearByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode) {
        return BaseResponse.ok(conMeasureRecordService.getAllConMearByConIdAndCode(conId, boqdCode));
    }

    @PostMapping("/webAutoSaveConMeasureRecord")
    public BaseResponse webAutoSaveConMeasureRecord(@RequestBody ConMeasureRecordDto request,@RequestHeader String token) {
        return conMeasureRecordService.webAutoSaveConMeasureRecord(request,token);
    }


    @PostMapping("/can2deleteBoqd")
    public BaseResponse can2deleteBoqd(@RequestBody List<String> boqdIds) {
        return BaseResponse.ok(conMeasureRecordService.can2deleteBoqd(boqdIds));
    }

}
