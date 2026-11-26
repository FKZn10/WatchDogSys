package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.oa.ReportedCompany;
import com.jpmsoft.projectProducer.pojo.vo.oa.ReportedCompanyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 14:40
 */
public interface ReportedCompanyMapper extends BaseMapper<ReportedCompany> {

    List<ReportedCompanyVo> getReportedCompanyList(@Param("page") IPage<ReportedCompanyVo> page, @Param("params") Map<String, Object> params);

    ReportedCompanyVo getReportedCompanyById(@Param("id") String id);

    List<ReportedCompanyVo> getReportedCompanyGroupTypeByPid(@Param("pId") String pId);
}
