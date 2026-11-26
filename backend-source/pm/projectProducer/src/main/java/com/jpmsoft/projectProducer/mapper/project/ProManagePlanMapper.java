package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProManagePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:19
 */
public interface ProManagePlanMapper extends BaseMapper<ProManagePlan> {

    List<ProManagePlanDTO> getProManagePlanList(@Param("page") IPage<ProManagePlanDTO> page, @Param("params") Map<String, Object> params);

    ProManagePlanDTO getProManagePlanById(@Param("id") String id);
}
