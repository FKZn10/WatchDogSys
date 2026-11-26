package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.SysPayApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPayApplication;
import com.jpmsoft.userProducer.pojo.vo.SysTenantApplicationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 15:13
 */
public interface SysPayApplicationMapper extends BaseMapper<SysPayApplication> {

    List<SysPayApplicationDTO> getSysPayApplicationList(@Param("page") IPage<SysPayApplicationDTO> page, @Param("params") Map<String, Object> params);

}
