package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysToolDataBackupMapper extends BaseMapper<SysToolDataBackup> {

    Integer deleteBath(@Param("sqls") List<String> sqls);
    List<SysToolDataBackup> getSysToolDataBackup(@Param("params") Map<String, Object> params);
    Integer getSysToolDataBackupCount(@Param("params") Map<String, Object> params);

    List<Map<String,Object>> querySql(@Param("sql") String sql);
    Integer queryPageSize(@Param("sql") String sql);
    Integer delete(@Param("sql") String sql);
}
