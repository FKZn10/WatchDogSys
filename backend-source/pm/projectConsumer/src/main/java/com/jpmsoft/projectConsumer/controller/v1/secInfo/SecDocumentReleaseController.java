package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecDocumentReleaseModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标文件发布", value = "招标文件发布")
@RequestMapping("/v1/documentRelease")
public class SecDocumentReleaseController {
    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改招标文件发布")
    @PostMapping("/createOrUpdateSecDocumentRelease")
    public BaseResponse createOrUpdateSecDocumentRelease(@RequestBody SecDocumentReleaseModel request) {
        log.info("createOrUpdateSecDocumentRelease");
        return secFeign.createOrUpdateSecDocumentRelease(request);
    }

    @ApiOperation(value = "分页查询招标文件发布")
    @PostMapping("/getSecDocumentReleaseList")
    public BaseResponse getSecDocumentReleaseList(@RequestBody SecDocumentReleaseModel request) {
        log.info("getSecDocumentReleaseList");
        return secFeign.getSecDocumentReleaseList(request);
    }

    @ApiOperation(value = "ID查询招标文件发布")
    @GetMapping("/getSecDocumentReleaseById")
    public BaseResponse getSecDocumentReleaseById(@RequestParam String sdrId) {
        log.info("getSecDocumentReleaseById");
        return secFeign.getSecDocumentReleaseById(sdrId);
    }

    @ApiOperation(value = "审批结束同步招标文件开标时间")
    @GetMapping("/appoval2SyncDocument")
    public BaseResponse appoval2SyncDocument(@RequestParam String sdrId) {
        log.info("appoval2SyncDocument");
        return secFeign.appoval2SyncDocument(sdrId);
    }
}
