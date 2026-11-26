package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.custom.pojo.entity.SysCompany;

public interface ISysCompanyService extends IService<SysCompany> {
    Boolean havingCreditCode(String creditCode);

    SysCompany getByCreditCode(String creditCode);
}
