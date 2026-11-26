package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QualitySafetyStatisticFowYWVO;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/qsRecord")
public class QsRecordController {
    @Resource
    private IQsRecordService qsRecordService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateQsRecord")
    public BaseResponse createOrUpdateQsRecord(@RequestBody QsRecordRequest request) {
        log.info("createOrUpdateQsRecord");
        return qsRecordService.createOrUpdateQsRecord(request);
    }

    @TenantId
    @PostMapping("/getQsRecordList")
    public BaseResponse getQsRecordList(@RequestBody QsRecordRequest request, @RequestHeader String token) {
        log.info("getQsRecordList");
        return qsRecordService.getQsRecordList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getQsRecordLedgerList")
    public BaseResponse getQsRecordLedgerList(@RequestBody QsRecordRequest request,
                                              @RequestHeader String token) {
        log.info("getQsRecordLedgerList");
        return qsRecordService.getQsRecordLedgerList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getQsRecordByQsrId")
    public BaseResponse getQsRecordByQsrId(@RequestParam String qsrId) {
        log.info("getQsRecordByQsrId");
        return qsRecordService.getQsRecordByQsrId(qsrId);
    }

    @PostMapping("/updateRectifyPic")
    public BaseResponse updateRectifyPic(@RequestBody QsRecordRequest request) {
        log.info("updateRectifyPic");
        return qsRecordService.updateRectifyPic(request);
    }

    @PostMapping("/updateRecord")
    public BaseResponse updateRecord(@RequestBody QsRecordRequest request) {
        log.info("updateRecord");
        return qsRecordService.updateRecord(request);
    }

    /**
     * 义乌质量安全问题数量统计
     * @param request
     * @return
     */
    @PostMapping("/qualitySafetyStatisticFowYW")
    public BaseResponse getQualitySafetyStatisticFowYW(@RequestBody QualitySafetyStatisticFowYWRequest request){

        return qsRecordService.getQualitySafetyStatisticFowYW(request);
    }


    @PostMapping("/getQualitySafetyStatisticFowYWExport")
    public List<QualitySafetyStatisticFowYWVO> getQualitySafetyStatisticFowYWExport(@RequestBody QualitySafetyStatisticFowYWRequest request){
        return  qsRecordService.getQualitySafetyStatisticFowYWExport(request);
    }

    @PostMapping("/createOrUpdateQsRecordRequest")
    public BaseResponse createOrUpdateQsRecordRequest(@RequestBody QsRecordRequest request) {
        log.info("createOrUpdateQsRecordRequest");
        return qsRecordService.createOrUpdateQsRecordRequest(request);
    }

    @GetMapping("deleteByQsrId")
    public BaseResponse deleteByQsrId(@RequestParam String qsrId) {
        log.info("deleteByQsrId");
        return qsRecordService.deleteByQsrId(qsrId);
    }

}
