package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysCommonGlobal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 全局默认功能菜单或外部系统入口
 */
@Mapper
public interface SysCommonGlobalMapper extends BaseMapper<SysCommonGlobal> {
}
