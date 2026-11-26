package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Design.DesignInitialResponse;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitial;
import com.jpmsoft.projectProducer.pojo.vo.design.DesignInitialMoneySumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignInitialMapper extends BaseMapper<DesignInitial> {

    List<DesignInitialResponse> getDesignInitialList(@Param("page") IPage<DesignInitialResponse> page, @Param("params") Map<String, Object> params);

    DesignInitialMoneySumVo getDesignInitialMoneySum(@Param("params") Map<String, Object> params);

    DesignInitialResponse getDesignInitialById(@Param("diId") String diId);
}
