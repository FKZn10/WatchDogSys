package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SecExpert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SecExpertMapper extends BaseMapper<SecExpert> {

    @Select("select * from Sec_Expert where Ex_Id in (select Ex_Id from Sec_Selection_Record_Dtl where Sr_Id=#{srId})")
    List<SecExpert> getSecExpertBySrId(@Param("srId") String srId);
}
