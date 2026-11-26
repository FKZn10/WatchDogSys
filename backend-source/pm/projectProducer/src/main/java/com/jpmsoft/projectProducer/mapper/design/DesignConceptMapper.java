package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConcept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignConceptMapper extends BaseMapper<DesignConcept> {

    List<DesignConceptModel> getDesignConceptList(@Param("page") IPage<DesignConceptModel> page, @Param("params") Map<String, Object> params);

    DesignConceptModel getDesignConceptById(@Param("dcId") String dcId);
}
