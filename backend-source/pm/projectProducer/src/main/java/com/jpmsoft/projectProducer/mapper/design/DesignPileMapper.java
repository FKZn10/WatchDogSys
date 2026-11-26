package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignPile;
import com.jpmsoft.projectProducer.pojo.vo.design.DesignPileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:57
 */

public interface DesignPileMapper extends BaseMapper<DesignPile> {

    List<DesignPileVo> getDesignPileList(@Param("page") IPage<DesignPileVo> page, @Param("params") Map<String, Object> params);


    DesignPileVo getDesignPileById(@Param("dpId") String dpId);
}
