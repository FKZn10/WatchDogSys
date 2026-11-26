package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/21 11:14
 */

public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    @Select("select * from Sys_Dictionary where D_ParentID in (select D_Id from Sys_Dictionary where D_Code=#{code})")
    List<SysDictionary> getDictionaryByParentCode(@Param("code") String code);

    List<SysDictionary> getDictionaryByParentCodes(@Param("codes")  List<String> codes);
}
