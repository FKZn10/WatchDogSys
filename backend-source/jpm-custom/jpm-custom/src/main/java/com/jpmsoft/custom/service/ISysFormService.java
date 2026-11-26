package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.pojo.entity.SysForm;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:07
 */
public interface ISysFormService extends IService<SysForm> {


    BaseResponse getSysFormById(String id);

    BaseResponse getSysFormByCode(String code, String teId);
}
