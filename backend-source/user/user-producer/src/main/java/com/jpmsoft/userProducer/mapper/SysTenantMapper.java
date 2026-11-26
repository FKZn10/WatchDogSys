package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysTenant;
import com.jpmsoft.userProducer.pojo.vo.SysTenantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:34
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {


    List<SysTenantVO> getSysTenantList(@Param("page")  IPage<SysTenantVO> page,@Param("params") Map<String, Object> params);

}
