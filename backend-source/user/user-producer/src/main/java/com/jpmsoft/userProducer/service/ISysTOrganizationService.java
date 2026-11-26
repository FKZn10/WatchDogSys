package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysTOrganizationDto;
import com.jpmsoft.userProducer.pojo.entity.SysTOrganization;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:52
 */
public interface ISysTOrganizationService extends IService<SysTOrganization> {

    BaseResponse createOrUpdateSysTOrganization(SysTOrganizationDto request);

    BaseResponse getSysTOrganizationList(SysTOrganizationDto request);

    BaseResponse getSysTOrganizationById(String id);

    BaseResponse delTOrganizationById(String id);
}
