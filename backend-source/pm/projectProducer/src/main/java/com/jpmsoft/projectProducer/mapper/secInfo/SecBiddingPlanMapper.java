package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanTZRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecModularsGroupByProjectModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.SecInfo.SecBiddingPlanResponse;
import com.jpmsoft.common.model.response.SecInfo.SecBiddingPlanTZRespose;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlan;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecBiddingPlanStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBiddingPlanMapper extends BaseMapper<SecBiddingPlan> {

    SecBiddingPlanStatisticsVO  statisticsSecBiddingPlanList(@Param("params") Map<String, Object> params);

    List<SecBiddingPlanResponse> getSecBiddingPlanList(@Param("page") IPage<SecBiddingPlanResponse> page,@Param("params") Map<String, Object> params);

    SecBiddingPlanResponse getSecBiddingPlanById(@Param("bpId") String bpId);

    List<SecModularsGroupByProjectModel> getSbpProjectList(@Param("params") Map<String, Object> params);

    Integer getSbpProjectListCount(@Param("params") Map<String, Object> params);

    List<SecBiddingPlanResponse> getSecBiddingPlanListByPids(@Param("pIds") List<String> pIds, @Param("params") Map<String, Object> params);

    List<SecBiddingPlanTZRespose> getValueByCodeWithPid(@Param("params") SecBiddingPlanTZRequest requests);

    List<SecBiddingPlanResponse> getSecBiddingPlanByPId(@Param("pId") String pId);

    SecBiddingPlanResponse getSecBiddingPlanByPId2(@Param("pId") String pId);

    List<FileTemplateTree> getFileTemplateByTCodes(@Param("tCodes") List<String> tCodes);

    Integer getSecPrecheckCount(@Param("table") String table, @Param("stateKey") String stateKey, @Param("secId") String secId);
}
