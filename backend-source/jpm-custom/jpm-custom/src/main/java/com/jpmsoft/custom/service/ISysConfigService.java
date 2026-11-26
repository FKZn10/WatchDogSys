package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.custom.pojo.entity.SysConfig;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/28 11:35
 */
public interface ISysConfigService extends IService<SysConfig> {

    String getConfigByCode(String code);

    /**
     * 是否流程审批结束才执行编码生成逻辑
     * 此逻辑临时使用
     *
     * @return
     */
    boolean endProcessGenerateCode();
}
