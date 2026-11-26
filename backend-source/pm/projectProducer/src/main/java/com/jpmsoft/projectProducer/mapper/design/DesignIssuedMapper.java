package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssued;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DesignIssuedMapper extends BaseMapper<DesignIssued> {
    List<DesignIssuedModel> getDesignIssuedList(@Param("params") DesignIssuedModel params);

    DesignResultsIssuedModel getDesignIssuedById(@Param("id") String id);
}
