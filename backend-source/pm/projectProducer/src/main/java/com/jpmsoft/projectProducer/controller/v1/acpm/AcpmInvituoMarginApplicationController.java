package com.jpmsoft.projectProducer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmInvituoMarginApplicationDto;
import com.jpmsoft.projectProducer.service.acpm.IAcpmInvituoMarginApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 投拓保证金申请
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:51
 */
@Slf4j
@RestController
@RequestMapping("/acpm/v1/InvMarginApp")
public class AcpmInvituoMarginApplicationController {

    @Resource
    private IAcpmInvituoMarginApplicationService acpmInvituoMarginApplicationService;


    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmInvituoMarginApplicationDto request) {
        return acpmInvituoMarginApplicationService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmInvituoMarginApplicationDto request) {
        return acpmInvituoMarginApplicationService.getList(request);
    }

    @GetMapping("/getInvMarginAppById")
    public BaseResponse getInvMarginAppById(@RequestParam String id) {
        return acpmInvituoMarginApplicationService.getById(id);
    }
}
