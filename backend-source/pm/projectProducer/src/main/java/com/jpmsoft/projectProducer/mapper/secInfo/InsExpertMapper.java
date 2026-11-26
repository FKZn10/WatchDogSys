package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.InsExpert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ins_expert】的数据库操作Mapper
* @createDate 2025-04-24 09:01:40
* @Entity com.whjpsoft.pojo/entity.InsExpert
*/
public interface InsExpertMapper extends BaseMapper<InsExpert> {

    @Select("select distinct ${value} as dbValue from Ins_Expert")
    List<Object>   getInsExpertClassInfo(@Param("value") String value);
}




