package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateIsOverModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.EstimateListModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.*;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConEstimateMaintenanceMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvAdjustmentMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDocumentMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenance;
import com.jpmsoft.projectProducer.pojo.entity.fund.PayAmountInit;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustment;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.bim.QsRecordCountVO;
import com.jpmsoft.projectProducer.service.contract.IBGContactOrderService;
import com.jpmsoft.projectProducer.service.contract.IConEstimateMaintenanceDtlService;
import com.jpmsoft.projectProducer.service.contract.IConEstimateMaintenanceService;
import com.jpmsoft.projectProducer.service.contract.IConEstimateService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.investment.ITrackAuditMonthService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProApprovalDtlService;
import com.jpmsoft.projectProducer.service.project.IPreProjectDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlPriceService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConEstimateMaintenanceImpl extends ServiceImpl<ConEstimateMaintenanceMapper, ConEstimateMaintenance> implements IConEstimateMaintenanceService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IConEstimateMaintenanceDtlService conEstimateMaintenanceDtlService;

    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;

    @Resource
    private IPreProjectDtlService preProjectDtlService;

    @Resource
    private IPreProApprovalDtlService preProApprovalDtlService;

    @Resource
    private IConEstimateService conEstimateService;

    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private ISecInfoBudgetService secInfoBudgetService;

    @Resource
    private ISecControlPriceService secControlPriceService;

    @Resource
    private ITrackAuditMonthService trackAuditMonthService;

    @Resource
    private InvAdjustmentMapper invAdjustmentMapper;

    @Resource
    private SecDocumentMapper secDocumentMapper;


    @Resource
    private IBGContactOrderService bgContactOrderService;


    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConEstimateMaintenance(ConEstimateMaintenanceRequest request) {
        //新增修改操作
        ConEstimateMaintenance entity = createOrUpdateEntity(request);

        return BaseResponse.ok(entity.getCemId());
    }

    /**
     * 创建修改合同概况维护
     */
    private ConEstimateMaintenance createOrUpdateEntity(ConEstimateMaintenanceRequest request) {
        ConEstimateMaintenance entity;
        //endregion
        //region 新增修改操作
        if (ValidateUtil.isEmpty(request.getCemId())) {
            entity = new ConEstimateMaintenance();
            BeanUtils.copyProperties(request, entity);
            entity.setCemId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setAprrovalEndTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getCemId(), request.gettId(), entity.getState() + "", entity.getCreateUserId(), entity.getCreateUserName()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getCemId());
            //保存子表

            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getCemId());

        } else {
            entity = baseMapper.selectById(request.getCemId());
            if (entity == null) throw new JPMException(ResMsg.infoNotExist);
            ConEstimateMaintenance pojo = new ConEstimateMaintenance();
            BeanUtils.copyProperties(request, pojo);
            //以下参数不可更改
            pojo.setCreateUserId(entity.getCreateUserId());
            pojo.setCreateUserName(entity.getCreateUserName());
            pojo.setCreateTime(entity.getCreateTime());
            pojo.setState(entity.getState());
            pojo.setAprrovalEndTime(entity.getAprrovalEndTime());
            baseMapper.updateById(pojo);
            sysFileService.saveFile(entity.getCemId(), request.gettId(), entity.getState() + "", entity.getCreateUserId(), entity.getCreateUserName()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getCemId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getCemId());

        }
        conEstimateMaintenanceDtlService.createOrUpdateConEstimateMaintenanceDtl(request.getConEstimateMaintenanceDtlModel(), entity.getCemId());
        //endregion
        return entity;
    }

    @Override
    public BaseResponse getConEstimateMaintenanceList(ConEstimateMaintenanceRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Estimate_Maintenance", uId, "Create_User_Id", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ConEstimateMaintenanceRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("secName").put("cemType").put("state").put("createUserName");
        IPage<ConEstimateMaintenanceRequest> page = new Page<>(request.getPage(), request.getSize());
        List<ConEstimateMaintenanceRequest> data = baseMapper.getConEstimateMaintenanceList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    public BaseResponse getConEstimateMaintenanceById(String cemId) {
        return BaseResponse.ok(baseMapper.getConEstimateMaintenanceById(cemId));
    }

    public BaseResponse getConEstimateMaintenanceByConIdAndSecId(String conId, String secId) {
        return BaseResponse.ok(baseMapper.getConEstimateMaintenanceByConIdAndSecId(conId, secId));
    }

    public BaseResponse getConEstimateMaintenanceByIds(String pId, String type) {
        List<EstimateListModel> estimateListModels = new ArrayList<>();
        //概算类型 JPMsoft_GSLX    概算 01    估算 02     匡算 03
        if ("01".equals(type)) {
            //概算通过项目ID去获取
            List<InvBudgetDeclareDtlModel> value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlTreeListByPid(pId, "Budget");
            List<EstimateListModel> datas = new ArrayList<>();
            setNodesDtls(datas, value);
            estimateListModels = datas;
        } else if ("02".equals(type)) {
            //估算通过项目ID去获取
            List<InvBudgetDeclareDtlModel> value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlTreeListByPid(pId, "Estimate");
            List<EstimateListModel> datas = new ArrayList<>();
            setNodesDtls(datas, value);
            estimateListModels = datas;
        } else if ("03".equals(type)) {
            //匡算通过项目ID去获取
            List<InvBudgetDeclareDtlModel> value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlTreeListByPid(pId, "Rough");
            List<EstimateListModel> datas = new ArrayList<>();
            setNodesDtls(datas, value);
            estimateListModels = datas;
        }

        return BaseResponse.ok(estimateListModels);
    }

    private void setNodesDtls(List<EstimateListModel> datas, List<InvBudgetDeclareDtlModel> mainDtl) {
        for (InvBudgetDeclareDtlModel dtl : mainDtl) {
            EstimateListModel data = new EstimateListModel();
            data.setId(dtl.getBddId());
            data.setFid(dtl.getBdId());
            data.setNumber(dtl.getBddNumber());
            data.setParenetId(dtl.getBddParentId());
            data.setCode(dtl.getBddCode());
            data.setName(dtl.getBddName());
            data.setUnit(dtl.getBddUnit());
            data.setCount(dtl.getBddCount());
            data.setPrice(dtl.getBddPrice());
            data.setTotal(dtl.getBddTotal());
            data.setRemark(dtl.getBddRemark());
            data.setOrder(dtl.getBddOrder());
            data.setLevel(dtl.getBddLevel());
            data.setType(dtl.getBddType());
            data.setState(dtl.getBddState());
            data.setPath(dtl.getBddPath());
            data.setLeafNode(dtl.getBddLeafNode());
            if (dtl.getChildNodes() != null && dtl.getChildNodes().size() > 0) {
                List<EstimateListModel> childNode = new ArrayList<>();
                setNodesDtls(childNode, dtl.getChildNodes());
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void setNodesDtls2(List<EstimateListModel> datas, List<PreProApprovalDtlModel> mainDtl) {
        for (PreProApprovalDtlModel dtl : mainDtl) {
            EstimateListModel data = new EstimateListModel();
            data.setId(dtl.getPadId());
            data.setFid(dtl.getPadFid());
            data.setNumber(dtl.getPadNumber());
            data.setParenetId(dtl.getPadParenetId());
            data.setCode(dtl.getPadCode());
            data.setName(dtl.getPadNumber());
            data.setUnit(dtl.getPadUnit());
            data.setCount(dtl.getPadCount());
            data.setPrice(dtl.getPadPrice());
            data.setTotal(dtl.getPadTotal());
            data.setRemark(dtl.getPadRemark());
            data.setOrder(dtl.getPadOrder());
            data.setLevel(dtl.getPadLevel());
            data.setType(dtl.getPadType());
            data.setState(dtl.getPadState());
            data.setPath(dtl.getPadPath());
            data.setLeafNode(dtl.getPadLeafNode());
            if (dtl.getChildNodes() != null && dtl.getChildNodes().size() > 0) {
                List<EstimateListModel> childNode = new ArrayList<>();
                setNodesDtls2(childNode, dtl.getChildNodes());
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    private void setNodesDtls3(List<EstimateListModel> datas, List<PreProjectDtlModel> mainDtl) {
        for (PreProjectDtlModel dtl : mainDtl) {
            EstimateListModel data = new EstimateListModel();
            data.setId(dtl.getPpdId());
            data.setFid(dtl.getPpdFid());
            data.setNumber(dtl.getPpdNumber());
            data.setParenetId(dtl.getPpdParenetId());
            data.setCode(dtl.getPpdCode());
            data.setName(dtl.getPpdName());
            data.setUnit(dtl.getPpdUnit());
            data.setCount(dtl.getPpdCount());
            data.setPrice(dtl.getPpdPrice());
            data.setTotal(dtl.getPpdTotal());
            data.setRemark(dtl.getPpdRemark());
            data.setOrder(dtl.getPpdOrder());
            data.setLevel(dtl.getPpdLevel());
            data.setType(dtl.getPpdType());
            data.setState(dtl.getPpdState());
            data.setPath(dtl.getPpdPath());
            data.setLeafNode(dtl.getPpdLeafNode());
            if (dtl.getChildNodes() != null && dtl.getChildNodes().size() > 0) {
                List<EstimateListModel> childNode = new ArrayList<>();
                setNodesDtls3(childNode, dtl.getChildNodes());
                data.setChildNodes(childNode);
            }
            datas.add(data);
        }
    }

    //审批节点更新---流程为7的时候像合同概算表中插入数据
    @Override
    public BaseResponse updateConEstimate(ConEstimateMaintenanceRequest request) {
        SysFile file = sysFileService.getById(request.getCemId());
        String state = file.getFileflowstatus();
        if ("7".equals(state)) {
            List<ConEstimateMaintenanceDtlModel> conEstimateMaintenance = conEstimateMaintenanceDtlService.getConEstimateMaintenanceDtlByCemId(request.getCemId());
            if (ValidateUtil.isNotNull(conEstimateMaintenance)) {
                //向合同概算表中插入数据
                List<ConEstimateModel> value = conEstimateMaintenance.stream().map(n -> {
                    ConEstimateModel model = new ConEstimateModel();
                    model.setCeId(GenerateUUID.getUUID());
                    model.setConId(request.getConId());
                    model.setCeCost(n.getCemdCost());
                    model.setCeCostName(n.getCemdCostName());
                    model.setCeMainCost(n.getCemdMainCost());
                    model.setCeMainCostName(n.getCemdMainCostName());
                    model.setCeCompany(n.getCemdCompany());
                    model.setCeQuantities(n.getCemdQuantities());
                    model.setCeUnitPrice(n.getCemdUnitPrice());
                    model.setCeInvestment(n.getCemdInvestment());
                    model.setCeRemarks(n.getCemdRemarks());
                    model.setCeTotalExpenses(n.getCemdTotalExpenses());
                    model.setCeExpenseType(n.getCemdExpenseType());
                    model.setCeStageType(n.getCemdStageType());
                    return model;
                }).collect(Collectors.toList());
                conEstimateService.createOrUpdateConEstimate(value, request.getConId());
            }
        }
        return BaseResponse.ok("1");
    }


    /**
     * 根据项目ID获取该项目下概算/估算/匡算的总额
     */
    @Override
    public BaseResponse getConEstimateIsOver(String pId, String secId) {
        ConEstimateIsOverModel result = new ConEstimateIsOverModel();
        //判断这个标段是否关联概算等信息
        if (ValidateUtil.isNotNull(secId)) {
            SecInfoResponse secInfoResponse = (SecInfoResponse) secInfoService.getSecInfoById(secId).getData();
            if (ValidateUtil.isNotNull(secInfoResponse) && ValidateUtil.isNotNull(secInfoResponse.getSecInfoBudgets())) {
                List<SecInfoBudgetModel> list = secInfoResponse.getSecInfoBudgets();
                result.setType(list.get(0).getSibStageType());
                result.setAmount(list.stream().filter(Objects::nonNull).
                        map(SecInfoBudgetModel::getSibInvestment).
                        reduce(BigDecimal.ZERO, NumberUtil::add));
            }
        } else {
            //如果没做关联的话，直接查询这个项目有没有概算，有概算的话获取子节点中的概算值
            BigDecimal value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlAmountByPid(pId);
            if (ValidateUtil.isNotNull(value)) {
                result.setType("01");
                result.setAmount(value);
            } else {
                //概算为空的话查找项目立项中的估算
                BigDecimal value2 = preProApprovalDtlService.getPreProApprovalDtlAmountByPid(pId);
                if (ValidateUtil.isNotNull(value2)) {
                    result.setType("02");
                    result.setAmount(value2);
                } else {
                    //最后获取项目信息里面的匡算
                    BigDecimal value3 = preProjectDtlService.getPreProjectDtlAmountByPid(pId);
                    if (ValidateUtil.isNotNull(value3)) {
                        result.setType("03");
                        result.setAmount(value3);
                    }
                }
            }
        }
        return BaseResponse.ok(result);
    }


    public String getFromCache(String key) {
        return cache.get(key);
    }

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 投资控制汇总表
     * 根据项目ID去读取
     */
    public BaseResponse getInvestmentTableByPId(String pId) {
        Map<String, Object> map = new HashMap<>();
        List<InvPlanTableModel> datas = new ArrayList<>();
        List<InvPlanTableModel> ndata = new ArrayList<>();
        //根据PID获取概算科目
        List<InvBudgetDeclareDtlModel> value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlDetailsListByPid(pId, "Budget");
        //根据PID获取跟审月报的信息
        List<TrackAuditMonthDtlModel> trackAuditMonthDtlModels = (List<TrackAuditMonthDtlModel>) trackAuditMonthService.getTrackAuditMonthListByPId(pId).getData();
        //根据项目ID获取招标控制价集合
        List<SecControlPriceResponse> secControlPriceResponse = secControlPriceService.getControlPriceBypId(pId);
        //根据项目获取招标文件信息

        QueryWrapper<SecDocument> wrappersd = new QueryWrapper<>();
        wrappersd.eq("D_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrappersd.eq("P_Id", pId);
        List<SecDocument> secDocumentList = secDocumentMapper.selectList(wrappersd);

        //获取项目下的标段的关联的概算
        List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtlList = secInfoBudgetService.getSecInfoBudgetListByPId(pId);
        if (ValidateUtil.isNotNull(value)) {
            for (InvBudgetDeclareDtlModel n : value) {
                InvPlanTableModel model = new InvPlanTableModel();
                model.setBudgetId(n.getBddId());
                model.setBudgetParentId(n.getBddParentId());
                model.setOrder(n.getBddOrder());
                //概算A的总金额
                BigDecimal totalBudgetAmount = n.getBddTotal();
                BigDecimal subBudgetAmount = new BigDecimal(0);
                //根据概算ID查询出  概算-标段-合同 关联关系表
                List<InvBudgetConSecModel> invBudgetConSecModels = secInfoBudgetService.getSecInfoBudgetById(n.getBddId());
                List<String> conIds = invBudgetConSecModels.stream().map(InvBudgetConSecModel::getConId).collect(Collectors.toList());
                Map<String, BigDecimal> bgContactOrderServiceContractChangeAmountSum = bgContactOrderService.getContractChangeAmountSum(conIds);

                if (ValidateUtil.isNotEmpty(invBudgetConSecModels)) {
                    for (InvBudgetConSecModel data : invBudgetConSecModels) {
                        String budgetId = data.getBudgetId();
                        String FID = data.getBudgetParentId();
                        //判断是否有父节点
                        if ("0".equals(FID))
                            model.setNodeDes("0");
                        else {
                            Integer count = invBudgetConSecModels.stream().filter(k -> FID.equals(k.getBudgetId())).collect(Collectors.toList()).size();
                            if (count > 0)
                                model.setNodeDes("1");
                            else
                                model.setNodeDes("2");
                        }
                        //先判断父节点是否有缓存
//                    String path=getFromCache(FID);
//                    if(ValidateUtil.isNotNull(path))
//                    {
//                        path=path+"-"+budgetId;
//                        model.setLeafPath(path);
//                        addToCache(FID,budgetId);
//                    }
//                    else {
//                        path=GetFParentPath(FID,value,"");
//                        path=path+"-"+budgetId;
//                        model.setLeafPath(path);
//                        addToCache(FID,budgetId);
//                    }
                        InvPlanTableModel ifexist = datas.stream().filter(k -> data.getConName().equals(k.getConName())).findFirst().orElse(null);

                        String SecId = data.getSecId();
                        subBudgetAmount = subBudgetAmount.add(data.getSelectAmount());
                        model.setNum(n.getBddNumber());
                        if (ValidateUtil.isNotNull(ifexist)) {
                            continue;
                        }
                        //合同信息
                        model.setConId(data.getConId());
                        model.setConName(data.getConName());

                        //判断该合同是否有多个科目
                        if (ValidateUtil.isNotNull(secInfoBudgetDeclareDtlList)) {
                            List<SecInfoBudgetDeclareDtl> seclist = secInfoBudgetDeclareDtlList.stream().filter(k -> SecId.equals(k.getSecId())).collect(Collectors.toList());
                            String nameList = seclist.stream().map(SecInfoBudgetDeclareDtl::getBddName).collect(Collectors.joining("、"));
                            BigDecimal BudAmount = seclist.stream().map(SecInfoBudgetDeclareDtl::getSelectAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                            if (seclist.size() > 1) {
                                model.setIfManyBudget("1");
                                List<InvPlanTableModel> subList = new ArrayList<>();
                                for (SecInfoBudgetDeclareDtl it : seclist) {
                                    InvPlanTableModel sub = new InvPlanTableModel();
                                    sub.setCostName(it.getBddName());
                                    sub.setBudgetAmount(it.getSelectAmount());
                                    subList.add(sub);
                                }
                                model.setManyBudget(subList);
                            }
                            model.setCostName(nameList);
                            model.setBudgetAmount(BudAmount);
                        } else {
                            model.setCostName(n.getBddName());
                            //概算金额 A
                            model.setBudgetAmount(data.getSelectAmount());
                        }
                        //招标控制价 A1
                        SecControlPriceResponse secControlPrice = secControlPriceResponse.stream().filter(k -> SecId.equals(k.getSecId())).sorted(Comparator.comparing(SecControlPriceResponse::getCpStateTime).reversed()).findFirst().orElse(null);
                        if (ValidateUtil.isNotNull(secControlPrice)) {
                            model.setTenderAmount(secControlPrice.getCpBudgetPrice());
                        } else {
                            String conBidDocument = data.getConBidDocument();
                            if (ValidateUtil.isNotNull(conBidDocument)) {
                                //取招标文件中的
                                SecDocument secDocument = secDocumentList.stream().filter(k -> conBidDocument.equals(k.getDId())).findFirst().orElse(null);
                                if (ValidateUtil.isNotNull(secDocument)) {
                                    model.setTenderAmount(secDocument.getDAmount());
                                }
                            }
                        }

                        //合同金额 B
                        model.setConAmount(data.getConMoneyTotal());
                        //预留金--新增合同字段、抓取 B1
                        model.setReserveAmount(data.getConReserveMoney());

                        //已完成产值 B2  获取跟审月报中的数据
                        if (ValidateUtil.isNotNull(trackAuditMonthDtlModels)) {
                            TrackAuditMonthDtlModel track = trackAuditMonthDtlModels.stream().filter(k -> data.getConId().equals(k.getConId())).findFirst().orElse(null);
                            if (ValidateUtil.isNotNull(track)) {
                                BigDecimal completedValue = track.getTrackAuditMonthDtlQD().stream().map(TrackAuditMonthDtlQD::getCompleteValue).reduce(BigDecimal.ZERO, NumberUtil::add);
                                model.setCompletedValue(completedValue);
                            }
                        }
                        //获取金额
                        BigDecimal changeVisaAmount=bgContactOrderServiceContractChangeAmountSum.get(data.getConId());
                        //变更签证累计金额 C
                        model.setChangeVisaAmount(changeVisaAmount);

                        //预计调整金额 D
                        QueryWrapper<InvAdjustment> wrapper = new QueryWrapper<>();
                        wrapper.eq("P_Id", pId);
                        wrapper.eq("Con_Id", data.getConId());
                        wrapper.eq("State", ApprovalStatusEnum.Already_Over.getIndex());
                        wrapper.orderByDesc("State_Time");
                        InvAdjustment invAdjustment = this.invAdjustmentMapper.selectList(wrapper).stream().findFirst().orElse(null);
                        if (ValidateUtil.isNotNull(invAdjustment)) {
                            model.setPlanAdjustAmount(invAdjustment.getTotalAmount());
                        }

                        BigDecimal A = model.getBudgetAmount() == null ? new BigDecimal(0) : model.getBudgetAmount();
                        BigDecimal B = model.getConAmount() == null ? new BigDecimal(0) : model.getConAmount();
                        BigDecimal C = model.getChangeVisaAmount() == null ? new BigDecimal(0) : model.getChangeVisaAmount();
                        BigDecimal D = model.getPlanAdjustAmount() == null ? new BigDecimal(0) : model.getPlanAdjustAmount();
                        BigDecimal B1 = model.getReserveAmount() == null ? new BigDecimal(0) : model.getReserveAmount();

                        //合同结算金额（预估） E=B+C+D-B1
                        if (model.getConAmount() != null || model.getChangeVisaAmount() != null || model.getPlanAdjustAmount() != null || model.getReserveAmount() != null) {
                            model.setConSettlementAmount(B.add(C).add(D).subtract(B1));
                        }
                        BigDecimal E = model.getConSettlementAmount() == null ? new BigDecimal(0) : model.getConSettlementAmount();

                        //超合同金额（预估） F=E-B
                        if (model.getConSettlementAmount() != null || model.getConAmount() != null) {
                            model.setOverConAmount(E.subtract(B));
                        }

                        BigDecimal F = model.getOverConAmount() == null ? new BigDecimal(0) : model.getOverConAmount();
                        //超合同比例（预估） F1=F/B
                        if (model.getOverConAmount() != null || model.getConAmount() != null) {
                            if (ValidateUtil.isNotNull(model.getConAmount()) && !B.equals(new BigDecimal(0))) {
                                model.setOverConRate(MathUtil.decimalDivide(F, B, 4).multiply(new BigDecimal(100)));
                            }
                        }
                        //超概算金额（预估） G=E-A
                        if (model.getConSettlementAmount() != null || model.getBudgetAmount() != null) {
                            model.setOverInvAmount(E.subtract(A));
                        }

                        BigDecimal G = model.getOverInvAmount() == null ? new BigDecimal(0) : model.getOverInvAmount();
                        //超概算比例（预估） G1=G/A
                        if (model.getOverInvAmount() != null || model.getBudgetAmount() != null) {
                            if (ValidateUtil.isNotNull(model.getBudgetAmount()) && !A.equals(new BigDecimal(0))) {
                                model.setOverInvRate(MathUtil.decimalDivide(G, A, 4).multiply(new BigDecimal(100)));
                            }
                        }

                        //概算动态管控金额 H=A+G
                        if (model.getBudgetAmount() != null || model.getOverInvAmount() != null) {
                            model.setDynamicControlAmount(A.add(G));
                        }
                        //region 河北投资控制台账信息
//                        model.setEnisHbMoneySum(data.getEnisHbMoneySum());
//                        model.setEcFinalMoneySum(data.getEcFinalMoneySum());
//                        model.setPayApprovalSum(data.getPayApprovalSum());
//                        //付款比例 累计付款金额/合同金额
//                        model.setPayRatio(MathUtil.decimalDivide(data.getPayApprovalSum(),model.getConAmount()));
//                        model.setChangeAmountSum(MathUtil.decAdd(data.getEnisHbMoneySum(),data.getEcFinalMoneySum()));
//                        model.setChangeConAmount(MathUtil.decAdd(model.getConAmount(),model.getChangeAmountSum()));
//                        model.setChangeBudgetRatio(MathUtil.decimalDivide(model.getChangeConAmount(),model.getBudgetAmount()));
                        //变更累计金额

                        //endregion
                        //备注
                        model.setRemark(null);
                    }
                }
//            else {
//                String budgetId=n.getBddId();
//                String FID=n.getBddParentId();
//                //判断是否有父节点
//                if("0".equals(FID))
//                    model.setNodeDes("0");
//                else
//                {
//                    Integer count=invBudgetConSecModels.stream().filter(k->FID.equals(k.getBudgetId())).collect(Collectors.toList()).size();
//                    if(count>0)
//                        model.setNodeDes("1");
//                    else
//                        model.setNodeDes("2");
//                }
//                //判断是否有父节点
//                //先判断父节点是否有缓存
//                String path=getFromCache(FID);
//                if(ValidateUtil.isNotNull(path))
//                {
//                    path=path+"-"+budgetId;
//                    model.setLeafPath(path);
//                    addToCache(FID,budgetId);
//                }
//                else {
//                    path=GetFParentPath(FID,value,"");
//                    path=path+"-"+budgetId;
//                    model.setLeafPath(path);
//                    addToCache(FID,budgetId);
//                }
//                model.setCostName(n.getBddName());
//                BigDecimal leftBudgetAmount=totalBudgetAmount.subtract(subBudgetAmount);
//                subBudgetAmount=totalBudgetAmount;
//                model.setBudgetAmount(leftBudgetAmount);
//            }
                if (ValidateUtil.isNotNull(model.getCostName()))
                    datas.add(model);
//            if(subBudgetAmount!=totalBudgetAmount)
//            {
//                InvPlanTableModel model2=new InvPlanTableModel();
//                String budgetId=n.getBddId();
//                String FID=n.getBddParentId();
//                //判断是否有父节点
//                //判断是否有父节点
//                if("0".equals(FID))
//                    model.setNodeDes("0");
//                else
//                {
//                    Integer count=invBudgetConSecModels.stream().filter(k->FID.equals(k.getBudgetId())).collect(Collectors.toList()).size();
//                    if(count>0)
//                        model.setNodeDes("1");
//                    else
//                        model.setNodeDes("2");
//                }
//                //先判断父节点是否有缓存
//                String path=getFromCache(FID);
//                if(ValidateUtil.isNotNull(path))
//                {
//                    path=path+"-"+budgetId;
//                    model2.setLeafPath(path);
//                    addToCache(FID,budgetId);
//                }
//                else {
//                    path=GetFParentPath(FID,value,"");
//                    path=path+"-"+budgetId;
//                    model2.setLeafPath(path);
//                    addToCache(FID,budgetId);
//                }
//                model2.setCostName(n.getBddName());
//                BigDecimal leftBudgetAmount=totalBudgetAmount.subtract(subBudgetAmount);
//                model2.setBudgetAmount(leftBudgetAmount);
//                datas.add(model2);
//            }

            } ;

            //对主节点数据的处理
            //datas=SetData("0",ndata,value,datas).stream().sorted(Comparator.comparing(InvPlanTableModel::getOrder)).collect(Collectors.toList());

            List<InvPlanTableModel> kdata = SetNewData(datas, value);
            List<InvPlanTableModel> mainDtl = kdata.stream().filter(n -> n.getBudgetParentId().equals("0")).collect(Collectors.toList());
            //对主节点数据的处理
            ndata = SetData2(ndata, datas, mainDtl).stream().sorted(Comparator.comparing(InvPlanTableModel::getOrder)).collect(Collectors.toList());
        }
        BigDecimal money = ndata.stream().filter(n -> n.getBudgetParentId().equals("0")).map(InvPlanTableModel::getBudgetAmount).reduce(BigDecimal.ZERO, NumberUtil::add);
        BigDecimal totalmoney = value.stream().filter(n -> n.getBddParentId().equals("0")).map(InvBudgetDeclareDtlModel::getBddTotal).reduce(BigDecimal.ZERO, NumberUtil::add);
        BigDecimal leftmoney = totalmoney.subtract(money);
        map.put("datas", ndata);
        map.put("money", money);
        map.put("totalmoney", totalmoney);
        map.put("leftmoney", leftmoney);
        return BaseResponse.ok(map);
    }

    public List<InvPlanTableModel> SetNewData(List<InvPlanTableModel> datas, List<InvBudgetDeclareDtlModel> value) {
        List<InvPlanTableModel> kdata = new ArrayList<>();
        for (InvPlanTableModel item : datas) {
            kdata.add(item);
            InvPlanTableModel it = kdata.stream().filter(k -> item.getBudgetParentId().equals(k.getBudgetId())).findFirst().orElse(null);
            if (ValidateUtil.isNull(it)) {
                InvBudgetDeclareDtlModel obj = value.stream().filter(k -> item.getBudgetParentId().equals(k.getBddId())).findFirst().orElse(null);
                if (ValidateUtil.isNotNull(obj)) {
                    InvPlanTableModel result = new InvPlanTableModel();
                    result.setBudgetId(obj.getBddId());
                    result.setBudgetParentId(obj.getBddParentId());
                    result.setCostName(obj.getBddName());
                    result.setOrder(obj.getBddOrder());
                    kdata.add(result);
                }
            }

        }
        return kdata;
    }

    public List<InvPlanTableModel> SetData2(List<InvPlanTableModel> ndata, List<InvPlanTableModel> pojos, List<InvPlanTableModel> mainDtl) {
        for (InvPlanTableModel dtl : mainDtl) {
            InvPlanTableModel data = new InvPlanTableModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvPlanTableModel> childs = pojos.stream().filter(n -> n.getBudgetParentId().equals(data.getBudgetId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvPlanTableModel> childNode = new ArrayList<>();
                SetData2(childNode, pojos, childs);
                data.setChildData(childNode);

                if (ValidateUtil.isNotNull(childNode)) {
                    BigDecimal A = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getBudgetAmount())).map(InvPlanTableModel::getBudgetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal A1 = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getTenderAmount())).map(InvPlanTableModel::getTenderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal B = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getConAmount())).map(InvPlanTableModel::getConAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal B1 = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getReserveAmount())).map(InvPlanTableModel::getReserveAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal B2 = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getCompletedValue())).map(InvPlanTableModel::getCompletedValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal C = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getChangeVisaAmount())).map(InvPlanTableModel::getChangeVisaAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal D = childNode.stream().filter(k -> ValidateUtil.isNotNull(k.getPlanAdjustAmount())).map(InvPlanTableModel::getPlanAdjustAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                    data.setBudgetAmount(A);
                    data.setTenderAmount(A1);
                    data.setConAmount(B);
                    data.setReserveAmount(B1);
                    data.setCompletedValue(B2);
                    data.setChangeVisaAmount(C);
                    data.setPlanAdjustAmount(D);

                    //合同结算金额（预估） E=B+C+D-B1
                    data.setConSettlementAmount(B.add(C).add(D).subtract(B1));
                    BigDecimal E = data.getConSettlementAmount() == null ? new BigDecimal(0) : data.getConSettlementAmount();

                    //超合同金额（预估） F=E-B
                    if (data.getConSettlementAmount() != null || data.getConAmount() != null) {
                        data.setOverConAmount(E.subtract(B));
                    }

                    BigDecimal F = data.getOverConAmount() == null ? new BigDecimal(0) : data.getOverConAmount();
                    //超合同比例（预估） F1=F/B
                    if (data.getOverConAmount() != null || data.getConAmount() != null) {
                        if (ValidateUtil.isNotNull(data.getConAmount()) && !B.equals(new BigDecimal(0))) {
                            data.setOverConRate(MathUtil.decimalDivide(F, B, 4).multiply(new BigDecimal(100)));
                        }
                    }
                    //超概算金额（预估） G=E-A
                    if (data.getConSettlementAmount() != null || data.getBudgetAmount() != null) {
                        data.setOverInvAmount(E.subtract(A));
                    }

                    BigDecimal G = data.getOverInvAmount() == null ? new BigDecimal(0) : data.getOverInvAmount();
                    //超概算比例（预估） G1=G/A
                    if (data.getOverInvAmount() != null || data.getBudgetAmount() != null) {
                        if (ValidateUtil.isNotNull(data.getBudgetAmount()) && !A.equals(new BigDecimal(0))) {
                            data.setOverInvRate(MathUtil.decimalDivide(G, A, 4).multiply(new BigDecimal(100)));
                        }
                    }

                    //概算动态管控金额 H=A+G
                    if (data.getBudgetAmount() != null || data.getOverInvAmount() != null) {
                        data.setDynamicControlAmount(A.add(G));
                    }
                }

            }
            ndata.add(data);
        }

        return ndata;
    }

    public List<InvPlanTableModel> SetData(String FID, List<InvPlanTableModel> data, List<InvBudgetDeclareDtlModel> total, List<InvPlanTableModel> leftdata) {
        InvBudgetDeclareDtlModel obj = total.stream().filter(k -> FID.equals(k.getBddId())).findFirst().orElse(null);
        List<InvPlanTableModel> nlist = new ArrayList<>();
        if (ValidateUtil.isNotNull(obj)) {
            InvPlanTableModel it = new InvPlanTableModel();
            it.setBudgetId(obj.getBddId());
            it.setBudgetParentId(obj.getBddParentId());
            it.setCostName(obj.getBddName());
            nlist = leftdata.stream().filter(k -> FID.equals(k.getBudgetParentId())).sorted(Comparator.comparing(InvPlanTableModel::getOrder)).collect(Collectors.toList());
            BigDecimal Amount = leftdata.stream().map(InvPlanTableModel::getBudgetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            it.setBudgetAmount(Amount);
            it.setOrder(obj.getBddOrder());
            it.setChildData(nlist);
            data.add(it);
        }
        leftdata = leftdata.stream().filter(k -> !FID.equals(k.getBudgetParentId())).collect(Collectors.toList());
        InvPlanTableModel leftlist = leftdata.stream().findFirst().orElse(null);
        if (ValidateUtil.isNotNull(leftlist)) {
            String nFId = leftlist.getBudgetParentId();
            SetData(nFId, data, total, leftdata);
        }
        return data;
    }


    public String GetFParentPath(String FID, List<InvBudgetDeclareDtlModel> data, String path) {
        Optional<InvBudgetDeclareDtlModel> FList = data.stream().filter(k -> FID.equals(k.getBddId())).findFirst();
        if (FList.isPresent()) {
            if ("".equals(path)) {
                if (FList.map(InvBudgetDeclareDtlModel::getBddId).isPresent())
                    path = FList.map(InvBudgetDeclareDtlModel::getBddId).get();
            } else {
                if (FList.map(InvBudgetDeclareDtlModel::getBddId).isPresent())
                    path = FList.map(InvBudgetDeclareDtlModel::getBddId).get() + "-" + path;
            }
            String NFID = "";
            if (FList.map(InvBudgetDeclareDtlModel::getBddParentId).isPresent())
                NFID = FList.map(InvBudgetDeclareDtlModel::getBddParentId).get();
            String Kpath = getFromCache(FID);
            if (ValidateUtil.isNotNull(Kpath)) {
                path = path + "-" + FID;
            } else {
                GetFParentPath(NFID, data, path);
            }
        }
        return path;
    }

    /**
     * 投资控制汇总表
     * 根据项目ID去读取
     */
    public BaseResponse getInvestmentTableSecondByPId(String pId) {
        Map<String, Object> map = new HashMap<>();
        List<InvPlanTableSecModel> datas = new ArrayList<>();
        List<InvPlanTableSecModel> ndata = new ArrayList<>();
        //根据PID获取概算科目
        List<InvBudgetDeclareDtlModel> value = invBudgetDeclareDtlService.getInvBudgetDeclareDtlDetailsListByPid(pId, "Budget");
        //获取项目下的标段的关联的概算
        List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtlList = secInfoBudgetService.getSecInfoBudgetListByPId(pId);
        if (ValidateUtil.isNotNull(value)) {
            for (InvBudgetDeclareDtlModel n : value) {
                InvPlanTableSecModel model = new InvPlanTableSecModel();
                model.setBudgetId(n.getBddId());
                model.setBudgetParentId(n.getBddParentId());
                model.setOrder(n.getBddOrder());
                model.setCostName(n.getBddName());
                model.setTotalAmount(n.getBddTotal());
                //根据概算ID查询出  概算-标段-合同 关联关系表
                BigDecimal alReadyAmount = secInfoBudgetService.getSecInfoBudgetById(n.getBddId()).stream().map(InvBudgetConSecModel::getSelectAmount).reduce(BigDecimal.ZERO, NumberUtil::add);
                model.setAlReadyAmount(alReadyAmount);
                BigDecimal noAmount = n.getBddTotal().subtract(alReadyAmount);
                model.setNoAmount(noAmount);
                //已使用占比
                if (ValidateUtil.isNotNull(n.getBddTotal()) && !n.getBddTotal().equals(new BigDecimal(0))) {
                    model.setRate(MathUtil.decimalDivide(alReadyAmount, n.getBddTotal(), 4).multiply(new BigDecimal(100)));
                }
                datas.add(model);
            }
            ;
            List<InvPlanTableSecModel> mainDtl = datas.stream().filter(n -> n.getBudgetParentId().equals("0")).collect(Collectors.toList());
            //对主节点数据的处理
            SetDataSec(ndata, datas, mainDtl);
            ndata = ndata.stream().sorted(Comparator.comparing(InvPlanTableSecModel::getOrder)).collect(Collectors.toList());

        }
        map.put("datas", ndata);
        return BaseResponse.ok(map);
    }

    public List<InvPlanTableSecModel> SetDataSec(List<InvPlanTableSecModel> ndata, List<InvPlanTableSecModel> pojos, List<InvPlanTableSecModel> mainDtl) {
        for (InvPlanTableSecModel dtl : mainDtl) {
            InvPlanTableSecModel data = new InvPlanTableSecModel();
            BeanUtils.copyProperties(dtl, data);
            List<InvPlanTableSecModel> childs = pojos.stream().filter(n -> n.getBudgetParentId().equals(data.getBudgetId())).collect(Collectors.toList());
            if (childs != null && childs.size() > 0) {
                List<InvPlanTableSecModel> childNode = new ArrayList<>();
                SetDataSec(childNode, pojos, childs);
                data.setChildData(childNode);

                BigDecimal TotalAmount = childNode.stream().map(InvPlanTableSecModel::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal AlReadyAmount = childNode.stream().map(InvPlanTableSecModel::getAlReadyAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal noAmount = childNode.stream().map(InvPlanTableSecModel::getNoAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                data.setTotalAmount(TotalAmount);
                data.setAlReadyAmount(AlReadyAmount);
                data.setNoAmount(noAmount);
                //已使用占比
                if (ValidateUtil.isNotNull(TotalAmount) && !TotalAmount.equals(new BigDecimal(0))) {
                    data.setRate(MathUtil.decimalDivide(AlReadyAmount, TotalAmount, 4).multiply(new BigDecimal(100)));
                }
            }
            ndata.add(data);
        }

        return ndata;
    }
}
