package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnitChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目组织机构单位变更表(PreProjectOrgStructUnitChange)表数据库访问层
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface PreProjectOrgStructUnitChangeMapper extends BaseMapper<PreProjectOrgStructUnitChange> {

    List<PreProjectOrgStructUnitResponse> getByOrgStructChangeId(@Param("orgStructId") String orgStructId);


    List<PreProjectOrgStructUnitResponse> getByOrgStructChangeIds(@Param("ids") List<String> ids);
}

