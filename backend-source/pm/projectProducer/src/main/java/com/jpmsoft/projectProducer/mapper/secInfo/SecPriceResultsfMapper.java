package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPriceResultsf;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecPriceResultsfMapper extends BaseMapper<SecPriceResultsf> {

    SecPriceResultsfModel getSecPriceResultsfBySprId(@Param("sprId") String sprId);

    List<SecPriceResultsfModel> getSecPriceResultsfList(@Param("page") IPage<SecPriceResultsfModel> page, @Param("params") Map<String, Object> params);

}
