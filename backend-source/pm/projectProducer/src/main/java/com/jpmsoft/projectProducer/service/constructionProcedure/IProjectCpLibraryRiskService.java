package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.proCp.ProjectCpLibraryRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibraryRisk;

import java.util.List;

/**
 * 项目建设程序库-风险点防控措施关联表 Service
 */
public interface IProjectCpLibraryRiskService extends IService<ProjectCpLibraryRisk> {
    List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplId(String cplId);

    List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplIds(List<String> cplIds);
}
