package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysFileMapper extends BaseMapper<SysFile> {

    @Select(" ${sql} ")
    String executeSql(@Param("sql") String sql);

    List<SysFile>  getUnJmArchive(@Param("size") int size);

    Integer insertFormData(@Param("sql") List<String> sql);

    Integer insertFormDataOne(@Param("sql") String sql);

    List<Map> selectByImportSQL(@Param("sql") String sql,@Param("codes") List<String> codes);

    List<Map> selectBySql(@Param("sql") String sql);
}
