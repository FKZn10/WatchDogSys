package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {
    @Select("select * from Sys_Dictionary where D_ParentID in (select D_Id from Sys_Dictionary where D_Code=#{code})")
    List<SysDictionary> getDictionaryByParentCode(@Param("code") String code);
}
