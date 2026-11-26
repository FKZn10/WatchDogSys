package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkCommentDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpWorkCommentMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkComment;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpWorkCommentService;
import org.springframework.stereotype.Service;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 11:58
 */
@Service
public class ProjectCpWorkCommentImpl extends ServiceImpl<ProjectCpWorkCommentMapper, ProjectCpWorkComment>
        implements IProjectCpWorkCommentService {
    @Override
    public BaseResponse createOrUpdateWorkComment(ProjectCpWorkCommentDTO request) {
        ProjectCpWorkComment entity = new ProjectCpWorkComment();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getId())) {
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getWorkCommentList(ProjectCpWorkCommentDTO request) {
        QueryWrapper<ProjectCpWorkComment> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getCpwrId()))
            wrapper.eq("Cpwr_Id", request.getCpwrId());
        if (!ValidateUtil.isAllEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) wrapper.orderByAsc(request.getSortFiled());
            else wrapper.orderByDesc(request.getSortFiled());
        } else wrapper.orderByDesc("Create_Time");
        IPage<ProjectCpWorkComment> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse deleteById(String id) {
        ProjectCpWorkComment entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public void deleteByCpwrId(String cpwrId) {
        QueryWrapper<ProjectCpWorkComment> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpwr_Id", cpwrId);
        baseMapper.delete(wrapper);
    }
}
