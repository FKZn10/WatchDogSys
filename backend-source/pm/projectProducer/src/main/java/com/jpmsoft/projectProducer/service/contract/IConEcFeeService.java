package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRelationListRequest;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFee;

public interface IConEcFeeService extends IService<ConEcFee> {
    /**
     * 变更费用申请
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateConEcFee(ConEcFeeRequest request);


    BaseResponse getConEcFeeList(ConEcFeeRequest request, String uId);

    BaseResponse getConEcFeeRelationList(ConEcFeeRelationListRequest request, String uId);

    BaseResponse getConEcFeeStandingBook(ConEcFeeRequest request, String uId);

    BaseResponse getConEcFeeListNoAuth(ConEcFeeRequest request);

    BaseResponse getConEcFeeByEcfId(ConEcFeeRequest request, String uId);

    BaseResponse getConEcFeeApplyMoneySum(String conId);

    /***************************************清标************************************************/
    /**
     * 变更费用申请（清标）
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateConEcFeeClear(ConEcFeeRequest request);

    BaseResponse getConEcFeeClearList(ConEcFeeRequest request, String uId);

    BaseResponse getConEcFeeClearById(String id);

    BaseResponse getPreliminaryAmountSumByConId(String conId);

    BaseResponse createOrUpdateConEcFeeOrRecord(ConEcFeeRequest request);

    BaseResponse getConEcFeeClassInfo(ConEcFeeRequest request,String uId);

    BaseResponse getConEcFeeListorRecord(ConEcFeeRequest request, String uid);

    BaseResponse getConEcFeeorRecordByEcfId(ConEcFeeRequest request, String uid);

    BaseResponse getConEcFeeChangePayList(ConEcFeeRequest request, String uid);
}
