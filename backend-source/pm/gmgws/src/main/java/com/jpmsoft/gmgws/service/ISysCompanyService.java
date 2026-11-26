package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.SysCompany;

import java.util.List;

public interface ISysCompanyService extends IService<SysCompany> {

    List<SysCompany> getComPany();
}
