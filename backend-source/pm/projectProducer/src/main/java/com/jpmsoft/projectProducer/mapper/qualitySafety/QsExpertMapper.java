package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsExpert;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsExpertVacationVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface QsExpertMapper extends BaseMapper<QsExpert> {

    List<QsExpertModel> getQsExpertList(@Param("params") Map<String, Object> params);

    Integer getQsExpertListCount(@Param("params") Map<String, Object> params);

    QsExpertModel getQsExpertById(@Param("exId") String exId);


    List<QsExpertModel> getExtractExpertList(@Param("excludeIds") List<String> excludeIds, @Param("excludeCpy") String excludeCpy
            , @Param("teId") String teId, @Param("monStart") String monStart, @Param("monEnd") String monEnd,@Param("monthExNum") Integer monthExNum, @Param("avoidCompanyIds") List<String> avoidCompanyIds);


    @Update("update Qs_Expert set Ex_Status=#{status} where Ex_Id=#{exId}")
    Integer updateQsExpertStatus(@Param("exId") String exId, @Param("status") Integer status);

}
