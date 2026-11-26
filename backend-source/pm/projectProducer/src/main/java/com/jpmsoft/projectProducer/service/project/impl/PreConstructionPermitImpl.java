package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitUnitModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreConstructionPermitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreConstructionPermit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.project.PreConstructionPermitStatisticsVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreConstructionPermitService;
import com.jpmsoft.projectProducer.service.project.IPreConstructionPermitUnitService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PreConstructionPermitImpl extends ServiceImpl<PreConstructionPermitMapper, PreConstructionPermit> implements IPreConstructionPermitService {
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IPreConstructionPermitUnitService preConstructionPermitUnitService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePreConstructionPermit(PreConstructionPermitModel request) {
        PreConstructionPermit entity = new PreConstructionPermit();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getCpId())) {
            entity.setCpCreateTime(new Date());
            entity.setCpId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getCpId(), request.gettId(), entity.getCpState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),
                    request.getCpApprovalDoc(), request.getCpCommApp(), request.getCpHeadquarters(), request.getCpApprovalDoc(), request.getCpConsPermit(),
                    request.getCpQualitySafety(), request.getCpFundProve(), request.getCpHandleProve(), request.getCpContractFile(), request.getCpPerfoPayProve()
                    , request.getCpCompletionProve(), request.getCpPayrollAccount(), request.getCpBillQuantities()), entity.getCpId());

        } else {
            PreConstructionPermit model = baseMapper.selectById(request.getCpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getCpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setCpState(model.getCpState());
            entity.setCpStateTime(model.getCpStateTime());
            entity.setCpCreateTime(model.getCpCreateTime());
            entity.setCpCreateUid(model.getCpCreateUid());
            entity.setCpCreateUname(model.getCpCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getCpId(), request.gettId(), entity.getCpState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(),
                            request.getCpProApprDoc(), request.getCpCommApp(), request.getCpHeadquarters(), request.getCpApprovalDoc(), request.getCpConsPermit(),
                            request.getCpQualitySafety(), request.getCpFundProve(), request.getCpHandleProve(), request.getCpContractFile(), request.getCpPerfoPayProve()
                            , request.getCpCompletionProve(), request.getCpPayrollAccount(), request.getCpBillQuantities()),
                    Arrays.asList(request.getFileIds(), request.getCpProApprDocIds(), request.getCpCommAppIds(), request.getCpHeadquartersIds(), request.getCpApprovalDocIds(), request.getCpConsPermitIds(),
                            request.getCpQualitySafetyIds(), request.getCpFundProveIds(), request.getCpHandleProveIds(), request.getCpContractFileIds(), request.getCpPerfoPayProveIds()
                            , request.getCpCompletionProveIds(), request.getCpPayrollAccountIds(), request.getCpBillQuantitiesIds()), entity.getCpId());

        }
        preConstructionPermitUnitService.createOrUpdatePreConstructionPermitUnit(request.getPreConstructionPermitUnits(), entity.getCpId());
        return BaseResponse.ok(entity.getCpId());
    }

    @Override
    public BaseResponse getPreConstructionPermitList(PreConstructionPermitModel request, String uId) {
        QueryMap<PreConstructionPermitModel> params = getFilter(request, uId);
        IPage<PreConstructionPermitModel> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreConstructionPermitStatisticsList(PreConstructionPermitModel request, String uId) {
        QueryMap<PreConstructionPermitModel> params = getFilter(request, uId);
        IPage<PreConstructionPermitModel> page = getList(request, params);
        PreConstructionPermitStatisticsVo data = baseMapper.getCpContractPriceSum(params.getQuery());
        if (data == null) data = new PreConstructionPermitStatisticsVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    private QueryMap<PreConstructionPermitModel> getFilter(PreConstructionPermitModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Construction_Permit", uId, "Cp_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Cp_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<PreConstructionPermitModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conId")
                .put("conName").put("cpCreateCName").put("cpCreateCid").put("cpState")
                .put("cpCreateUname").put("cpProjectAddress").put("cpFixedPrice").put("cpOverview")
                .put(request.getCpContractPrices(), "cpContractPriceStart", "cpContractPriceEnd")
                .put(request.getCpStartTimes(), "cpStartTimeStart", "cpStartTimeEnd")
                .put(request.getCpEndTimes(), "cpEndTimeStart", "cpEndTimeEnd")
                .put(request.getCpCreateTimes(), "cpCreateTimeStart", "cpCreateTimeEnd");
        return params;
    }

    private IPage<PreConstructionPermitModel> getList(PreConstructionPermitModel request, QueryMap<PreConstructionPermitModel> params) {
        IPage<PreConstructionPermitModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreConstructionPermitModel> datas = baseMapper.getPreConstructionPermitList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }

    @Override
    public BaseResponse getPreConstructionPermitById(String cpId) {
        PreConstructionPermitModel data = baseMapper.getPreConstructionPermitById(cpId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPreConstructionPermitUnitListByPid(String pId) {
        List<PreConstructionPermitModel> datas = baseMapper.getPreConstructionPermitListByPid(pId);
        List<PreConstructionPermitUnitModel> PreConstructionPermitUnitModelList = new ArrayList<>();
        for (PreConstructionPermitModel data : datas) {
            PreConstructionPermitUnitModelList.addAll(data.getPreConstructionPermitUnits());
        }
        return BaseResponse.ok(PreConstructionPermitUnitModelList);
    }

    @Override
    public BaseResponse approval2RefershProjectInfo(String cpId) {
        PreConstructionPermit entity = baseMapper.selectById(cpId);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (entity.getCpState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProject pro = preProjectService.getById(entity.getPId());
        if (pro == null) return BaseResponse.fail(ResMsg.infoNotExist);
        pro.setpLatitude(entity.getCpLatitude());
        pro.setpLongitude(entity.getCpLongitude());
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        pro.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), pro));
        preProjectService.updateById(pro);
        return BaseResponse.ok();
    }
}
