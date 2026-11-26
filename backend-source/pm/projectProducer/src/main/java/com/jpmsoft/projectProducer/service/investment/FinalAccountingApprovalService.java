package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApproval;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalVo;

public interface FinalAccountingApprovalService extends IService<FinalAccountingApproval> {

    BaseResponse getfinalApprovalById(String id);

    BaseResponse getfinalApprovalList(FinalAccountingApprovalVo request);

    BaseResponse delFinalApprovalById(String id);

    BaseResponse createOrUpdatefinalApproval(FinalAccountingApprovalVo request, String uId);

    BaseResponse queryApprovalListClassInfo(FinalAccountingApprovalVo request);
}
