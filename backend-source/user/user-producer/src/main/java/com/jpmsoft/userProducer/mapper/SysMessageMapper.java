package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysMessage;
import com.jpmsoft.userProducer.pojo.vo.SysMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:27
 */
public interface SysMessageMapper extends BaseMapper<SysMessage> {

    SysMessageVo getSysMessageById(@Param("id") String id);


    List<SysMessageVo> getSysMessageList(@Param("page") IPage<SysMessageVo> page, @Param("params") Map<String, Object> params);
}
