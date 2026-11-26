package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.PeInfo.PeInfoRankingRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoRankingModel;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeInfoMapper extends BaseMapper<PeInfo> {

    List<PeInfoModel> getPeInfoList(@Param("page") IPage<PeInfoModel> page, @Param("params") Map<String, Object> params);


    List<PeInfoRankingModel> getPeInfoRanking(@Param("params") PeInfoRankingRequest params);

    Integer getPeInfoRankingCount(@Param("params") PeInfoRankingRequest params);

    PeInfoModel getPeInfoByPeId(@Param("peId") String peId);

    List<PeInfoModel> getPeInfoByMaId(@Param("maId") String maId);

    List<PeInfoModel> getPeInfoUnMeetApproval(@Param("params") Map<String, Object> params);

    Integer getPeInfoUnMeetApprovalCount(@Param("params") Map<String, Object> params);

    List<Map> getGradeCount(@Param("yearStr") String yearStr);

    List<Map> getDIdCount(@Param("yearStr") String yearStr);

    List<Map> getConTypeCount(@Param("yearStr") String yearStr);
}
