package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConPayMultiRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMulti;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
public interface IConPayMultiService extends IService<ConPayMulti> {
    /**
     * 同步支付信息到合同支付表
     *
     * @param request
     * @return
     */
    BaseResponse syncToConPay(ConPayMultiRequest request);

    /**
     * 根据ID获取多项目合同支付信息
     *
     * @param id
     * @return
     */
    BaseResponse getConPayMultiById(String id);

    /**
     * 多项目合同支付分页查询
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getConPayMultiPage(ConPayMultiRequest request, String token);
}
