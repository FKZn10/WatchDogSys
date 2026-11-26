package com.jpmsoft.projectProducer.mapper.blxw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlxwTypeMapper extends BaseMapper<BlxwType> {
    @Select("select * from  Blxw_Type where Blxw_Parent_Id=#{parentId} and Blxw_Sort>#{blxwSort}")
    List<BlxwType> selectSameLevelBySort(@Param("parentId")String blxwParentId,@Param("blxwSort")Integer blxwSort);
}
