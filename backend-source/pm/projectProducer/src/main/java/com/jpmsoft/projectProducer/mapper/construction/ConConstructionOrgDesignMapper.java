package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Construction.ConConstructionOrgDesignResponse;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConConstructionOrgDesign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConConstructionOrgDesignMapper extends BaseMapper<ConConstructionOrgDesign> {


    List<ConConstructionOrgDesignResponse> getConConstructionOrgDesignList(@Param("page") IPage<ConConstructionOrgDesignResponse> page, @Param("params") Map<String, Object> params);

    ConConstructionOrgDesignResponse getConConstructionOrgDesignById(@Param("codId") String codId);
}
