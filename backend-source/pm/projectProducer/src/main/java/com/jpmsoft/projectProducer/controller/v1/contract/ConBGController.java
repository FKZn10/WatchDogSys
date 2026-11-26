package com.jpmsoft.projectProducer.controller.v1.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.service.contract.IBGConUnitOrderService;
import com.jpmsoft.projectProducer.service.contract.IBGContactOrderService;
import com.jpmsoft.projectProducer.service.contract.IBGDeclarationService;
import com.jpmsoft.projectProducer.service.contract.IBGVisaOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/conBG")
public class ConBGController {
    @Resource
    private IBGConUnitOrderService BGConUnitOrderService;

    @Resource
    private IBGDeclarationService BGDeclarationService;

    @Resource
    private IBGContactOrderService BGContactOrderService;

    @Resource
    private IBGVisaOrderService BGVisaOrderService;


    @TenantId
    @PostMapping("/SetContactOrderBL")
    public BaseResponse SetContactOrderBL(@RequestBody ProBGRequest request) {

        String BGMoney1 = BGContactOrderService.SetContactOrderBL(request);

        return BaseResponse.ok("ok");
    }

    @TenantId
    @PostMapping("/SetDeclarationBL")
    public BaseResponse SetDeclarationBL(@RequestBody ProBGRequest request) {

        String BGMoney1 = BGDeclarationService.SetDeclarationBL(request);

        return BaseResponse.ok("ok");
    }

    @TenantId
    @PostMapping("/SetConUnitOrderBL")
    public BaseResponse SetConUnitOrderBL(@RequestBody ProBGRequest request) {

        String BGMoney1 = BGConUnitOrderService.SetConUnitOrderBL(request);

        return BaseResponse.ok("ok");
    }

    @TenantId
    @PostMapping("/getLJBGMoney")
    public BaseResponse getLJBGMoney(@RequestBody ProBGRequest request) {
//        BigDecimal Sum = new BigDecimal("0.0");
//
//        BigDecimal BGMoney1 = BGConUnitOrderService.getLJBGMoney(request);
//
//        BigDecimal BGMoney2 = BGContactOrderService.getLJBGMoney(request);
//
//        BigDecimal BGMoney3 = BGDeclarationService.getLJBGMoney(request);
//
//        BigDecimal BGMoney4 = BGVisaOrderService.getLJBGMoney(request);
//        if (BGMoney1 == null) BGMoney1 = new BigDecimal("0.0");
//        if (BGMoney2 == null) BGMoney2 = new BigDecimal("0.0");
//        if (BGMoney3 == null) BGMoney3 = new BigDecimal("0.0");
//        if (BGMoney4 == null) BGMoney4 = new BigDecimal("0.0");
//        Sum = Sum.add(BGMoney1);
//        Sum = Sum.add(BGMoney2);
//        Sum = Sum.add(BGMoney3);
//        Sum = Sum.add(BGMoney4);

        //DecimalFormat df = new DecimalFormat("0.00");
        //df.format(BGMoneyTotal)
        Map<String, BigDecimal> contractChangeAmountSumMap = BGContactOrderService.getContractChangeAmountSum(Arrays.asList(request.getConId()));
        return BaseResponse.ok(contractChangeAmountSumMap.get(request.getConId()));
    }

    @TenantId
    @PostMapping("/getBGListByConId")
    public BaseResponse getBGListByConId(@RequestBody ProBGRequest request) {

        if (ValidateUtil.isEmpty(request.getConId()))
            return BaseResponse.fail("请先选择相应合同内容！");
        if (ValidateUtil.isEmpty(request.getPayType()))
            return BaseResponse.fail("请先选择联系事项类型！");


        if (request.getPayType().equals("指令单")) {
            List<ProBGDTO> BGConUnitOrderVO = BGConUnitOrderService.getBGConUnitOrderListByConId(request);

            IPage<ProBGDTO> page = new Page<>(request.getPage(), request.getSize());
            return BaseResponse.ok(BGConUnitOrderVO, Long.valueOf(page.getTotal()).intValue());
        } else if (request.getPayType().equals("变更单")) {
            List<ProBGDTO> BGDeclarationVO = BGDeclarationService.getBGDeclarationListByConId(request);
            IPage<ProBGDTO> page = new Page<>(request.getPage(), request.getSize());
            return BaseResponse.ok(BGDeclarationVO, Long.valueOf(page.getTotal()).intValue());
        } else {
            return BaseResponse.fail("请选择正确的联系事项类型！");
        }
    }


    @TenantId
    @PostMapping("/getBGListByType")
    public BaseResponse getBGListByType(@RequestBody ProBGRequest request) {

        if (ValidateUtil.isEmpty(request.getPayType().isEmpty()))
            return BaseResponse.fail("请先选择联系事项类型！");
        if (request.getPayType().equals("单位指令单")) {
            Page<ProBGDTO> BGConUnitOrderVO = BGConUnitOrderService.getBGConUnitOrderListByType(request);
            return BaseResponse.ok(BGConUnitOrderVO.getRecords(), Long.valueOf(BGConUnitOrderVO.getTotal()).intValue());
        } else if (request.getPayType().equals("工程变更审批单")) {
            Page<ProBGDTO> BGDeclarationVO = BGDeclarationService.getBGDeclarationListByType(request);
            return BaseResponse.ok(BGDeclarationVO.getRecords(), Long.valueOf(BGDeclarationVO.getTotal()).intValue());
        } else if (request.getPayType().equals("工程联系单")) {
            Page<ProBGDTO> BGContactOrderVO = BGContactOrderService.getBGContactOrderListByType(request);
            return BaseResponse.ok(BGContactOrderVO.getRecords(), Long.valueOf(BGContactOrderVO.getTotal()).intValue());
        } else {
            return BaseResponse.fail("请选择正确的联系事项类型！");
        }
    }

    /**
     * 获取指定联系事项被使用的金额比例和
     *
     * @param contactItemId
     * @return
     */
    @GetMapping("/getSumOfContactItemRateByContactItemId")
    public BaseResponse getSumOfContactItemRateByContactItemId(String contactItemId) {
        BigDecimal rate = BGContactOrderService.getSumOfContactItemRateByContactItemId(contactItemId);
        return BaseResponse.ok(rate);
    }

    /**
     * 获取合同累计变更金额和（统计工程变更申报、建设单位指令单、工程量签证、工程联系单）
     * @param contractId
     * @return
     */
    @GetMapping("/getContractChangeAmountSum")
    public BaseResponse getContractChangeAmountSum(String contractId) {
        Map<String,BigDecimal> contractChangeAmountSumMap = BGContactOrderService.getContractChangeAmountSum(Arrays.asList(contractId));
        return BaseResponse.ok(contractChangeAmountSumMap.get(contractId));
    }

}
