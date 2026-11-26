package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignGeologyModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignGeology;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignGeologyMapper extends BaseMapper<DesignGeology> {

    List<DesignGeologyModel> getDesignGeologyList(@Param("page") IPage<DesignGeologyModel> page, @Param("params") Map<String, Object> params);

    DesignGeologyModel getDesignGeologyById(@Param("dgId") String dgId);
}
