package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecNoticeRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "招标公告", value = "招标公告")
@RequestMapping("/v1/notice")
public class SecNoticeController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标公告")
    @PostMapping("/createOrUpdateSecNotice")
    public BaseResponse createOrUpdateSecNotice(@RequestBody SecNoticeRequest request) {
        log.info("createOrUpdateSecNotice");
        return secFeign.createOrUpdateSecNotice(request);
    }

    @ApiOperation(value = "ID删除招标公告")
    @GetMapping("/deleteSecNoticeByNid")
    public BaseResponse deleteSecNoticeByNid(@RequestParam String nId) {
        log.info("deleteSecNoticeByNid");
        return secFeign.deleteSecNoticeByNid(nId);
    }

    @ApiOperation(value = "ID查询招标公告")
    @GetMapping("/getSecNoticeByNid")
    public BaseResponse getSecNoticeByNid(@RequestParam String nId) {
        log.info("getSecNoticeByNid");
        return secFeign.getSecNoticeByNid(nId);
    }

    @ApiOperation(value = "标段ID查询最新一条审批通过的招标公告")
    @GetMapping("/getSecNoticeLastApprovedBySecId")
    public BaseResponse getSecNoticeLastApprovedBySecId(@RequestParam String secId) {
        log.info("getSecNoticeLastApprovedBySecId");
        return secFeign.getSecNoticeLastApprovedBySecId(secId);
    }

    @ApiOperation(value = "分页查询招标公告")
    @PostMapping("/getSecNoticeList")
    public BaseResponse getSecNoticeList(@RequestBody SecNoticeRequest request) {
        log.info("getSecNoticeList");
        return secFeign.getSecNoticeList(request);
    }
}
