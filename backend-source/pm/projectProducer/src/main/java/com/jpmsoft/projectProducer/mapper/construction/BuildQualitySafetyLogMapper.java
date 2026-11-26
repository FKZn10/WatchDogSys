package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.construction.BuildQualitySafetyLog;
import com.jpmsoft.projectProducer.pojo.vo.construction.BuildQualitySafetyLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 17:48
 */
public interface BuildQualitySafetyLogMapper extends BaseMapper<BuildQualitySafetyLog> {

    List<BuildQualitySafetyLogVo> getBuildQualitySafetyLogList(@Param("page") IPage<BuildQualitySafetyLogVo> page, @Param("params") Map<String, Object> params);


    BuildQualitySafetyLogVo getBuildQualitySafetyLogById(@Param("id") String id);

}




