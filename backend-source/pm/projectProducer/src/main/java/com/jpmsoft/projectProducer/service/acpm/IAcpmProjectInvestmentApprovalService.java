package com.jpmsoft.projectProducer.service.acpm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProInvApprovalFormDto;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmProjectInvestmentApprovalDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmProjectInvestmentApproval;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:41
 */
public interface IAcpmProjectInvestmentApprovalService extends IService<AcpmProjectInvestmentApproval> {

    BaseResponse createOrUpdate(AcpmProjectInvestmentApprovalDto request);

    BaseResponse getList(AcpmProInvApprovalFormDto request);

    BaseResponse getById(String id);

    BaseResponse getSelectList(AcpmProInvApprovalFormDto request);
}
