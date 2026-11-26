package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.response.Oa.OaProReportInvSum;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProjectReport;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface OaProjectReportMapper extends BaseMapper<OaProjectReport> {

    OaProReportInvSum getOaProReportInvSum(@Param("wrId") String wrId);

    List<OaProjectReportModel> getOaProjectReportList(@Param("page") IPage<OaProjectReportModel> page, @Param("params") Map<String, Object> params);


    OaProjectReportModel getOaProjectReportById(@Param("prId") String prId);

    List<OaProjectReportModel> getOaProjectReportByPidsAndYearMon(@Param("pIds") List<String> pIds, @Param("year") Integer year, @Param("month") String month);

    List<OaProjectReportModel>  getOaProjectQuarterlReportByPidsAndYear(@Param("pIds") List<String> pIds, @Param("year") Integer year);

    @Select("select mn.P_Id from  OA_Project_Report mn " +
            " left join  OA_Work_Report wr on wr.Wr_Id=mn.Wr_Id " +
            "where wr.Wr_Year=#{year} and wr.Wr_Month=#{mon} and wr.Wr_State!=3")
    List<String> getCurrentMonthReportPids(@Param("year") Integer year, @Param("mon") Integer mon);


    ProjectInvStatisticsVo getOaProjectReportInvStatistics(@Param("params") Map<String, Object> params);


    OaProjectReportModel getLastProjectReportByPid(@Param("pId") String pId, @Param("prType") String prType);
}
