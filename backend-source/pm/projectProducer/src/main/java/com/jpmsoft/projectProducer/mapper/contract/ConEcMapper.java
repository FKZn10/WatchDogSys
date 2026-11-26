package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcChangeVisaSituation;
import com.jpmsoft.projectProducer.pojo.entity.other.EngineeringKeepRecord;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcStandingBookVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractConEcHistoryVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConEcMapper extends BaseMapper<ConEc> {

    List<ConEcResponse> getConEcList(@Param("page") IPage<ConEcResponse> page, @Param("params") Map<String, Object> params);

    ConEcStandingBookVo getConEcStandingBook(@Param("params") Map<String, Object> params);

    ConEcResponse getConEcByEcId(@Param("ecId") String ecId);

    List<ConEcResponse> getConEcByConId(@Param("conId") String conId);

    /**
     * 合并查询合同已审批的设计变更和现场签证明细，以主流程审批时间降序返回。
     *
     * @param projectId     项目ID
     * @param contractId    合同ID
     * @param excludeEcId   排除的当前子表记录ID，可为空
     * @param finishedState 主流程审批结束状态
     * @param authSql       服务端生成的项目权限SQL
     * @return 包含主流程审批时间和已保存余额的合同历史明细
     */
    List<ContractConEcHistoryVO> getContractChangeHistory(
            @Param("projectId") String projectId,
            @Param("contractId") String contractId,
            @Param("excludeEcId") String excludeEcId,
            @Param("finishedState") Integer finishedState,
            @Param("authSql") String authSql);

    Map<String, Object> getConEcStatisticsOverview(@Param("teId") String teId);

    List<ConEcResponse> getConEcStatisticsOverviewList(@Param("page") Page<ConEcResponse> page, @Param("params") ConEcRequest request, @Param("teId") String teId);

    List<Map<String, Object>> getConEcStatisticsGroupByGrade(@Param("teId") String teId);

    List<Map<String, Object>> getConEcStatisticsChangeSituation(@Param("teId") String teId);

    List<Map<String, Object>> getConEcStatisticsByRanking(@Param("size") Integer size, @Param("teId") String teId);

    List<MpcChangeVisaSituation> countMpcChangeVisaSituation();

    @Update("update  Con_Ec set Ec_State=#{state} where Ec_Id=#{id}")
    Integer updateState(@Param("state") Integer state, @Param("id") String id);

    @Update("update Con_EC set Ec_State = #{state} " +
            "where multi_contract_con_ec_id = #{id} " +
            "and Ec_Change_Type = #{type} " +
            "and (Ec_State is null or Ec_State <> #{state}) " +
            "and exists (select 1 from multi_contract_con_ec " +
            "where uuid = #{id} and state = #{state})")
    Integer updateDesignChangeChildState(@Param("id") String id,
                                         @Param("type") String type,
                                         @Param("state") Integer state);

    @Update("update Con_EC set Ec_State = #{state} " +
            "where Con_Svis_Id = #{id} " +
            "and Ec_Change_Type = #{type} " +
            "and (Ec_State is null or Ec_State <> #{state}) " +
            "and exists (select 1 from Con_Svis " +
            "where Svis_Id = #{id} and Svis_State = #{state})")
    Integer updateSiteVisaChildState(@Param("id") String id,
                                     @Param("type") String type,
                                     @Param("state") Integer state);

    List<ConEcResponse> listConEc(@Param("params") Map<String, Object> params);

    /**
     * 获取项目的变更签证金额的和
     * @param projectId
     * @param ecChangeType
     * @return
     */
    BigDecimal getProjectTotalConEcMoney(@Param("projectId") String projectId, @Param("ecChangeType") String ecChangeType, @Param("contractId") String contractId);

    Double getConEcCountPlusByConId(@Param("conId") String conId);

    Double getConEcCountMinusByConId(@Param("conId") String conId);

    List<ConEcResponse> getReportingChangesByPidHB(@Param("conIds") List<String> conIds);

    List<EngineeringKeepRecord> getDetermineChangesByPidHB(@Param("conIds") List<String> conIds);

    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") Map<String, Object> params
    );
}
