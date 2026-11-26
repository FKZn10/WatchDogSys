package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConMeasurePriceRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasurePrice;

/**
 * 合同计量计价 Service
 *
 * @author tianhelin
 * @date 2025/10/21
 */
public interface IConMeasurePriceService extends IService<ConMeasurePrice> {
    /**
     * 创建或更新合同计量计价
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateConMeasurePrice(ConMeasurePriceRequest request);

    /**
     * 获取合同计量计价列表
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getConMeasurePriceList(ConMeasurePriceRequest request, String token);

    /**
     * 获取合同计量计价列表（不鉴权）
     *
     * @param request
     * @return
     */
    BaseResponse getConMeasurePriceListNoAuth(ConMeasurePriceRequest request);

    /**
     * 获取合同计量计价详情
     *
     * @param id
     * @return
     */
    BaseResponse getConMeasurePriceById(String id);

    /**
     * 通过合同ID获取合同计量计价信息
     *
     * @param conId
     * @param id
     * @return
     */
    BaseResponse getConMeasurePriceInfoByConId(String conId, String id);
}
