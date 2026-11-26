package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysMessageReceive;
import com.jpmsoft.userProducer.pojo.vo.SysMessageReceiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:27
 */
public interface SysMessageReceiveMapper extends BaseMapper<SysMessageReceive> {

    List<SysMessageReceiveVo> getSysMessageReceiveList(@Param("page") IPage<SysMessageReceiveVo> page, @Param("params") Map<String, Object> params);

}
