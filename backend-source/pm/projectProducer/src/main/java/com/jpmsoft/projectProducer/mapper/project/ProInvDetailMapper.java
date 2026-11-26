package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProInvDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ProInvDetailMapper extends BaseMapper<ProInvDetail> {

    List<Map<String, Object>> getSourcesFundsSumByPids(@Param("pIds") List<String> pIds);

    BigDecimal getPInvTotal(@Param("params") ProMonthlyReportDto params);
}
