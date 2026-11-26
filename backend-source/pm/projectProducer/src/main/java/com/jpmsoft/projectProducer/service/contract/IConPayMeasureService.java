package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMeasure;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 13:49
 */
public interface IConPayMeasureService extends IService<ConPayMeasure> {


    void createOrUpdate(List<String> meaIds, String conId, String payId);

    BaseResponse getApplyAndAmtByPayId(String payId);

    BaseResponse getUnMeasureApplyAndAmtByConId(String conId);
}
