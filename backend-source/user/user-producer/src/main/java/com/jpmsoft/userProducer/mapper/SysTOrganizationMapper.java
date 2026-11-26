package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysTOrganization;
import com.jpmsoft.userProducer.pojo.vo.SysTOrganizationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:52
 */
public interface SysTOrganizationMapper extends BaseMapper<SysTOrganization> {

    List<SysTOrganizationVo> getSysTOrganizationList(@Param("page") IPage<SysTOrganizationVo> page, @Param("params") Map<String, Object> params);

    SysTOrganizationVo getSysTOrganizationById(@Param("id") String id);
}
