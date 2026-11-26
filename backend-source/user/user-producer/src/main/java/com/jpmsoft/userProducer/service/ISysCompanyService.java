package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.CompanyDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;

import java.util.List;


public interface ISysCompanyService extends IService<SysCompany> {

    BaseResponse GetCompanySelect(String token);

    BaseResponse GetCompanySelectByType(String cType,String token);

    List<SysCompany> GetCompanySelect(List<String> idlist,String token);

    BaseResponse GetCompanyList(CompanyDto data);

    BaseResponse GetAllCompanyList(CompanyDto data);

    BaseResponse CreateOrUpdateCompany(CompanyDto data);

    /**
     * 国资单位数据接入
     * @param data
     * @return
     */
    BaseResponse saveForGZ(CompanyDto data);

    BaseResponse DeleteCompany(CompanyDto data);

    BaseResponse getCompanyById(String id);

    /**
     * 社会统一信用代码查询单位
     *
     * @param code
     * @return
     */
    BaseResponse getCompanyByCreditCode(String code, String token);

    BaseResponse GetCompanyNameByIds(String ids);

    /**
     * 根据pId查询项目组织机构单位信息
     *
     * @param request
     * @return
     */
    BaseResponse getCompanyByPId(CompanyDto request);
}
