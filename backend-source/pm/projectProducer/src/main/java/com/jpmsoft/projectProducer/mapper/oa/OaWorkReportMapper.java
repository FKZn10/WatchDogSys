package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jpmsoft.common.model.response.Oa.OaWorkReportResponse;
import com.jpmsoft.common.model.response.Oa.OaWorkReportStandingbook;
import com.jpmsoft.projectProducer.pojo.dto.oa.HolidayJudgeTimeDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OaWorkReportMapper extends BaseMapper<OaWorkReport> {


    List<OaWorkReportResponse> getOaWorkReportList(@Param("page") IPage<OaWorkReportResponse> page,@Param("params") Map<String, Object> params);

    /**
     * 工作报告列表，附带子表 OA_Work_Report_Detail 的 Wrd_Number 数量合计
     *
     * @param page   MyBatis-Plus 分页对象，回填 total
     * @param params QueryMap 生成的条件，键名与 getOaWorkReportList 一致
     */
    List<OaWorkReportResponse> getOaWorkReportSumList(@Param("page") IPage<OaWorkReportResponse> page, @Param("params") Map<String, Object> params);


    OaWorkReportResponse getOaWorkReportById(@Param("wrId") String wrId);
    /**
     * 根据 用户id 查找部门
     */


    /*********************施工日志台账*/

    IPage<OaWorkReportStandingbook> getOaWorkReportStandingbook(IPage<OaWorkReportStandingbook> page
            , @Param(Constants.WRAPPER) Wrapper<OaWorkReportStandingbook> queryWrapper, @Param("authSql") String authSql, @Param("projectName") String projectName
            , @Param("pId") String pId);

    //算总数
    Long getholidayJudgeCount(@Param("projectId") String projectId, @Param("isWork") String isWork, @Param("functionCode") String functionCode);

    //通过项目id 查询配置的 开始结束时间
    @Select("select to_char(start_time,'yyyyMMdd') as \"startTime\",to_char(end_time,'yyyyMMdd') \"endTime\",is_work from function_time_config where delete_status = '0' and project_id =#{projectId} and function_code =#{functionCode}")
    HolidayJudgeTimeDto getHolidayJudgeTime(@Param("projectId") String projectId, @Param("functionCode") String functionCode);

    List<String> getOaWorkReportNeverSub(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("projectId") String projectId, @Param("isWork") String isWork);


    List<OaWorkReport> selectoaWorkReportList(@Param("entity") OaWorkReport entity);

    Long getholidayJudgeCountNowTime(@Param("projectId") String projectId, @Param("isWork") String isWork, @Param("functionCode") String functionCode);

    long getAlreadySubmitNowTime(@Param("projectId") String projectId, @Param("isWork") String isWork, @Param("functionCode") String functionCode);

    long countOaWorkReport(@Param("bizIds")List<String> bizIds, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("type")String type);
}
