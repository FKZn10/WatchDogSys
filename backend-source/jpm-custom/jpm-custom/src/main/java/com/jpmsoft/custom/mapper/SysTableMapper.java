package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.custom.pojo.dto.TableColumnDTO;
import com.jpmsoft.custom.pojo.entity.SysTable;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysTableMapper extends BaseMapper<SysTable> {


    @Delete(" drop table #{schema}.#{table}")
    Integer dropTable(String schema, String table);

    Integer createTable(@Param("tbName") String tbName, @Param("pk") String pk, @Param("columns") List<TableColumnDTO> columns);

    Integer alterTable(@Param("columns") List<TableColumnDTO> columns, @Param("tbName") String tbName, @Param("action") String action);

    Integer renameColumn(@Param("columns") List<TableColumnDTO> columns, @Param("tbName") String tbName);


    @Select("select * from Sys_Table where upper(table_code)=upper(#{tableCode}) limit 1")
    SysTable getByCode(@Param("tableCode") String tableCode);
}

