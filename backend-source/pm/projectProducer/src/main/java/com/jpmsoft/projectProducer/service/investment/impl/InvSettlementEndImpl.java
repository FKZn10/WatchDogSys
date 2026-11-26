package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvSettlementEndDTO;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementEndVO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementEndMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementEnd;
import com.jpmsoft.projectProducer.service.investment.IInvSettlementEndService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvSettlementEndImpl extends ServiceImpl<InvSettlementEndMapper, InvSettlementEnd> implements IInvSettlementEndService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvSettlementEnd(InvSettlementEndDTO request) {
        return createOrUpdateInvSettlementEnd(request, true);
    }

    @Override
    public BaseResponse updateInvSettlementEndOnlyInfo(InvSettlementEndDTO request) {
        if (ValidateUtil.isEmpty(request.getSaeId())) return BaseResponse.fail(ResMsg.notReqParam);
        return createOrUpdateInvSettlementEnd(request, false);
    }


    @Override
    public BaseResponse getInvSettlementEndList(InvSettlementEndDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Inv_Settlement_End", uId, "Sae_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvSettlementEndDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("saeCuAf").put("conId").put("saeSubmissionUser").put("saeSubmissionPhone")
                .put("pName").put("conName").put("pId").put("saeExamineUser").put("state")
                .put("saeExaminePhone").put("haveCostConsult");
        IPage<InvSettlementEndVO> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementEndVO> datas = baseMapper.getInvSettlementEndList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvSettlementEndNoAuthList(InvSettlementEndDTO request, String uId) {
        QueryMap<InvSettlementEndDTO> params = new QueryMap<>(request);
        params.put("authSql", " where 1=1 ").put("saeCuAf").put("conId").put("saeSubmissionUser").put("saeSubmissionPhone")
                .put("saeProjectName").put("saeConName").put("pId").put("saeExamineUser").put("outAudit").put("state")
                .put("saeExaminePhone").put("haveCostConsult").put("saePartyAName").put("conName");
        IPage<InvSettlementEndVO> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementEndVO> datas = baseMapper.getInvSettlementEndNoAuthList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse refreshSettlementEndOtherTableInfo() {
        pageSelect(1, 100);
        return BaseResponse.ok();
    }

    /**
     * 轮询刷新合同已支付金额
     *
     * @param index
     * @param size
     */
    private void pageSelect(int index, int size) {
        Page<InvSettlementEnd> page = new Page<>(index, size);
        IPage<InvSettlementEnd> result = baseMapper.selectPage(page, null);
        if (!CollectionUtils.isEmpty(result.getRecords())) {
            List<String> ids = result.getRecords().stream().map(InvSettlementEnd::getSaeId).collect(Collectors.toList());
            baseMapper.refreshApplySum(ids);
            pageSelect(index + 1, size);
        }
    }

    @Override
    public BaseResponse getInvSettlementEndBySaeId(String saeId) {
        InvSettlementEndVO data = baseMapper.getInvSettlementEndBySaeId(saeId);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvSettlementEndBySaeId(String saeId) {
        InvSettlementEnd model = baseMapper.selectById(saeId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(saeId);
        baseMapper.deleteById(saeId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getLastApprovalInvSettlementEndByConId(String conId) {
        return BaseResponse.ok(baseMapper.getLastApprovalInvSettlementEndByConId(conId));
    }

    @Override
    public BaseResponse getLastApprovalInvSettlementEndByPayId(String id) {
        InvSettlementEndVO invSettlementEndVO = baseMapper.getLastApprovalInvSettlementEndByPayId(id);
        BigDecimal amount = invSettlementEndVO.getSaeAuditReport().subtract(invSettlementEndVO.getSaeIncreaseDecrease());
        invSettlementEndVO.setAmount(amount);
        log.info("invSettlementEndVO:{}", invSettlementEndVO);
        return BaseResponse.ok(invSettlementEndVO);
    }

    private BaseResponse createOrUpdateInvSettlementEnd(InvSettlementEndDTO request, boolean refershFile) {
        InvSettlementEnd entity = new InvSettlementEnd();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSaeId())) {
            entity.setSaeCreateTime(new Date());
            entity.setSaeId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSaeId(), request.gettId(), entity.getState() + "", entity.getSaeCreateUid(), entity.getSaeCreateUname()
                    , entity.getSaeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            if (refershFile) {
                sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSaeEndCarefulFile()), entity.getSaeId());
            }
        } else {
            InvSettlementEnd model = baseMapper.selectById(request.getSaeId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
//            if (JudgeUtil.nonModifyState(model.getSaState()))
//                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            entity.setSaeCreateTime(model.getSaeCreateTime());
            entity.setSaeCreateUid(model.getSaeCreateUid());
            entity.setSaeCreateUname(model.getSaeCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSaeId(), request.gettId(), entity.getState() + "", entity.getSaeCreateUid(), entity.getSaeCreateUname()
                    , entity.getSaeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            if (refershFile) {
                sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSaeEndCarefulFile())
                        , Arrays.asList(request.getFileIds(), request.getSaeEndCarefulFileIds())
                        , entity.getSaeId());
            }
        }
        return BaseResponse.ok(entity.getSaeId());
    }


    @Override
    public List<InvSettlementEnd> getInvConIdList(List<String> conIds) {
        LambdaQueryWrapper<InvSettlementEnd> queryWrapper = new LambdaQueryWrapper<>();
        if (!CollectionUtils.isEmpty(conIds)) {
            queryWrapper.in(InvSettlementEnd::getConId, conIds);
        }
        queryWrapper.eq(InvSettlementEnd::getState, 7);
        return baseMapper.selectList(queryWrapper);
    }
}
