package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaFeeRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaFee;


public interface IConVisaFeeService extends IService<ConVisaFee> {


    BaseResponse createOrUpdateConVisaFee(ConVisaFeeRequest request);

    BaseResponse getConVisaFeeList(ConVisaFeeRequest request, String uId);

    /**
     * 区分链表查询合同是是查询本表单还是签证计量备案
     * 0:签证备案 1：本表单 默认 0
     *
     * @param vfId
     * @param conWhere
     * @return
     */
    BaseResponse getConVisaFeeByVfId(String vfId, Integer conWhere);

    BaseResponse getVisaAmt(String conId);
}
