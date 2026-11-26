package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MePriceList;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MePriceListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:33
 */
public interface MePriceListMapper extends BaseMapper<MePriceList> {

    List<MePriceListVo> getMePriceListList(@Param("page") IPage<MePriceListVo> page, @Param("params") Map<String, Object> params);


    MePriceListVo getMePriceListById(@Param("id") String id);
}
