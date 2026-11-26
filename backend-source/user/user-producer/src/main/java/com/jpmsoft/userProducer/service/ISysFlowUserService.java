package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.SysFlowUser;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/15 10:59
 */
public interface ISysFlowUserService extends IService<SysFlowUser> {


    boolean roleExist(String roleId);

    boolean positionExist(String posId);
}
