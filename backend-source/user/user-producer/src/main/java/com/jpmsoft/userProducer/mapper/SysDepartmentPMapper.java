package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysDepartmentP;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysDepartmentPMapper extends BaseMapper<SysDepartmentP> {

    @Select("select distinct d.P_Id from SYS_Department_P  d " +
            "left join SYS_Position_P p on p.PD_ID=d.PD_ID " +
            "left join SYS_Position_User_P pup on pup.PP_ID=p.PP_ID where pup.U_Id=#{uId}")
    List<String> getUserAllSysDepPProject(String uId);

    Integer getMaxSort(@Param("projectId")String projectId);

    Set<String> getPdCodeByCIds(@Param("pId") String pId, @Param("cIdList") Set<String> cIdList);
}
