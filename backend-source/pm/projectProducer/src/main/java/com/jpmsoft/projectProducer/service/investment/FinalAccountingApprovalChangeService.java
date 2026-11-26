package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApprovalChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalChangeVo;

public interface FinalAccountingApprovalChangeService extends IService<FinalAccountingApprovalChange> {
    BaseResponse createOrUpdatefinalApprovalChange(FinalAccountingApprovalChangeVo request, String uid);

    BaseResponse getfinalApprovalChangeById(String id);

    BaseResponse changeFinalApproval(String id);

    BaseResponse getfinalApprovalChangeList(FinalAccountingApprovalChangeVo request, String uid);
}
