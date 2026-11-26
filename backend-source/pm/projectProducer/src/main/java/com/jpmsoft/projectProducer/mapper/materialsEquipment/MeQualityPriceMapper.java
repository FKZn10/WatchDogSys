package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeQualityPrice;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeQualityPriceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:50
 */
public interface MeQualityPriceMapper extends BaseMapper<MeQualityPrice> {

    List<MeQualityPriceVo> getMeQualityPriceList(@Param("page") IPage<MeQualityPriceVo> page,
                                                   @Param("params") Map<String, Object> params);

    MeQualityPriceVo getMeQualityPriceById(@Param("id") String id);
}
