package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanSummaryDetailDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummary;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanSummaryRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupFundPlanSummaryMapper extends BaseMapper<GroupFundPlanSummary> {
    List<GroupFundPlanSummaryVo> getGroupFundPlanSummaryVosList(@Param("params")Map<String, Object> query,
                                                                @Param("page")IPage<GroupFundPlanSummaryVo> page);

    GroupFundPlanSummaryVo getGroupFundPlanSummaryById(@Param("id")String id);

    List<GroupFundPlanSummaryDetailVo> getGroupFundPlanSummaryInfo(@Param("params")Map<String, Object> query);

    String getGroupFundPlanSummaryByGroupFundPlanId(@Param("gfpId")String gfpId);

    List<GroupFundPlanSummary> getGroupFundPlanSummaryByDetailId(@Param("gfpDetailIds")List<String> gfpDetailIds);

    List<GroupFundPlanSummaryDetailVo> getCompanyProjectFundPlan(@Param("params") GroupFundPlanSummaryRequest request);

    GroupFundPlanSummaryVo getCompanyGroupFundPlanSummaryById(@Param("id") String id);

    List<GroupFundPlanSummaryDetailVo> getGroupFundPlanSummaryDetailByPid(@Param("page") Page<GroupFundPlanSummaryDetailDto> page, @Param("params") GroupFundPlanSummaryDetailDto request);

    long existUseByGfpId(@Param("id") String id);

    List<String> existProjectSubmit(@Param("id") String id, @Param("year") Integer gfpsYear, @Param("month") Integer gfpsMonth, @Param("type") Integer gfpsType);
}
