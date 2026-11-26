package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPowerDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplicationPower;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:23
 */
public interface ISysApplicationPowerService extends IService<SysApplicationPower> {

    void createOrUpdateSysAppPower(List<SysApplicationPowerDTO> powers,String appId);

    void delSysAppPowerByAppId(String appId);

    List<SysApplicationPowerDTO> getSysAppPowerByAppId(String appId);
}
