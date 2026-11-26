package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.enums.CppBasicsEnum;
import com.jpmsoft.projectProducer.enums.CppConJdEnum;
import com.jpmsoft.projectProducer.enums.CppConditionTypeEnum;
import com.jpmsoft.projectProducer.enums.PayMethodEnum;
import com.jpmsoft.projectProducer.mapper.contract.ConContractPayPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractPayPlanDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.ProgressDeclarationDtl;
import com.jpmsoft.projectProducer.service.audit.IAuditSettlementService;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.fundPlan.IProgressDeclarationDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvFinalAccountsApplyService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConContractPayPlanImpl extends ServiceImpl<ConContractPayPlanMapper, ConContractPayPlan> implements IConContractPayPlanService {

    @Resource
    private IProgressDeclarationDtlService progressDeclarationDtlService;
    @Resource
    private IConContractService contractService;
    @Resource
    private IInvFinalAccountsApplyService invFinalAccountsApplyService;
    @Resource
    private IAuditSettlementService auditSettlementService;


    @Override
    public void createOrUpdateConContractPayPlan(List<ConContractPayPlanModel> datas, String conId) {
        deleteAllConContractPayPlanByConId(conId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConContractPayPlan> entitys = new ArrayList<>();
            Date now = new Date();
            for (ConContractPayPlanModel data : datas) {
                ConContractPayPlan entity = new ConContractPayPlan();
                BeanUtils.copyProperties(data, entity);
                entity.setConId(conId);
                entity.setCppCreateTime(data.getCppCreateTime() == null ? now : data.getCppCreateTime());
                entity.setCppId(GenerateUUID.getUUID(data.getCppId()));
                entitys.add(entity);
            }
            if (ValidateUtil.isNotEmpty(entitys))
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteAllConContractPayPlanByConId(String conId) {
        QueryWrapper<ConContractPayPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConContractPayPlanModel> getConContractPayPlanByConIds(List<String> conIds) {
        QueryWrapper<ConContractPayPlan> wrapper = new QueryWrapper<>();

        wrapper.eq("Con_Id", conIds.get(0));
        for (int i = 1; i < conIds.size(); i++) {
            wrapper.or().eq("Con_Id", conIds.get(i));
        }
        wrapper.orderByAsc("Cpp_Sort");
        List<ConContractPayPlan> entitys = baseMapper.selectList(wrapper);
        List<ConContractPayPlanModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConContractPayPlanModel data = new ConContractPayPlanModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public List<ConContractPayPlanModel> getConContractPayPlanByConId(String conId) {
        LambdaQueryWrapper<ConContractPayPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConContractPayPlan::getConId, conId);
        wrapper.orderByAsc(ConContractPayPlan::getCppSort);
        List<ConContractPayPlan> entitys = baseMapper.selectList(wrapper);
        List<ConContractPayPlanModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConContractPayPlanModel data = new ConContractPayPlanModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public BaseResponse ProgressDeclarationCompute(ProgressDeclarationDtlDTO request) {
        QueryWrapper<ConContractPayPlan> wrapper=new QueryWrapper<>();
        wrapper.eq("con_id",request.getConId());
        wrapper.orderByAsc("cpp_sort");
        List<ConContractPayPlan> plans=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(plans))
        {
            List<ProgressDeclarationDtl> pds=progressDeclarationDtlService.selectAllByContract(request.getConId());
            if (ValidateUtil.isNotNull(request.getPddProgress())){//录入的百分比
                //仅仅支持合同进度类型并且合同进度类型为百分比的的支付条件类型
                List<ConContractPayPlan> plan=plans.stream().filter(f->ValidateUtil.isNotEmpty(f.getCppConditionType())
                &&f.getCppConditionType().equals(CppConditionTypeEnum.HTJD.getkey())
                &&ValidateUtil.isNotEmpty(f.getCppConJdlx())&&f.getCppConJdlx().equals(CppConJdEnum.RATE.getkey())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(plan)) return BaseResponse.ok(request);//没有满足的条件 无法按百分比判断你是否可以申报
                request.setPddMoney(htjd(plan, request, CollectionUtils.isEmpty(pds) ? BigDecimal.ZERO : pds.stream().map(ProgressDeclarationDtl::getPddMoney).reduce(BigDecimal.ZERO, BigDecimal::add)));
            }
            else { //选择的支付节点
                request.setPddMoney(money(plans.stream().filter(f->f.getCppId().equals(request.getCppId())).findFirst().get(),request.getConId(), CollectionUtils.isEmpty(pds) ? BigDecimal.ZERO : pds.stream().map(ProgressDeclarationDtl::getPddMoney).reduce(BigDecimal.ZERO, BigDecimal::add)));
            }
        }
        return BaseResponse.ok(request);
    }

    /**
     * 合同进度 按百分比计算金额
     *  录入的进度百分比 需要先找到合同进度 并且百分比满足的支付计划
     * @return
     */
    private BigDecimal htjd(List<ConContractPayPlan> plans,ProgressDeclarationDtlDTO request,BigDecimal zjf){
        ConContractPayPlan p=null;
        for (ConContractPayPlan plan : plans) {
            if (strDecBd(plan.getCppConditionValue(),request.getPddProgress()))
                break;
            else {
                p=plan;
            }
        }
        return money(p,request.getConId(),zjf);
    }

    /**
     * 支付计算
     * 按金额 按 比例
     *
     * @param p
     * @param conId
     * @param yzf
     * @return
     */
    private BigDecimal money(ConContractPayPlan p,String conId,BigDecimal yzf)
    {
        if (ValidateUtil.isNotNull(p))
        {
            if (p.getCppMode().equals(PayMethodEnum.JA.getkey()))//支付指定金额
                return p.getCppMoney();
            else {//支付比例
                ConContract con=contractService.getById(conId);
                switch (Objects.requireNonNull(CppBasicsEnum.getEnum(p.getCppBasics()))) {
                    case HTJ:
                        // （合同金额-合同暂列金）* 支付比例 - 之前已申报金额累计
                        return MathUtil.decSubtract(MathUtil.decMultiplyFloat(MathUtil.decSubtract(con.getConMoneyTotal(),con.getConMoneyTentative()),p.getCppRatioPayTo()),yzf);
                    case MCJLZ:
                        //todo
                        break;
                    case LJJLZ:
                        //todo
                        break;
                    case JGJSJ:
                        // 竣工决算金额 * 支付比例 - 之前已申报金额累计
                        return MathUtil.decSubtract(MathUtil.decMultiplyFloat(invFinalAccountsApplyService.selectContractApprovedAmt(conId),p.getCppRatioPayTo()),yzf);
                    case CBSDJ:
                        //todo 初步审计金额 * 支付比例 - 之前已申报金额累计  初步审计金额计算未知
                        return  MathUtil.decSubtract(MathUtil.decMultiplyFloat(BigDecimal.ZERO,p.getCppRatioPayTo()),yzf);
                    case SJJ:
                        // 结算审计金额 * 支付比例 - 之前已申报金额累计
                        return  MathUtil.decSubtract(MathUtil.decMultiplyFloat(auditSettlementService.selectAuditedAmountByCon(conId),p.getCppRatioPayTo()),yzf);
                }
            }
        }
        return BigDecimal.ZERO;
    }


    /**
     * 比对值
     * str>b return true
     * str<=b return false
     * @param str
     * @param b
     * @return
     */
    public static boolean strDecBd(String str, BigDecimal b)
    {
        BigDecimal a=new BigDecimal(str);
        return a.compareTo(MathUtil.decMultiply(b, new BigDecimal("100"))) > 0;
    }

    @Override
    public List<ConContractPayPlan> getConPaymentPlan( String conId) {
        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.
                eq(ConContractPayPlan::getConId,conId);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ConContractPayPlan> getConPaymentPlanList(List<String> ids) {
        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
        if(ValidateUtil.isNotEmpty(ids)){
            queryWrapper.
                    in(ConContractPayPlan::getConId,ids);
        }


        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ConContractPayPlan> getConPaymentPlanConIds(List<String> conIds) {
        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
        if(!CollectionUtils.isEmpty(conIds)){
            queryWrapper.in(ConContractPayPlan::getConId,conIds);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ConContractPayPlan getConPaymentPlanConIdNode(String conId, Integer pddThisNode) {
        LambdaQueryWrapper<ConContractPayPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConContractPayPlan::getConId,conId);
        queryWrapper.eq(ConContractPayPlan::getCppSort,pddThisNode);
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 修改合同付款计划
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse updateConPayPlan(ConContractPayPlanDto request) {
        ConContractPayPlan entity=baseMapper.selectById(request.getCppId());
        if (entity==null) return BaseResponse.fail("付款计划不存在");
        entity.setCppPayDate(request.getCppPayDate());
        entity.setCppPayAmount(request.getCppPayAmount());
        entity.setCppPaySource(request.getCppPaySource());
        baseMapper.updateById(entity);
        return BaseResponse.ok("保存成功");
    }


}
