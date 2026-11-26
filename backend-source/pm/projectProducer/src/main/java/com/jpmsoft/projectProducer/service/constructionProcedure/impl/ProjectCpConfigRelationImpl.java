package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpConfigRelationMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpConfigRelation;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpConfigRelationService;
import org.springframework.stereotype.Service;

/**
 * 建设程序模板项目类型配置
 */
@Service
public class ProjectCpConfigRelationImpl extends ServiceImpl<ProjectCpConfigRelationMapper, ProjectCpConfigRelation> implements IProjectCpConfigRelationService {
}
