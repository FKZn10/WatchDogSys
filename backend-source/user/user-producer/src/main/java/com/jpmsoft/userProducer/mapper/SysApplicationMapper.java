package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysApplication;
import com.jpmsoft.userProducer.pojo.vo.SysApplicationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/6 17:54
 */
public interface SysApplicationMapper extends BaseMapper<SysApplication> {

    List<SysApplicationVO> getSysApplicationList(@Param("page") IPage<SysApplicationVO> page, @Param("params") Map<String, Object> params);

}
