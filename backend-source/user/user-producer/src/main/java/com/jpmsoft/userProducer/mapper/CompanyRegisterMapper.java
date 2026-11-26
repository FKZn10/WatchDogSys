package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.CompanyRegisterModel;
import com.jpmsoft.userProducer.pojo.entity.CompanyRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CompanyRegisterMapper extends BaseMapper<CompanyRegister> {
    List<CompanyRegisterModel> getCompanyRegisterList(@Param("params")Map<String, Object> query, @Param("page") IPage<CompanyRegisterModel> page);

    CompanyRegisterModel getCompanyRegister(@Param("id")String id);
}
