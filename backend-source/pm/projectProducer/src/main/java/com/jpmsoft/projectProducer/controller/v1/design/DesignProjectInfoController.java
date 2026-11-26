package com.jpmsoft.projectProducer.controller.v1.design;

import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignProjectInfoService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designProjectInfo")
public class DesignProjectInfoController {

    @Resource
    private IDesignProjectInfoService designProjectInfoService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/getDesignProjectInfoList")
    public BaseResponse getDesignProjectInfoList(@RequestBody DesignProjectInfoModel request, @RequestHeader String token) {
        log.info("getDesignProjectInfoList");
        return BaseResponse.ok(designProjectInfoService.getDesignProjectInfoList(request, redisUtils.getUid(token)));
    }

    @TenantId
    @PostMapping("/getDesignProjectInfoByBusiness")
    public BaseResponse getDesignProjectInfoByBusiness(@RequestBody DesignProjectInfoModel request) {
        log.info("getDesignProjectInfoByBusiness");
        Integer businessType = request.getBusinessType();
        String pId = request.getpId();
        if(businessType == null){
            throw new JPMException("业务类型不能为空");
        }
        if(StrUtil.isBlank(pId)){
            throw new JPMException("项目id不能为空");
        }
        return BaseResponse.ok(designProjectInfoService.getDesignProjectInfoByBusinessId(null,businessType,pId));
    }


}
