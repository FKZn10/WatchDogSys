package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvBudgetDeclareDetailResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvBudgetDeclareResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvBudgetDeclareMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvRelatedRelationshipsMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetDeclareDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRelatedRelationships;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetCompanyVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvBudgetDeclareImpl extends ServiceImpl<InvBudgetDeclareMapper, InvBudgetDeclare> implements IInvBudgetDeclareService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IConContractService conContractService;

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private InvRelatedRelationshipsMapper invRelatedRelationshipsMapper;

    @Resource
    private SecInfoBudgetDeclareDtlMapper secInfoBudgetDeclareDtlMapperMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvBudgetDeclare(InvBudgetDeclareRequest request) {
        InvBudgetDeclare entity = new InvBudgetDeclare();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getBdId())) {
            if (havingBudgetWithPid(request.getpId(), request.getBdType()))
                return BaseResponse.fail(ResMsg.proHavingBudget);
            entity.setBdCreateTime(new Date());
            entity.setBdId(GenerateUUID.getUUID());
            entity.setBdState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getBdId(), request.gettId(), entity.getBdState() + "", entity.getBdCreateUid(), entity.getBdCreateUname(), entity.getBdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBdApprovalFile(), request.getBdReviewFile(), request.getBdAuthorizeFile()), entity.getBdId());
        } else {
            InvBudgetDeclare model = baseMapper.selectById(request.getBdId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getBdState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setBdState(model.getBdState());
            entity.setBdStateTime(model.getBdStateTime());
            entity.setBdCreateTime(model.getBdCreateTime());
            entity.setBdCreateUid(model.getBdCreateUid());
            entity.setBdCreateUname(model.getBdCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getBdId(), request.gettId(), entity.getBdState() + "", entity.getBdCreateUid(), entity.getBdCreateUname(), entity.getBdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBdApprovalFile(), request.getBdReviewFile(), request.getBdAuthorizeFile()), Arrays.asList(request.getFileIds(), request.getBdApprovalFileIds(), request.getBdReviewFileIds(), request.getBdAuthorizeFileIds()), entity.getBdId());
        }
        invBudgetDeclareDtlService.createOrUpdateInvBudgetDeclareDtl(request.getInvBudgetDeclareDtls(), entity.getBdId());
        invBudgetDeclareDtlService.createOrUpdateInvRelatedRelationships(request.getInvRelatedRelationships(), entity.getBdId());
        return BaseResponse.ok(entity.getBdId());
    }

    @Override
    public BaseResponse getInvBudgetDeclareList(InvBudgetDeclareRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Bd_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Bd_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvBudgetDeclareRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("bdPreparedByName").put("bdAuditUnitName").put("pName").put("pId").put("bdPreparedBy").put("bdAuditUnit").put("bdSplitType").put("bdType");
        IPage<InvBudgetDeclareResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvBudgetDeclareResponse> datas = baseMapper.getInvBudgetDeclareList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvBudgetDeclareByBdId(String bdId) {
        InvBudgetDeclareResponse data = baseMapper.getInvBudgetDeclareByBdId(bdId);
        if (data != null) {
            List<InvBudgetDeclareDtlModel> child = invBudgetDeclareDtlService.getInvBudgetDeclareDtlModelByBdId(data.getBdId(), "").stream().sorted(Comparator.comparing(InvBudgetDeclareDtlModel::getBddOrder)).collect(Collectors.toList());
            data.setInvBudgetDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteInvBudgetDeclareByBdId(String bdId) {
        InvBudgetDeclare model = baseMapper.selectById(bdId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(model.getBdState())) return BaseResponse.fail(ResMsg.stateNotDel);
        sysFileService.deleteFileById(bdId);
        invBudgetDeclareDtlService.deleteAllByBdId(bdId);
        baseMapper.deleteById(bdId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getInvBudgetDeclareByPId(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Bd_State", ApprovalStatusEnum.Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }

    @Override
    public BaseResponse getInvBudgetDeclareAndDtlByPid(String pId) {
        InvBudgetDeclareResponse data = baseMapper.getInvBudgetDeclareAndDtlByPid(pId);
        if (data != null) {
            List<InvBudgetDeclareDtlModel> child = invBudgetDeclareDtlService.getInvBudgetDeclareDtlModelByBdId(data.getBdId(), pId);
            data.setInvBudgetDeclareDtls(child);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getInvBudegetCompanyByPid(String pId) {
        BudgetCompanyVo data = baseMapper.getBudgetCompanyByPid(pId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approve2PApproveBudget(String bdId) {
        InvBudgetDeclare data = baseMapper.selectById(bdId);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(data.getBdState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProject project = preProjectService.getById(data.getPId());
        if (project == null) return BaseResponse.fail(ResMsg.infoNotExist);
        project.setpApproveBudget(data.getBdMoney());
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        preProjectService.updateById(project);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectBudgetBalance(String pId) {
        InvBudgetDeclare data = selectApproveOne(pId);
        BigDecimal conTotal = conContractService.getConMoneyTotalSum(pId);
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("balance", MathUtil.decSubtract(data == null ? BigDecimal.ZERO : data.getBdAuditAmt(), conTotal));
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getConBudgetRate(String pId, BigDecimal conMoney) {
        InvBudgetDeclare data = selectApproveOne(pId);
        BigDecimal conTotal = MathUtil.decAdd(conContractService.getConMoneyTotalSum(pId), conMoney);
        return BaseResponse.ok(MathUtil.decimalDivide(conTotal, data == null ? BigDecimal.ZERO : data.getBdAuditAmt()));
    }


    private InvBudgetDeclare selectApproveOne(String pId) {
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Bd_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("Bd_Create_Time");
        wrapper.last(" limit 1");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean havingBudgetWithPid(String pId, String type) {
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.ne("Bd_State", ApprovalStatusEnum.Invalid.getIndex());
        if (ValidateUtil.isNotNull(type))
            wrapper.eq("Bd_Type", type);
        if (baseMapper.exists(wrapper)) return true;
        return false;
    }

    @Override
    public BaseResponse getInvBudgetDeclareDtlByPIds(List<String> pIds) {
        if (pIds == null || pIds.size() < 1) return BaseResponse.fail(ResMsg.notReqParam);
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pIds.get(0));
        for (int i = 1; i < pIds.size(); i++) {
            wrapper.or().eq("P_Id", pIds.get(i));
        }
        List<InvBudgetDeclare> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<String> pId = new ArrayList<>();
            List<String> bdids = new ArrayList<>();
            datas.forEach(data -> {
                pId.add(data.getPId());
                bdids.add(data.getBdId());
            });
            Map<String, List<InvBudgetDeclareDtlModel>> map = invBudgetDeclareDtlService.getInvBudgetDeclareDtlModelByBdIds(pId, bdids);
            return BaseResponse.ok(map);
        }
        return BaseResponse.ok(null);
    }

    @Override
    public BaseResponse getPreStageListByPIdAndType(String pId, String type) {
        String str = sysConfigService.getConfigByCode("calculationValue");
        List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(str)) {
            JSONObject json = JSONObject.parseObject(str);
            Integer value = new Integer(json.getString(type)) - 1;
            Iterator<String> keys = json.keySet().iterator();
            String TypeName = "";
            while (keys.hasNext()) {
                String key = keys.next();
                Integer value2 = new Integer(json.getString(key));
                if (value2.equals(value)) {
                    TypeName = key;
                    break;
                }
            }
            if ("".equals(TypeName)) {
                //获取项目信息下的匡算信息
                List<PreProjectDtlModel> preProjectDtlModels = preProjectService.getPreProjectDtlByPId(pId);
                if (ValidateUtil.isNotEmpty(preProjectDtlModels)) {
                    for (PreProjectDtlModel data : preProjectDtlModels) {
                        InvBudgetDeclareDtlModel obj = new InvBudgetDeclareDtlModel();
                        obj.setBddId(data.getPpdId());
                        obj.setBdId(data.getPpdFid());
                        obj.setBddNumber(data.getPpdNumber());
                        obj.setBddParentId(data.getPpdParenetId());
                        obj.setBddCode(data.getPpdCode());
                        obj.setBddName(data.getPpdName());
                        obj.setBddUnit(data.getPpdUnit());
                        obj.setBddCount(data.getPpdCount());
                        obj.setBddPrice(data.getPpdPrice());
                        obj.setBddTotal(data.getPpdTotal());
                        obj.setBddRemark(data.getPpdRemark());
                        obj.setBddOrder(data.getPpdOrder());
                        obj.setBddLevel(data.getPpdLevel());
                        obj.setBddType(data.getPpdType());
                        obj.setBddState(data.getPpdState());
                        obj.setBddPath(data.getPpdPath());
                        obj.setBddLeafNode(data.getPpdLeafNode());
                        obj.setpId(pId);
                        obj.setSfAssociation(data.getSfAssociation());
                        obj.setAssociationSectionIds(data.getAssociationSectionIds());
                        datas.add(obj);
                    }
                }
            } else {
                //获取前置的
                QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
                wrapper.eq("P_Id", pId);
                wrapper.eq("Bd_Type", TypeName);
                wrapper.eq("Bd_State", ApprovalStatusEnum.Already_Over.getIndex());
                InvBudgetDeclare data = baseMapper.selectOne(wrapper);
                if (ValidateUtil.isNotNull(data)) {
                    String id = data.getBdId();
                    datas = invBudgetDeclareDtlService.getInvBudgetDeclareDtlModelByBdId(id, pId);
                }
            }
        }
        datas = datas.stream().sorted(Comparator.comparing(InvBudgetDeclareDtlModel::getBddOrder)).collect(Collectors.toList());
//        Map<String, Object> result = new HashMap<>();
//        result.put("status", dcBudgetSum);
//        result.put("data", datas);
        return BaseResponse.ok(datas);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvalInvBudgetDeclare(InvBudgetDeclareRequest request) {
        String bdId = request.getBdId();
        SysFile sysFile = sysFileService.getById(bdId);
        String fileflowstatus = sysFile.getFileflowstatus();
        if ("7".equals(fileflowstatus)) {

            QueryWrapper<InvRelatedRelationships> wrapper = new QueryWrapper<>();
            wrapper.eq("Main_Id", bdId);
            List<InvRelatedRelationships> list = this.invRelatedRelationshipsMapper.selectList(wrapper);
            //调整数据
            for (InvRelatedRelationships item : list) {
                QueryWrapper<SecInfoBudgetDeclareDtl> wrapper2 = new QueryWrapper<>();
                wrapper.eq("P_Id", request.getpId());
                wrapper.eq("Bdd_Id", item.getOldSubId());
                List<SecInfoBudgetDeclareDtl> nlist = this.secInfoBudgetDeclareDtlMapperMapper.selectList(wrapper2);
                for (SecInfoBudgetDeclareDtl it : nlist) {
                    it.setBddId(item.getNowSubId());
                    this.secInfoBudgetDeclareDtlMapperMapper.updateById(it);
                }

            }

        }
        return BaseResponse.ok();
    }

    @Override
    public List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByPid(String pId) {
        List<InvBudgetDeclareResponse> resultList = getInvBudgetTypeDeclareByPid(pId);

        String bdId = resultList.stream().map(item -> item.getBdId()).findFirst().orElse(null);

        List<InvBudgetDeclareDtl> invBudgetDeclareDtlList = new ArrayList<>();
        if (StrUtil.isNotBlank(bdId)) {
            invBudgetDeclareDtlList = invBudgetDeclareDtlService.getInvTypeBudgetDeclareDtlByBdIdList(Arrays.asList(bdId));
        }

        return invBudgetDeclareDtlList;
    }

    /**
     * 获取项目类型预算信息: 获取顺序 Budget：概算、Estimate：估算 、Rough：匡算
     *
     * @param pId
     * @return
     */
    private List<InvBudgetDeclareResponse> getInvBudgetTypeDeclareByPid(String pId) {
        List<InvBudgetDeclareResponse> invBudgetDeclareResponseList = baseMapper.getInvBudgetDeclareByPid(pId, 7, Arrays.asList("Budget", "Estimate", "Rough"));
        //Budget：概算、Estimate：估算 、Rough：匡算
        List<InvBudgetDeclareResponse> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(invBudgetDeclareResponseList)) {
            resultList = invBudgetDeclareResponseList.stream().filter(item -> "Budget".equals(item.getBdType())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(resultList)) {
                resultList = invBudgetDeclareResponseList.stream().filter(item -> "Estimate".equals(item.getBdType())).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(resultList)) {
                    resultList = invBudgetDeclareResponseList.stream().filter(item -> "Rough".equals(item.getBdType())).collect(Collectors.toList());
                }
            }
        }
        return resultList;
    }

    @Override
    public List<InvBudgetDeclareDtlModel> getInvTypeBudgetDeclareDtlTreeByPid(String pId) {
        List<InvBudgetDeclareDtl> invBudgetDeclareDtls = getInvTypeBudgetDeclareDtlByPid(pId);
        List<InvBudgetDeclareDtlModel> firstList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(invBudgetDeclareDtls)) {
            List<InvBudgetDeclareDtlModel> budgetDtlModelList = com.jpmsoft.common.utils.BeanUtils.copyList(invBudgetDeclareDtls, InvBudgetDeclareDtlModel.class);
            firstList = budgetDtlModelList.stream().filter(item -> item.getBddParentId().equals("0")).collect(Collectors.toList());
            assembleTreeBudgetDtl(new InvBudgetDeclareDtlModel(), firstList, budgetDtlModelList);
        }
        return firstList;
    }

    private void assembleTreeBudgetDtl(InvBudgetDeclareDtlModel parentDeclareDtlModel, List<InvBudgetDeclareDtlModel> firstList, List<InvBudgetDeclareDtlModel> budgetDtlModelList) {
        String parentPath = "";
        if (parentDeclareDtlModel != null) {
            parentPath = parentDeclareDtlModel.getBddPath();
        }
        if (CollectionUtil.isNotEmpty(firstList)) {
            for (InvBudgetDeclareDtlModel invBudgetDeclareDtl : firstList) {
                List<InvBudgetDeclareDtlModel> childList = budgetDtlModelList.stream().filter(item -> item.getBddParentId().equals(invBudgetDeclareDtl.getBddId())).collect(Collectors.toList());
                invBudgetDeclareDtl.setBddPath((StrUtil.isNotBlank(parentPath) ? parentPath + "/" : "") + invBudgetDeclareDtl.getBddId());
                invBudgetDeclareDtl.setBddLeafNode(CollectionUtil.isEmpty(childList) ? 1 : 0);
                invBudgetDeclareDtl.setChildNodes(childList);
                assembleTreeBudgetDtl(invBudgetDeclareDtl, childList, budgetDtlModelList);
            }
        }
    }


    @Override
    public BaseResponse getInvBudgetDeclareDtoById(String id) {
        return BaseResponse.ok(baseMapper.getInvBudgetDeclareDtoById(id));
    }

    @Override
    public BaseResponse getInvBudgetDeclareDetailByPId(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail(ResMsg.notReqParam);
        // 项目下已审批结束的概算必须唯一，多条时直接报错
        QueryWrapper<InvBudgetDeclare> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Bd_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<InvBudgetDeclare> datas = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(datas)) return BaseResponse.fail(ResMsg.budgetDeclareNotExist);
        if (datas.size() > 1) return BaseResponse.fail(MessageFormat.format(ResMsg.budgetDeclareMultiple, datas.size()));

        // 主表详情（含编制单位、审核单位、项目名称等联表字段）
        InvBudgetDeclareResponse data = baseMapper.getInvBudgetDeclareByBdId(datas.get(0).getBdId());
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        // 概算清单树
        data.setInvBudgetDeclareDtls(invBudgetDeclareDtlService.getInvBudgetDeclareDtlModelByBdId(data.getBdId(), pId));

        // 汇总费用项：概算类型为 Budget：概算、Estimate：估算、Rough：匡算，按此优先级取唯一一条
        InvBudgetDeclareDetailResponse result = new InvBudgetDeclareDetailResponse();
        result.setDetail(data);
        List<String> bdTypes = Arrays.asList("Budget", "Estimate", "Rough");
        for (String bdType : bdTypes) {
            List<InvBudgetDeclare> items = datas.stream().filter(n -> bdType.equals(n.getBdType())).collect(Collectors.toList());
            if (items.size() > 1) return BaseResponse.fail(MessageFormat.format(ResMsg.budgetDeclareDetailNoUnique, items.size()));
            if (items.isEmpty()) continue;
            InvBudgetDeclare item = items.get(0);
            result.setEngineeringConstructionSafetyFeeAmount(item.getEngineeringConstructionSafetyFeeAmount());
            result.setEngineeringConstructionOtherFeeAmount(item.getEngineeringConstructionOtherFeeAmount());
            result.setLandFeeAmount(item.getLandFeeAmount());
            result.setConstructionPeriodLoanInterestAmount(item.getConstructionPeriodLoanInterestAmount());
            result.setReserveFeeAmount(item.getReserveFeeAmount());
            break;
        }
        return BaseResponse.ok(result);
    }
}
