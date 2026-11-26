package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPriceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplicationPrice;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 17:45
 */
public interface ISysApplicationPriceService extends IService<SysApplicationPrice> {


    void createOrUpdateSysAppPrice(List<SysApplicationPriceDTO> powers, String appId);

    void delSysAppPriceByAppId(String appId);

    List<SysApplicationPriceDTO> getSysAppPriceByAppId(String appId);
}
