package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.proCp.ProjectCpExperienceRequest;
import com.jpmsoft.common.model.response.proCp.ProCpExperienceResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpExperienceMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpExperience;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpExperienceService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProjectCpExperienceImpl extends ServiceImpl<ProjectCpExperienceMapper, ProjectCpExperience> implements IProjectCpExperienceService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IProjectCpDtlService projectCpDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProCpExperience(ProjectCpExperienceRequest request) {
        ProjectCpExperience entity = new ProjectCpExperience();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getCpeId())) {
            if (ValidateUtil.isEmpty(entity.getCpdId()))
                return BaseResponse.fail(ResMsg.notReqParam);
            entity.setCpeId(GenerateUUID.getUUID());
            entity.setCpeCreateTime(new Date());
            baseMapper.insert(entity);
            projectCpDtlService.updateProCpDtlAllParentState(null, entity.getCpdId(), ApprovalStatusEnum.Under_Approval.getIndex(),null);
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.getCpId(), request.gettCode());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getCpeId());
            return BaseResponse.ok();
        } else {
            ProjectCpExperience model = baseMapper.selectById(entity.getCpeId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCpeCreateTime(model.getCpeCreateTime());
            entity.setCpeCreateUname(model.getCpeCreateUname());
            entity.setCpeCreateUid(model.getCpeCreateUid());
            entity.setCpdId(model.getCpdId());
            baseMapper.updateById(entity);
            return BaseResponse.ok();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCpExperienceById(String cpeId) {
        ProjectCpExperience model = baseMapper.selectById(cpeId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(cpeId);
        sysFileService.deleteFileById(cpeId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProCpExperienceList(ProjectCpExperienceRequest request) {
        QueryMap<ProjectCpExperienceRequest> params = new QueryMap<>(request);
        params.put("cpdId").put("pName").put("pId").put("cpdName").put("cpeCreateUname").put("cpeContent");
        IPage<ProCpExperienceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProCpExperienceResponse> datas = baseMapper.getProCpExperienceList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public Integer getCpExperienceCountByCpdId(String cpdId) {
        QueryWrapper<ProjectCpExperience> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpd_Id", cpdId);
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public Integer getCpExperienceCountByCpId(String cpId) {
        if (ValidateUtil.isEmpty(cpId))
            return 0;
        return baseMapper.getCpExperienceCountByCpId(cpId);
    }
}
