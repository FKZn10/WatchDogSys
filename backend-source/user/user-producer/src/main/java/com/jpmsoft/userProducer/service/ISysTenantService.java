package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTenantDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenant;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:34
 */
public interface ISysTenantService extends IService<SysTenant> {

    BaseResponse createOrUpdateSysTenant(SysTenantDTO request);

    BaseResponse getSysTenantList(SysTenantDTO request);

    BaseResponse getSysTenantById(String id);

}
