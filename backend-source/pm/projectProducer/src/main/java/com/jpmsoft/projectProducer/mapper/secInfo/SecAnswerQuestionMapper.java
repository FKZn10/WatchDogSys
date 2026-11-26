package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecAnswerQuestionResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnswerQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface SecAnswerQuestionMapper extends BaseMapper<SecAnswerQuestion> {
    List<SecAnswerQuestionResponse> getSecAnswerQuestionList(@Param("params") Map<String, Object> params);

    Integer getSecAnswerQuestionListCount(@Param("params") Map<String, Object> params);

    SecAnswerQuestionResponse getSecAnswerQuestionByaqId(@Param("aqId") String aqId);


    Integer approveSyncDocumentBidTimeAndLocation(@Param("secId") String secId, @Param("dBidTime") Date dBidTime, @Param("dLocation") String dLocation);

    List<SecAnswerQuestionResponse> getSecAnswerQuestionListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecAnswerQuestionListOne2ManyCount(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause,@Param("params") Map<String, Object> params);

    List<SecAnswerQuestionResponse> getSecAnswerQuestionListClass(@Param("authSql") String authSql, @Param("params") Map<String, Object> params);

    Integer getSecAnswerQuestionListClassCount(@Param("authSql") String authSql, @Param("params") Map<String, Object> params);
}
