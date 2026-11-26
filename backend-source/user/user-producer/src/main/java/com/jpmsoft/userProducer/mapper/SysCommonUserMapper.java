package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysCommonUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户常用功能及个性化设置
 */
@Mapper
public interface SysCommonUserMapper extends BaseMapper<SysCommonUser> {
}
