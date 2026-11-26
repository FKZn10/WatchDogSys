package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeReadRequest;
import com.jpmsoft.common.model.request.Oa.OaNoticeRequest;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "通知公告、阅读记录", value = "通知公告、阅读记录")
@RequestMapping("/OA/Notice")
public class OaNoticeController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改通知公告")
    @PostMapping("/createOrUpdateOaNotice")
    public BaseResponse createOrUpdateOaNotice(@RequestBody OaNoticeRequest request) {
        log.info("createOrUpdateOaNotice");
        return oaFeign.createOrUpdateOaNotice(request);
    }

    @ApiOperation(value = "ID查询通知公告")
    @GetMapping("/getOaNoticeByNid")
    public BaseResponse getOaNoticeByNid(@RequestParam String nId) {
        log.info("getOaNoticeByNid");
        return oaFeign.getOaNoticeByNid(nId);
    }

    @ApiOperation(value = "分页查询通知公告")
    @PostMapping("/getOaNoticeList")
    public BaseResponse getOaNoticeList(@RequestBody OaNoticeRequest request) {
        log.info("getOaNoticeList");
        return oaFeign.getOaNoticeList(request);
    }

    @ApiOperation(value = "分页查询用户通知公告")
    @PostMapping("/getUserOaNoticeList")
    public BaseResponse getUserOaNoticeList(@RequestBody OaNoticeRequest request) {
        log.info("getUserOaNoticeList");
        return oaFeign.getUserOaNoticeList(request);
    }

    @ApiOperation(value = "ID删除通知公告")
    @GetMapping("/deleteOaNoticeByNid")
    public BaseResponse deleteOaNoticeByNid(@RequestParam String nId) {
        log.info("deleteOaNoticeByNid");
        return oaFeign.deleteOaNoticeByNid(nId);
    }

    /**
     * 添加查看记录
     *
     * @param requestBean
     * @author:zhangl
     */
    @ApiOperation(value = "添加查看记录")
    @PostMapping("/createOrUpdateViewRecords")
    public BaseResponse createOrUpdateViewRecords(@RequestBody RequestBean requestBean){
        log.info("createOrUpdateViewRecords");
        return oaFeign.createOrUpdateViewRecords(requestBean);
    }
    /**
     * 获取分页列表 查看记录
     */
    @ApiOperation(value = "获取分页列表 查看记录")
    @PostMapping("/getViewRecordsById")
    public BaseResponse getViewRecordsById(@RequestBody RequestBean requestBean){
        log.info("getViewRecordsById");
        return oaFeign.getViewRecordsById(requestBean);
    }

    @ApiOperation(value = "新增阅读记录")
    @PostMapping("/createOaNoticeRead")
    public BaseResponse createOaNoticeRead(@RequestBody OaNoticeReadRequest request) {
        log.info("createOaNoticeRead");
        return oaFeign.createOaNoticeRead(request);
    }

    @ApiOperation(value = "修改阅读记录")
    @PostMapping("/updateOaNoticeRead")
    public BaseResponse updateOaNoticeRead(@RequestBody OaNoticeReadRequest request) {
        log.info("updateOaNoticeRead");
        return oaFeign.updateOaNoticeRead(request);
    }

    @ApiOperation(value = "ID删除阅读记录")
    @GetMapping("/deleteOaNoticeReadByNrId")
    public BaseResponse deleteOaNoticeReadByNrId(@RequestParam String nrId) {
        log.info("deleteOaNoticeReadByNrId");
        return oaFeign.deleteOaNoticeReadByNrId(nrId);
    }

    @ApiOperation(value = "通知公告ID查询所有阅读记录")
    @GetMapping("/getOaNoticeReadListByNid")
    public BaseResponse getOaNoticeReadListByNid(@RequestParam("nId") String nId, @RequestParam(value = "name",required = false)String name ,@RequestParam(value = "isRead",required = false)String isRead) {
        log.info("getOaNoticeReadListByNid");
        return oaFeign.getOaNoticeReadListByNid(nId,name,isRead);
    }

    @ApiOperation(value = "分页查询阅读记录（可根据通知公告ID筛选）")
    @PostMapping("/getOaNoticeReadList")
    public BaseResponse getOaNoticeReadList(@RequestBody OaNoticeReadRequest request) {
        log.info("getOaNoticeReadList");
        return oaFeign.getOaNoticeReadList(request);
    }

}
