package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/13 9:38
 */
@Slf4j
@RestController
@Api(tags = "计量报审表", value = "计量报审表")
@RequestMapping("/v1/conMeasure")
public class ConMeasureController {
    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增修改计量报审表")
    @PostMapping("/createOrUpdateConMeasure")
    public BaseResponse createOrUpdateConMeasure(@RequestBody ConMeasureDto request) {
        log.info("createOrUpdateConMeasure");
        return conFeign.createOrUpdateConMeasure(request);
    }

    @ApiOperation(value = "新增计量报审表")
    @PostMapping("/createConMeasure")
    public BaseResponse createConMeasure(@RequestBody ConMeasureDto request) {
        log.info("createConMeasure");
        return conFeign.createConMeasure(request);
    }


    @ApiOperation(value = "修改计量报审表")
    @PostMapping("/updateConMeasure")
    public BaseResponse updateConMeasure(@RequestBody ConMeasureDto request) {
        log.info("updateConMeasure");
        return conFeign.updateConMeasure(request);
    }

    @ApiOperation(value = "修改计量报审表(不操作子表单)")
    @PostMapping("/updateConMeasureOnly")
    public BaseResponse updateConMeasureOnly(@RequestBody ConMeasureDto request) {
        log.info("updateConMeasureOnly");
        return conFeign.updateConMeasureOnly(request);
    }


    @ApiOperation(value = "分页计量报审表")
    @PostMapping("/getConMeasureList")
    public BaseResponse getConMeasureList(@RequestBody ConMeasureDto request,@RequestHeader String token) {
        log.info("getConMeasureList");
        return conFeign.getConMeasureList(request,token);
    }

    @ApiOperation(value = "ID查询计量报审表")
    @GetMapping("/getConMeasureById")
    public BaseResponse getConMeasureById(@RequestParam String id) {
        log.info("getConMeasureById");
        return conFeign.getConMeasureById(id);
    }


    @ApiOperation(value = "合同ID查询最新审批通过的一条计量报审数据")
    @GetMapping("/getLastApproveMeasureByConId")
    public BaseResponse getLastApproveMeasureByConId(@RequestParam String conId) {
        log.info("getLastApproveMeasureByConId");
        return conFeign.getLastApproveMeasureByConId(conId);
    }


    @ApiOperation(value = "计量报审退回发起人(删除计量单审核记录（Con_Measure_Record_Check），重新赋值计量单（Con_Measure_Record）)")
    @GetMapping("/return2Initiator")
    public BaseResponse return2Initiator(@RequestParam String id) {
        log.info("return2Initiator");
        return conFeign.return2Initiator(id);
    }


    @ApiOperation(value = "ID统计已申报的清单的总数，已审核的总数，申报金额统计，审核金额统计")
    @GetMapping("/statisticsApplyAndAmt")
    public BaseResponse statisticsApplyAndAmt(@RequestParam String id,@RequestParam(required = false) String conId) {
        log.info("statisticsApplyAndAmt");
        return conFeign.statisticsApplyAndAmt(id,conId);
    }

    @ApiOperation(value = "根据合同统计审批通过的本期审定产值")
    @GetMapping("/statisticsAmtByConId")
    public BaseResponse statisticsAmtByConId(@RequestParam String conId) {
        log.info("statisticsAmtByConId");
        return conFeign.statisticsAmtByConId(conId);
    }


    /**
     * 多条件筛选
     *
     * @param request
     * @return
     */
    @PostMapping("/list/getConMeasureClassInfo")
    public BaseResponse getConMeasureClassInfo(@RequestBody ConMeasureDto request) {
        log.info("getConMeasureClassInfo");
        return conFeign.getConMeasureClassInfo(request);
    }

    @PostMapping("/list/getConMeasureInfo")
    public BaseResponse getConMeasureInfo(@RequestBody ConMeasureDto request) {
        log.info("getConMeasureInfo");
        return conFeign.getConMeasureInfo(request);
    }
}
