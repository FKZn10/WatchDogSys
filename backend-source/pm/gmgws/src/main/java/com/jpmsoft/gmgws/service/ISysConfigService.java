package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.gmgws.pojo.entity.SysConfig;




public interface ISysConfigService extends IService<SysConfig> {

    String getSysConfigByCode(String code);

    String getConfigByCode(String code);


    InterfaceEncDecDTO getInterfaceEncDec();

}
