package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.MultiContractConEcRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 多合同变更/签证 控制器
 */
@RestController
@RequestMapping("/v1/multiContractConEc")
public class MultiContractConEcController {

    @Autowired
    private ConFeign conFeign;

    /**
     * 列表
     *
     * @param pageNum
     * @param pageSize
     * @param projectName
     * @return
     */
    @GetMapping("/page")
    public BaseResponse page(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String projectName,
                             @RequestParam(required = false) String pId,
                             @RequestParam(defaultValue = "1") String ecChangeType,@RequestHeader String token) {
        return conFeign.pageMultiContractConEc(pageNum, pageSize, projectName, pId,ecChangeType,token);
    }

    // 详情
    @GetMapping("/detail/{uuid}")
    public BaseResponse detail(@PathVariable("uuid") String uuid, @RequestHeader String token) {
        return conFeign.detailMultiContractConEc(uuid, token);
    }

    // 获取项目累计变更签证金额
    @GetMapping("/getProjectTotalConEcMoney")
    public BaseResponse getProjectTotalConEcMoney(String projectId, String contractId, String ecChangeType) {
        return conFeign.getProjectTotalConEcMoney(projectId, ecChangeType, contractId);
    }

    /**
     * 获取合同已审批的设计变更、签证历史及主流程审批时间。
     *
     * @param projectId   项目ID
     * @param contractId  合同ID
     * @param excludeEcId 排除的当前子表记录ID，可为空
     * @param token       当前登录令牌
     * @return 合同历史金额、余额明细
     */
    @GetMapping("/getContractChangeHistory")
    public BaseResponse getContractChangeHistory(
            @RequestParam("projectId") String projectId,
            @RequestParam("contractId") String contractId,
            @RequestParam(value = "excludeEcId", required = false) String excludeEcId,
            @RequestHeader("token") String token) {
        return conFeign.getContractChangeHistory(projectId, contractId, excludeEcId, token);
    }

    // 根据项目ID汇总审批结束的设计变更申报金额
    @GetMapping("/getFinishedDesignChangeApplyMoneyByProjectId")
    public BaseResponse getFinishedDesignChangeApplyMoneyByProjectId(
            @RequestParam("projectId") String projectId,
            @RequestHeader("token") String token) {
        return conFeign.getFinishedDesignChangeApplyMoneyByProjectId(projectId, token);
    }

    // 创建
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody MultiContractConEcRequest multiContractConEcRequest) {
        return conFeign.createOrUpdateMultiContractConEc(multiContractConEcRequest);
    }

    // 流程回调
    @GetMapping("/processSyn/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid) {
        return conFeign.processSynMultiContractConEc(uuid);
    }

    @PostMapping("/syncChildState")
    public BaseResponse syncChildState(@RequestParam("id") String id,
                                       @RequestParam("type") String type) {
        return conFeign.syncMultiContractConEcChildState(id, type);
    }

    // 删除?

    // 作废？
}
