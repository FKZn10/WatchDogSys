package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.proCp.ProjectCpLibraryRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibraryRisk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目建设程序库-风险点防控措施关联表 Mapper
 */
@Mapper
public interface ProjectCpLibraryRiskMapper extends BaseMapper<ProjectCpLibraryRisk> {
    /**
     * 通过cplId获取风险关联
     *
     * @param cplId
     * @return
     */
    List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplId(@Param("cplId") String cplId);

    /**
     * 通过cplIds获取风险关联
     *
     * @param cplIds
     * @return
     */
    List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplIds(@Param("cplIds") List<String> cplIds);
}
