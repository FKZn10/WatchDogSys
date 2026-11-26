package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesignTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysPortalDesignTemplateMapper  extends BaseMapper<SysPortalDesignTemplate> {
    List<SysPortalDesignTemplate> GetSysPortalDesignTemplateList(@Param("page") IPage<SysPortalDesignTemplate> page, @Param("params") Map<String, Object> params);
}
