package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConMeasureRecordCheckDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/14 8:58
 */
@Slf4j
@RestController
@Api(tags = "计量单审核记录", value = "计量单审核记录")
@RequestMapping("/v1/conMeasureRecordCheck")
public class ConMeasureRecordCheckController {

    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "新增计量单审核记录")
    @PostMapping("/createConMeasureRecordCheck")
    public BaseResponse createConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("createConMeasureRecordCheck");
        return conFeign.createConMeasureRecordCheck(request);
    }

    @ApiOperation(value = "修改计量单审核记录")
    @PostMapping("/updateConMeasureRecordCheck")
    public BaseResponse updateConMeasureRecordCheck(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("updateConMeasureRecordCheck");
        return conFeign.updateConMeasureRecordCheck(request);
    }

    @ApiOperation(value = "ID查询计量单审核记录")
    @GetMapping("/getConMeasureRecordCheckById")
    public BaseResponse getConMeasureRecordCheckById(@RequestParam String id) {
        log.info("getConMeasureRecordCheckById");
        return conFeign.getConMeasureRecordCheckById(id);
    }

    @ApiOperation(value = "分页计量单审核记录")
    @PostMapping("/getConMeasureRecordCheckList")
    public BaseResponse getConMeasureRecordCheckList(@RequestBody ConMeasureRecordCheckDto request) {
        log.info("getConMeasureRecordCheckList");
        return conFeign.getConMeasureRecordCheckList(request);
    }

}
