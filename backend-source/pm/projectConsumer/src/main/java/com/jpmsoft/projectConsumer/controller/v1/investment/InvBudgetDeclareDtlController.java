package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/18 14:00
 */
@Slf4j
@RestController
@Api(tags = "概算申报清单", value = "概算申报清单")
@RequestMapping("/v1/invBudgetDeclareDtl")
public class InvBudgetDeclareDtlController {

    @Autowired
    private InvFeign invFeign;

    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目ID查询审批结束的概算申报清单树")
    @GetMapping("/getInvBudgetDeclareDtlTreeByPid")
    public BaseResponse getInvBudgetDeclareDtlTreeByPid(@RequestParam String pId) {
        return invFeign.getInvBudgetDeclareDtlTreeByPid(pId);
    }


    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目ID查询审批结束的概算申报清单列表")
    @GetMapping("/getInvBudgetDeclareDtlListByPid")
    public BaseResponse getInvBudgetDeclareDtlListByPid(@RequestParam String pId) {
        return invFeign.getInvBudgetDeclareDtlListByPid(pId);
    }

    @ApiOperation(value = "项目ID查询审批结束的概算申报清单列表以及合同金额，合同支付金额")
    @GetMapping("/getInvBudgetConPayInfo")
    public BaseResponse getInvBudgetConPayInfo(@RequestParam String pId) {
        return invFeign.getInvBudgetConPayInfo(pId);
    }

}
