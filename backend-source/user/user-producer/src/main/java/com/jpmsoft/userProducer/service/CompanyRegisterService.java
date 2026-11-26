package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.CompanyRegisterModel;
import com.jpmsoft.userProducer.pojo.entity.CompanyRegister;

import javax.servlet.http.HttpServletRequest;

public interface CompanyRegisterService extends IService<CompanyRegister> {
    BaseResponse createOrUpdateCompanyRegister(CompanyRegisterModel dto, HttpServletRequest request);

    BaseResponse approveCompanyRegister(CompanyRegisterModel request);

    BaseResponse getCompanyRegisterList(CompanyRegisterModel request,String uid);

    BaseResponse getCompanyRegister(String id);
}
