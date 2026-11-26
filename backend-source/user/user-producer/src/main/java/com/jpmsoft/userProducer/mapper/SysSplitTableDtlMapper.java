package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTableDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:04
 */
public interface SysSplitTableDtlMapper extends BaseMapper<SysSplitTableDtl> {


    @Select("select st.st_code from Sys_Split_Table st left join Sys_Split_Table_dtl std on st.id=std.st_id " +
            "where std.t_code=#{code} or std.table_code=#{code}")
    List<String> getSplitTableCode(@Param("code") String code);

    @Select("select st.st_code as stCode,std.* from Sys_Split_Table_dtl std left join Sys_Split_Table st on st.id=std.st_id")
    List<SysSplitTableDtlDTO> getAllSysSplitTableDtlDTO();
}
