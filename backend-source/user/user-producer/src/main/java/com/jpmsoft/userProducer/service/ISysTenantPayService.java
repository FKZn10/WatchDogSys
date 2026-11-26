package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantPayDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenantPay;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 14:40
 */
public interface ISysTenantPayService extends IService<SysTenantPay> {

    BaseResponse createOrUpdateSysTenantPay(SysTenantPayDTO request);

    BaseResponse getSysTenantPayList(SysTenantPayDTO request);

    BaseResponse getSysTenantPayById(String id);

}
