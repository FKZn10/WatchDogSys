package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目问题登记表
 */

public interface OaProblemMapper extends BaseMapper<OaProblem> {


    List<OaProblemModel> getOaProblemList(@Param("page") IPage<OaProblemModel> page, @Param("params") Map<String, Object> params);

    OaProblemModel getOaProblemById(@Param("pmId") String pmId);

}
