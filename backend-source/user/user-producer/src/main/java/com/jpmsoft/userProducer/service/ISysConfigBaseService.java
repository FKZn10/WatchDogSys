package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SysConfigBaseDTO;
import com.jpmsoft.userProducer.pojo.entity.SysConfigBase;

/**
 * 系统配置表
 */
public interface ISysConfigBaseService extends IService<SysConfigBase> {
    /**
     * 新增或更新配置信息
     *
     * @param request 请求参数
     * @return 响应结果
     */
    BaseResponse createOrUpdateSysConfigBase(SysConfigBaseDTO request);

    /**
     * 根据codes获取配置信息
     *
     * @param request 请求参数
     * @return 响应结果
     */
    BaseResponse getSysConfigBaseList(SysConfigBaseDTO request);

    /**
     * 根据id获取配置信息
     *
     * @param id 配置信息id
     * @return 响应结果
     */
    BaseResponse getSysConfigBaseById(String id);

    /**
     * 根据codes获取配置信息
     *
     * @param codes 配置信息codes
     * @return 响应结果
     */
    BaseResponse getSysConfigBaseByCodes(String codes);

    /**
     * 根据id删除配置信息
     *
     * @param id 配置信息id
     * @return 响应结果
     */
    BaseResponse deleteSysConfigBaseById(String id);
}
