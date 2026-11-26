package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.OaAttendanceGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface OaAttendanceGroupMapper extends BaseMapper<OaAttendanceGroup> {

    @Select("select ag.Ag_Id,ag.Ag_Name from  OA_Attendance_Group ag\n" +
            "left join  OA_Attendance_Member am on am.Ag_Id=ag.Ag_Id\n" +
            "where am.Am_Uid=#{uId} and ag.Ag_State=1")
    List<OaAttendanceGroup> getOaAttendanceGroupByUid(@Param("uId") String uId);
}
