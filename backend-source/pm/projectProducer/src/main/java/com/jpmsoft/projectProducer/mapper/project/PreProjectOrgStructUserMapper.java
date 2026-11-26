package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目组织机构的单位人员
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface PreProjectOrgStructUserMapper extends BaseMapper<PreProjectOrgStructUser> {


    List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdList(@Param("orgStructUnitIdList") List<String> orgStructUnitIdList);


}

