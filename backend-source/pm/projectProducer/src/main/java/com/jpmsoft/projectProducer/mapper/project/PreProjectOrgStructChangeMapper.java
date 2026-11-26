package com.jpmsoft.projectProducer.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructChange;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProjectOrgStructChangeVo;
import org.apache.ibatis.annotations.Param;

/**
 * 项目组织机构（变更表）
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface PreProjectOrgStructChangeMapper extends BaseMapper<PreProjectOrgStructChange> {


    IPage<PreProjectOrgStructChangeVo> selectData(@Param("page") Page<PreProjectOrgStructChangeVo> page,@Param("pId") String pId);

    PreProjectOrgStructResponse getPreProjectOrgStructChangeById(@Param("id") String id);
}

