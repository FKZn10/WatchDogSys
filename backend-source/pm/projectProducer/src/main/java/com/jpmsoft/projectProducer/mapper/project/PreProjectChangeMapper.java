package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreProjectChangeMapper extends BaseMapper<PreProjectChange> {

    List<PreProjectChangeModel> getPreProjectChangeList(@Param("page") IPage<PreProjectChangeModel> page,@Param("params") Map<String, Object> params);


    PreProjectChangeModel getPreProjectChangeById(@Param("pcId") String pcId);

}
