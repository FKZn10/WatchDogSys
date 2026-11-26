package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFileName;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:08
 */
public interface ArchiveFileNameMapper extends BaseMapper<ArchiveFileName> {



    @Select("select ${code} from pre_project where p_id=#{id}")
    String selectProject(@Param("id") String id,@Param("code") String code);


    @Select("select ${code} from con_contract where con_id=#{id}")
    String selectContract(@Param("id") String id,@Param("code") String code);


    @Select("select ${code} from sec_info where sec_id=#{id}")
    String selectSecinfo(@Param("id") String id,@Param("code") String code);

    @Select("select ${code} from ${table} where ${pk}=#{id}")
    String selectCurrent(@Param("id") String id,@Param("code") String code,@Param("table") String table,@Param("pk") String pk);

    @Select("${sql}")
    String selectSql(@Param("sql") String sql);

}
