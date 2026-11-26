package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarnIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RiskWarnIndexMapper extends BaseMapper<RiskWarnIndex> {

   @Select("select * from  Risk_Warn_Index where  Rwi_Id  in ('${rwiId}');")
    List<RiskWarnIndex> selectListByRwiId(@Param("rwiId") String rwiId);
}
