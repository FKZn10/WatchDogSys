package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConPayApplyMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvance;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApply;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.service.contract.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConPayApplyImpl extends ServiceImpl<ConPayApplyMapper, ConPayApply> implements IConPayApplyService {
    @Resource
    private IAgentFundAdvanceService agentFundAdvanceService;
    @Resource
    private IConPayService conPayService;
    @Resource
    private IConContractService contractService;
    @Resource
    private IConPayApplyFlowService conPayApplyFlowService;

    @Override
    public BaseResponse getConPayApplyList(ConPayApplyDto request) {
        QueryMap<ConPayApplyDto> params = new QueryMap<>(request);
        params.put("pId").put("pName").put("conId").put("conName").put("cpaSources");
        List<ConPayApplyDto> list = baseMapper.getConPayApplyList(params.getQuery());
        //此处mysql-plus使用IPage分页弹出优化SQL失败警告
        Integer count = baseMapper.getConPayApplyCount(params.getQuery());
        return BaseResponse.ok(list, count);
    }

    @Override
    public BaseResponse getConPayApplyById(String id) {
        ConPayApplyDto obj = baseMapper.getConPayApplyById(id);
        return BaseResponse.ok(obj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createConPayApply(String teId) {
        //自动生成资金支付主表信息

        List<AgentFundAdvance> list = agentFundAdvanceService.getFundAdvanceNotConPayApply(teId);
        List<ConPayResponse> conPayList = conPayService.getConPayBypayNotConPayApply(teId);
        if (list.size() > 0) {
            //代建预拨信息同步
            list.forEach(n -> {
                ConPayApply obj = new ConPayApply();
                obj.setCpaId(GenerateUUID.getUUID());
                obj.setConId(n.getConId());
                obj.setCpaForeign(n.getAfaId());
                obj.setPId(n.getPId());
                obj.setCpaSources("02");
                obj.setCpaApplyDate(n.getAfaCreateTime());
                obj.setCpaApplyUid(n.getAfaCreateUid());
                obj.setCpaApprovedAmount(n.getAfaThisMoney());
                obj.setCpaCreateTime(n.getAfaStateTime());
                obj.setTeId(teId);
                baseMapper.insert(obj);
            });
        }
        if (conPayList.size() > 0) {
            //合同支付信息同步
            //获取支付的合同信息和补充合同信息
            List<String> conIds = conPayList.stream()
                    .filter(n -> !"01".equals(n.getPayApplicationType()))
                    .filter(n -> n.getPayIsHistory() != null && n.getPayIsHistory().equals("1"))
                    .map(n -> n.getConId()).collect(Collectors.toList());


            List<ConContract> contractList = new ArrayList<>();

            //获取当前合同的累计资金支付信息
            List<ConPayApplyFlow> conPayApplyList = new ArrayList<>();
            if (conIds.size() > 0) {
                QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
                wrapper.in("Con_Id", conIds);
                wrapper.or().in("Con_Master_Con_Id", conIds);
                contractList = contractService.list(wrapper);

                QueryWrapper<ConPayApplyFlow> conPayWrapper = new QueryWrapper<>();
                conPayWrapper.in("Con_Id", conIds);
                conPayWrapper.eq("State", "7");
                conPayApplyList = conPayApplyFlowService.list(conPayWrapper);

            }
            for (ConPayResponse n : conPayList
            ) {

                if ("01".equals(n.getPayApplicationType())) {
                    continue;
                }

                ConPayApply obj = new ConPayApply();
                obj.setCpaId(GenerateUUID.getUUID());
                obj.setConId(n.getConId());
                obj.setCpaForeign(n.getPayId());
                obj.setPId(n.getpId());
                obj.setCpaSources("01");
                obj.setCpaApplyDate(n.getPayStateTime());
                obj.setCpaApplyUid(n.getPayCreateUid());
                obj.setCpaApprovedAmount(ValidateUtil.isEmpty(n.getPayApplyOwner()) ? n.getPayApply() : n.getPayApplyOwner());
                obj.setCpaCreateTime(n.getPayStateTime());
                obj.setCpaType(n.getPayType());
                obj.setTeId(teId);
                baseMapper.insert(obj);
                if (ValidateUtil.isNotEmpty(n.getPayIsHistory()) && n.getPayIsHistory().equals("1")) {
                    //历史支付信息，生成资金申请信息
                    ConPayApplyFlowDto conPayApplyFlow = new ConPayApplyFlowDto();
                    conPayApplyFlow.setCpaId(obj.getCpaId());
                    conPayApplyFlow.setConId(n.getConId());
                    conPayApplyFlow.setpId(n.getpId());
                    conPayApplyFlow.setCpafPayee(n.getPayPayee());
                    conPayApplyFlow.setCpafDepositBank(n.getPayPayeeBank());
                    conPayApplyFlow.setCpafBankAccount(n.getPayPayeeCa());
                    List<ConPayApplyFlow> conPayApply = conPayApplyList.stream().filter(o -> o.getConId().equals(n.getConId())).collect(Collectors.toList());
                    if (conPayApply.size() > 0) {
                        conPayApplyFlow.setCpafTotalLast(conPayApply.stream().map(k -> k.getCpafApplication()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    } else {
                        conPayApplyFlow.setCpafTotalLast(BigDecimal.ZERO);
                    }
                    if (contractList.size() > 0) {
                        //主合同
                        List<ConContract> mainCon = contractList.stream().filter(p -> p.getConId().equals(n.getConId())).collect(Collectors.toList());
                        if (mainCon.size() > 0) {
                            //组主合同
                            conPayApplyFlow.setCpafConstructionUnit(mainCon.get(0).getConPartyAId());
                            conPayApplyFlow.setCpafContractContent(mainCon.get(0).getConPayClause());
                            conPayApplyFlow.setConMoneyTotal(mainCon.get(0).getConMoneyTotal());
                        }
                        //补充合同
                        List<ConContract> conIdsSup = contractList.stream().filter(p -> p.getConMasterConId() != null && p.getConMasterConId().equals(n.getConId()) && p.getConState() != null && p.getConState().equals("7")).collect(Collectors.toList());
                        if (conIdsSup.size() > 0) {
                            conIdsSup.forEach(k -> {
                                if (k.getConSupChangeMonType() != null && k.getConSupChangeMonType() == 1) {
                                    conPayApplyFlow.setConMoneyTotal(conPayApplyFlow.getConMoneyTotal().add(k.getConSupChangeMoney()));
                                } else {
                                    conPayApplyFlow.setConMoneyTotal(conPayApplyFlow.getConMoneyTotal().subtract(k.getConSupChangeMoney()));
                                }
                            });
                            conPayApplyFlow.setConIdSup(String.join(",", conIdsSup.stream().map(p -> p.getConId()).collect(Collectors.toList())));
                        }
                    }
                    conPayApplyFlow.setCpafPaidThisPeriod(BigDecimal.ZERO);
                    conPayApplyFlow.setCpafContractProgress("");
                    conPayApplyFlow.setCpafApply(obj.getCpaApprovedAmount());
                    conPayApplyFlow.setCpafApplication(obj.getCpaApprovedAmount());
                    conPayApplyFlow.setCpafRemainingAmount(BigDecimal.ZERO);
                    conPayApplyFlow.setCpafTotalPayment(obj.getCpaApprovedAmount());
                    BigDecimal paymentProportion = BigDecimal.ZERO;
                    try {
                        conPayApplyFlow.setCpafApplyProportion(conPayApplyFlow.getCpafTotalPayment().divide(conPayApplyFlow.getConMoneyTotal(), 5, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
                    } catch (Exception ex) {
                        conPayApplyFlow.setCpafApplyProportion(paymentProportion);
                    }
                    conPayApplyFlow.setCpafApplyTime(n.getPayStateTime());
                    conPayApplyFlow.setCpafHistorical(1);//历史数据
                    //conPayApplyFlow.setCpafApplyUname(n.getPayCreateUname());
                    conPayApplyFlow.setCpafCreateTime(n.getPayStateTime());
                    //conPayApplyFlow.setCpafCreateUid(n.getPayCreateUid());
                    conPayApplyFlow.setState(7);
                    conPayApplyFlow.setCpafContractProgress("申请至" + conPayApplyFlow.getCpafApplyProportion() + "%");
                    conPayApplyFlow.setStateTime(n.getPayStateTime());
                    conPayApplyFlow.setTeId(teId);
                    conPayApplyFlowService.createOrUpdateConPayApplyFlow(conPayApplyFlow);
                }
            }
        }
        return BaseResponse.ok();
    }
}
