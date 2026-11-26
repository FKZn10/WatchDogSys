package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnit;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Past;
import java.util.List;

/**
 * 项目组织机构单位表(PreProjectOrgStructUnit)表数据库访问层
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface PreProjectOrgStructUnitMapper extends BaseMapper<PreProjectOrgStructUnit> {


    List<PreProjectOrgStructUnitResponse> getByOrgStructId(@Param("orgStructId") String orgStructId);

}

