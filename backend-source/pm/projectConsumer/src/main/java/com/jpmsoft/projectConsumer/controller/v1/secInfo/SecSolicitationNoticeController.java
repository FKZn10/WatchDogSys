package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecSolicitationNoticeDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:44
 */
@Slf4j
@RestController
@Api(tags = "征集公告", value = "征集公告")
@RequestMapping("/v1/solicitationNotice")
public class SecSolicitationNoticeController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改征集公告")
    @PostMapping("/createOrUpdateSecSolicitationNotice")
    public BaseResponse createOrUpdateSecSolicitationNotice(@RequestBody SecSolicitationNoticeDto request) {
        log.info("createOrUpdateSecSolicitationNotice");
        return secFeign.createOrUpdateSecSolicitationNotice(request);
    }

    @ApiOperation(value = "分页查询征集公告")
    @PostMapping("/getSecSolicitationNoticeList")
    public BaseResponse getSecSolicitationNoticeList(@RequestBody SecSolicitationNoticeDto request) {
        log.info("getSecSolicitationNoticeList");
        return secFeign.getSecSolicitationNoticeList(request);
    }

    @ApiOperation(value = "ID查询征集公告")
    @GetMapping("/getSecSolicitationNoticeById")
    public BaseResponse getSecSolicitationNoticeById(@RequestParam String id) {
        log.info("getSecSolicitationNoticeById");
        return secFeign.getSecSolicitationNoticeById(id);
    }


    @ApiOperation(value = "标段ID查询最新一条审批通过的征集公告")
    @GetMapping("/getSecSolicitationNoticeLastApprovedBySecId")
    public BaseResponse getSecSolicitationNoticeLastApprovedBySecId(@RequestParam String secId) {
        log.info("getSecSolicitationNoticeLastApprovedBySecId");
        return secFeign.getSecSolicitationNoticeLastApprovedBySecId(secId);
    }
}
