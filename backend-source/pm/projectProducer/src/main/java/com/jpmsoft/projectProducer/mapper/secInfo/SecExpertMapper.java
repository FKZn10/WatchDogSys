package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertVacationVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface SecExpertMapper extends BaseMapper<SecExpert> {

    List<SecExpertModel> getSecExpertList(@Param("params") Map<String, Object> params);

    Integer getSecExpertListCount(@Param("params") Map<String, Object> params);

    SecExpertModel getSecExpertById(@Param("exId") String exId);


    List<SecExpertModel> getExtractExpertList(
            @Param("excludeIds") List<String> excludeIds,
            @Param("excludeCpy") String excludeCpy,
            @Param("teId") String teId,
            @Param("monStart") String monStart,
            @Param("monEnd") String monEnd,
            @Param("monthExNum") Integer monthExNum,
            @Param("avoidCompanyIds") List<String> avoidCompanyIds,
            @Param("keyExpert") String keyExpert,
            @Param("year") Integer year
    );


    @Update("update Sec_Expert set Ex_Status=#{status} where Ex_Id=#{exId}")
    Integer updateSecExpertStatus(@Param("exId") String exId, @Param("status") Integer status);

    SecExpertVacationVO getSecExpertWhenVacation(@Param("uPlaintextNum") String uPlaintextNum, @Param("secId") String secId);

    List<SecExpertVacationVO> getAllSecExpertWhenVacation(@Param("secId") String secId);

    List<SecExpertModel> getExpertListByIds(@Param("ids") List<String> ids);

    List<SecExpertModel> getOutExtractExpertList(@Param("avoidUser") List<String> avoidUser, @Param("avoidUnit") List<String> avoidUnit);

    SecExpertModel getSecExpertByuId(String uId);

    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query);
}
