package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibrary;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandLibraryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/23 9:53
 */
public interface MeBrandLibraryMapper extends BaseMapper<MeBrandLibrary> {

    List<MeBrandLibraryVo> getMeBrandLibraryList(@Param("page") IPage<MeBrandLibraryVo> page,
                                                 @Param("params") Map<String, Object> params);

    MeBrandLibraryVo getMeBrandLibraryById(@Param("id") String id);

    List<MeBrandLibraryVo> getMeBrandLibraryByMetId(@Param("metId")  String metId);

    @Select("select  id from Me_BrandLibrary where Bla_Id=#{blaId}")
    List<String> getAllIdByBlaId(@Param("blaId") String blaId);
}
