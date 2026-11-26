package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/18 14:00
 */
@Slf4j
@RestController
@RequestMapping("/v1/invBudgetDeclareDtl")
public class InvBudgetDeclareDtlController {

    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;

    /**
     * 项目ID查询审批结束的概算申报清单树
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvBudgetDeclareDtlTreeByPid")
    public BaseResponse getInvBudgetDeclareDtlTreeByPid(@RequestParam String pId) {
        return invBudgetDeclareDtlService.getInvBudgetDeclareDtlTreeByPid(pId);
    }


    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvBudgetDeclareDtlListByPid")
    public BaseResponse getInvBudgetDeclareDtlListByPid(@RequestParam String pId) {
        return invBudgetDeclareDtlService.getInvBudgetDeclareDtlListByPid(pId);
    }


    @GetMapping("/getInvBudgetConPayInfo")
    public BaseResponse getInvBudgetConPayInfo(@RequestParam String pId) {
        return invBudgetDeclareDtlService.getInvBudgetConPayInfo(pId);
    }

}
