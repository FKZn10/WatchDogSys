package com.jpmsoft.projectProducer.service.audit.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.audit.AuditSettlementMapper;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditSettlement;
import com.jpmsoft.projectProducer.service.audit.IAuditSettlementService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AuditSettlementImpl extends FileImpl<AuditSettlementMapper, AuditSettlement, AuditSettlementModel> implements IAuditSettlementService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAuditSettlement(AuditSettlementModel request) {
        AuditSettlement entity = new AuditSettlement();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getAsId());
    }

    @Override
    public BaseResponse getAuditSettlementList(AuditSettlementModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Audit_Settlement", uId, "As_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "As_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<AuditSettlementModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("secId").put("secName").put("pName").put("asType")
                .put("conName").put("conId").put("cName").put("asCreateCid").put("asNeedMoney")
                .put("asContactName").put("asContactTel").put("asState").put("pId");
        IPage<AuditSettlementModel> page = new Page<>(request.getPage(), request.getSize());
        List<AuditSettlementModel> datas = baseMapper.getAuditSettlementList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getAuditSettlementById(String asId) {
        AuditSettlementModel data = baseMapper.getAuditSettlementById(asId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getApproveAuditSettlementByConId(String conId) {
        return BaseResponse.ok(baseMapper.getApproveAuditSettlementByConId(conId));
    }

    @Override
    public BigDecimal selectAuditedAmountByCon(String conId) {
        QueryWrapper<AuditSettlement> wrapper = new QueryWrapper<>();
        wrapper.eq("con_Id", conId);
        wrapper.ne("as_state", ApprovalStatusEnum.Already_Over.getIndex());
        List<AuditSettlement> data=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
        {
            return data.stream().findFirst().get().getAsAuditedAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public AuditSettlement selectSettlementById(String id) {
        LambdaQueryWrapper<AuditSettlement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuditSettlement::getAsId,id);
        queryWrapper.last(" limit 1 ");
        return baseMapper.selectOne(queryWrapper);
    }
}
