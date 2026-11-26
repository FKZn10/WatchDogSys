package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceCurrentDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceCurrentCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysSequenceCurrentCodeMapper extends BaseMapper<SysSequenceCurrentCode> {

    List<SysSequenceCurrentDto> getSysSequenceCurrentCodeBySid(@Param("params") Map<String, Object> params);

    Integer getSysSequenceCurrentCodeBySidCount(@Param("params") Map<String, Object> params);


    @Delete("delete from  SYS_Sequence_Current_Code where S_Id=#{sId} and right(Cc_Reset,1)=#{restRule}")
    void deleteCurrentCodeByReset(@Param("sId") String sId, @Param("restRule") String restRule);

    @Select("select * from  SYS_Sequence_Current_Code where S_Id=#{sId} and right(Cc_Reset,1)=#{rule}")
    List<SysSequenceCurrentCode> getSysSequenceCurrentCodes(@Param("sId") String sId, @Param("rule") String rule);
}
