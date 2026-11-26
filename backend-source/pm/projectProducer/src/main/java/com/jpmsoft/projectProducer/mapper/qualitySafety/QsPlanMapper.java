package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface QsPlanMapper extends BaseMapper<QsPlan> {

    List<QsPlanModel> getQsPlanList(@Param("page") IPage<QsPlanModel> page, @Param("params") Map<String, Object> params);

    QsPlanModel getQsPlanById(@Param("qspId") String qspId);
}
