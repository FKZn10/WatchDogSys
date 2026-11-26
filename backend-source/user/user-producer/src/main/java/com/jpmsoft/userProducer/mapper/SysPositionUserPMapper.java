package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.UserPositionPDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUserP;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPositionUserPMapper extends BaseMapper<SysPositionUserP> {
    Integer getMaxPupSort(@Param("pdId") String pdId);

    List<UserPositionPDTO> selectPositionPUser(@Param("pId") String pId, @Param("pdCodes") List<String> pdCodes);

    List<UserPositionPDTO> selectPositionPOwner(@Param("pId") String pId, @Param("yzdwoCode") String yzdwoCode, @Param("yzdwtCode") String yzdwtCode);
}
