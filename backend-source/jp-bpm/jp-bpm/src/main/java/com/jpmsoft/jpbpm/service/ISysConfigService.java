package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysConfig;


public interface ISysConfigService extends IService<SysConfig> {

    String getSysConfigByCode(String code);

}
