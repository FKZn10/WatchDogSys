package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "中标通知书", value = "中标通知书")
@RequestMapping("/v1/winNotice")
public class SecWinNoticeController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改中标通知书")
    @PostMapping("/createOrUpdateWinNotice")
    public BaseResponse createOrUpdateWinNotice(@RequestBody SecWinNoticeRequest request) {
        log.info("createOrUpdateWinNotice");
        return secFeign.createOrUpdateWinNotice(request);
    }

    @ApiOperation(value = "分页查询中标通知书")
    @PostMapping("/getWinNoticeList")
    public BaseResponse getWinNoticeList(@RequestBody SecWinNoticeRequest request) {
        log.info("getWinNoticeList");
        return secFeign.getWinNoticeList(request);
    }

    @ApiOperation(value = "分页查询中标通知书(默认项目标段一对多)")
    @PostMapping("/getWinNoticeListOne2Many")
    public BaseResponse getWinNoticeListOne2Many(@RequestBody SecWinNoticeRequest request) {
        log.info("getWinNoticeListOne2Many");
        return secFeign.getWinNoticeListOne2Many(request);
    }


    @ApiOperation(value = "ID查询中标通知书")
    @GetMapping("/getWinNoticeByWnId")
    public BaseResponse getWinNoticeByWnId(@RequestParam String wnId) {
        log.info("getWinNoticeByWnId");
        return secFeign.getWinNoticeByWnId(wnId);
    }

    @ApiOperation(value = "标段ID查询最新一条审批通过的数据")
    @GetMapping("/getLatestApproveWinNotice")
    public BaseResponse getLatestApproveWinNotice(@RequestParam String secId) {
        log.info("getLatestApproveWinNotice");
        return secFeign.getLatestApproveWinNotice(secId);
    }

    @ApiOperation(value = "ID删除中标通知书")
    @GetMapping("/deleteWinNoticeByWnId")
    public BaseResponse deleteWinNoticeByWnId(@RequestParam String wnId) {
        log.info("deleteWinNoticeByWnId");
        return secFeign.deleteWinNoticeByWnId(wnId);
    }

    @GetMapping("/getWinNoticeCompleteBySecId")
    public BaseResponse getWinNoticeCompleteBySecId(@RequestParam String secId) {
        log.info("getWinNoticeCompleteBySecId");
        return secFeign.getWinNoticeCompleteBySecId(secId);
    }

    @ApiOperation("分页查询中标通知书-城建")
    @PostMapping("/getWinNoticeListClass")
    public BaseResponse getWinNoticeListClass(@RequestBody SecWinNoticeRequest request, @RequestHeader String token) {
        log.info("getWinNoticeListClass");
        return secFeign.getWinNoticeListClass(request, token);
    }

    @ApiOperation("中标通知书多条件查询信息-城建")
    @PostMapping("/getWinNoticeListClassInfo")
    public BaseResponse getWinNoticeListClassInfo(@RequestBody SecWinNoticeRequest request, @RequestHeader String token) {
        log.info("getWinNoticeListClassInfo");
        return secFeign.getWinNoticeListClassInfo(request, token);
    }

    @ApiOperation("获取中标信息")
    @GetMapping("/getWinningBidInformation")
    public BaseResponse getWinningBidInformation(@RequestParam String secId) {
        log.info("getWinningBidInformation");
        return secFeign.getWinningBidInformation(secId);
    }
}
