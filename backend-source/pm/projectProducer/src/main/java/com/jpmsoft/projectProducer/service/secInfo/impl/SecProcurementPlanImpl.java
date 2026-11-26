package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProcurementPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanAmountDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlan;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecProcurementPlanBudgetDeclareDtlVo;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementPlanAmountService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProcurementPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SecProcurementPlanImpl extends ServiceImpl<SecProcurementPlanMapper, SecProcurementPlan> implements ISecProcurementPlanService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IInvBudgetDeclareService invBudgetDeclareService;
    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;
    @Resource
    private ISecProcurementPlanAmountService amountService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISecProcurementPlanAmountService procurementPlanAmountService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ppId", codeParam = "planCode", method = "setPlanCode", codeRule = "SecProcurementPlan", beanClass = SecProcurementPlanDto.class)
    public BaseResponse createOrUpdateSecProcurementPlan(SecProcurementPlanDto request) {
        SecProcurementPlan entity = new SecProcurementPlan();
        BeanUtil.copyProperties(request, entity);
        SecProcurementPlan secProcurementPlan = baseMapper.getSecProcurementPlanByName(request.getpId(),request.getPlanName());
        if(StrUtil.isBlank(request.getPpId())){
            if(secProcurementPlan!=null){
                throw new JPMException("计划名称已存在");
            }
            entity.setPpId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPpId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()),entity.getPpId());

        }else{
            SecProcurementPlan model = baseMapper.selectById(request.getPpId());
            if(secProcurementPlan!=null && !model.getPpId().equals(secProcurementPlan.getPpId())){
                throw new JPMException("计划名称已存在");
            }
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPpId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), entity.getPpId());

        }
        procurementPlanAmountService.createOrUpdateSecProcurementPlanAmount(request.getPlanAmountList(), entity.getPpId());
        return BaseResponse.ok(entity.getPpId());
    }

    @Override
    public BaseResponse getSecProcurementPlanList(SecProcurementPlanDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SecProcurementPlanDto> params = new QueryMap<>(request);
        params.put("planName").put("createUname").put("state").put("pName").put("pId")
                .put("planCode").put("planType").put("planStatus").put("planForm").put("biddingMethod")
                .put("state").put("stateList").put("noStateList").put("planStartTime");
        IPage<SecProcurementPlanDto> page = new Page<>(request.getPage(), request.getSize());
        List<SecProcurementPlanDto> list = baseMapper.getSecProcurementPlanList(page,params.getQuery());

        return BaseResponse.ok(list,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getSecProcurementPlanById(String ppId) {
        SecProcurementPlanDto dto = baseMapper.getSecProcurementPlanById(ppId);
        List<SecProcurementPlanAmountDto> amountDtoList = amountService.getListByPpId(ppId);
        dto.setPlanAmountList(amountDtoList);
        return BaseResponse.ok(dto);
    }

    @Override
    public BaseResponse getSecProcurementPlanByPid(String pId) {
        List<SecProcurementPlanDto> secProcurementPlanDtos = baseMapper.getSecProcurementPlanByPid(pId);
        return BaseResponse.ok(secProcurementPlanDtos);
    }

    @Override
    public BaseResponse getSecProcurementPlanAmountTree(String pId) {
        List<InvBudgetDeclareDtl> invBudgetDeclareDtls = invBudgetDeclareService.getInvTypeBudgetDeclareDtlByPid(pId);


        Map<String, Float> planAmountMap = procurementPlanAmountService.getPlanAmountMapByPid(pId);
        List<SecProcurementPlanBudgetDeclareDtlVo> budgetDeclareDtlVos= new ArrayList<>();
        List<SecProcurementPlanBudgetDeclareDtlVo> firstList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(invBudgetDeclareDtls)){
            budgetDeclareDtlVos= BeanUtils.copyList(invBudgetDeclareDtls,SecProcurementPlanBudgetDeclareDtlVo.class);
            firstList = budgetDeclareDtlVos.stream().filter(e -> "0".equals(e.getBddParentId())).collect(Collectors.toList());
            for (SecProcurementPlanBudgetDeclareDtlVo budgetDeclareDtlVo : firstList) {
                budgetDeclareDtlVo.setBddLevel(1);
                budgetDeclareDtlVo.setBddPath(budgetDeclareDtlVo.getBddId());
                if(planAmountMap.containsKey(budgetDeclareDtlVo.getBddId())){
                    Float planAmount = planAmountMap.get(budgetDeclareDtlVo.getBddId());
                    if(planAmount==null){
                        planAmount=0F;
                    }
                    budgetDeclareDtlVo.setResidueProportion(100-planAmount);
                }
                List<SecProcurementPlanBudgetDeclareDtlVo> childList = getChildList(budgetDeclareDtlVo, budgetDeclareDtlVos, planAmountMap);
                if(CollectionUtil.isEmpty(childList)){
                    if(!planAmountMap.containsKey(budgetDeclareDtlVo.getBddId())){
                        budgetDeclareDtlVo.setResidueProportion(100F);
                    }
                    budgetDeclareDtlVo.setBddLeafNode(1);
                }else{
                    budgetDeclareDtlVo.setBddLeafNode(0);
                }
                budgetDeclareDtlVo.setChildren(childList);
            }
        }

        return BaseResponse.ok(firstList);
    }

    private List<SecProcurementPlanBudgetDeclareDtlVo> getChildList(SecProcurementPlanBudgetDeclareDtlVo parentBudgetDeclareDtlVo, List<SecProcurementPlanBudgetDeclareDtlVo> budgetDeclareDtlVos,Map<String, Float> planAmountMap) {
        List<SecProcurementPlanBudgetDeclareDtlVo> declareDtlVos = new ArrayList<>();
        String bddId = parentBudgetDeclareDtlVo.getBddId();
        String bddPath = parentBudgetDeclareDtlVo.getBddPath();
        Integer bddLevel = parentBudgetDeclareDtlVo.getBddLevel();
        if(CollectionUtil.isNotEmpty(budgetDeclareDtlVos)){
            declareDtlVos = budgetDeclareDtlVos.stream().filter(e -> bddId.equals(e.getBddParentId())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(declareDtlVos)){
                for (SecProcurementPlanBudgetDeclareDtlVo child : declareDtlVos) {
                    child.setBddLevel(bddLevel+1);
                    child.setBddPath(bddPath+"/"+child.getBddId());
                    if(planAmountMap.containsKey(child.getBddId())){
                        Float planAmount = planAmountMap.get(child.getBddId());
                        child.setResidueProportion(100-planAmount);
                    }
                    List<SecProcurementPlanBudgetDeclareDtlVo> childList = getChildList(child, budgetDeclareDtlVos, planAmountMap);
                    if(CollectionUtil.isEmpty(childList)){
                        if(!planAmountMap.containsKey(child.getBddId())){
                            child.setResidueProportion(100F);
                        }
                        child.setBddLeafNode(1);
                    }else{
                        child.setBddLeafNode(0);
                    }
                    child.setChildren(childList);
                }
            }
        }
        return declareDtlVos;
    }


}
