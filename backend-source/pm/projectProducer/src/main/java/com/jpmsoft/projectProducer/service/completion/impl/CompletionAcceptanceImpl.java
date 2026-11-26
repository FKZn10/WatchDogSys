package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.common.model.response.Construction.CompletionAcceptanceResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.completion.CompletionAcceptanceMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptance;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContactList;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaFee;
import com.jpmsoft.projectProducer.pojo.vo.completion.CompletionAcceptanceMoneySumVo;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceCompanyService;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceContractService;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceSectionService;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceService;
import com.jpmsoft.projectProducer.service.contract.IConContactListService;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.contract.IConVisaFeeService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CompletionAcceptanceImpl extends FileImpl<CompletionAcceptanceMapper, CompletionAcceptance, CompletionAcceptanceRequest> implements ICompletionAcceptanceService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ICompletionAcceptanceCompanyService completionAcceptanceCompanyService;
    @Resource
    private ICompletionAcceptanceSectionService completionAcceptanceSectionService;
    @Resource
    private ICompletionAcceptanceContractService completionAcceptanceContractService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IConContactListService conContactListService;
    @Resource
    private IConEcService conEcService;
    @Resource
    private IConVisaFeeService conVisaFeeService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateCompletionAcceptance(CompletionAcceptanceRequest request) {
        CompletionAcceptance entity = new CompletionAcceptance();
        createOrUpdateFileEntity(entity, request);
        completionAcceptanceCompanyService.createOrUpdateCompletionAcceptanceCompany(request.getCompAccepCompanys(), entity.getCaId());
        completionAcceptanceSectionService.createOrUpdateCompletionAcceptanceSection(request.getCompletionAcceptanceSections(), entity.getCaId());
        completionAcceptanceContractService.createOrUpdateCompletionAcceptanceContract(request.getCompletionAcceptanceContracts(), entity.getCaId());
        return BaseResponse.ok(entity.getCaId());
    }

    @Override
    public BaseResponse getCompletionAcceptanceList(CompletionAcceptanceRequest request, String uId) {
        QueryMap<CompletionAcceptanceRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<CompletionAcceptanceResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionAcceptanceStatisticsList(CompletionAcceptanceRequest request, String uId) {
        QueryMap<CompletionAcceptanceRequest> params = getFilter(request);
        params.put("authSql", getSelfAuthSql(uId));
        IPage<CompletionAcceptanceResponse> page = getList(request, params);
        CompletionAcceptanceMoneySumVo data = baseMapper.getCompletionAcceptanceMoneySum(params.getQuery());
        if (data == null) data = new CompletionAcceptanceMoneySumVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionAcceptanceListNoAuth(CompletionAcceptanceRequest request) {
        QueryMap<CompletionAcceptanceRequest> params = getFilter(request);
        IPage<CompletionAcceptanceResponse> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionAcceptanceById(String caId) {
        CompletionAcceptanceResponse data = baseMapper.getCompletionAcceptanceById(caId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getCompletionAcceptanceLastByPidAndType(CompletionAcceptanceRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        return BaseResponse.ok(baseMapper.getCompletionAcceptanceLastByPidAndType(request));
    }

    @Override
    public BaseResponse approve2RefreshProStage(String caId) {
        CompletionAcceptance entity = baseMapper.selectById(caId);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getCaState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        String code = sysConfigService.getConfigByCode("projectStageCompletedCode");
        if (ValidateUtil.isEmpty(code))
            return BaseResponse.fail(String.format(ResMsg.notConf, "projectStageCompletedCode"));
        preProjectService.updateProjectStage(entity.getPId(), code);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse checkCompletionAcceptance(String conId) {

        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        int conEcCount = Math.toIntExact(conEcService.count(new LambdaQueryWrapper<ConEc>()
                .eq(ConEc::getConId, conId).ne(ConEc::getEcState, ApprovalStatusEnum.Invalid.getIndex())));
        int conVisaFeeCount = Math.toIntExact(conVisaFeeService.count(new LambdaQueryWrapper<ConVisaFee>()
                .eq(ConVisaFee::getConId, conId).ne(ConVisaFee::getVfState, ApprovalStatusEnum.Invalid.getIndex())));

        return BaseResponse.ok(conEcCount + conVisaFeeCount == 0);
    }

    private String getSelfAuthSql(String uId) {
        String authSql = sysUserService.getAuthSql("Completion_Acceptance", uId, "Ca_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ca_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        return authSql;
    }

    private QueryMap<CompletionAcceptanceRequest> getFilter(CompletionAcceptanceRequest request) {
        QueryMap<CompletionAcceptanceRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("conName").put("caType").put("caContractorId").put("secName")
                .put("caSupervisorId").put("caState").put("caCreateUname").put("caContractorName")
                .put(request.getCaCreateTimes(), "caCreateTimeStart", "caCreateTimeEnd").put("secGroupName")
                .put(request.getCaDates(), "caDateStart", "caDateEnd").put("caApplicantName");
        return params;
    }

    private IPage<CompletionAcceptanceResponse> getList(CompletionAcceptanceRequest request, QueryMap<CompletionAcceptanceRequest> params) {
        IPage<CompletionAcceptanceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionAcceptanceResponse> datas = baseMapper.getCompletionAcceptanceList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }

}
