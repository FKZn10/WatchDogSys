package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.proCp.ProjectCpLibraryRiskResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpLibraryRiskMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibraryRisk;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpLibraryRiskService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 项目建设程序库-风险点防控措施关联表 Service 实现类
 */
@Service
public class ProjectCpLibraryRiskServiceImpl extends ServiceImpl<ProjectCpLibraryRiskMapper, ProjectCpLibraryRisk> implements IProjectCpLibraryRiskService {
    @Override
    public List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplId(String cplId) {
        return baseMapper.getProjectCpLibraryRiskByCplId(cplId);
    }

    @Override
    public List<ProjectCpLibraryRiskResponse> getProjectCpLibraryRiskByCplIds(List<String> cplIds) {
        if (ValidateUtil.isEmpty(cplIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getProjectCpLibraryRiskByCplIds(cplIds);
    }
}
