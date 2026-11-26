package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysPower;
import com.jpmsoft.userProducer.pojo.vo.PowerCheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPowerMapper extends BaseMapper<SysPower> {


    List<PowerCheckVO> getPowerCheckByRoleAndCode(@Param("roleId") String roleId,@Param("code") String code);


   void BatchSavePower(@Param("list") List<SysPower> list);
}
