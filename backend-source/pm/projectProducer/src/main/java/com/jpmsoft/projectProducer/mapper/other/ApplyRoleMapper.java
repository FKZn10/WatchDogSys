package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.ApplyRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ApplyRoleMapper extends BaseMapper<ApplyRole> {



    @Select("select count(*) from SYS_Department_P where p_id=#{pid}")
    Integer havingDepartmentP(@Param("pid") String pid);


    @Select("select count(*) from SYS_Position_User_P where pp_id=#{ppId} and u_id=#{uid}")
    Integer havingPositionUserP(@Param("ppId") String ppId,@Param("uid") String uid);

    @Select("select PP_ID from SYS_Position_P pp left join SYS_Department_P pd on pd.PD_ID=pp.PD_ID where pd.P_ID=#{pid} and pp.PP_Code=#{ppCode}")
    String getDepartmentPPosition(@Param("pid") String pid,@Param("ppCode") String ppCode);
}
