package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecJGExpertMapper extends BaseMapper<SecJGExpert> {


    List<SecJGExpertModel> getSecExpertList(@Param("params") Map<String, Object> params,@Param("page") IPage<SecJGExpertModel> page);


    SecJGExpertModel getSecJGExpertById(@Param("exId") String exId);


    List<SecJGExpertModel> getExpertListByIds(List<String> ids);


    List<SecJGExpertModel> getExtractExpertList(@Param("exInsideType") Integer exInsideType,@Param("excludeIds") List<String> excludeIds,
                                                @Param("excludeCpy") String excludeCpy, @Param("teId") String teId, @Param("monStart") String monStart,
                                                @Param("monEnd") String monEnd,@Param("monthExNum") Integer monthExNum,
                                                @Param("avoidCompanyIds") List<String> avoidCompanyIds);

    List<SecJGExpert> getByExIdNumberSecJgExpert(@Param("idNumberList") List<String> idNumberList);

}
