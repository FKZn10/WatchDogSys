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


@Slf4j
@RestController
@Api(tags = "估算申报清单", value = "估算申报清单")
@RequestMapping("/v1/invEstimateDeclareDtl")
public class InvEstimateDeclareDtlController {

    @Autowired
    private InvFeign invFeign;

    /**
     * 项目ID查询审批结束的估算申报清单树
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目ID查询审批结束的估算申报清单树")
    @GetMapping("/getInvEstimateDeclareDtlTreeByPid")
    public BaseResponse getInvEstimateDeclareDtlTreeByPid(@RequestParam String pId) {
        return invFeign.getInvEstimateDeclareDtlTreeByPid(pId);
    }


    /**
     * 项目ID查询审批结束的估算申报清单列表
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目ID查询审批结束的估算申报清单列表")
    @GetMapping("/getInvEstimateDeclareDtlListByPid")
    public BaseResponse getInvEstimateDeclareDtlListByPid(@RequestParam String pId) {
        return invFeign.getInvEstimateDeclareDtlListByPid(pId);
    }

}
