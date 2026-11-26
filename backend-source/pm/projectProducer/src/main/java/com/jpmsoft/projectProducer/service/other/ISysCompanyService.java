package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;

import java.util.List;

public interface ISysCompanyService extends IService<SysCompany> {
    Boolean havingCreditCode(String creditCode);

    SysCompany getByCreditCode(String creditCode);

    List<SysCompany> getCompanyByIds(List<String> ids);

    SysCompany  getComPanyName(String cId);


}
