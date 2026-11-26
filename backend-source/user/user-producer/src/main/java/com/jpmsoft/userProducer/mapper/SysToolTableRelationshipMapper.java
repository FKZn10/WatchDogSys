package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysToolTableRelationshipMapper   extends BaseMapper<SysToolTableRelationship> {
    Integer deleteBath(@Param("sqls") List<String> sqls);
    Integer delete(@Param("sql") String sql);
    List<Map<String,Object>> querySql(@Param("sql") String sql);
    List<Map<String, Object>> queryList(String sql);
    Integer queryPageSize(@Param("sql") String sql);
    Integer insertBatch(@Param("sqls") List<String> sqls);
    Integer insertSql(@Param("sql") String sql);

}
