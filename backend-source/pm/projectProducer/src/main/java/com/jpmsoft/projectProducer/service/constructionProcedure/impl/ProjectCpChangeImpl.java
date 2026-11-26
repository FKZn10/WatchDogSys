package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.proCp.ProjectCpChangeDTO;
import com.jpmsoft.common.model.response.proCp.ProjectCpChangeVO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChange;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpChangeDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpChangeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectCpChangeImpl extends ServiceImpl<ProjectCpChangeMapper, ProjectCpChange> implements IProjectCpChangeService {

    @Resource
    private IProjectCpChangeDtlService projectCpChangeDtlService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectCpChange(ProjectCpChangeDTO request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ProjectCpChange entity = new ProjectCpChange();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getId())) {
            QueryWrapper<ProjectCpChange> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            wrapper.eq("Cp_Id", request.getCpId());
            wrapper.ne("state", ApprovalStatusEnum.Invalid.getIndex());
            wrapper.ne("state", ApprovalStatusEnum.Already_Over.getIndex());
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.recordUnFinish);
            entity.setId(GenerateUUID.getUUID());
            entity.setCpCreateTime(new Date());
            baseMapper.insert(entity);
            projectCpChangeDtlService.createOrUpdateProCpChangeDtl(request.getProCpls(), entity.getId(), entity.getCpId());
            sysFileService.saveFile(entity.getId(), request.gettId(), request.getState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
            return BaseResponse.ok(entity.getId());
        } else {
            ProjectCpChange model = baseMapper.selectById(entity.getId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCpCreateTime(model.getCpCreateTime());
            entity.setCpCreateUid(model.getCpCreateUid());
            entity.setCpCreateUname(model.getCpCreateUname());
            entity.setPId(model.getPId());
            entity.setCpId(model.getCpId());
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            baseMapper.updateById(entity);
            projectCpChangeDtlService.createOrUpdateProCpChangeDtl(request.getProCpls(), entity.getId(), entity.getCpId());
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
            return BaseResponse.ok(entity.getId());
        }
    }

    @Override
    public BaseResponse getProCpChangeList(ProjectCpChangeDTO request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Cp_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        QueryMap<ProjectCpChangeDTO> params = getQueryMap(request, authSql);
        IPage<ProjectCpChangeVO> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpChangeVO> datas = baseMapper.getProCpChangeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCpChangeById(String id) {
        ProjectCpChange entity = baseMapper.selectById(id);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        projectCpChangeDtlService.deleteProCpChangeDtlByCpId(id);
        sysFileService.deleteFileById(id);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectCpChangeById(String id) {
        ProjectCpChangeVO data = baseMapper.getProjectCpChangeById(id);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getProjectCpChangeCplIdList(String id) {
        return BaseResponse.ok(projectCpChangeDtlService.getProjectCpChangeCplIdList(id));
    }

    @Override
    public BaseResponse approve2SyncProjectCpDtl(String id) {
        ProjectCpChange change = baseMapper.selectById(id);
        if (change == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(change.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        projectCpChangeDtlService.approve2SyncProjectCpDtl(id, change.getCpId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProCpChangeDtlTreeById(String id,String teId) {
        return projectCpChangeDtlService.getProCpChangeDtlTreeById(id,teId);
    }


    private QueryMap<ProjectCpChangeDTO> getQueryMap(ProjectCpChangeDTO request, String authSql) {
        QueryMap<ProjectCpChangeDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("state").put("pCode").put("pType").put("proState")
                .put("pRegion").put("pProjectStage").put("pConstructionUnit").put("pCompetentDepartment")
                .put("pBuildModel").put("pImpCompany").put("pUseUnit").put("pId").put("cpId");
        return params;
    }

}
