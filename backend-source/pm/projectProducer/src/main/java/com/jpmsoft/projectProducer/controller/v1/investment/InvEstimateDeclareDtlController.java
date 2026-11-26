package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IInvEstimateDeclareDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invEstimateDeclareDtl")
public class InvEstimateDeclareDtlController {

    @Resource
    private IInvEstimateDeclareDtlService invEstimateDeclareDtlService;

    /**
     * 项目ID查询审批结束的估算申报清单树
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvEstimateDeclareDtlTreeByPid")
    public BaseResponse getInvEstimateDeclareDtlTreeByPid(@RequestParam String pId) {
        return invEstimateDeclareDtlService.getInvEstimateDeclareDtlTreeByPid(pId);
    }


    /**
     * 项目ID查询审批结束的估算申报清单列表
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvEstimateDeclareDtlListByPid")
    public BaseResponse getInvEstimateDeclareDtlListByPid(@RequestParam String pId) {
        return invEstimateDeclareDtlService.getInvEstimateDeclareDtlListByPid(pId);
    }
}
