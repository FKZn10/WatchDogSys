package com.jpmsoft.jpbpm.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.jpbpm.pojo.dto.SysProcessInvalidationRecordDto;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.vo.SysFileVo;
import com.jpmsoft.jpbpm.service.FileService;
import com.jpmsoft.jpbpm.service.SecBiddingProcessingService;
import com.jpmsoft.jpbpm.service.SysProcessInvalidationRecordService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@ApiIgnore
@Api(tags = "流程实例", value = "流程实例")
@RequestMapping("/v1/sysFile")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SecBiddingProcessingService secBiddingProcessingService;
    @Autowired
    private SysProcessInvalidationRecordService sysProcessInvalidationRecordService;

    /**
     * 流程管理
     * 流程实例
     * reuqest.started ==True 则为已启动流程
     *
     * @param request
     * @return
     */
    @PostMapping("/getFileList")
    public BaseResponse getFileList(@RequestBody SysFileRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFileList");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getFileList(request, customerId);
    }

    /**
     * 流程管理
     * 流程实例
     * reuqest
     *
     * @param id
     * @return
     */
    @GetMapping("/getFileByFileId")
    public BaseResponse getFileByFileId(@RequestParam String id, @RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestHeader(value = "token", required = false) String token) {
        log.info("getFileByFileId");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.getFileByFileId(id, customerId, tCode, year);
    }

    @GetMapping("/detailById")
    public BaseResponse detailById(@RequestParam("id") String id, @RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:detailById");
        return new BaseResponse("操作成功！", fileService.getFileById(id, tCode, year), 1);
    }

    @PostMapping("/create")
    public BaseResponse create(@RequestBody SysFileVo sysFileVo, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:create");
        String customerId = redisUtils.getCustomerId(token);
        File file = new File();
        file.setFileRelationId(customerId);
        BeanUtils.copyProperties(sysFileVo, file);
        return fileService.insertSysFile(file, customerId);
    }

    @GetMapping("/getFileSecauthority")
    public BaseResponse getFileSecauthority(@RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestParam("id") String id, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:getFileSecauthority");
        String customerId = redisUtils.getCustomerId(token);
        String uId = redisUtils.getUid(token);
        return fileService.getFileSecauthority(id, tCode, uId, customerId, year);
    }

    @GetMapping("/judgeNodeApproved")
    public BaseResponse judgeNodeApproved(@RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestParam("fileId") String fileId, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:judgeNodeApproved");
        String customerId = redisUtils.getCustomerId(token);
        return fileService.judgeNodeApproved(fileId, tCode, customerId, year);
    }


    @GetMapping("/updateSecBiddingProcessing")
    public BaseResponse getFileSecauthority(@RequestParam(value = "tCode", required = false) String tCode, @RequestParam("id") String id, @RequestParam("secId") String secId, @RequestParam("states") Integer states, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:updateSecBiddingProcessing");
        String customerId = redisUtils.getCustomerId(token);
        String uId = redisUtils.getUid(token);
        return secBiddingProcessingService.updateSecBiddingProcessing(id, secId, tCode, states, customerId);
    }

    @GetMapping("/updateOtherSecBiddingProcessing")
    public BaseResponse updateOtherSecBiddingProcessing(@RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestParam("id") String id, @RequestParam("states") Integer states, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:updateOtherSecBiddingProcessing");
        String customerId = redisUtils.getCustomerId(token);
        String uId = redisUtils.getUid(token);
        return secBiddingProcessingService.updateOtherSecBiddingProcessing(id, tCode, states, customerId, year);
    }

    @PostMapping("/getSysProcessInvalidationRecordList")
    public BaseResponse getSysProcessInvalidationRecordList(@RequestBody SysProcessInvalidationRecordDto request) {
        return BaseResponse.ok(sysProcessInvalidationRecordService.getSysProcessInvalidationRecordList(request));
    }

    @GetMapping("/updatePageCodeInFile")
    public BaseResponse updatePageCodeInFile(@RequestParam(value = "tCode", required = false) String tCode, @RequestParam(value = "year", required = false) String year, @RequestParam("id") String id, @RequestParam("pageCode") String pageCode, @RequestHeader(value = "token", required = false) String token) {
        log.info("SysFile:updatePageCodeInFile");
        return fileService.updatePageCodeInFile(id, pageCode, tCode, year);
    }

    @PostMapping("/getApprovalProgressByFileIds")
    public BaseResponse getApprovalProgressByFileIds(@RequestBody List<String> ids, @RequestParam(value = "actCode", required = false) String actCode, @RequestParam("year") String year){
        log.info("SysFile:updatePageCodeInFile");
        return fileService.getApprovalProgressByFileIds(ids, actCode, year);
    }
}
