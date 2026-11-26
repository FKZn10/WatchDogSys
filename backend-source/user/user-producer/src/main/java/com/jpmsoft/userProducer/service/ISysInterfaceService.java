package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterface;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:46
 */
public interface ISysInterfaceService extends IService<SysInterface> {

    BaseResponse createOrUpdate(SysInterfaceDTO request,String token);

    BaseResponse getSysInterfaceList(SysInterfaceDTO request);

    BaseResponse getSysInterfaceById(String id);

    BaseResponse delSysInterfaceById(String id);

    List<SysInterface> refreshRedis();

    List<SysInterface> getAll();
}
