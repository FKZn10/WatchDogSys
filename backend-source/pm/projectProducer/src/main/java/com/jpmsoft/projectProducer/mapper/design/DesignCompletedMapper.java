package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignCompletedModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignCompleted;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignCompletedMapper extends BaseMapper<DesignCompleted> {

    List<DesignCompletedModel> getDesignCompletedList(@Param("page") IPage<DesignCompletedModel> page,
                                                      @Param("params") Map<String, Object> params);

    DesignCompletedModel getDesignCompletedById(@Param("dcId") String dcId);
}
