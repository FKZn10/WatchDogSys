package com.jpmsoft.projectProducer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmDJTBDocumentsApprovalDto;
import com.jpmsoft.projectProducer.service.acpm.IAcpmDJTBDocumentsApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 代建投标文件审批
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 16:49
 */
@Slf4j
@RestController
@RequestMapping("/acpm/v1/DJTBDocuApproval")
public class AcpmDJTBDocumentsApprovalController {

    @Resource
    private IAcpmDJTBDocumentsApprovalService acpmDJTBDocumentsApprovalService;


    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmDJTBDocumentsApprovalDto request) {
        return acpmDJTBDocumentsApprovalService.createOrUpdate(request);
    }

    @TenantId
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmDJTBDocumentsApprovalDto request) {
        return acpmDJTBDocumentsApprovalService.getList(request);
    }

    @GetMapping("/getDJTBDocuApprovalById")
    public BaseResponse getDJTBDocuApprovalById(@RequestParam String id) {
        return acpmDJTBDocumentsApprovalService.getById(id);
    }
}
