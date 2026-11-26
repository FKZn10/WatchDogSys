package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConEstimateMaintenanceService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;


/**
 * 合同概算维护表
 * */
@Slf4j
@RestController
@RequestMapping("/v1/conEstimateMaintenance")
public class ConEstimateMaintenanceController {
    @Resource
    private IConEstimateMaintenanceService conEstimateMaintenanceService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改合同概算维护
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateConEstimateMaintenance")
    public BaseResponse createOrUpdateConEstimateMaintenance(@RequestBody ConEstimateMaintenanceRequest request) {
        log.info("createOrUpdateConEstimateMaintenance");
        return conEstimateMaintenanceService.createOrUpdateConEstimateMaintenance(request);
    }


    @TenantId
        @PostMapping("/getConEstimateMaintenanceList")
    public BaseResponse getConEstimateMaintenanceList(@RequestBody ConEstimateMaintenanceRequest request, @RequestHeader String token) {
        log.info("getConEstimateMaintenanceList");
        return conEstimateMaintenanceService.getConEstimateMaintenanceList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConEstimateMaintenanceById")
    public BaseResponse getConEstimateMaintenanceById(@RequestParam String cemId) {
        log.info("getConEstimateMaintenanceById");
        return conEstimateMaintenanceService.getConEstimateMaintenanceById(cemId);
    }

    ///根据合同、标段获取集合
    @GetMapping("/getConEstimateMaintenanceByConIdAndSecId")
    public BaseResponse getConEstimateMaintenanceByConIdAndSecId(@RequestParam String conId,@RequestParam String secId) {
        log.info("getConEstimateMaintenanceByConIdAndSecId");
        return conEstimateMaintenanceService.getConEstimateMaintenanceByConIdAndSecId(conId,secId);
    }

    // 根据项目ID、合同ID、标段ID、类型
    @GetMapping("/getConEstimateMaintenanceByIds")
    public BaseResponse getConEstimateMaintenanceByIds(@RequestParam String pId,@RequestParam String type) {
        log.info("getConEstimateMaintenanceByIds");
        return conEstimateMaintenanceService.getConEstimateMaintenanceByIds(pId,type);
    }

    @PostMapping("/updateConEstimate")
    public BaseResponse updateConEstimate(@RequestBody ConEstimateMaintenanceRequest request) {
        log.info("updateConEstimate");
        return conEstimateMaintenanceService.updateConEstimate(request);
    }


    /**
     * 根据项目ID获取该项目下概算/估算/匡算的总额
     */
    @GetMapping("/getConEstimateIsOver")
    public BaseResponse getConEstimateIsOver(@RequestParam String pId,@RequestParam String secId) {
        log.info("getConEstimateMaintenanceByIds");
        return conEstimateMaintenanceService.getConEstimateIsOver(pId,secId);
    }

    @GetMapping("/getInvestmentTableByPId")
    public BaseResponse getInvestmentTableByPId(@RequestParam String pId) {
        log.info("getInvestmentTableByPId");
        return conEstimateMaintenanceService.getInvestmentTableByPId(pId);
    }

    @GetMapping("/getInvestmentTableSecondByPId")
    public BaseResponse getInvestmentTableSecondByPId(@RequestParam String pId) {
        log.info("getInvestmentTableSecondByPId");
        return conEstimateMaintenanceService.getInvestmentTableSecondByPId(pId);
    }
}
