package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnFee;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:35
 */
public interface IConEnFeeService extends IService<ConEnFee> {

    BaseResponse createOrUpdateConEnFee(ConEnFeeDTO request);

    BaseResponse getConEnFeeList(ConEnFeeDTO request, String uId);

    BaseResponse getConEnFeeById(String id);
}
