package com.jpmsoft.projectProducer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProInvApprovalFormDto;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProjectInvestmentApprovalDto;
import com.jpmsoft.projectProducer.service.acpm.IAcpmProjectInvestmentApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目投拓立项
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:24
 */
@Slf4j
@RestController
@RequestMapping("/acpm/v1/proInvApproval")
public class AcpmProjectInvestmentApprovalController {

    @Resource
    private IAcpmProjectInvestmentApprovalService acpmProjectInvestmentApprovalService;

    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmProjectInvestmentApprovalDto request, HttpServletResponse response) {

        return acpmProjectInvestmentApprovalService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmProInvApprovalFormDto request) {
        return acpmProjectInvestmentApprovalService.getList(request);
    }

    @GetMapping("/getProInvApprovalById")
    public BaseResponse getProInvApprovalById(@RequestParam String id) {
        return acpmProjectInvestmentApprovalService.getById(id);
    }

    @TenantId
    @PostMapping("/getSelectList")
    public BaseResponse getSelectList(@RequestBody AcpmProInvApprovalFormDto request) {
        return acpmProjectInvestmentApprovalService.getSelectList(request);
    }
}
