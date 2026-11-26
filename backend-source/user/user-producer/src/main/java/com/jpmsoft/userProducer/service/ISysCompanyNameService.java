package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.CompanyNameDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompanyName;


public interface ISysCompanyNameService extends IService<SysCompanyName> {

    /**
     * 获取单位更名信息列表
     *
     * @param request
     * @return
     */
    BaseResponse GetCompanyNameList(CompanyNameDto request);

    /**
     * 新增or修改单位更名记录
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateCompanyName(CompanyNameDto request);

    /**
     * 删除单位更名记录
     *
     * @param request
     * @return
     */
    BaseResponse DeleteCompanyName(CompanyNameDto request);


    /**
     * 获取单位曾用名List
     *
     * @param cId
     * @return
     */
    BaseResponse getCompanyNameUsedBeforeListById(String cId);

}
