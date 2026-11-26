package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStruct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目组织机构(PreProjectOrgStruct)表数据库访问层
 *
 * @author c
 * @since 2024-12-11 17:39:09
 */
public interface PreProjectOrgStructMapper extends BaseMapper<PreProjectOrgStruct> {


    List<PreProjectOrgStructResponse> getPreProjectOrgStructList(@Param("params") Map<String, Object> params, @Param("page") IPage<PreProjectOrgStructResponse> page);

    PreProjectOrgStructResponse getPreProjectOrgStructById(@Param("id") String id);

}

