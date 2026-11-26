package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsExpertService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/v1/QsExpert")
public class QsExpertController {

    @Resource
    private IQsExpertService QsExpertService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateQsExpert")
    public BaseResponse createOrUpdateQsExpert(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpert");
        return QsExpertService.createOrUpdateQsExpert(request);
    }


    @TenantId
    @PostMapping("/createOrUpdateQsExpertCurrent")
    public BaseResponse createOrUpdateQsExpertCurrent(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpertCurrent");
        return QsExpertService.createOrUpdateQsExpertCurrent(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateQsExpertVerify")
    public BaseResponse createOrUpdateQsExpertVerify(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpertVerify");
        return QsExpertService.createOrUpdateQsExpertVerify(request);
    }


    @GetMapping("/resetPlaintextNum")
    public BaseResponse resetPlaintextNum() {
        log.info("resetPlaintextNum");
        return QsExpertService.resetPlaintextNum();
    }

    @TenantId
    @PostMapping("/getQsExpertList")
    public BaseResponse getQsExpertList(@RequestBody QsExpertModel request) {
        log.info("getQsExpertList");
        return QsExpertService.getQsExpertList(request);
    }


    @GetMapping("/getQsExpertById")
    public BaseResponse getQsExpertById(@RequestParam String exId) {
        log.info("getQsExpertById");
        return QsExpertService.getQsExpertById(exId);
    }

    @GetMapping("/deleteQsExpertById")
    public BaseResponse deleteQsExpertById(@RequestParam String exId) {
        log.info("deleteQsExpertById");
        return QsExpertService.deleteQsExpertById(exId);
    }


    @GetMapping("/expertExists")
    public BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertExists");
        return BaseResponse.ok(QsExpertService.expertExists(idNum, id, redisUtils.getTeId(token)));
    }

}
