package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.FiledChangeRecordDto;
import com.jpmsoft.userProducer.pojo.dto.SendFiledChangeDto;
import com.jpmsoft.userProducer.pojo.entity.FiledChangeRecord;
import com.jpmsoft.userProducer.service.IFiledChangeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "修改留痕-表字段修改记录", value = "修改留痕-表字段修改记录")
@RequestMapping("/v1/FiledChange")
public class FiledChangeRecordController {

    @Resource
    private IFiledChangeRecordService filedChangeRecordService;

    @ApiOperation(value = "新增表字段修改记录")
    @PostMapping("/insertFiledChangeRecord")
    public BaseResponse insertFiledChangeRecord(@RequestBody SendFiledChangeDto request) {
        log.info("insertFiledChangeRecord");
        return filedChangeRecordService.insertFiledChangeRecord(request);
    }


    @ApiOperation(value = "新增表字段修改记录(直接生成记录)")
    @PostMapping("/saveFiledChangeRecord")
    public BaseResponse saveFiledChangeRecord(@RequestBody FiledChangeRecord request) {
        log.info("saveFiledChangeRecord");
        return filedChangeRecordService.saveFiledChangeRecord(request);
    }

    @ApiOperation(value = "批量新增表字段修改记录(直接生成记录)")
    @PostMapping("/saveFiledChangeRecordBath")
    public BaseResponse saveFiledChangeRecordBath(@RequestBody List<FiledChangeRecord> request) {
        log.info("saveFiledChangeRecordBath");
        return filedChangeRecordService.saveFiledChangeRecordBath(request);
    }


    @ApiOperation(value = "新增表字段修改记录")
    @PostMapping("/insertFiledChangeRecordBath")
    public BaseResponse insertFiledChangeRecordBath(@RequestBody List<SendFiledChangeDto> request) {
        log.info("insertFiledChangeRecordBath");
        return filedChangeRecordService.insertFiledChangeRecordBath(request);
    }


    @ApiOperation(value = "分页查询表字段修改记录")
    @PostMapping("/getFiledChangeRecordList")
    public BaseResponse getFiledChangeRecordList(@RequestBody FiledChangeRecordDto request) {
        log.info("getFiledChangeRecordList");
        return filedChangeRecordService.getFiledChangeRecordList(request);
    }

    @ApiOperation(value = "表单主键ID查询字段修改记录(每个字段的最后一次修改)")
    @GetMapping("/getFiledLastChangeByKey")
    public BaseResponse getFiledLastChangeByKey(@RequestParam String fcrKey) {
        log.info("getFiledLastChangeByKey");
        return filedChangeRecordService.getFiledLastChangeByKey(fcrKey);
    }
    @ApiOperation(value = "表单主键ID查询所有字段修改记录")
    @GetMapping("/getAllFiledLastChangeByKey")
    public BaseResponse getAllFiledLastChangeByKey(@RequestParam String fcrKey) {
        log.info("getAllFiledLastChangeByKey");
        return filedChangeRecordService.getAllFiledLastChangeByKey(fcrKey);
    }

}
