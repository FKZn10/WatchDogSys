package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawing;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignDrawingMapper extends BaseMapper<DesignDrawing> {


    List<DesignDrawingModel> getDesignDrawingList(@Param("page") IPage<DesignDrawingModel> page, @Param("params") Map<String, Object> params);


    DesignDrawingModel getDesignDrawingById(@Param("ddId") String ddId);
}
