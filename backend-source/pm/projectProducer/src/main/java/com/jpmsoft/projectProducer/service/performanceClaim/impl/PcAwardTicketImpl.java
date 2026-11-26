package com.jpmsoft.projectProducer.service.performanceClaim.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Claim.PcAwardTicketRequest;
import com.jpmsoft.common.model.request.Contract.BindAwardTicketRequest;
import com.jpmsoft.common.model.response.Claim.PcAwardTicketResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.AtTypeEnum;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.performanceClaim.PcAwardTicketMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcAwardTicket;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcAwardTicketService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class PcAwardTicketImpl extends FileImpl<PcAwardTicketMapper, PcAwardTicket, PcAwardTicketRequest> implements IPcAwardTicketService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IConPayService conPayService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "atId", codeParam = "atCode", method = "setAtCode", codeRule = "PcAwardTicket", beanClass = PcAwardTicketRequest.class)
    public BaseResponse createOrUpdatePcAwardTicket(PcAwardTicketRequest request) {
        PcAwardTicket entity = new PcAwardTicket();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getAtId());
    }

    @Override
    public BaseResponse getPcAwardTicketList(PcAwardTicketRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "At_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "At_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<PcAwardTicketRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("paySelect")
                .put("atCode").put("pId").put("atType").put("atCId").put("payId")
                .put("conId").put("atState").put("atCName");
        IPage<PcAwardTicketResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PcAwardTicketResponse> datas = baseMapper.getPcAwardTicketList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getPcAwardTicketById(String claimId) {
        PcAwardTicketResponse data = baseMapper.getPcAwardTicketById(claimId);
        return BaseResponse.ok(data);
    }

    @Override
    public void refreshConPayId(List<String> atIds, String payId) {
        if (CollectionUtils.isEmpty(atIds)) return;
        baseMapper.refreshConPayId2Null(atIds);
        baseMapper.refreshConPayId(atIds, payId);
    }

    @Override
    public List<PcAwardTicketResponse> getPcAwardTicketByConPay(String payId) {
        return baseMapper.getPcAwardTicketByConPay(payId);
    }

    @Override
    public void bindPay(BindAwardTicketRequest bindAwardTicketRequest) {
        String payId = bindAwardTicketRequest.getPayId();
        String contractId = bindAwardTicketRequest.getContractId();
        if (StrUtil.isBlank(payId)) {
            throw new JPMException("payId不允许为空");
        }
        if (StrUtil.isBlank(contractId)) {
            throw new JPMException("contractId不允许为空");
        }
        // 查询支付申请的时间
        ConPay conPay = conPayService.getById(payId);
        if (conPay == null) {
            return;
        }
        if (Arrays.asList(FlowStatusEnum.SendBack.getIndex(),
                        FlowStatusEnum.SendBackStart.getIndex(),
                        FlowStatusEnum.Stop.getIndex())
                .contains(conPay.getPayState())){
            // 流程被退回
            return;
        }
        update(new LambdaUpdateWrapper<PcAwardTicket>()
                .eq(PcAwardTicket::getConId, contractId)
                .eq(PcAwardTicket::getAtType, AtTypeEnum.PUNISH.getCode())
                .isNull(PcAwardTicket::getPayId)
                .eq(PcAwardTicket::getAtState, FlowStatusEnum.Finish.getIndex())
                .le(PcAwardTicket::getAtCreateTime, conPay.getPayCreateTime())
                .set(PcAwardTicket::getPayId, payId));
    }

}
