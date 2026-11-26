package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Construction.ConConstructionOrgDesignRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.construction.IConConstructionOrgDesignService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conCOD")
public class ConConstructionOrgDesignController {

    @Resource
    private IConConstructionOrgDesignService conConstructionOrgDesignService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConConstructionOrgDesign")
    public BaseResponse createOrUpdateConConstructionOrgDesign(@RequestBody ConConstructionOrgDesignRequest request) {
        log.info("createOrUpdateConConstructionOrgDesign:"+ JacksonUtils.toJSONString(request));
        return conConstructionOrgDesignService.createOrUpdateConConstructionOrgDesign(request);
    }

    @TenantId
    @PostMapping("/getConConstructionOrgDesignList")
    public BaseResponse getConConstructionOrgDesignList(@RequestBody ConConstructionOrgDesignRequest request, @RequestHeader String token) {
        log.info("getConConstructionOrgDesignList");
        return conConstructionOrgDesignService.getConConstructionOrgDesignList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConConstructionOrgDesignById")
    public BaseResponse getConConstructionOrgDesignById(@RequestParam String codId) {
        log.info("getConConstructionOrgDesignById");
        return conConstructionOrgDesignService.getConConstructionOrgDesignById(codId);
    }
}
