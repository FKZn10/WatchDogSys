package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexComponentDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexComponent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysPortalIndexComponentMapper extends BaseMapper<SysPortalIndexComponent> {

    List<SysPortalIndexComponentDto> getSysPortalIndexComponentByMainIdsList(@Param("mainIds") List<String> mainIds);

    List<SysPortalIndexComponent> getSysPortalIndexComponentByRoleIds(@Param("roleIds") Set<String> roleIds);
}
