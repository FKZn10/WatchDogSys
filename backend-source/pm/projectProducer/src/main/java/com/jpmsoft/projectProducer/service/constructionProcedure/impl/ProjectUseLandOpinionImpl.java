package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.ProjectUseLandOpinionRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectUseLandOpinionMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectUseLandOpinion;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectUseLandOpinionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ProjectUseLandOpinionImpl extends
        ServiceImpl<ProjectUseLandOpinionMapper, ProjectUseLandOpinion>
        implements IProjectUseLandOpinionService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * ..项目用地预审意见
     */
    public BaseResponse createProjectUseLandOpinion(ProjectUseLandOpinionRequest request) {
        request.setUuid(GenerateUUID.getUUID());
        if (ValidateUtil.isNull(request.getApproveStatus())) {
            request.setApproveStatus("0");
        }
        ProjectUseLandOpinion entity = new ProjectUseLandOpinion();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        baseMapper.insert(entity);
        //调用流程
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "create", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }

    public BaseResponse getProjectUseLandOpinionList(ProjectUseLandOpinionRequest request) {
        QueryWrapper<ProjectUseLandOpinion> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            queryWrapper.eq("Te_Id", request.getTeId());
        if (ValidateUtil.isNotEmpty(request.getProductName())) {
            queryWrapper.like("Product_Name", request.getProductName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateName())) {
            queryWrapper.like("Create_Name", request.getCreateName());
        }
        queryWrapper.orderByDesc("Create_Time");
        IPage<ProjectUseLandOpinion> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    public BaseResponse getProjectUseLandOpinionInfoById(String uuId) {
        ProjectUseLandOpinion projectUseLandOpinionPojo = baseMapper.selectById(uuId);
        return BaseResponse.ok(projectUseLandOpinionPojo);
    }

    public BaseResponse deleteProjectUseLandOpinion(String uuId) {

        baseMapper.deleteById(uuId);
        return BaseResponse.ok();
    }

    public BaseResponse updateProjectUseLandOpinion(ProjectUseLandOpinionRequest request) {
        ProjectUseLandOpinion entity = new ProjectUseLandOpinion();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getUuid(), request.gettId(), entity.getApproveStatus() + "", entity.getCreateId(), entity.getCreateName()
                , entity.getCreateTime(), request.getProductId(), request.getFileTitle(), request.getFileCode(),
                "update", request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUuid());
        return BaseResponse.ok(request.getUuid());
    }

    public BaseResponse updateApproveStatusProjectUseLandOpinion(ProjectUseLandOpinionRequest request) {
        ProjectUseLandOpinion projectUseLandOpinionPojo = baseMapper.selectById(request.getUuid());
        SysFile File = sysFileService.getById(request.getUuid());
        projectUseLandOpinionPojo.setApproveTime(new Date());
        projectUseLandOpinionPojo.setApproveStatus(File.getFileflowstatus());
        baseMapper.updateById(projectUseLandOpinionPojo);
        return BaseResponse.ok();
    }

}
