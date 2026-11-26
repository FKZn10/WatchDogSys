package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayChangeAssociation;

/**
 * 支付变更关联
 */
public interface IPayChangeAssociationService extends IService<PayChangeAssociation> {
    /**
     * 创建支付变更关联列表
     *
     * @param request
     * @return
     */
    BaseResponse createPayChangeAssociationList(ConPayRequest request);

    /**
     * 根据payId获取支付变更关联列表
     *
     * @param payId
     * @return
     */
    BaseResponse getAssociationListByPayId(String payId);

    /**
     * 合同ID或者支付ID查看是否存在数据
     *
     * @param conId
     * @param payId
     * @return
     */
    BaseResponse existsAssociation(String conId, String payId);
}
