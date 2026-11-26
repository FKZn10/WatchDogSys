package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUserChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目组织机构的单位人员变更(PreProjectOrgStructUserChange)表数据库访问层
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
public interface PreProjectOrgStructUserChangeMapper extends BaseMapper<PreProjectOrgStructUserChange> {

    List<PreProjectOrgStructUserResponse> getByOrgStructUnitChangeIdList(@Param("orgStructUnitIdList") List<String> orgStructUnitIdList);

    List<PreProjectOrgStructUserResponse> getByOrgStructUnitChangeIdsList(@Param("orgStructUnitIdList") List<String> orgStructUnitIdList,@Param("id") String id);
}

