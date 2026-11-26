package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectChangeNameRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProjectChangeNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proChangeName")
public class PreProjectChangeNameController {
    @Resource
    private IPreProjectChangeNameService preProjectChangeNameService;

    /**
     * 新增或者修改
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/CreateOrUpdateProChangeName")
    public BaseResponse CreateOrUpdateProChangeName(@RequestBody PreProjectChangeNameRequest request) {
        log.info("CreateOrUpdateProChangeName");
        return preProjectChangeNameService.CreateOrUpdateProChangeName(request);
    }

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getPreProjectChangeNameByPage")
    public BaseResponse getPreProjectChangeNameByPage(@RequestBody PreProjectChangeNameRequest request) {
        log.info("getPreProjectChangeNameByPage");
        return preProjectChangeNameService.getPreProjectChangeNameByPage(request);
    }


    @GetMapping("/getPreProjectChangeNameById")
    public BaseResponse getPreProjectChangeNameById(@RequestParam String pcnId) {
        log.info("getPreProjectChangeNameById");
        return preProjectChangeNameService.getPreProjectChangeNameById(pcnId);
    }

    @GetMapping("/approve2RefreshProjectName")
    public BaseResponse approve2RefreshProjectName(@RequestParam String pcnId) {
        log.info("approve2RefreshProjectName");
        return preProjectChangeNameService.approve2RefreshProjectName(pcnId);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @PostMapping("/deleteProChangeNameById")
    public BaseResponse deleteProChangeNameById(@RequestBody PreProjectChangeNameRequest request) {
        log.info("deleteProChangeNameById");
        return preProjectChangeNameService.deleteProChangeNameById(request);
    }

    /**
     * 修改审批状态
     *
     * @param request
     * @return
     */
    @PostMapping("/updateProChangeNameFlowState")
    public BaseResponse updateProChangeNameFlowState(@RequestBody PreProjectChangeNameRequest request) {
        log.info("updateProChangeNameFlowState");
        return preProjectChangeNameService.updateProChangeNameFlowState(request);
    }

    @GetMapping("/getProjectNameUsedBeforeListById")
    public BaseResponse getProjectNameUsedBeforeListById(@RequestParam String pId) {
        //log.info("getProjectNameUsedBeforeListById");
        return preProjectChangeNameService.getProjectNameUsedBeforeListById(pId);
    }
}