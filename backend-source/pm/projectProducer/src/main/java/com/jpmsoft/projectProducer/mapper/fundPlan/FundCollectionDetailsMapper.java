package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundCollectionDetails;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:34
 */
public interface FundCollectionDetailsMapper extends BaseMapper<FundCollectionDetails> {

    List<FundCollectionDetailsVo> getFundCollectionDetailsList(@Param("page") IPage<FundCollectionDetailsVo> page, @Param("params") Map<String, Object> params);


    List<FundCollectionProjectVo> getFundCollectionProjectList(@Param("page") IPage<FundCollectionProjectVo> page, @Param("params") Map<String, Object> params);


    FundCollectionDetailsStatisticVo getFundCollectionDetailsStatistic(@Param("params") Map<String, Object> params);

    FundCollectionDetailsVo getFundCollectionDetailsById(@Param("id") String id);

    FundCollectionDetailsVo  getLastFundCollectionDetailsByPid(@Param("pId") String pId);

    @Select("select sum(Fcd_Money) as \"receivedAmt\",count(*) as \"batchNo\" from Fund_Collection_Details where P_Id=#{pId} and State=7 ")
    StatisticsFundCollectionBatchNoAndAmtVo statisticsBatchNoAndAmt(@Param("pId") String pId);

    /**
     * 查询项目资金收款和拨付台账
     *
     * @param page
     * @param params
     * @return
     */
    List<ProjectFundCollectionDetailsVo> getProjectFundCollectionDetailsList(@Param("page") IPage<ProjectFundCollectionDetailsVo> page, @Param("params") Map<String, Object> params);

    /**
     * 统计getProjectFundCollectionDetailsList的金额字段
     *
     * @param params
     * @return
     */
    ProjectFundCollectionDetailsStatisticsVo getMoneySum(@Param("params") Map<String, Object> params);
}
