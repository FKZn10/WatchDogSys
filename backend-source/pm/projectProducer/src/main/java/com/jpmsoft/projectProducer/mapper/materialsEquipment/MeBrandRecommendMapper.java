package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandRecommend;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandRecommendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:45
 */
public interface MeBrandRecommendMapper extends BaseMapper<MeBrandRecommend> {

    List<MeBrandRecommendVo> getMeBrandRecommendList(@Param("page") IPage<MeBrandRecommendVo> page,
                                                   @Param("params") Map<String, Object> params);

    MeBrandRecommendVo getMeBrandRecommendById(@Param("id") String id);
}
