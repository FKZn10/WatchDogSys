package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenantApplication;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:42
 */
public interface ISysTenantApplicationService extends IService<SysTenantApplication> {

    BaseResponse createOrUpdateSysTenantApplication(SysTenantApplicationDTO request);

    BaseResponse getSysTenantApplicationList(SysTenantApplicationDTO request);

    BaseResponse delSysTenantApp(String taId);
}
