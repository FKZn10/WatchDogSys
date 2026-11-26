package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysTenantPay;
import com.jpmsoft.userProducer.pojo.vo.SysTenantPayVO;
import com.jpmsoft.userProducer.pojo.vo.SysTenantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 14:40
 */
public interface SysTenantPayMapper extends BaseMapper<SysTenantPay> {

    List<SysTenantPayVO> getSysTenantPayList(@Param("page") IPage<SysTenantPayVO> page, @Param("params") Map<String, Object> params);

    SysTenantPayVO  getSysTenantPayById(@Param("id") String id);
}
