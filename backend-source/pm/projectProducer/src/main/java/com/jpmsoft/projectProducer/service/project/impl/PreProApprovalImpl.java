package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProFundingSourceModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApproval;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProApprovalStatisticsVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProApprovalDtlService;
import com.jpmsoft.projectProducer.service.project.IPreProApprovalService;
import com.jpmsoft.projectProducer.service.project.IPreProjectRegisterDtlService;
import com.jpmsoft.projectProducer.service.project.IProFundingSourceService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PreProApprovalImpl extends ServiceImpl<PreProApprovalMapper, PreProApproval> implements IPreProApprovalService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IProFundingSourceService proFundingSourceService;

    @Resource
    private IPreProApprovalDtlService preProApprovalDtlService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "paId", codeParam = "paCode", method = "setPaCode", codeRule = "PreProApproval", beanClass = PreProApprovalModel.class)
    public BaseResponse createOrUpdatePreProApproval(PreProApprovalModel request) {
        PreProApproval entity = new PreProApproval();
        if (ValidateUtil.isEmpty(request.getPaCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<PreProApproval> wrapper = new QueryWrapper<>();
        wrapper.and(c -> c.eq("Pa_Name", entity.getPaName())
                .or().eq("Pa_Code", entity.getPaCode()));
        if (!ValidateUtil.isEmpty(request.getPaId()))
            wrapper.ne("Pa_ID", request.getPaId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.nameCodeRepeat);
        if (ValidateUtil.isEmpty(entity.getPaId())) {
            entity.setPaCreateTime(new Date());
            entity.setPaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPaId(), request.gettId(), entity.getPaState() + "", entity.getPaCreateUid(), entity.getPaCreateUname()
                    , entity.getPaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPaGroupDoc(), request.getPaReformDoc(), request.getPaSasacDoc()), entity.getPaId());

        } else {
            PreProApproval model = baseMapper.selectById(request.getPaId());
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
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPaGroupDoc(), request.getPaReformDoc(), request.getPaSasacDoc())
                    , Arrays.asList(request.getFileIds(), request.getPaGroupDocIds(), request.getPaReformDocIds(), request.getPaSasacDocIds()), entity.getPaId());

        }
        proFundingSourceService.createOrUpdateProFundingSource(request.getProFundingSources(), entity.getPaId());
        preProApprovalDtlService.createOrUpdatePreProApprovalDtl(request.getPreProApprovalDtl(), entity.getPaId());
        return BaseResponse.ok(entity.getPaId());
    }

    @Override
    public BaseResponse getPreProApprovalList(PreProApprovalModel request, String uId) {
        QueryMap<PreProApprovalModel> params = getFilter(request, uId);
        IPage<PreProApprovalModel> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreProApprovalStatisticsList(PreProApprovalModel request, String uId) {
        QueryMap<PreProApprovalModel> params = getFilter(request, uId);
        IPage<PreProApprovalModel> page = getList(request, params);
        PreProApprovalStatisticsVo data = baseMapper.getPaTotalInvSum(params.getQuery());
        if (data == null) data = new PreProApprovalStatisticsVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    private QueryMap<PreProApprovalModel> getFilter(PreProApprovalModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Pro_Approval", uId, "Pa_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Pa_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<PreProApprovalModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pCode").put("paCode").put("paName").put("pId")
                .put("paType").put("paConstructionUnitId").put("paConstructionUnitName").put("paPmId")
                .put("paPmName").put("paPmTel").put("paNature").put("paState").put("paCreateUname").put("paIsParticipate")
                .put(request.getPaTotalInvs(), "paTotalInvStart", "paTotalInvEnd")
                .put(request.getPaCreateTimes(), "paCreateTimeStart", "paCreateTimeEnd");
        return params;
    }

    private IPage<PreProApprovalModel> getList(PreProApprovalModel request, QueryMap<PreProApprovalModel> params) {
        IPage<PreProApprovalModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProApprovalModel> datas = baseMapper.getPreProApprovalList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }


    @Override
    public BaseResponse getPreProApprovalById(String paId) {
        PreProApprovalModel data = baseMapper.getPreProApprovalById(paId);
        if(data != null){
            List<PreProApprovalDtlModel> child = preProApprovalDtlService.getPreProApprovalDtlByPrId(data.getPaId());
            data.setPreProApprovalDtl(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPreProApprovalByPid(String pId) {
        PreProApprovalModel data = baseMapper.getPreProApprovalByPid(pId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPreProApprovalWYById(String paId) {
        PreProApprovalModel data = baseMapper.getPreProApprovalById(paId);
        if (data.getProFundingSources() != null && data.getProFundingSources().size() > 0) {
            List<ProFundingSourceModel> funds = new ArrayList<>();
            data.getProFundingSources().forEach(fund -> {
                fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                funds.add(fund);
            });
            data.setProFundingSources(funds);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPreProApprovalDtlByPIds(List<String> pIds) {
        if (pIds == null || pIds.size() < 1) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<PreProApproval> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", pIds.get(0));
        for (int i = 1; i < pIds.size(); i++) {
            wrapper.or().eq("P_ID", pIds.get(i));
        }
        List<PreProApproval> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<String> pId = new ArrayList<>();
            List<String> padFids = new ArrayList<>();
            datas.forEach(data -> {
                pId.add(data.getPId());
                padFids.add(data.getPaId());
            });
            Map<String, List<PreProApprovalDtlModel>> map = preProApprovalDtlService.getPreProApprovalDtlModelByBdIds(pId, padFids);
            return BaseResponse.ok(map);
        }
        return BaseResponse.ok(null);
    }
}
