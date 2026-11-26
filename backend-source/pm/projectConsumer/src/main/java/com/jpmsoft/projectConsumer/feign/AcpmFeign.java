package com.jpmsoft.projectConsumer.feign;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmDJTBDocumentsApprovalDto;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmInvituoMarginApplicationDto;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmProInvApprovalFormDto;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmProjectInvestmentApprovalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 16:51
 */
@FeignClient(name = "project-producer", contextId = "acpm1")
public interface AcpmFeign {

    /***********************************************项目投拓立项***************************************************/
    @PostMapping("/acpm/v1/proInvApproval/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody AcpmProjectInvestmentApprovalDto request);

    @PostMapping("/acpm/v1/proInvApproval/getList")
    BaseResponse getList(@RequestBody AcpmProInvApprovalFormDto request);

    @GetMapping("/acpm/v1/proInvApproval/getProInvApprovalById")
    BaseResponse getProInvApprovalById(@RequestParam String id);

    @PostMapping("/acpm/v1/proInvApproval/getSelectList")
    BaseResponse getSelectList(@RequestBody AcpmProInvApprovalFormDto request);

    /***********************************************投拓保证金申请***************************************************/

    @PostMapping("/acpm/v1/InvMarginApp/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody AcpmInvituoMarginApplicationDto request);

    @PostMapping("/acpm/v1/InvMarginApp/getList")
    BaseResponse getList(@RequestBody AcpmInvituoMarginApplicationDto request);

    @GetMapping("/acpm/v1/InvMarginApp/getInvMarginAppById")
    BaseResponse getInvMarginAppById(@RequestParam String id);

    /***********************************************代建投标文件审批***************************************************/

    @PostMapping("/acpm/v1/DJTBDocuApproval/createOrUpdate")
    BaseResponse createOrUpdate(@RequestBody AcpmDJTBDocumentsApprovalDto request);

    @PostMapping("/acpm/v1/DJTBDocuApproval/getList")
    BaseResponse getList(@RequestBody AcpmDJTBDocumentsApprovalDto request);

    @GetMapping("/acpm/v1/DJTBDocuApproval/getDJTBDocuApprovalById")
    BaseResponse getDJTBDocuApprovalById(@RequestParam String id);
}
