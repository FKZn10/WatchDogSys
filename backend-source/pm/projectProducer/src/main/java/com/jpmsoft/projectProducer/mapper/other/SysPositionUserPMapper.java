package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.UserPositionPDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysPositionUserP;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/18 13:29
 */
public interface SysPositionUserPMapper extends BaseMapper<SysPositionUserP> {


    @Select("     select " +
            "        d.PD_Code as  pdCode, " +
            "        p.PP_ID as ppId, " +
            "        p.PP_Code as ppCode " +
            "        from " +
            "        SYS_Position_P p " +
            "        left join SYS_Department_P d on p.PD_ID=d.PD_ID " +
            "        WHERE d.P_ID=#{pId} and (p.Pp_Code=#{jtLd} or p.Pp_Code=#{jtJbr} or p.Pp_Code=#{gldwXmjl} or p.Pp_Code=#{gldwFgld} " +
            "or p.Pp_Code=#{xmfzoCode} or p.Pp_Code=#{xmfgoCode} or p.Pp_Code=#{xmfztCode} or p.Pp_Code=#{xmfgtCode}) ")
    List<UserPositionPDTO> selectPositionP(@Param("pId") String pId, @Param("jtLd") String jtLd, @Param("jtJbr") String jtJbr,
                                           @Param("gldwXmjl") String gldwXmjl, @Param("gldwFgld") String gldwFgld,
                                           @Param("xmfzoCode") String xmfzoCode, @Param("xmfgoCode") String xmfgoCode,
                                           @Param("xmfztCode") String xmfztCode, @Param("xmfgtCode") String xmfgtCode);




    @Select("    select " +
            "    p.PP_ID as ppId, " +
            "    p.pp_code as ppCode, " +
            "    d.PD_Code as pdCode, " +
            "    d.owner_unit as ownerUnit, " +
            "    u.U_ID as uid " +
            "    from " +
            "    SYS_User u " +
            "    left join SYS_Position_User_P pu on u.U_ID=pu.U_ID " +
            "    left join SYS_Position_P p on pu.PP_ID=p.PP_ID " +
            "    left join SYS_Department_P d on d.PD_ID=p.PD_ID " +
            "    where d.P_ID=#{pId} and (p.Pp_Code=#{jtLd} or p.Pp_Code=#{jtJbr} or p.Pp_Code=#{gldwXmjl} or p.Pp_Code=#{gldwFgld}" +
            "or p.Pp_Code=#{xmfzoCode} or p.Pp_Code=#{xmfgoCode} or p.Pp_Code=#{xmfztCode} or p.Pp_Code=#{xmfgtCode}) ")
    List<UserPositionPDTO> selectPositionPUser(@Param("pId") String pId, @Param("jtLd") String jtLd, @Param("jtJbr") String jtJbr,
                                               @Param("gldwXmjl") String gldwXmjl, @Param("gldwFgld") String gldwFgld,
                                               @Param("xmfzoCode") String xmfzoCode, @Param("xmfgoCode") String xmfgoCode,
                                               @Param("xmfztCode") String xmfztCode, @Param("xmfgtCode") String xmfgtCode);
}
