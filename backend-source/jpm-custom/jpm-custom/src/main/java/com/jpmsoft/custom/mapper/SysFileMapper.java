package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.custom.pojo.entity.SysFile;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


public interface SysFileMapper extends BaseMapper<SysFile> {

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


    @Select("select st.st_code as stCode,std.* from Sys_Split_Table_dtl std left join Sys_Split_Table st on st.id=std.st_id")
    List<SysSplitTableDtlDTO> getAllSysSplitTableDtlDTO();
}
