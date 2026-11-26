package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProcurementFilingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementFiling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecProcurementFilingMapper extends BaseMapper<SecProcurementFiling> {


    List<SecProcurementFilingModel> getSecProcurementFilingList(@Param("page") IPage<SecProcurementFilingModel> page,@Param("params") Map<String, Object> params);

    SecProcurementFilingModel getSecProcurementFilingById(@Param("pfId") String pfId);

    SecProcurementFilingModel getSecProcurementFilingLastByPid(@Param("pId") String pId);
}
