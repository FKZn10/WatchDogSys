package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.custom.pojo.entity.SysTableColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysTableColumnMapper extends BaseMapper<SysTableColumn> {


    @Select("select mn.* from  Sys_TableColumn mn left join  Sys_Table tb on mn.Table_ID=tb.Table_ID where upper(tb.Table_Code)=upper(#{tableCode})")
    List<SysTableColumn> getColumnListByTableCode(@Param("tableCode") String tableCode);
}
