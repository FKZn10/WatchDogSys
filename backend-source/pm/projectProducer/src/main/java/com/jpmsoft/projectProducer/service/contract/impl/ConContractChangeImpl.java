package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConContractChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractChange;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConContractChangeVO;
import com.jpmsoft.projectProducer.service.contract.*;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:47
 */
@Service
public class ConContractChangeImpl extends ServiceImpl<ConContractChangeMapper, ConContractChange> implements IConContractChangeService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConContractMoneyDtlChangeService conContractMoneyDtlChangeService;
    @Resource
    private IConContractPayPlanChangeService conContractPayPlanChangeService;
    @Resource
    private IConPayeeDtlChangeService conPayeeDtlChangeService;
    @Resource
    private IConContractRecordService conContractRecordService;
    @Resource
    private IConContractService contractService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConContractChange(ConContractChangeDTO request) {
        ConContractChange entity;
        //region 前置校验
        if (ValidateUtil.isEmpty(request.getConId()))
            return BaseResponse.fail(ResMsg.notConId);
        if (havingProcessContractChange(request.getConId(), request.getId()))
            return BaseResponse.fail(ResMsg.recordUnFinish);
        //endregion
        //region 新增修改操作
        if (ValidateUtil.isEmpty(request.getId())) {
            entity = new ConContractChange();
            BeanUtils.copyProperties(request, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setConCreateTime(new Date());
            entity.setConApprovalState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setConApprovalTime(null);
            if (ValidateUtil.isNull(request.getConTimeConsuming()) && ValidateUtil.isNotNull(request.getConBasisTime()))
                entity.setConTimeConsuming(DateUtil.daysCalculation(request.getConBasisTime(), entity.getConCreateTime()));
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getConApprovalState() + "", entity.getConCreateUserId(), entity.getConCreateUserName()
                    , entity.getConCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getConAcProxy(), request.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo()), entity.getConId());
            conContractRecordService.createConContractRecord(request.getConId(), entity.getId());
        } else {
            entity = baseMapper.selectById(request.getId());
            if (entity == null) throw new JPMException(ResMsg.infoNotExist);
            ConContractChange pojo = new ConContractChange();
            BeanUtils.copyProperties(request, pojo);
            //以下参数不可更改
            pojo.setConCreateUserId(entity.getConCreateUserId());
            pojo.setConCreateUserName(entity.getConCreateUserName());
            pojo.setConCreateTime(entity.getConCreateTime());
            pojo.setConApprovalState(entity.getConApprovalState());
            pojo.setConApprovalTime(entity.getConApprovalTime());
            baseMapper.updateById(pojo);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getConApprovalState() + "", entity.getConCreateUserId(), entity.getConCreateUserName()
                    , entity.getConCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), entity.getConAcProxy(), entity.getConWord(), request.getConScan(), request.getConBasisFile(), request.getConStartsTo())
                    , Arrays.asList(request.getFileIds(), request.getConAcProxyIds(), request.getConWordIds(), request.getConScanIds(), request.getConBasisFileIds(), request.getConStartsToIds()), pojo.getConId());
        }
        conContractMoneyDtlChangeService.createOrUpdate(request.getMoneyDtlModels(), entity.getId());
        conContractPayPlanChangeService.createOrUpdate(request.getPayPlanModels(), entity.getId());
        conPayeeDtlChangeService.createOrUpdate(request.getPayeeDtlModels(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getContractChangeList(ConContractChangeDTO request, String uId) {
        QueryMap<ConContractChangeDTO> params = assembleScreen(getAuthSql(uId, request.getConCategory()), request);
        IPage<ConContractChangeVO> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractChangeVO> data = baseMapper.getContractChangeList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getContractChangeById(String id) {
        return BaseResponse.ok(baseMapper.getContractChangeById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approve2RefreshContract(String id) {
        ConContractChange change = baseMapper.selectById(id);
        if (change == null) return BaseResponse.ok(ResMsg.infoNotExist);
        if (!Objects.equals(change.getConApprovalState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        ConContract con = contractService.getById(change.getConId());
        if (con != null) {
            ConContract nw = new ConContract();
            BeanUtils.copyProperties(change, nw);
            nw.setConId(con.getConId());
            nw.setConCreateUserId(con.getConCreateUserId());
            nw.setConCreateUserName(con.getConCreateUserName());
            nw.setConCreateTime(con.getConCreateTime());
            nw.setConApprovalState(con.getConApprovalState());
            nw.setConApprovalTime(con.getConApprovalTime());
            //附件
            nw.setConAcProxy(con.getConAcProxy());
            nw.setConWord(con.getConWord());
            nw.setConScan(con.getConScan());
            nw.setConBasisFile(con.getConBasisFile());
            nw.setConStartsTo(con.getConStartsTo());
            contractService.updateById(nw);
            conContractMoneyDtlChangeService.approve2Sync(id, nw.getConId());
            conContractPayPlanChangeService.approve2Sync(id, nw.getConId());
            conPayeeDtlChangeService.approve2Sync(id, nw.getConId());
        }
        return BaseResponse.ok();
    }

    @Override
    public boolean havingProcessContractChange(String conId, String id) {
        QueryWrapper<ConContractChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        if (ValidateUtil.isNotEmpty(id))
            wrapper.ne("id", id);
        wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.ne("Con_Approval_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }


    private String getAuthSql(String uId, String conCategory) {
        if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("6")) {
            //年度合同
            return sysUserService.onlyInnerAndPartyB("Con_Contract", uId, "Con_Create_User_Id", "Con_Approval_State");
        } else if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("7")) {
            //经费合同
            String authsql= sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
            authsql=authsql.replaceFirst(" where "," where  (( mn.Con_Project_Id is null and ")+")  or ( mn.Con_Project_Id is not null and ";
            String projectAuth=sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State");
            projectAuth=projectAuth.replaceFirst(" where ",authsql)+") ) ";
            return projectAuth;
            //return sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
        } else {//其他合同
            String authSql = sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State");
            if (ValidateUtil.isEmpty(authSql)) throw new JPMException(ResMsg.noPermission);
            return authSql;
        }
    }

    private QueryMap<ConContractChangeDTO> assembleScreen(String authSql, ConContractChangeDTO request) {
        QueryMap<ConContractChangeDTO> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("conCategory").put("conApprovalState").put("pId").put("conScanUp")
                .put("conType").put("conName").put("conCode").put("conProjectId").put("conIsHistory")
                .put("conProjectName").put("conState").put("conExecution").put("conPartyBPm").put("pAdvanceFund").put("pCorporateRisk")
                .put("conPartyBPmConsNu").put("conPartyBPmIdcard").put("conBiddingType").put("conUsingType")
                .put("conPartyBLegal").put("conPartyBAgent").put("conPartyBPoxyNum").put("conIsBidding").put("excludeSeven")
                .put("conPartyBAgentTel").put("conTdName").put("conTdNum").put("conTdMobileNumber").put("filterSubChangeAmt")
                .put("conConsortium").put("conMultiSubject").put("conSup").put("onPartyBPmTel").put("conProfessionalType")
                .put("conCreateUserName").put("conCurrency").put("conPartyAName").put("conPartyAOtherName")
                .put("conPartyBName").put("conMasterConId").put("conSectionId").put("bUnitId").put("conTypes").put("containState")
                .put(request.getConMoneyTotals(), "moneyTotalStart", "moneyTotalEnd")
                .put(request.getConTotalDurations(), "totalDurationStart", "totalDurationEnd")
                .put(request.getConPlanStartDates(), "planStartDateStart", "planStartDateEnd")
                .put(request.getConPlanEndDates(), "planEndDateStart", "planEndDateEnd")
                .put(request.getConCreateTimes(), "createTimeStart", "createTimeEnd")
                .put(request.getConDates(), "conDateStart", "conDateEnd").put("filterSettlement");
        return query;
    }
}
