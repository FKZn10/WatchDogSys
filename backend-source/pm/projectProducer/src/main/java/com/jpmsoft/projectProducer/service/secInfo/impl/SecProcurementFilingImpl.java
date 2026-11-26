package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProcurementFilingModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProcurementFilingMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementFiling;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProject;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecFundingSourceService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementFilingService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SecProcurementFilingImpl extends ServiceImpl<SecProcurementFilingMapper, SecProcurementFiling> implements ISecProcurementFilingService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecFundingSourceService secFundingSourceService;
    @Resource
    private ISysUserService sysUserService;


    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private UserProducerFeign userProducerFeign;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecProcurementFiling(SecProcurementFilingModel request) {
        SecProcurementFiling entity = new SecProcurementFiling();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getPfId())) {
            entity.setPfId(GenerateUUID.getUUID());
            entity.setPfCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPfId(), request.gettId(), entity.getPfState() + "", entity.getPfCreateUid(), entity.getPfCreateUname()
                    , entity.getPfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPfCalibrationFile(), request.getPfMeetingFile(), request.getPfProcurementFile()
                    , request.getPfInstructionsFile(), request.getPfLawFile(), request.getPfNoticeFile(), request.getPfScopeFile(), request.getPfLowerFile()
                    , request.getPfControlFile(), request.getPfDurationFile(), request.getPfBidLetterFile(), request.getPfApproveFile(), request.getPfAwardFile(),request.getPfMeetingJtFile()), entity.getPfId());

        } else {
            SecProcurementFiling model = baseMapper.selectById(request.getPfId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPfCreateTime(model.getPfCreateTime());
            entity.setPfCreateUid(model.getPfCreateUid());
            entity.setPfCreateUname(model.getPfCreateUname());
            entity.setPfState(model.getPfState());
            entity.setPfStateTime(model.getPfStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPfId(), request.gettId(), entity.getPfState() + "", entity.getPfCreateUid(), entity.getPfCreateUname()
                    , entity.getPfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPfCalibrationFile(), request.getPfMeetingFile(), request.getPfProcurementFile()
                            , request.getPfInstructionsFile(), request.getPfLawFile(), request.getPfNoticeFile(), request.getPfScopeFile(), request.getPfLowerFile()
                            , request.getPfControlFile(), request.getPfDurationFile(), request.getPfBidLetterFile(), request.getPfApproveFile(), request.getPfAwardFile(),request.getPfMeetingJtFile()),
                    Arrays.asList(request.getFileIds(), request.getPfCalibrationFileIds(), request.getPfMeetingFileIds(), request.getPfProcurementFileIds()
                            , request.getPfInstructionsFileIds(), request.getPfLawFileIds(), request.getPfNoticeFileIds(), request.getPfScopeFileIds(), request.getPfLowerFileIds()
                            , request.getPfControlFileIds(), request.getPfDurationFileIds(), request.getPfBidLetterFileIds(), request.getPfApproveFileIds(), request.getPfAwardFileIds(),request.getPfMeetingJtFileIds()), entity.getPfId());
        }
        secFundingSourceService.createOrUpdateSecFundingSource(request.getSecFundingSources(), entity.getPfId());
        return BaseResponse.ok(entity.getPfId());
    }

    @Override
    public BaseResponse getSecProcurementFilingList(SecProcurementFilingModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Sec_Procurement_Filing", uId, "Pf_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Pf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SecProcurementFilingModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("pfPermit").put("pType").put("pfSecName")
                .put("pfPmName").put("pfTelephone").put("pfState").put("pfBidding").put("pfCalibration").put("pfAgreeChange");
        IPage<SecProcurementFilingModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecProcurementFilingModel> datas = baseMapper.getSecProcurementFilingList(page, params.getQuery());
        return BaseResponse.ok(datas, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getSecProcurementFilingById(String pfId) {
        SecProcurementFilingModel data = baseMapper.getSecProcurementFilingById(pfId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecProcurementFilingLastByPid(String pId) {
        return BaseResponse.ok(baseMapper.getSecProcurementFilingLastByPid(pId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approve2GenerateSecInfo(String pfId) {
        SecProcurementFilingModel data = baseMapper.getSecProcurementFilingById(pfId);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(data.getPfState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        FileTemplateTree tmp = userProducerFeign.getFileTemplateByCode("SectionInfo");
        if (tmp == null) throw new JPMException(String.format(ResMsg.notFoundFileTmp, "SectionInfo"));
        SecInfo sec = new SecInfo();
        sec.setSecId(GenerateUUID.getUUID());
        sec.setSecName(data.getPfSecName());
        sec.setSecPid(data.getpId());
        sec.setSecBiddingType(data.getPfBidding());
        sec.setSecMoney(data.getPfAmount());
        sec.setSecStartTime(data.getPfPlanStart());
        sec.setSecEndTime(data.getPfPlanEnd());
        sec.setSecDeterminType(data.getPfCalibration());
        sec.setSecRate(BigDecimal.valueOf(data.getPfLower()));
        sec.setSecFlowState(data.getPfState());
        sec.setSecFlowTime(data.getPfStateTime());
        sec.setSecCreateTime(new Date());
        sec.setSecCreateUid(data.getPfCreateUid());
        sec.setSecCreateUname(data.getPfCreateUname());
        secInfoService.save(sec);
        sysFileService.saveFile(sec.getSecId(), tmp.gettId(), sec.getSecFlowState() + "", sec.getSecCreateUid(), sec.getSecCreateUname()
                , sec.getSecCreateTime(), data.getpId(), tmp.gettName(), tmp.gettCode(), "create",
                GenerateUUID.getUUID(), sec.getSecId(), tmp.gettCode(), null);
        SecProject secp = new SecProject();
        secp.setSecId(sec.getSecId());
        secp.setPId(data.getpId());
        secp.setSpId(GenerateUUID.getUUID());
        secp.setSecInfoCreateUid(data.getPfCreateUid());
        secProjectService.save(secp);
        return BaseResponse.ok();
    }
}
