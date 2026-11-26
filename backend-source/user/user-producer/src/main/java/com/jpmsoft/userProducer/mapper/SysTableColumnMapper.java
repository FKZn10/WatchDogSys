package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysTableColumnMapper extends BaseMapper<SysTableColumn> {


    @Select("select mn.* from  Sys_TableColumn mn left join  Sys_Table tb on mn.Table_ID=tb.Table_ID where upper(tb.Table_Code)=#{tableCode}")
    List<SysTableColumn> getColumnListByTableCode(@Param("tableCode") String tableCode);

    @Update("update Sys_TableColumn set ${action}=#{enable} where Table_Id=#{tableId}")
    Integer setColumnExpOrImp(String tableId, String action, Boolean enable);
}
