package com.jpmsoft.gmgws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.gmgws.pojo.entity.SysFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface SysFileMapper extends BaseMapper<SysFile> {

    @Update("update  SYS_File set FileFlowStatus=#{state} where FileID=#{fileId}")
    Integer onlyUpdateState(@Param("state") Integer state, @Param("fileId") String fileId);

    @Insert(" ${sql} ")
    Integer sql2Insert(@Param("sql") String sql);

    @Delete(" ${sql} ")
    Integer sql2Delete(@Param("sql") String sql);

    @Update(" ${sql} ")
    Integer sql2Upate(@Param("sql") String sql);

    Integer deleteBath(@Param("sqls") List<String> sqls);

    Integer insertBath(@Param("sqls") List<String> sqls);

    Map<String, Object> querySql(@Param("sql") String sql);

    List<Map<String, Object>> queryList(String sql);

    List<Map<String, Object>> queryPage(@Param("sql") String sql, @Param("params") Map<String, Object> params, @Param("start") int start, @Param("size") int size);
    Map<String, Object> sumQuery(@Param("sql") String sql);

    Integer queryPageSize(@Param("sql") String sql);
}
