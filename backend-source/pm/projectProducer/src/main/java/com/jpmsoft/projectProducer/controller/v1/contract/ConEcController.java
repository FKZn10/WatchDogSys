package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conEc")
public class ConEcController {

    @Resource
    private IConEcService conEcService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改变更事项申请
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateConEc")
    public BaseResponse createOrUpdateConEc(@RequestBody ConEcRequest request) {
        log.info("createOrUpdateConEc");
        return conEcService.createOrUpdateConEc(request);
    }

    @PostMapping("/updateConEc")
    public BaseResponse updateConEc(@RequestBody ConEcRequest request) {
        log.info("updateConEc");
        return conEcService.updateConEc(request);
    }

    /**
     * ID删除
     *
     * @param ecId
     * @return
     */
    @GetMapping("/deleteConEcByEcId")
    public BaseResponse deleteConEcByEcId(@RequestParam String ecId) {
        log.info("deleteConEcByEcId");
        return conEcService.deleteConEcByEcId(ecId);
    }
    @GetMapping("/invalidConEcByEcId")
    public BaseResponse invalidConEcByEcId(@RequestParam String ecId) {
        log.info("invalidConEcByEcId");
        return conEcService.invalidConEcByEcId(ecId);
    }

    @GetMapping("/getConEcByEcId")
    public BaseResponse getConEcByEcId(@RequestParam String ecId) {
        log.info("getConEcByEcId");
        return conEcService.getConEcByEcId(ecId);
    }

    @GetMapping("/getConEcCountByConId")
    public BaseResponse getConEcCountByConId(@RequestParam String conId) {
        log.info("getConEcCountByConId");
        return conEcService.getConEcCountByConId(conId);
    }

    @GetMapping("/getConEcByConId")
    public BaseResponse getConEcByConId(@RequestParam String conId) {
        log.info("getConEcByConId");
        return conEcService.getConEcByConId(conId);
    }

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getConEcList")
    public BaseResponse getConEcList(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcList");
        return conEcService.getConEcList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getConEcListClasInfo")
    public BaseResponse getConEcListClasInfo(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcListClasInfo");
        return conEcService.getConEcListClasInfo(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcListByPermission")
    public BaseResponse getConEcListByPermission(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcListByPermission");
        return conEcService.getConEcListByPermission(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcStandingBook")
    public BaseResponse getConEcStandingBook(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcStandingBook");
        return conEcService.getConEcStandingBook(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConEcListNoAuth")
    public BaseResponse getConEcListNoAuth(@RequestBody ConEcRequest request) {
        log.info("getConEcListNoAuth");
        return conEcService.getConEcListNoAuth(request);
    }

    @GetMapping("/approve2CancelPreEc")
    public BaseResponse approve2CancelPreEc(@RequestParam  String ecId) {
        log.info("approve2CancelPreEc");
        return conEcService.approve2CancelPreEc(ecId);
    }

    @TenantId
    @GetMapping("/processSync/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid){
        conEcService.processSyn(uuid);
        return BaseResponse.ok();
    }


    @GetMapping("/changeTtems")
    public BaseResponse changeTtems(@RequestParam String conId){
       return conEcService.changeTtems(conId);
    }


    @PostMapping("/saveBgzl")
    public BaseResponse saveBgzl(@RequestBody ConEcRequest request){
        return conEcService.saveBgzl(request);
    }

    /**
     * 判断是否重大变更
     * @param request
     */
    @PostMapping("/isMajorChangeTtems")
    public BaseResponse isMajorChangeTtems(@RequestBody ConEcRequest request){
        return conEcService.isMajorChangeTtems(request);
    }

    /**
     * 根据合同ID 校验此变更的暂列金 和 暂估价 的累计 是否超出 合同的 暂列金额 和 专业工程暂估价
     * @param request
     */
    @PostMapping("/checkClzgAndZlje")
    public BaseResponse checkClzgAndZlje(@RequestBody ConEcRequest request){
        return conEcService.checkClzgAndZlje(request);
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
        return conEcService.getMoneyStatistics(conId, itemCategory);
    }
}