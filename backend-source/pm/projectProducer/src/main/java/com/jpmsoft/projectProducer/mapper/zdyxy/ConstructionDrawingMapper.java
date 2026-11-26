package com.jpmsoft.projectProducer.mapper.zdyxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionDrawingModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionDrawing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface ConstructionDrawingMapper extends BaseMapper<ConstructionDrawing> {
    List<ConstructionDrawingModel> getList(@Param("params")Map<String, Object> query,
                                           @Param("page")IPage<ConstructionDrawingModel> page);

    ConstructionDrawingModel getDetail(@Param("id")String id);
}
