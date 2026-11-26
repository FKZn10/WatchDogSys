package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPayApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPayApplication;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 15:13
 */
public interface ISysPayApplicationService extends IService<SysPayApplication> {

    BaseResponse createOrUpdateSysPayApplication(SysPayApplicationDTO request);

    BaseResponse getSysPayApplicationList(SysPayApplicationDTO request);

    BaseResponse delSysPayApplicationByPaId(String paId);
}
