package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.investment.IInvRoughDeclareDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invRoughDeclareDtl")
public class InvRoughDeclareDtlController {

    @Resource
    private IInvRoughDeclareDtlService invRoughDeclareDtlService;

    /**
     * 项目ID查询审批结束的匡算申报清单树
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvRoughDeclareDtlTreeByPid")
    public BaseResponse getInvRoughDeclareDtlTreeByPid(@RequestParam String pId) {
        return invRoughDeclareDtlService.getInvRoughDeclareDtlTreeByPid(pId);
    }


    /**
     * 项目ID查询审批结束的匡算申报清单列表
     *
     * @param pId
     * @return
     */
    @GetMapping("/getInvRoughDeclareDtlListByPid")
    public BaseResponse getInvRoughDeclareDtlListByPid(@RequestParam String pId) {
        return invRoughDeclareDtlService.getInvRoughDeclareDtlListByPid(pId);
    }
}
