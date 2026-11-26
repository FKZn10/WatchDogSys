package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更事项申请", value = "变更事项申请")
@RequestMapping("/v1/conEc")
public class ConEcController {
    @Autowired
    private ConFeign conFeign;


    /**
     * 新增或修改变更事项申请
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改变更事项申请")
    @PostMapping("/createOrUpdateConEc")
    public BaseResponse createOrUpdateConEc(@RequestBody ConEcRequest request) {
        log.info("createOrUpdateConEc");
        return conFeign.createOrUpdateConEc(request);
    }

    @ApiOperation(value = "修改变更事项申请部分字段")
    @PostMapping("/updateConEc")
    public BaseResponse updateConEc(@RequestBody ConEcRequest request) {
        log.info("updateConEc");
        return conFeign.updateConEc(request);
    }

    /**
     * ID删除
     *
     * @param ecId
     * @return
     */
    @ApiOperation(value = "ID删除变更事项申请")
    @GetMapping("/deleteConEcByEcId")
    public BaseResponse deleteConEcByEcId(@RequestParam String ecId) {
        log.info("deleteConEcByEcId");
        return conFeign.deleteConEcByEcId(ecId);
    }

    @ApiOperation(value = "ID失效变更事项申请")
    @GetMapping("/invalidConEcByEcId")
    public BaseResponse invalidConEcByEcId(@RequestParam String ecId) {
        log.info("invalidConEcByEcId");
        return conFeign.invalidConEcByEcId(ecId);
    }
    @ApiOperation(value = "ID查询变更事项申请")
    @GetMapping("/getConEcByEcId")
    public BaseResponse getConEcByEcId(@RequestParam String ecId) {
        log.info("getConEcByEcId");
        return conFeign.getConEcByEcId(ecId);
    }

    @ApiOperation(value = "合同ID查询正负变更累计额")
    @GetMapping("/getConEcCountByConId")
    public BaseResponse getConEcCountByConId(@RequestParam String conId) {
        log.info("getConEcCountByConId");
        return conFeign.getConEcCountByConId(conId);
    }

    @ApiOperation(value = "合同ID查询变更事项申请")
    @GetMapping("/getConEcByConId")
    public BaseResponse getConEcByConId(@RequestParam String conId) {
        log.info("getConEcByConId");
        return conFeign.getConEcByConId(conId);
    }

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "变更事项申请列表分页查询")
    @PostMapping("/getConEcList")
    public BaseResponse getConEcList(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcList");
        return conFeign.getConEcList(request, token);
    }

    @PostMapping("/getConEcListClasInfo")
    public BaseResponse getConEcListClasInfo(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcListClasInfo");
        return conFeign.getConEcListClasInfo(request, token);
    }

    @ApiOperation(value = "变更事项申请列表分页查询(合同权限配置过滤)")
    @PostMapping("/getConEcListByPermission")
    public BaseResponse getConEcListByPermission(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcListByPermission");
        return conFeign.getConEcListByPermission(request, token);
    }

    @ApiOperation(value = "分页查询变更事项申请台账")
    @PostMapping("/getConEcStandingBook")
    public BaseResponse getConEcStandingBook(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcStandingBook");
        return conFeign.getConEcStandingBook(request, token);
    }

    @ApiOperation(value = "变更事项申请列表分页查询-不鉴权")
    @PostMapping("/getConEcListNoAuth")
    public BaseResponse getConEcListNoAuth(@RequestBody ConEcRequest request) {
        log.info("getConEcListNoAuth");
        return conFeign.getConEcListNoAuth(request);
    }

    @ApiOperation(value = "审批通过作废关联的前变更")
    @GetMapping("/approve2CancelPreEc")
    public BaseResponse approve2CancelPreEc(@RequestParam String ecId) {
        log.info("approve2CancelPreEc");
        return conFeign.approve2CancelPreEc(ecId);
    }

    @GetMapping("/processSync/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid){
        return conFeign.processSynConEc(uuid);
    }

    @GetMapping("/changeTtems")
    public BaseResponse changeTtems(@RequestParam String conId){
        log.info("changeTtems");
        return conFeign.changeTtems(conId);
    }

    @PostMapping("/saveBgzl")
    public BaseResponse saveBgzl(@RequestBody ConEcRequest request){
        return conFeign.saveBgzl(request);
    }

    @PostMapping("/isMajorChangeTtems")
    public BaseResponse isMajorChangeTtems(@RequestBody ConEcRequest request){
        return conFeign.isMajorChangeTtems(request);
    }

    @PostMapping("/checkClzgAndZlje")
    public BaseResponse checkClzgAndZlje(@RequestBody ConEcRequest request){
        return conFeign.checkClzgAndZlje(request);
    }

    /**
     * 获取合同累计变更金额统计
     *
     * @param conId
     * @return
     */
    @GetMapping("/getMoneyStatistics")
    public BaseResponse getMoneyStatistics(@RequestParam String conId, @RequestParam String itemCategory){
        log.info("getMoneyStatistics");
        return conFeign.getMoneyStatistics(conId, itemCategory);
    }
}