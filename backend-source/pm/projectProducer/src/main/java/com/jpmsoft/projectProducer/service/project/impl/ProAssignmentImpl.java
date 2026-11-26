package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Project.ProAssignmentModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProAssignmentMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDepartment;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.ProAssignment;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.project.IProAssignmentService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProAssignmentImpl extends ServiceImpl<ProAssignmentMapper, ProAssignment> implements IProAssignmentService {
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysDepartmentService sysDepartmentService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "paId", codeParam = "paCode", method = "setPaCode", codeRule = "ProAssignment", beanClass = ProAssignmentModel.class)
    public BaseResponse createOrUpdateProAssignment(ProAssignmentModel request) {
        ProAssignment entity = new ProAssignment();
        if (ValidateUtil.isEmpty(request.getPaCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<ProAssignment> wrapper = new QueryWrapper<>();
        wrapper.eq("Pa_Code", entity.getPaCode());
        if (!ValidateUtil.isEmpty(request.getPaId()))
            wrapper.ne("Pa_ID", request.getPaId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(entity.getPaId())) {
            entity.setPaCreateTime(new Date());
            entity.setPaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPaId(), request.gettId(), entity.getPaState() + "", entity.getPaCreateUid(), entity.getPaCreateUname()
                    , entity.getPaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPaId());

        } else {
            ProAssignment model = baseMapper.selectById(request.getPaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPaState(model.getPaState());
            entity.setPaStateTime(model.getPaStateTime());
            entity.setPaCreateTime(model.getPaCreateTime());
            entity.setPaCreateUid(model.getPaCreateUid());
            entity.setPaCreateUname(model.getPaCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPaId(), request.gettId(), entity.getPaState() + "", entity.getPaCreateUid(), entity.getPaCreateUname()
                    , entity.getPaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPaId());

        }
        return BaseResponse.ok(entity.getPaId());
    }

    @Override
    public BaseResponse getProAssignmentList(ProAssignmentModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pro_Assignment", uId, "Pa_Create_Uid", OutUserAuthEnum.Only_Inner.getIndex(), "Pa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ProAssignmentModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode")
                .put("paImplementationStatus").put("paApprovalNo").put("paUnitId")
                .put("paApprovalUnit").put("paConcentrateUnitId").put("paContent")
                .put("paCreateUname").put("paState").put("paStartYear").put("paEndYear")
                .put("paPayAudit").put("paBidCompiling").put("paAgentWay");
        IPage<ProAssignmentModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProAssignmentModel> datas = baseMapper.getProAssignmentList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProAssignmentById(String paId) {
        ProAssignmentModel data = baseMapper.getProAssignmentById(paId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse syncPCompetentDepartment(String paId) {
        ProAssignment pojo = baseMapper.selectById(paId);
        if (pojo == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isEmpty(pojo.getPaUnitId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (!Objects.equals(pojo.getPaState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("D_Company_Id", pojo.getPaUnitId());
        SysDepartment sd = sysDepartmentService.getOne(wrapper);
        if (sd != null) {
            PreProject pro = preProjectService.getById(pojo.getPId());
            pro.setpCompetentDepartment(sd.getdId());
            //项目数据完整性验证
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            pro.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), pro));
            preProjectService.updateById(pro);
        }
        return BaseResponse.ok();
    }
}
