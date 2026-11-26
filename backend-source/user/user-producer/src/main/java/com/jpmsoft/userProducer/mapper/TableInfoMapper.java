package com.jpmsoft.userProducer.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.TableInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.pojo.vo.TableForeignKeyVo;
import com.jpmsoft.userProducer.pojo.vo.TableInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface TableInfoMapper extends BaseMapper<TableInfo> {


    List<Map<String, Object>> selecttabledata(@Param("tableName") String tableName,@Param("key") String key,@Param("valueStr") String valueStr, @Param("start") Integer start, @Param("size") Integer size);


    Integer selecttabledatacount(@Param("tableName") String tableName,@Param("key") String key,@Param("valueStr") String valueStr);


    List<TableInfoVo> getTableInfoList(@Param("owner") String owner);
    List<TableInfoVo> selectSysTable(@Param("owner") String owner);
    List<TabcolumsVo> getTableColumn(@Param("owner") String owner,@Param("tableCode") String  tableCode);

    Integer insertTableAndColumn(@Param("sqlList") List<String> sqlList);

    List<String> getPatternName();
    List<TableInfoVo>  getTableByTableCode(@Param("owner") String owner,@Param("tableCode") String tableCode);
    List<TabcolumsVo>  getTableColumnByTableCode(@Param("owner") String owner,@Param("tableCode") String tableCode);
    List<TabcolumsVo>  getTableColumnByTableCode1(@Param("owner") String owner,@Param("tableCode") String tableCode);
    List<TabcolumsVo> getTableColumnbByTableCodeAndColumnCode(@Param("tableCode") String tableCode,@Param("columnCode") String columnCode);


    List<TableForeignKeyVo>  getTableForeignKey(@Param("owner") String owner);
}
