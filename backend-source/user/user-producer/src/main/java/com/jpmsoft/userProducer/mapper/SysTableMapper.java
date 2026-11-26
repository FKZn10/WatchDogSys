package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysTableMapper extends BaseMapper<SysTable> {


    @Delete(" drop table #{schema}.#{table}")
    Integer dropTable(String schema, String table);


    Integer createTable(@Param("table") SysTable table, @Param("columns") List<SysTableColumn> columns, @Param("pk") String pk);

    Integer createUniqueIndex(@Param("table") SysTable table, @Param("pk") String pk);


    @Update("update   ${tableDb}.${tablaName} set ${sqlStr} where ${primaryKey} = '${id}'")
    Integer updateTableDataById(@Param("tableDb") String tableDb, @Param("tablaName") String tablaName, @Param("sqlStr") String sqlStr, @Param("primaryKey") String primaryKey, @Param("id") String id);


    void deleteTableData(@Param("sqlStr") String sqlStr);

    void createTableSimple(@Param("tableDb") String tableDb, @Param("tableCode") String tableCode, @Param("tablePk") String tablePk);
    void createTableMin(@Param("tableDb") String tableDb, @Param("tableCode") String tableCode, @Param("tablePk") String tablePk);


    void updateTableName(@Param("tableDb") String tableDb, @Param("oldTableName") String oldTableName, @Param("newTableName") String newTableName);


    void createTableColumn(@Param("tableDb") String tableDb, @Param("tableCode") String tableCode, @Param("columnCode") String columnCode, @Param("columnLength") Integer columnLength, @Param("columnType") String columnType);

    void updateTableColumnName(@Param("tableDb") String tableDb, @Param("tableCode") String tableCode, @Param("oldColumnName") String oldColumnName,@Param("newColumnName") String newColumnName);

    void updateTableColumnType(@Param("tableDb") String tableDb, @Param("tableCode") String tableCode, @Param("columnCode") String columnCode, @Param("columnType") String columnType,@Param("columnLength") Integer columnLength);
}
