package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "质量安全检查记录", value = "质量安全检查记录")
@RequestMapping("/safety/v1/qsRecord")
public class QsRecordController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改质量安全检查记录")
    @PostMapping("/createOrUpdateQsRecord")
    public BaseResponse createOrUpdateQsRecord(@RequestBody QsRecordRequest request) {
        log.info("createOrUpdateQsRecord");
        return baseFeign.createOrUpdateQsRecord(request);
    }

    @ApiOperation(value = "分页质量安全检查记录")
    @PostMapping("/getQsRecordList")
    public BaseResponse getQsRecordList(@RequestBody QsRecordRequest request, @RequestHeader String token) {
        log.info("getQsRecordList");
        return baseFeign.getQsRecordList(request, token);
    }

    @ApiOperation(value = "分页查询质量安全问题台账")
    @PostMapping("/getQsRecordLedgerList")
    public BaseResponse getQsRecordLedgerList(@RequestBody QsRecordRequest request,
                                               @RequestHeader String token) {
        log.info("getQsRecordLedgerList");
        return baseFeign.getQsRecordLedgerList(request, token);
    }

    @ApiOperation(value = "ID查询质量安全检查记录")
    @GetMapping("/getQsRecordByQsrId")
    public BaseResponse getQsRecordByQsrId(@RequestParam String qsrId) {
        log.info("getQsRecordByQsrId");
        return baseFeign.getQsRecordByQsrId(qsrId);
    }

    @ApiOperation(value = "仅上传整改后照片")
    @PostMapping("/updateRectifyPic")
    public BaseResponse updateRectifyPic(@RequestBody QsRecordRequest request) {
        log.info("updateRectifyPic");
        return baseFeign.updateRectifyPic(request);
    }

    @ApiOperation(value = "修改问题类别")
    @PostMapping("/updateRecord")
    public BaseResponse updateRecord(@RequestBody QsRecordRequest request) {
        log.info("updateRecord");
        return baseFeign.updateRecord(request);
    }

    /**
     * 义乌质量安全问题数量统计
     * @param request
     * @return
     */
    @PostMapping("/qualitySafetyStatisticFowYW")
    public BaseResponse getQualitySafetyStatisticFowYW(@RequestBody QualitySafetyStatisticFowYWRequest request){
        return baseFeign.getQualitySafetyStatisticFowYW(request);
    }

    @PostMapping("/createOrUpdateQsRecordRequest")
    public BaseResponse createOrUpdateQsRecordRequest(@RequestBody QsRecordRequest request) {
        log.info("createOrUpdate");
        return baseFeign.createOrUpdateQsRecordRequest(request);
    }

    @GetMapping("deleteByQsrId")
    public BaseResponse deleteByQsrId(@RequestParam String qsrId) {
        log.info("deleteByQsrId");
        return baseFeign.deleteByQsrId(qsrId);
    }

}
