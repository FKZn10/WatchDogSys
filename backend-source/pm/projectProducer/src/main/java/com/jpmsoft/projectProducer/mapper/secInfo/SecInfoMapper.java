package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInfoTZResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecNodeTableAndFieldDto;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcBiddingSituation;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface SecInfoMapper extends BaseMapper<SecInfo> {

    List<SecInfoResponse> getSecInfoList(@Param("params") Map<String, Object> params);

    Integer getSecInfoListCount(@Param("params") Map<String, Object> params);

    List<SecInfoResponse> getSecInfoByPId(@Param("params") Map<String, Object> params);

    Integer getSecInfoByPIdCount(@Param("params") Map<String, Object> params);

    SecInfoResponse getSecInfoById(@Param("secId") String secId);

    List<SecInfoResponse> getSecInfoByName(@Param("secName") String secName);


    @Delete("delete from  Sec_Info where Bp_Id=#{bpId}")
    Integer deleteAllOldByBpId(@Param("bpId") String bpId);


    List<SecInfoResponse> getSecInfoListGroupByProject(@Param("secIds") List<String> secIds);


    List<SecProjectModel> getSecProjectList(@Param("page") IPage<SecProjectModel> page, @Param("params") Map<String, Object> params);

    BigDecimal getSecProjectListSum(@Param("params") Map<String, Object> params);

    List<SecInfoResponse> getSecInfoListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecInfoListOne2ManyCount(@Param("params") Map<String, Object> params);


    List<SecInfoDto> getSecInfoBySecInfoIds(@Param("secIds") Collection<String> secIds);

    /**
     * 定时任务-统计招投标情况一览
     *
     * @return
     */
    List<MpcBiddingSituation> countMpcBiddingSituation();


    Map<String,Integer> getNodeTableState(@Param("tbs") List<SecNodeTableAndFieldDto> tbs,@Param("secId") String secId);

    List<SecInfoTZResponse> getValueByCodeWithPid(@Param("projectIdList") List<String> projectIdList);

    SecInfoResponse getInvestmentSecInfo(@Param("params") Map<String,Object> params);

    IPage<SecInfoResponse> getSecInfoListGDGY(@Param("page") IPage<SecInfoResponse> page, @Param("request") SecInfoRequest request);
}
