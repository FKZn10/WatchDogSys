package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysMessageMapper extends BaseMapper<SysMessage> {
    @Select(" select P_Name from  Pre_Project  WHERE  P_ID=#{pId};")
    String selectPnameByPid(@Param("pId") String pId);


    @Select("select Sec_Name from  Sec_Info where Sec_Id=#{secId};")
    String selectsecNameBySecId(@Param("secId") String secId);

    @Select("select Con_Name from  Con_Contract where Con_Id=#{conId};")
    String selectConNamaByConId(@Param("conId") String conId);
}
