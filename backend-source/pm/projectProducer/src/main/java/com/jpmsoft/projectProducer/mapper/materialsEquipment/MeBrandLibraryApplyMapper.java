package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibraryApply;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandLibraryApplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:06
 */
public interface MeBrandLibraryApplyMapper extends BaseMapper<MeBrandLibraryApply> {

    List<MeBrandLibraryApplyVo> getMeBrandLibraryApplyList(@Param("page") IPage<MeBrandLibraryApplyVo> page,
                                                           @Param("params") Map<String, Object> params);

    MeBrandLibraryApplyVo getMeBrandLibraryApplyById(@Param("id") String id);
}
