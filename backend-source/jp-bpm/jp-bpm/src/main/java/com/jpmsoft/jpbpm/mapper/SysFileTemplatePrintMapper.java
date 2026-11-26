package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplatePrint;
import com.jpmsoft.jpbpm.pojo.vo.SysFileTemplatePrintVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysFileTemplatePrintMapper extends BaseMapper<SysFileTemplatePrint> {

    List<SysFileTemplatePrintVo> getFileTemplatePrintList(@Param("params") Map<String, Object> params);
    Integer getFileTemplatePrintListCount(@Param("params") Map<String, Object> params);

    String getTablePkName(String tableName);

    /**
     * 根据表名、主键名和实体ID查询表数据
     *
     * @param tableName 表名，用于指定查询的表。
     * @param pkName 主键名，用于指定表的主键字段。
     * @param entityId 实体ID值，用于匹配主键字段的具体值。
     * @return 包含表数据的列表，每个元素是一个键值对形式的映射对象。
     */
    List<Map<String, Object>> getTableDataByIds(@Param("tableName") String tableName, @Param("pkName") String pkName, @Param("entityId") String entityId);

    /**
     * 根据表名、字段名和字段值查询表数据
     *
     * @param tableName 表名，用于指定查询的表。
     * @param fieldName 字段名，用于指定要查询的字段。
     * @param fieldValue 字段值，用于匹配指定字段的具体值。
     * @return 包含表数据的列表，每个元素是一个键值对形式的映射对象。
     */
    List<Map<String, Object>> getTableDataByField(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);
}
