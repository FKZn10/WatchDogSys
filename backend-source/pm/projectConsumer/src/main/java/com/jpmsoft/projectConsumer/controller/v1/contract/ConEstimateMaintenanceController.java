package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "合同概算维护", value = "合同概算维护")
@RequestMapping("/v1/conEstimateMaintenance")
public class ConEstimateMaintenanceController {
    @Resource
    PreFeign preFeign;


    /**
     * 新增或修改合同概算维护
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改合同概算维护")
    @PostMapping("/createOrUpdateConEstimateMaintenance")
    public BaseResponse createOrUpdateConEstimateMaintenance(@RequestBody ConEstimateMaintenanceRequest request) {
        log.info("createOrUpdateConEstimateMaintenance");
        return preFeign.createOrUpdateConEstimateMaintenance(request);
    }



    @ApiOperation(value = "获取合同概算维护列表")
    @PostMapping("/getConEstimateMaintenanceList")
    public BaseResponse getConEstimateMaintenanceList(@RequestBody ConEstimateMaintenanceRequest request, @RequestHeader String token) {
        log.info("getConEstimateMaintenanceList");
        return preFeign.getConEstimateMaintenanceList(request,token);
    }

    @ApiOperation(value = "根据ID获取合同概算维护信息")
    @GetMapping("/getConEstimateMaintenanceById")
    public BaseResponse getConEstimateMaintenanceById(@RequestParam String cemId)  {
        log.info("getConEstimateMaintenanceById");
        return preFeign.getConEstimateMaintenanceById(cemId);
    }

    @ApiOperation(value = "根据合同ID标段ID获取原先合同关联的概算等信息")
    @GetMapping("/getConEstimateMaintenanceByConIdAndSecId")
    public BaseResponse getConEstimateMaintenanceByConIdAndSecId(@RequestParam String conId,@RequestParam String secId)  {
        log.info("getConEstimateMaintenanceByConIdAndSecId");
        return preFeign.getConEstimateMaintenanceByConIdAndSecId(conId,secId);
    }

    @ApiOperation(value = "根据项目ID、类型获取概算等信息")
    @GetMapping("/getConEstimateMaintenanceByIds")
    public BaseResponse getConEstimateMaintenanceByIds(@RequestParam String pId,@RequestParam String type)  {
        log.info("getConEstimateMaintenanceByIds");
        return preFeign.getConEstimateMaintenanceByIds(pId,type);
    }

    @ApiOperation(value = "审批过程中回调")
    @PostMapping("/updateConEstimate")
    public BaseResponse updateConEstimate(@RequestBody ConEstimateMaintenanceRequest request)  {
        log.info("updateConEstimate");
        return preFeign.updateConEstimate(request);
    }


    @ApiOperation(value = "根据项目ID获取该项目下概算/估算/匡算的总额")
    @GetMapping("/getConEstimateIsOver")
    public BaseResponse getConEstimateIsOver(String pId,String secId)  {
        log.info("getConEstimateIsOver");
        return preFeign.getConEstimateIsOver(pId,secId);
    }

    @ApiOperation(value = "根据项目ID获取投资概算统计表")
    @GetMapping("/getInvestmentTableByPId")
    public BaseResponse getInvestmentTableByPId(String pId)  {
        log.info("getInvestmentTableByPId");
        return preFeign.getInvestmentTableByPId(pId);
    }

    @ApiOperation(value = "根据项目ID获取投资概算统计二表")
    @GetMapping("/getInvestmentTableSecondByPId")
    public BaseResponse getInvestmentTableSecondByPId(String pId)  {
        log.info("getInvestmentTableSecondByPId");
        return preFeign.getInvestmentTableSecondByPId(pId);
    }
}
