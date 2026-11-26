package com.jpmsoft.projectProducer.mapper.leaderBoard;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementEndVO;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYearYw;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.vo.leaderBoard.PlanLeaderBoardInfoListVo;
import com.jpmsoft.projectProducer.pojo.vo.leaderBoard.QsLeaderBoardInfoListVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LeaderBoardMapper {
    List<PreProjectResponse> getPreProjectInfo();

    List<PreProjectResponse> getPreProjectInfoList(@Param("params")Map<String, Object> query,@Param("page")IPage<PreProjectResponse> page);

    List<ConContractResponse> getContractInfo();

    List<ConContractResponse> getContractInfoList(@Param("params")Map<String, Object> query,@Param("page")IPage<ConContractResponse> page);

    List<String> getSysDictionaryCodeByParentId(@Param("parentId")String parentId);

    List<SysDictionary> getSysDictionaryByParentId(@Param("parentId")String parentId);

    @Select("select D_ID from sys_dictionary where D_Code=#{code} limit 1")
    String getSysDictionaryIdByCode(String code);
    List<QsPlanTaskVo> getQsPlanTask();

    List<QsRecordResponse> getQsRecordList(@Param("ptIds")List<String> ptIds);

    List<QsLeaderBoardInfoListVo> getSiteManagementInfoList(@Param("params")Map<String, Object> query,
                                                            @Param("page") IPage<QsLeaderBoardInfoListVo> page);

    List<ConPayResponse> getConPayResponse();

    List<ConContractResponse> getPayInfoList(@Param("params")Map<String, Object> query, @Param("page") IPage<ConContractResponse> page);

    List<InvPlanYearResponse> getInvPlanYears();

    List<InvPlanYearYw> getInvPlanYearYw();

    List<InvMonthlyScheduleResponse> getInvMonthlySchedule(@Param("ipvIds")List<String> ipvIds);

    List<PlanLeaderBoardInfoListVo> getPlanInfoList(@Param("params")Map<String, Object> query, @Param("page")IPage<PlanLeaderBoardInfoListVo> page);

    List<InvSettlementEndVO> getInvSettlementEnd();

    List<ConPayResponse> getConPayResponse2();
}