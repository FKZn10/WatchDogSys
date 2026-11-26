package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinNoticeResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecWinNoticeMapper extends BaseMapper<SecWinNotice> {

    SecWinNoticeResponse getWinNoticeByWnId(@Param("wnId") String wnId);

    SecWinNoticeResponse getLatestApproveWinNotice(@Param("secId") String secId);

    List<SecWinNoticeResponse> getWinNoticeList(@Param("params") Map<String, Object> params);

    Integer getWinNoticeListCount(@Param("params") Map<String, Object> params);

    List<SecWinNoticeResponse> getWinNoticeListOne2Many(@Param("params") Map<String, Object> params);

    Integer getWinNoticeListOne2ManyCount(@Param("params") Map<String, Object> params);

    List<SecWinNoticeResponse> getLatestApproveWinNotices(@Param("secIds") List<String> secIds);

    List<SecWinNoticeResponse> getWinNoticeListClass(@Param("authSql") String authSql, @Param("page") Page<SecWinNoticeResponse> page, @Param("params") SecWinNoticeRequest request);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") SecWinNoticeRequest request);

    List<Map<String, Object>> getWinningBidInformationFromCalibrationReport(@Param("secId") String secId);

    List<Map<String, Object>> getWinningBidInformationFromEvalReport(@Param("secId") String secId);
}
