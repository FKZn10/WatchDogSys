package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplication;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/6 17:55
 */
public interface ISysApplicationService extends IService<SysApplication> {


    BaseResponse createOrUpdateSysApplication(SysApplicationDTO request);

    BaseResponse getSysApplicationList(SysApplicationDTO request);

    BaseResponse getSysApplicationById(String id);
}
