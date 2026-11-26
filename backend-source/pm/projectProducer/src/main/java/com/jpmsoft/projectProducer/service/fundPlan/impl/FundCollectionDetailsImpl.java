package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundCollectionDetailsMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundCollectionDetailsDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundCollectionDetails;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.*;
import com.jpmsoft.projectProducer.service.fundPlan.IFundCollectionDetailsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:35
 */
@Service
public class FundCollectionDetailsImpl extends BaseFileImpl<FundCollectionDetailsMapper, FundCollectionDetails, FundCollectionDetailsDto>
        implements IFundCollectionDetailsService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateFundCollectionDetails(FundCollectionDetailsDto request) {
        FundCollectionDetails entity = new FundCollectionDetails();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getFundCollectionDetailsList(FundCollectionDetailsDto request, String uId) {
        String authSql = sysUserService.onlyCreateorAndApprover("Fund_Collection_Details", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State","id");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<FundCollectionDetailsDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("state").put("createUname")
                .put("pBuildModel").put("pId");
        IPage<FundCollectionDetailsVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundCollectionDetailsVo> datas = baseMapper.getFundCollectionDetailsList(page, params.getQuery());
        FundCollectionDetailsStatisticVo data = baseMapper.getFundCollectionDetailsStatistic(params.getQuery());
        if (data == null) data = new FundCollectionDetailsStatisticVo();
        data.setData(datas);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getFundCollectionProjectList(PreProjectRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Fund_Collection_Details", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pBuildModel").put("pId")
                .put("pType").put("pCompetentDepartmentName").put("pImpCompany")
                .put("pConstructionUnitName");
        IPage<FundCollectionProjectVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundCollectionProjectVo> datas = baseMapper.getFundCollectionProjectList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getFundCollectionDetailsById(String id) {
        return BaseResponse.ok(baseMapper.getFundCollectionDetailsById(id));
    }

    @Override
    public BaseResponse getLastFundCollectionDetailsByPid(String pId) {
        return BaseResponse.ok(baseMapper.getLastFundCollectionDetailsByPid(pId));
    }

    @Override
    public BaseResponse statisticsBatchNoAndAmt(String pId) {
        StatisticsFundCollectionBatchNoAndAmtVo data = baseMapper.statisticsBatchNoAndAmt(pId);
        if (data == null) data = new StatisticsFundCollectionBatchNoAndAmtVo();
        data.setBatchNo(data.getBatchNo() + 1);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProjectFundCollectionDetailsList(PreProjectRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Project", uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<PreProjectRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pConstructionUnitName").put("pCompetentDepartmentName")
                .put("pBuildModel").put("pType").put("pImpCompany").put("state").put("pId");
        IPage<ProjectFundCollectionDetailsVo> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectFundCollectionDetailsVo> results = baseMapper.getProjectFundCollectionDetailsList(page, params.getQuery());
        ProjectFundCollectionDetailsStatisticsVo data = baseMapper.getMoneySum(params.getQuery());
        if (data == null) data = new ProjectFundCollectionDetailsStatisticsVo();
        data.setData(results);
        data.setBalanceAmtSum(data.getFcdMoneySum().subtract(data.getFaDemandSum()));
        data.setPayBalanceAmtSum(data.getFcdMoneySum().subtract(data.getPayApplySum()));
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }
}
