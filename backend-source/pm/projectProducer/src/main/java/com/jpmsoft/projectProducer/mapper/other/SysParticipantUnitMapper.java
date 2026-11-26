package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysParticipantUnit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 15:09
 */
public interface SysParticipantUnitMapper extends BaseMapper<SysParticipantUnit> {

    /**
     * 查询项目单位是否已存在
     *
     * @param pId  项目ID
     * @param code 单位统一社会信用代码
     * @param type 单位类型
     * @return
     */
    @Select("select count(*) from SYS_Participant_Unit pu left join SYS_Company c1 on c1.C_Id=pu.C_Id" +
            " where pu.P_Id=#{pId} and c1.C_CreditCode=#{code} and pu.C_Type=#{type} ")
    Integer getAlreadyHaveCount(@Param("pId") String pId, @Param("code") String code, @Param("type") String type);
}
