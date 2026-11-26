package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkRecordRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "工作办理记录", value = "工作办理记录")
@RequestMapping("/v1/ConstructionProcedure/proCpWorkRecord")
public class ProjectCpWorkRecordController {
    @Autowired
    private CpFeign cpFeign;


    @ApiOperation(value = "创建或修改工作办理记录")
    @PostMapping("/createOrUpdateProCpWorkRecord")
    public BaseResponse createOrUpdateProCpWorkRecord(@RequestBody ProjectCpWorkRecordRequest request) {
        log.info("createOrUpdateProCpWorkRecord");
        return cpFeign.createOrUpdateProCpWorkRecord(request);
    }

    @ApiOperation(value = "ID删除工作办理记录")
    @GetMapping("/deleteProCpWorkRecordById")
    public BaseResponse deleteProCpWorkRecordById(@RequestParam String cpwrId) {
        log.info("deleteProCpWorkRecordById");
        return cpFeign.deleteProCpWorkRecordById(cpwrId);
    }

    @ApiOperation(value = "获取工作办理记录列表")
    @PostMapping("/getProCpWorkRecordList")
    public BaseResponse getProCpWorkRecordList(@RequestBody ProjectCpWorkRecordRequest request) {
        log.info("getProCpWorkRecordList");
        return cpFeign.getProCpWorkRecordList(request);
    }
}
