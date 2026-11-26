package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReport;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FinalAccountingReportMapper extends BaseMapper<FinalAccountingReport> {
    List<Object> getprojectValueByCode(@Param("value")String dbValue);


    List<Object> getValueByCode(@Param("value")String dbValue);

    IPage<FinalAccountingReportVo> getFinalAccountsList(@Param("page") Page<FinalAccountingReport> page,@Param("request") FinalAccountingReportVo request);

    List<Object> getValueCnameByCode(@Param("value") String dbValue);
}
