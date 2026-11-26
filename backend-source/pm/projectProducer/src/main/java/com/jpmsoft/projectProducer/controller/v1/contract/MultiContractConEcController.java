package com.jpmsoft.projectProducer.controller.v1.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.MultiContractConEcRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.MultiContractConEc;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcDetailVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcPageVO;
import com.jpmsoft.projectProducer.service.contract.IMultiContractConEcService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 多合同变更/签证 控制器
 */
@RestController
@RequestMapping("/v1/multiContractConEc")
public class MultiContractConEcController {

    @Resource
    private IMultiContractConEcService multiContractConEcService;

    @Resource
    private RedisUtils redisUtils;

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
        Page<MultiContractConEcPageVO> multiContractConEcPageVOPage = multiContractConEcService.page(pageNum, pageSize, projectName, pId, ecChangeType,redisUtils.getUid(token));
        return BaseResponse.ok(multiContractConEcPageVOPage.getRecords(), (int) multiContractConEcPageVOPage.getTotal());
    }

    // 详情
    @GetMapping("/detail/{uuid}")
    public BaseResponse detail(@PathVariable("uuid") String uuid, @RequestHeader String token) {
        MultiContractConEcDetailVO multiContractConEcDetailVO = multiContractConEcService.detail(uuid, redisUtils.getUid(token));
        return BaseResponse.ok(multiContractConEcDetailVO);
    }

    // 获取项目累计变更签证金额
    @GetMapping("/getProjectTotalConEcMoney")
    public BaseResponse getProjectTotalConEcMoney(String projectId, String ecChangeType, String contractId) {
        BigDecimal projectTotalConEcMoney = multiContractConEcService.getProjectTotalConEcMoney(projectId, ecChangeType, contractId);
        return BaseResponse.ok(projectTotalConEcMoney);
    }

    /**
     * 查询合同已审批的设计变更、签证价款明细，供表单计算累计金额和可用余额。
     *
     * @param projectId   项目ID
     * @param contractId  合同ID
     * @param excludeEcId 排除的当前子表记录ID，可为空
     * @param token       当前登录令牌
     * @return 按主流程审批时间降序排列的历史明细
     */
    @GetMapping("/getContractChangeHistory")
    public BaseResponse getContractChangeHistory(
            @RequestParam("projectId") String projectId,
            @RequestParam("contractId") String contractId,
            @RequestParam(value = "excludeEcId", required = false) String excludeEcId,
            @RequestHeader("token") String token) {
        return BaseResponse.ok(multiContractConEcService.getContractChangeHistory(
                projectId, contractId, excludeEcId, redisUtils.getUid(token)));
    }

    // 根据项目ID汇总审批结束的设计变更申报金额
    @GetMapping("/getFinishedDesignChangeApplyMoneyByProjectId")
    public BaseResponse getFinishedDesignChangeApplyMoneyByProjectId(
            @RequestParam("projectId") String projectId,
            @RequestHeader("token") String token) {
        BigDecimal totalMoney = multiContractConEcService.getFinishedDesignChangeApplyMoneyByProjectId(
                projectId, redisUtils.getUid(token));
        return BaseResponse.ok(totalMoney);
    }

    // 创建
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody MultiContractConEcRequest multiContractConEcRequest) {
        MultiContractConEc multiContractConEc = multiContractConEcService.createOrUpdate(multiContractConEcRequest);
        return BaseResponse.ok(multiContractConEc);
    }

    // 流程回调
    @GetMapping("/processSyn/{uuid}")
    public BaseResponse processSyn(@PathVariable("uuid") String uuid) {
        multiContractConEcService.processSyn(uuid);
        return BaseResponse.ok();
    }

    @PostMapping("/syncChildState")
    public BaseResponse syncChildState(@RequestParam("id") String id,
                                       @RequestParam("type") String type) {
        return BaseResponse.ok(multiContractConEcService.syncChildState(id, type));
    }

    // 删除?

    // 作废？
}
