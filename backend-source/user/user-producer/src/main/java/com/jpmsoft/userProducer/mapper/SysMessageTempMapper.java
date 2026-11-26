package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysMessageTemp;
import com.jpmsoft.userProducer.pojo.vo.SysMessageTempVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 13:57
 */
public interface SysMessageTempMapper extends BaseMapper<SysMessageTemp> {
    SysMessageTempVo getSysMessageTempById(@Param("id") String id);

    SysMessageTempVo getSysMessageTempByCode(@Param("code") String code);

    List<SysMessageTempVo> getSysMessageTempList(@Param("page") IPage<SysMessageTempVo> page, @Param("params") Map<String, Object> params);
}
