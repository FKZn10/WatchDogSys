package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceRequestDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterfaceRequest;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:46
 */
public interface ISysInterfaceRequestService extends IService<SysInterfaceRequest> {

    void deleteByIfId(String ifId);

    void createOrUpdate(List<SysInterfaceRequestDTO> data, String ifId);

    List<SysInterfaceRequestDTO> selectById(String ifId);
}
