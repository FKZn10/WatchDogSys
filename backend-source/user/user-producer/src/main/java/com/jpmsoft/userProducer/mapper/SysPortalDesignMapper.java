package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysPortalDesignMapper extends BaseMapper<SysPortalDesign> {
    List<SysPortalDesign> GetSysPortalDesignList(@Param("page") IPage<SysPortalDesign> page, @Param("params") Map<String, Object> params);
}


