package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceResponseDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterfaceResponse;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:47
 */
public interface ISysInterfaceResponseService extends IService<SysInterfaceResponse> {

    void deleteByIfId(String ifId);

    void createOrUpdate(List<SysInterfaceResponseDTO> data, String ifId);

    List<SysInterfaceResponseDTO> selectById(String ifId);
}
