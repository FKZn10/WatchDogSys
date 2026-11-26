package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsGroundCollapse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface QsGroundCollapseMapper extends BaseMapper<QsGroundCollapse> {

    List<QsGroundCollapseModel> getQsGroundCollapseList(@Param("page") IPage<QsGroundCollapseModel> page, @Param("params") Map<String, Object> params);


    QsGroundCollapseModel getQsGroundCollapseById(@Param("gcId") String gcId);
}
