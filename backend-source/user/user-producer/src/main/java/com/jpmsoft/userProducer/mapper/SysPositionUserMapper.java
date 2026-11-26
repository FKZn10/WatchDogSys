package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUser;
import com.jpmsoft.userProducer.pojo.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysPositionUserMapper extends BaseMapper<SysPositionUser> {


    List<SysUserVo> GetUserPositionList(@Param("dId") String dId, @Param("uName") String uName, @Param("sortFiled") String sortFiled,
                                        @Param("sortType") String sortType, @Param("start") Integer start, @Param("size") Integer size);

    Integer GetUserPositionListCount(@Param("dId") String dId, @Param("uName") String uName);


    /**
     * 组织机构部门ID获取用户ID
     *
     * @param dIds
     * @return
     */
    List<String> getUserIdsByDids(@Param("dIds") List<String> dIds);

    @Select(" select max(pu.Pu_Sort) from SYS_Position_User pu" +
            "  left join SYS_Position p on pu.P_ID=p.P_ID where p.D_ID=#{dId} ")
    Integer getMaxPuSort(@Param("dId") String dId);


    @Update("update SYS_Position_User set Pu_Main_Dep=false where U_Id=#{uId}")
    Integer disableOtherMainDep(@Param("uId") String uId);


    @Select("select pu_Id from SYS_Position_User pu left join SYS_Position p on p.P_Id=pu.P_Id where p.D_Id=#{dId} and pu.Pu_Main_Duty=true")
    List<String> getCurrentDepAllMainDuty(@Param("dId") String dId);

    Integer disableMainDuty(@Param("ids") List<String> ids);
}
