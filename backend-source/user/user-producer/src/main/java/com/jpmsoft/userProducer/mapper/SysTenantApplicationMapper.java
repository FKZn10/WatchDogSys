package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysTenantApplication;
import com.jpmsoft.userProducer.pojo.vo.SysTenantApplicationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:42
 */
public interface SysTenantApplicationMapper extends BaseMapper<SysTenantApplication> {

    List<SysTenantApplicationVO> getSysTenantApplicationList(@Param("page") IPage<SysTenantApplicationVO> page,@Param("params") Map<String, Object> params);

}
