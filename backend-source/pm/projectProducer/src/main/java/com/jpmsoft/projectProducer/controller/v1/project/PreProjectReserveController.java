package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveDTO;
import com.jpmsoft.projectProducer.service.project.IPreProjectReserveDtlService;
import com.jpmsoft.projectProducer.service.project.IPreProjectReserveService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 城建项目储备
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:55
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectReserve")
public class PreProjectReserveController {
    @Resource
    private IPreProjectReserveService preProjectReserveService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IPreProjectReserveDtlService preProjectReserveDtlService;

    @TenantId
    @PostMapping("/createOrUpdateProjectReserve")
    public BaseResponse createOrUpdateProjectReserve(@RequestBody PreProjectReserveDTO request) {
        log.info("createOrUpdateProjectReserve");
        return preProjectReserveService.createOrUpdateProjectReserve(request);
    }

    @TenantId
    @PostMapping("/getProjectReserveList")
    public BaseResponse getProjectReserveList(@RequestBody PreProjectReserveDTO request,@RequestHeader String token) {
        log.info("getProjectReserveList");
        return preProjectReserveService.getProjectReserveList(request,redisUtils.getUid(token));
    }

    @GetMapping("/getProjectReserveById")
    public BaseResponse getProjectReserveById(@RequestParam String id) {
        log.info("getProjectReserveById");
        return preProjectReserveService.getProjectReserveById(id);
    }


    @GetMapping("/deleteProjectReserveById")
    public BaseResponse deleteProjectReserveById(@RequestParam String id)
    {
        log.info("deleteProjectReserveById");
        return preProjectReserveService.deleteProjectReserveById(id);
    }
    @GetMapping("/updateReserveStatus")
    public BaseResponse updateReserveStatus(@RequestParam String id,@RequestParam String status, @RequestParam(required = false) String pId) {
        log.info("updateReserveStatus");
        return preProjectReserveService.updateReserveStatus(id,status,pId);
    }

    @PostMapping("/queryReserveListClassInfo")
    public BaseResponse queryReserveListClassInfo(@RequestBody PreProjectReserveDTO request,@RequestHeader String token) {
        log.info("queryReserveListClassInfo");
        return preProjectReserveService.queryReserveListClassInfo(request,redisUtils.getUid(token));
    }

    @GetMapping("/getProjectReserveDtlById")
    public  BaseResponse getProjectReserveDtlById(@RequestParam String id ){
        PreProjectReserveChangeDTO reserveDtl = preProjectReserveDtlService.getpreProjectReservePprByIdData(id);
         return  BaseResponse.ok(reserveDtl);
    }

    @GetMapping("/syncProjectReserveToProject")
    public BaseResponse syncProjectReserveToProject(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("syncProjectReserveToProject");
        return preProjectReserveService.syncProjectReserveToProject(id, token);
    }

    @GetMapping("/changeReserveCodeToProjectCode")
    public BaseResponse changeReserveCodeToProjectCode(@RequestParam("pId") String pId, @RequestHeader String token) {
        log.info("changeReserveCodeToProjectCode");
        return preProjectReserveService.changeReserveCodeToProjectCode(pId, token);
    }

    /**
     * 在建设模式变更时刷新项目储备编码
     *
     * @param id
     * @param oldPprModel
     * @return
     */
    @GetMapping("/refreshProjectReserveCode")
    public BaseResponse refreshProjectReserveCode(@RequestParam("id") String id, @RequestParam("oldPprModel") String oldPprModel) {
        log.info("refreshProjectReserveCode");
        return preProjectReserveService.refreshProjectReserveCode(id, oldPprModel);
    }
}
