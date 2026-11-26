package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "支付申请", value = "支付申请")
@RequestMapping("/v1/conBG")
public class ProBGController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "变更金额累计(该合同的累计变更金额)")
    @PostMapping("/getLJBGMoney")
    public BaseResponse getLJBGMoney(@RequestBody ProBGRequest request) {
        log.info("getLJBGMoney");
        return conFeign.getLJBGMoney(request);
    }

    @ApiOperation(value = "根据合同id获取相应数据（指令单、变更单）")
    @PostMapping("/getBGListByConId")
    public BaseResponse getBGListByConId(@RequestBody ProBGRequest request) {
        log.info("getBGListByConId");
        return conFeign.getBGListByConId(request);
    }

    @ApiOperation(value = "根据类型获取相应数据（指令单、变更单）")
    @PostMapping("/getBGListByType")
    public BaseResponse getBGListByType(@RequestBody ProBGRequest request) {
        log.info("getBGListByType");
        return conFeign.getBGListByType(request);
    }

    @ApiOperation(value = "设置指令单初始比例为100")
    @PostMapping("/SetConUnitOrderBL")
    public BaseResponse SetConUnitOrderBL(@RequestBody ProBGRequest request) {
        log.info("SetConUnitOrderBL");
        return conFeign.SetConUnitOrderBL(request);
    }


    @ApiOperation(value = "设置变更单初始比例为100")
    @PostMapping("/SetDeclarationBL")
    public BaseResponse SetDeclarationBL(@RequestBody ProBGRequest request) {
        log.info("SetConUnitOrderBL");
        return conFeign.SetDeclarationBL(request);
    }

    @ApiOperation(value = "流程审批结束后，设置选中的联系单，调整其剩余比例")
    @PostMapping("/SetContactOrderBL")
    public BaseResponse SetContactOrderBL(@RequestBody ProBGRequest request) {
        log.info("SetContactOrderBL");
        return conFeign.SetContactOrderBL(request);
    }

    /**
     * 获取指定联系事项被使用的金额比例和
     * @param contactItemId
     * @return
     */
    @GetMapping("/getSumOfContactItemRateByContactItemId")
    public BaseResponse getSumOfContactItemRateByContactItemId(String contactItemId){
        return conFeign.getSumOfContactItemRateByContactItemId(contactItemId);
    }

}
