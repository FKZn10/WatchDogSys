package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysMessageShield;
import com.jpmsoft.userProducer.pojo.vo.SysMessageShieldVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 9:40
 */
public interface SysMessageShieldMapper extends BaseMapper<SysMessageShield> {

    List<SysMessageShieldVo> getSysMessageShieldList(@Param("page") IPage<SysMessageShieldVo> page,
                                                     @Param("params") Map<String, Object> params);
}
