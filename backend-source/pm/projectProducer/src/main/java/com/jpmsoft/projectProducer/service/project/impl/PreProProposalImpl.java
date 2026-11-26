package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProProposalMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProProposal;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProProposalStatisticsVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProProposalService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PreProProposalImpl extends ServiceImpl<PreProProposalMapper, PreProProposal> implements IPreProProposalService {

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ppId", codeParam = "ppCode", method = "setPpCode", codeRule = "PreProProposal", beanClass = PreProProposalModel.class)
    public BaseResponse createOrUpdatePreProProposal(PreProProposalModel request) {
        PreProProposal entity = new PreProProposal();
        if (ValidateUtil.isEmpty(request.getPpCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<PreProProposal> wrapper = new QueryWrapper<>();
        wrapper.and(c -> c.eq("Pp_Pro_Name", entity.getPpProName())
                .or().eq("Pp_Code", entity.getPpCode()));
        wrapper.ne("Pp_State", ApprovalStatusEnum.Invalid.getIndex());
        if (!ValidateUtil.isEmpty(request.getPpId()))
            wrapper.ne("Pp_ID", request.getPpId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.nameCodeRepeat);

        if (ValidateUtil.isEmpty(entity.getPpId())) {
            entity.setPpCreateTime(new Date());
            entity.setPpId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPpId(), request.gettId(), entity.getPpState() + "", entity.getPpCreateUid(), entity.getPpCreateUname()
                    , entity.getPpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPpInvestmentFile()), entity.getPpId());

        } else {
            PreProProposal model = baseMapper.selectById(request.getPpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPpState(model.getPpState());
            entity.setPpStateTime(model.getPpStateTime());
            entity.setPpCreateTime(model.getPpCreateTime());
            entity.setPpCreateUid(model.getPpCreateUid());
            entity.setPpCreateUname(model.getPpCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPpId(), request.gettId(), entity.getPpState() + "", entity.getPpCreateUid(), entity.getPpCreateUname()
                    , entity.getPpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPpInvestmentFile()), Arrays.asList(request.getFileIds(), request.getPpInvestmentFileIds()), entity.getPpId());

        }
        return BaseResponse.ok(entity.getPpId());
    }

    @Override
    public BaseResponse getPreProProposalList(PreProProposalModel request, String uId) {
        QueryMap<PreProProposalModel> params = getFilter(request, uId);
        IPage<PreProProposalModel> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreProProposalStatisticsList(PreProProposalModel request, String uId) {
        QueryMap<PreProProposalModel> params = getFilter(request, uId);
        IPage<PreProProposalModel> page = getList(request, params);
        PreProProposalStatisticsVo data = baseMapper.getPpInvestmentSum(params.getQuery());
        if (data == null) data = new PreProProposalStatisticsVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    private QueryMap<PreProProposalModel> getFilter(PreProProposalModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Pro_Proposal", uId, "Pp_Create_Uid", OutUserAuthEnum.Only_Inner.getIndex(), "Pp_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<PreProProposalModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("ppName").put("ppCode").put("ppProName").put("ppIsParticipate")
                .put("ppEstimationUnitName").put("ppFeasibilityUnitName").put("ppEstimationUnitId")
                .put("ppFeasibilityUnitId").put("ppState").put("ppCreateUname").put("ppType").put("ppDepartment")
                .put(request.getPpInvestments(), "ppInvestmentStart", "ppInvestmentEnd")
                .put(request.getPpCreateTimes(), "ppCreateTimeStart", "ppCreateTimeEnd");
        return params;
    }

    private IPage<PreProProposalModel> getList(PreProProposalModel request, QueryMap<PreProProposalModel> params) {
        IPage<PreProProposalModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProProposalModel> datas = baseMapper.getPreProProposalList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }


    @Override
    public BaseResponse getPreProProposalById(String ppId) {
        PreProProposalModel data = baseMapper.getPreProProposalById(ppId);
        return BaseResponse.ok(data);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvePreProProposal(String ppId) {
        PreProProposal entity;
        SysFile fileentity;
        if (ValidateUtil.isEmpty(ppId))
            return BaseResponse.fail(ResMsg.notReqParam);
        else {
            entity = baseMapper.selectById(ppId);
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);

            fileentity = sysFileService.getById(ppId);
        }
        entity.setPpState(Integer.parseInt(fileentity.getFileflowstatus()));
        entity.setPpStateTime(new Date());
        baseMapper.updateById(entity);

        if (Objects.equals(entity.getPpState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            Date now = new Date();
            //审批通过生成项目
            PreProject projectPojo = new PreProject();
            projectPojo.setpId(GenerateUUID.getUUID());
            projectPojo.setpName(entity.getPpProName());
            projectPojo.setpCode(entity.getPpCode());
            projectPojo.setpCreateUserId(entity.getPpCreateUid());
            projectPojo.setpCreateTime(now);
            projectPojo.setpParentId("0");
            projectPojo.setpOverview(entity.getPpRemark());
            projectPojo.setpNameHistory(entity.getPpProName());
            projectPojo.setpCreateUserName(entity.getPpCreateUname());
            projectPojo.setpStatus("1");
            projectPojo.setState(ApprovalStatusEnum.Not_Started.getIndex());
            projectPojo.setStateTime(now);
            projectPojo.setpCompetentDepartment(entity.getPpDepartment());
            preProjectService.insertPreProject(projectPojo);
        }
        return BaseResponse.ok();
    }

}
