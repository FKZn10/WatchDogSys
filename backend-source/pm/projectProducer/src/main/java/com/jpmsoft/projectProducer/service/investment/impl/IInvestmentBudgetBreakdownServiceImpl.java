package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvestmentControlRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.*;
import com.jpmsoft.common.model.response.InvestmentControl.InvestmentBreakdownSecInfoModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvestmentControlResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.*;
import com.jpmsoft.projectProducer.pojo.entity.investment.*;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.request.investment.InvestmentControlInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentBudgetBreakdownVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownChangeService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownChangeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author yb.Sun
 * @date 2024/11/1 15:06
 */
@Service
@Slf4j
public class IInvestmentBudgetBreakdownServiceImpl extends BaseFileImpl<InvestmentBudgetBreakdownMapper, InvestmentBudgetBreakdown, InvestmentBudgetBreakdownModel> implements IInvestmentBudgetBreakdownService {
    @Autowired
    private InvestmentBudgetBreakdownDetailsMapper investmentBudgetBreakdownDetailsMapper;
    @Autowired
    private InvestmentBudgetBreakdownChangeDetailsMapper investmentBudgetBreakdownChangeDetailsMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private InvestmentEstimateBreakdownMapper investmentEstimateBreakdownMapper;
    @Autowired
    private InvestmentEstimateBreakdownChangeMapper investmentEstimateBreakdownChangeMapper;
    @Autowired
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Autowired
    private InvestmentEstimateBreakdownChangeDetailsMapper investmentEstimateBreakdownChangeDetailsMapper;

    @Resource
    private IInvestmentBudgetBreakdownChangeService investmentBudgetBreakdownChangeService;
    @Resource
    private IInvestmentEstimateBreakdownChangeService InvestmentEstimateBreakdownChangeService;

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvestmentBudgetBreakdown(InvestmentBudgetBreakdownModel request) {
        String primaryId = request.getId();
        InvestmentBudgetBreakdown entity;
        if (primaryId == null) {
            InvestmentBudgetBreakdown oldEntity = baseMapper.selectByProjectId(request.getProjectId());
            if (oldEntity != null) {
                //每个项目只能新增一次，如需修改，使用变更操作。
                return BaseResponse.fail("每个项目只能新增一次");
            }
            entity = new InvestmentBudgetBreakdown();
            BeanUtil.copyProperties(request, entity);
            primaryId = GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            createInvestmentBudgetBreakdownDetails(request, primaryId);
            if (ValidateUtil.isEmpty(request.getPId())) {
                request.setPId(request.getProjectId());
            }
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.getTCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getProjectBudgetFile(),request.getInvestMentFile()),Arrays.asList(request.getFileIds(),request.getProjectBudgetFileIds(),request.getInvestMentFileIds()), entity.getId());
            // 1. 检查前置条件（关键参数非空）
            if (request.getProjectBudgetFile() != null) {
                LambdaQueryWrapper<SysFileannex> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysFileannex::getFileRelationId, request.getProjectBudgetFile());
                List<SysFileannex> fileList = sysFileAnnexService.list(queryWrapper);

                if (CollectionUtil.isEmpty(fileList)) {
                    log.info("未找到关联的文件附件，relationId: {}", request.getProjectBudgetFile());
                }
                List<SysFileannex> sysfileAnnexs = fileList.stream()
                        .filter(Objects::nonNull) // 防御性过滤
                        .peek(file -> file.setUploadStatus(1))
                        .collect(Collectors.toList());
                try {
                    sysFileAnnexService.updateBatchById(sysfileAnnexs);
                } catch (
                        Exception e
                ){
                    return BaseResponse.fail("未刷新文件状态");
                }
            }

            //创建流程
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request, entity);
            baseMapper.updateById(entity);
            //删除原有详情
            investmentBudgetBreakdownDetailsMapper.deleteByIsbId(primaryId);
            createInvestmentBudgetBreakdownDetails(request, primaryId);
            if (ValidateUtil.isEmpty(request.getPId())) {
                request.setPId(request.getProjectId());
            }
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.getTCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getProjectBudgetFile(),request.getInvestMentFile()),Arrays.asList(request.getFileIds(),request.getProjectBudgetFileIds(),request.getInvestMentFileIds()), entity.getId());
            if (request.getProjectBudgetFile() != null) {
                LambdaQueryWrapper<SysFileannex> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysFileannex::getFileRelationId, request.getProjectBudgetFile());
                List<SysFileannex> fileList = sysFileAnnexService.list(queryWrapper);

                if (CollectionUtil.isEmpty(fileList)) {
                    log.info("未找到关联的文件附件，relationId: {}", request.getProjectBudgetFile());
                }
                List<SysFileannex> sysfileAnnexs = fileList.stream()
                        .filter(Objects::nonNull) // 防御性过滤
                        .peek(file -> file.setUploadStatus(1))
                        .collect(Collectors.toList());
                try {
                    sysFileAnnexService.updateBatchById(sysfileAnnexs);
                } catch (
                        Exception e
                ){
                    return BaseResponse.fail("未刷新文件状态");
                }
            }

        }
        return BaseResponse.ok(primaryId);
    }

/*    private void createInvestmentBudgetBreakdownDetails(InvestmentBudgetBreakdownModel request, String primaryId, InvestmentBudgetBreakdown entity) {
        List<InvestmentBudgetBreakdownDetailsModel> list = request.getChildNodes();
        for (InvestmentBudgetBreakdownDetailsModel detailsModel : list) {
            InvestmentBudgetBreakdownDetails entity2 = new InvestmentBudgetBreakdownDetails();
            detailsModel.setId(GenerateUUID.getUUID());
            detailsModel.setIsbId(primaryId);
            BeanUtil.copyProperties(detailsModel, entity2);
            investmentBudgetBreakdownDetailsMapper.insert(entity2);
        }
    }*/

    private void createInvestmentBudgetBreakdownDetails(InvestmentBudgetBreakdownModel model, String primaryId) {
        List<InvestmentBudgetBreakdownDetailsModel> rootNodes = model.getDetails();
        if (CollectionUtil.isEmpty(rootNodes)) {
            return;
        }

        List<InvestmentBudgetBreakdownDetails> flatList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);

        for (InvestmentBudgetBreakdownDetailsModel root : rootNodes) {
            collectDetailsRecursively(root, primaryId, null, index, flatList);
            index.incrementAndGet();
        }


        investmentBudgetBreakdownDetailsMapper.insertBatch(flatList);
    }

    private void collectDetailsRecursively(
            InvestmentBudgetBreakdownDetailsModel detailModel,
            String rootIsbId,
            String parentId,
            AtomicInteger sortIndex,
            List<InvestmentBudgetBreakdownDetails> flatList) {

        InvestmentBudgetBreakdownDetails entity = new InvestmentBudgetBreakdownDetails();
        BeanUtil.copyProperties(detailModel, entity);

        String levelId = GenerateUUID.getUUID();
        entity.setId(levelId);
        entity.setIsbId(rootIsbId);
        entity.setParentId(parentId);
        entity.setSort(BigDecimal.valueOf(sortIndex.get()));

        flatList.add(entity);

        // 如果有子节点，递归处理
        if (CollectionUtil.isNotEmpty(detailModel.getChildNodes())) {
            AtomicInteger childSort = new AtomicInteger(1);
            for (InvestmentBudgetBreakdownDetailsModel child : detailModel.getChildNodes()) {
                collectDetailsRecursively(child, rootIsbId, levelId, childSort, flatList);
                childSort.incrementAndGet();
            }
        }
    }
    /**
     * 递归保存明细及其子节点
     */
    private void saveDetailRecursively(
            InvestmentEstimateBreakdownDetailsModel detailModel,
            String rootIsbId,
            String parentId,
            AtomicInteger parentIndex) {

        InvestmentEstimateBreakdownDetails entity = new InvestmentEstimateBreakdownDetails();
        String levelId = GenerateUUID.getUUID();

        // 拷贝基础字段
        BeanUtil.copyProperties(entity, detailModel);
        entity.setId(levelId);                        // 主键ID
        entity.setIsbId(rootIsbId);                   // 关联主表ID
        entity.setParentId(parentId);                 // 上级节点ID
        entity.setSort(BigDecimal.valueOf(parentIndex.get())); // 设置排序号

        investmentEstimateBreakdownDetailsMapper.insert(entity);

        // 如果有子节点，继续递归插入，并重新开始计数
        if (CollectionUtil.isNotEmpty(detailModel.getChildNodes())) {
            AtomicInteger childIndex = new AtomicInteger(1);
            for (InvestmentEstimateBreakdownDetailsModel child : detailModel.getChildNodes()) {
                saveDetailRecursively(child, rootIsbId, levelId, childIndex);
                childIndex.incrementAndGet();
            }
        }
    }



    @Override
    public BaseResponse getInvestmentBudgetBreakdownList(InvestmentBudgetBreakdownModel request,String uId) {
        String authSql = sysUserService.getAuthSql("Investment_Budget_Breakdown",uId,null,OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isNotEmpty(authSql)) {
            authSql = authSql.replace("mn.P_ID", "ibb.Project_ID");
            StringBuilder sb = new StringBuilder();
            sb.append(" where (ibb.").append("Create_Uid").append("='").append(uId).append("' or ");
            sb.append(" (select count(*) from Sys_Task_Processin st1p where st1p.tp_entityid=ibb.").append("id")
                    .append(" and st1p.tp_user='").append(uId).append("')>0");
            sb.append(" or ").append(" (select count(*) from SYS_CarbonCopy scc where scc.CC_FileID=ibb.id ")
                    .append(" and scc.CC_User='").append(uId).append("')>0");
            sb.append(")");
            authSql = authSql.replace("where 1=1", sb.toString())
                    .replace("Pre_Project fpro on fpro.P_Id=ibb.Project_ID", " Investment_Budget_Breakdown sbp on sbp.Id=ibb.Id left join Pre_Project fpro on fpro.P_Id=sbp.Project_id")
                    .replace("and (fpro.p_auth_type is null","or (fpro.p_auth_type is null");
        } else {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<InvestmentBudgetBreakdownModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<InvestmentBudgetBreakdownModel> params = new QueryMap<>(request);
        //未发起流程仅发起人可见，审批中、已作废流程仅发起人、审批人可见
        List<InvestmentBudgetBreakdownVo> investmentBudgetBreakdownList
                = baseMapper.getInvestmentBudgetBreakdownList(params.getDto(), page,authSql);

        log.info("response:{}", JacksonUtils.toJSONString(investmentBudgetBreakdownList));
        return BaseResponse.ok(investmentBudgetBreakdownList, (int) page.getTotal());
    }

/*    @Override
    public BaseResponse getInvestmentBudgetBreakdownById(String id) {
        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = baseMapper.getInvestmentBudgetBreakdownById(id);
        if (investmentBudgetBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
         InvestmentEstimateBreakdownChange change = InvestmentEstimateBreakdownChangeService.getBaseMapper().selectOne(new LambdaQueryWrapper<InvestmentEstimateBreakdownChange>()
                .eq(InvestmentEstimateBreakdownChange::getProjectId, investmentBudgetBreakdownVo.getProjectId())
                .eq(InvestmentEstimateBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdownChange::getStateTime)
                .last("limit 1"));
        if(ValidateUtil.isNotNull(change)){
             BaseResponse gusuanList = InvestmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeById(change.getId());

            InvestmentEstimateBreakdownChangeVo vo = (InvestmentEstimateBreakdownChangeVo) gusuanList.getData();
            investmentBudgetBreakdownVo.setDetailsChange(vo.getDetails());
            List<InvestmentEstimateBreakdownChangeDetailsModel> modelList = vo.getDetails();
             JSONArray objects = JSONUtil.parseArray(modelList);



            Map<String, Double> amountMap = objects.stream()
                    .map(item -> (JSONObject) item)
                    .filter(item -> item.getDouble("isbdAmount") != null)
                    .collect(Collectors.toMap(
                            item -> item.getStr("isbdSectionName"),
                            item -> item.getDouble("isbdAmount"),
                            (existing, replacement) -> existing
                    ));


            Map<String, Double> amountFType = objects.stream()
                    .map(item -> (JSONObject) item)
                    .filter(item -> ValidateUtil.isNotEmpty(item.getStr("isbdFeeTypeName")) && ValidateUtil.isNotEmpty(item.getStr("ieDetailId")) && item.getDouble("isbdAmount") != null)
                    .collect(Collectors.toMap(
                            item -> item.getStr("ieDetailId"),
                            item -> item.getDouble("isbdAmount"),
                            (existing, replacement) -> existing
                    ));

            JSONArray objects1 = JSONUtil.parseArray(investmentBudgetBreakdownVo.getDetails());
            for (int i = 0; i < objects1.size(); i++) {
                JSONObject jsonDetail = objects1.getJSONObject(i);
                String sectionName = jsonDetail.getStr("isbdSectionName");
                if (amountMap.containsKey(sectionName)) {
                    Double amount = amountMap.get(sectionName);
                    jsonDetail.put("isbdAmount", amount);
                }

                String ieDetailId = jsonDetail.getStr("ieDetailId");
                String isbdFeeTypeName = jsonDetail.getStr("isbdFeeTypeName");
                if (amountFType.containsKey(ieDetailId)) {
                    Double ftypeId  = amountFType.get(ieDetailId);
                    if(isbdFeeTypeName.equals("合计") || isbdFeeTypeName.equals("小计")){
                        jsonDetail.put("isbdAmount", ftypeId);
                    }
                }


            }
            List<InvestmentBudgetBreakdownDetailsModel> updatedDetails = JSONUtil.toList(objects1, InvestmentBudgetBreakdownDetailsModel.class);
            investmentBudgetBreakdownVo.setDetails(updatedDetails);
        }

        return BaseResponse.ok(investmentBudgetBreakdownVo);
    }*/
    @Override
    public BaseResponse getInvestmentBudgetBreakdownById(String id) {
        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = baseMapper.getInvestmentBudgetBreakdownById(id);
        if (investmentBudgetBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (CollectionUtil.isNotEmpty(investmentBudgetBreakdownVo.getDetails())) {
            List<InvestmentBudgetBreakdownDetailsModel> investmentBudgetBreakdownDetailsModels = buildTreeFromModel(investmentBudgetBreakdownVo.getDetails());
            investmentBudgetBreakdownVo.setDetails(investmentBudgetBreakdownDetailsModels);
        } else {
            investmentBudgetBreakdownVo.setDetails(new ArrayList<>());
        }
        return BaseResponse.ok(investmentBudgetBreakdownVo);
    }

    private List<InvestmentBudgetBreakdownDetailsModel> buildTreeFromModel(List<InvestmentBudgetBreakdownDetailsModel> list) {
        Map<String, InvestmentBudgetBreakdownDetailsModel> nodeMap = new HashMap<>();

        // 1. 先全部放入 map，key 使用 levelId（唯一标识）
        for (InvestmentBudgetBreakdownDetailsModel model : list) {
            InvestmentBudgetBreakdownDetailsModel copy = new InvestmentBudgetBreakdownDetailsModel();
            BeanUtil.copyProperties(model, copy);
            copy.setChildNodes(new ArrayList<>());
            nodeMap.put(copy.getId(), copy);
        }

        // 2. 组织父子关系
        List<InvestmentBudgetBreakdownDetailsModel> rootList = new ArrayList<>();
        for (InvestmentBudgetBreakdownDetailsModel model : nodeMap.values()) {
            if (model.getParentId() == null || "null".equals(model.getParentId())) {
                rootList.add(model);
            } else {
                InvestmentBudgetBreakdownDetailsModel parent = nodeMap.get(model.getParentId());
                if (parent != null) {
                    parent.getChildNodes().add(model);
                }
            }
        }

        // 3. 对每一层进行排序
        sortTree(rootList);

        return rootList;
    }


    /**
     * 递归排序
     */
    private void sortTree(List<InvestmentBudgetBreakdownDetailsModel> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        // 按 sort 字段排序
        nodes.sort(Comparator.comparing(InvestmentBudgetBreakdownDetailsModel::getSort, Comparator.nullsLast(Comparator.naturalOrder())));

        // 递归排序子节点
        for (InvestmentBudgetBreakdownDetailsModel node : nodes) {
            sortTree(node.getChildNodes());
        }
    }

    @Override
    public BaseResponse deleteInvestmentBudgetBreakdownById(String id) {
        InvestmentBudgetBreakdown investmentBudgetBreakdown = baseMapper.selectById(id);
        if (investmentBudgetBreakdown == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        baseMapper.deleteById(investmentBudgetBreakdown);
        investmentBudgetBreakdownDetailsMapper.deleteByIsbId(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse removeInvestmentBudgetBreakdownById(String id) {
        InvestmentBudgetBreakdown investmentBudgetBreakdown = baseMapper.selectById(id);
        if (investmentBudgetBreakdown == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        investmentBudgetBreakdown.setState(ApprovalStatusEnum.Invalid.getIndex());
        baseMapper.updateById(investmentBudgetBreakdown);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getInvestmentBudgetBreakdownByPId(String pId) {
        List<InvestmentBudgetBreakdown> investmentBudgetBreakdownList = baseMapper.getInvestmentBudgetBreakdownByPId(pId);
        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = new InvestmentBudgetBreakdownVo();
        if (CollectionUtil.isNotEmpty(investmentBudgetBreakdownList)) {
            BeanUtil.copyProperties(investmentBudgetBreakdownList.get(0), investmentBudgetBreakdownVo);
            List<InvestmentBudgetBreakdownDetailsModel> details = investmentBudgetBreakdownDetailsMapper.getDataByIsbId(investmentBudgetBreakdownVo.getId());
            investmentBudgetBreakdownVo.setDetails(details);
        }
        InvestmentBudgetBreakdownChange change = investmentBudgetBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentBudgetBreakdownChange>()
                .eq(InvestmentBudgetBreakdownChange::getOriginApplyId, investmentBudgetBreakdownVo.getId())
                .eq(InvestmentBudgetBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentBudgetBreakdownChange::getStateTime)
                .last("limit 1"));
        if (change == null) {
            return BaseResponse.ok(investmentBudgetBreakdownVo);
        }
        return investmentBudgetBreakdownChangeService.getInvestmentBudgetBreakdownChangeById(change.getId());
    }

    @Override
    public BaseResponse getInvestmentSecInfoByPId(String pId) {
        List<InvestmentBreakdownSecInfoModel> secInfoList = new ArrayList<>();

        InvestmentEstimateBreakdown investmentEstimateBreakdown = investmentEstimateBreakdownMapper.selectOne(new LambdaQueryWrapper<InvestmentEstimateBreakdown>()
                .eq(InvestmentEstimateBreakdown::getProjectId, pId)
                .eq(InvestmentEstimateBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdown::getStateTime)
                .last("limit 1"));
        if (investmentEstimateBreakdown == null) {
            return BaseResponse.ok();
        }
        //估算

        InvestmentEstimateBreakdownChange investmentEstimateBreakdownChange = investmentEstimateBreakdownChangeMapper.selectOne(new LambdaQueryWrapper<InvestmentEstimateBreakdownChange>()
                .eq(InvestmentEstimateBreakdownChange::getProjectId, pId)
                .eq(InvestmentEstimateBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdownChange::getStateTime)
                .last("limit 1"));
        if (investmentEstimateBreakdownChange == null) {
            List<InvestmentEstimateBreakdownDetailsModel> details = investmentEstimateBreakdownDetailsMapper.getDataByIsbId(investmentEstimateBreakdown.getId());
            details = details.stream().filter(e -> StrUtil.isBlank(e.getIsbdFeeTypeName()) && StrUtil.isNotBlank(e.getIsbdSectionName())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(details)) {
                for (InvestmentEstimateBreakdownDetailsModel detail : details) {
                    InvestmentBreakdownSecInfoModel secInfoModel = new InvestmentBreakdownSecInfoModel();
                    BeanUtil.copyProperties(detail, secInfoModel);
                    secInfoModel.setIeId(detail.getIsbId());
                    secInfoModel.setIeDetailId(detail.getId());
                    secInfoList.add(secInfoModel);
                }
            }
        } else {
            List<InvestmentEstimateBreakdownChangeDetailsModel> details = investmentEstimateBreakdownChangeDetailsMapper.getDataByIsbId(investmentEstimateBreakdownChange.getId());
            details = details.stream().filter(e -> StrUtil.isBlank(e.getIsbdFeeTypeName()) && StrUtil.isNotBlank(e.getIsbdSectionName())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(details)) {
                for (InvestmentEstimateBreakdownChangeDetailsModel detail : details) {
                    InvestmentBreakdownSecInfoModel secInfoModel = new InvestmentBreakdownSecInfoModel();
                    BeanUtil.copyProperties(detail, secInfoModel);
                    secInfoModel.setIeId(detail.getIsbId());
                    secInfoModel.setIeDetailId(detail.getId());
                    secInfoModel.setChangeType(detail.getChangeType());
                    secInfoList.add(secInfoModel);
                }
            }
        }

        List<InvestmentBudgetBreakdown> investmentBudgetBreakdownList = baseMapper.getInvestmentBudgetBreakdownByPId(pId);
        InvestmentBudgetBreakdownChange budgetBreakdownChange = investmentBudgetBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentBudgetBreakdownChange>()
                .eq(InvestmentBudgetBreakdownChange::getProjectId, pId)
                .eq(InvestmentBudgetBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentBudgetBreakdownChange::getStateTime)
                .last("limit 1"));
        Map<String, InvestmentBudgetBreakdownDetailsModel> breakdownDetailsModelMap = new HashMap<>(16);
        List<InvestmentBudgetBreakdownDetailsModel> budgetDataList = null;

        if (budgetBreakdownChange == null){
            if (CollectionUtil.isNotEmpty(investmentBudgetBreakdownList)) {
                InvestmentBudgetBreakdown investmentBudgetBreakdown = investmentBudgetBreakdownList.get(0);
                List<InvestmentBudgetBreakdownDetailsModel> budgetDetails = investmentBudgetBreakdownDetailsMapper.getDataByIsbId(investmentBudgetBreakdown.getId());
                budgetDataList = new ArrayList<>(budgetDetails);
                budgetDetails = budgetDetails.stream().filter(e -> StrUtil.isBlank(e.getIsbdFeeTypeName()) && StrUtil.isNotBlank(e.getIsbdSectionName())).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(budgetDetails)) {
                    breakdownDetailsModelMap = budgetDetails.stream().collect(Collectors.toMap(InvestmentBudgetBreakdownDetailsModel::getIeDetailId, e -> e));
                }

            }
        }else {
            List<InvestmentBudgetBreakdownChangeDetailsModel> budgetDetails = investmentBudgetBreakdownChangeDetailsMapper.getDataByIsChangebId(budgetBreakdownChange.getId());
            budgetDetails = budgetDetails.stream().filter(e -> StrUtil.isBlank(e.getIsbdFeeTypeName()) && StrUtil.isNotBlank(e.getIsbdSectionName())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(budgetDetails)) {
                breakdownDetailsModelMap = budgetDetails.stream().filter(e -> StrUtil.isNotBlank(e.getIeDetailId())).collect(Collectors.toMap(InvestmentBudgetBreakdownChangeDetailsModel::getIeDetailId, e -> {
                    InvestmentBudgetBreakdownDetailsModel investmentBudgetBreakdownDetailsModel = new InvestmentBudgetBreakdownDetailsModel();
                    BeanUtil.copyProperties(e,investmentBudgetBreakdownDetailsModel);
                    return investmentBudgetBreakdownDetailsModel;
                }));
            }
        }

        if (CollectionUtil.isNotEmpty(secInfoList)) {
            for (InvestmentBreakdownSecInfoModel secInfoModel : secInfoList) {
                if (breakdownDetailsModelMap.containsKey(secInfoModel.getIeDetailId())) {
                    InvestmentBudgetBreakdownDetailsModel investmentBudgetBreakdownDetailsModel = breakdownDetailsModelMap.get(secInfoModel.getIeDetailId());
                    secInfoModel.setIbId(investmentBudgetBreakdownDetailsModel.getIsbId());
                    secInfoModel.setIbDetailId(investmentBudgetBreakdownDetailsModel.getId());
                    secInfoModel.setIsbdBudgetAmount(investmentBudgetBreakdownDetailsModel.getIsbdBudgetAmount());
                }else{
                    if(CollectionUtil.isNotEmpty(budgetDataList)){
                        budgetDataList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getIsbdSectionName())
                                        && e.getIsbdSectionName().equals(ValidateUtil.isEmpty(secInfoModel.getIsbdSectionName()) ? "" : secInfoModel.getIsbdSectionName())).findFirst().
                                ifPresent(model ->{
                                    secInfoModel.setIsbdBudgetAmount(model.getIsbdBudgetAmount());
                                });
                    }
                }
            }
        }

        String leixing = secInfoList.stream().filter(e-> ValidateUtil.isNotEmpty(e.getIsbdType()) && e.getIsbdType().equals("02")).map(e-> e.getIsbdSectionType()).collect(Collectors.joining(","));
        secInfoList.stream().map(e->{
            if(investmentEstimateBreakdown.getIsAllProcessAsk().equals(1)){
                e.setIsAllProcessAsk(1);
                e.setIsAllProcessMsg(leixing);
            }
            return  e;
        }).collect(Collectors.toList());
        return BaseResponse.ok(secInfoList);
    }

    @Override
    public BaseResponse getLatestInvestmentBudgetBreakdownById(String id) {
        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = baseMapper.getInvestmentBudgetBreakdownById(id);
        if (investmentBudgetBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        InvestmentBudgetBreakdownChange change = investmentBudgetBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentBudgetBreakdownChange>()
                .eq(InvestmentBudgetBreakdownChange::getOriginApplyId, investmentBudgetBreakdownVo.getId())
                .eq(InvestmentBudgetBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentBudgetBreakdownChange::getStateTime)
                .last("limit 1"));
        if (change == null) {
            return BaseResponse.ok(investmentBudgetBreakdownVo);
        }
        return investmentBudgetBreakdownChangeService.getInvestmentBudgetBreakdownChangeById(change.getId());
    }

    @Override
    public InvestmentBudgetBreakdown getInvestmentBudgetBreakdownByPIdData(String pId) {
        LambdaQueryWrapper<InvestmentBudgetBreakdown>  queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InvestmentBudgetBreakdown::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(InvestmentBudgetBreakdown::getProjectId,pId)
                .orderByDesc(InvestmentBudgetBreakdown::getStateTime)
                .last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public BaseResponse getInvestmentControlList(InvestmentControlRequest request) {

       Page<InvestmentControlResponse> list =  baseMapper.getInvestmentControlList(new Page<>(request.getPage(), request.getSize()),request);
        list.getRecords().forEach(e->{
//            if(ValidateUtil.isNotNull(e.getPBudgetInvestmentAmount())){
//                if(ValidateUtil.isNotNull(e.getPBudgetInvestmentAmount())){
//                    if(e.getPBudgetInvestmentCount()>0){
//                        if(e.getAllInvestment().compareTo(e.getPBudgetInvestmentAmount()) >=0){
//                            e.setRiskStatement("存在风险");
//                        }else{
//                            e.setRiskStatement("投资可控");
//                        }
//                    }else{
//                        if(e.getAllInvestment().compareTo(e.getPApproveBudget()) >=0){
//                            e.setRiskStatement("存在风险");
//                        }else{
//                            e.setRiskStatement("投资可控");
//                        }
//                    }
//                }else{
//                    if(e.getAllInvestment().compareTo(e.getPApproveBudget()) >=0){
//                        e.setRiskStatement("存在风险");
//                    }else{
//                        e.setRiskStatement("投资可控");
//                    }
//                }
//            }
            boolean amountNotNull = ValidateUtil.isNotNull(e.getPBudgetInvestmentAmount());
            BigDecimal allInvestment = ValidateUtil.isNotNull(e.getAllInvestment()) ?  e.getAllInvestment() : BigDecimal.ZERO;

            if (amountNotNull && e.getPBudgetInvestmentCount() > 0) {
                addCriterion(allInvestment, e.getPBudgetInvestmentAmount(), e);
            } else {
                addCriterion(allInvestment, ValidateUtil.isNotNull(e.getPApproveBudget()) ? e.getPApproveBudget() : BigDecimal.ZERO, e);
            }
        });

        return BaseResponse.ok(list.getRecords(),(int)list.getTotal());
    }

    @Override
    public BaseResponse getInvestmentControlByPId(String pId) {
         InvestmentBudgetBreakdown breakdown = baseMapper.selectByProjectId(pId);
         if(ValidateUtil.isNull(breakdown)) return BaseResponse.fail(ResMsg.infoNotExist);
         BaseResponse baseData = getInvestmentBudgetBreakdownById(breakdown.getId());

//        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = new InvestmentBudgetBreakdownVo();
//        investmentBudgetBreakdownVo = (InvestmentBudgetBreakdownVo)baseData.getData();

        return baseData;
    }

    @Override
    public BaseResponse getInvestmentControlClassInfo(InvestmentControlRequest request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        int i = 1;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(InvestmentControlInfoRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(i++);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getInvestmentControlClassInfo error:{}",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList=classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }

    private void addCriterion(BigDecimal allInvestment, BigDecimal budgetAmount, InvestmentControlResponse e) {
        if (allInvestment.compareTo(budgetAmount) > 0) {
            e.setRiskStatement("存在风险");
        } else {
            e.setRiskStatement("投资可控");
        }
    }

    @Override
    public BaseResponse getInvestmentBudgetBreakdownTreeByPId(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        InvestmentBudgetBreakdownVo investmentBudgetBreakdownVo = baseMapper.getInvestmentBudgetBreakdownByPid(pId);
        if (ValidateUtil.isNull(investmentBudgetBreakdownVo)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (CollectionUtil.isNotEmpty(investmentBudgetBreakdownVo.getDetails())) {
            List<InvestmentBudgetBreakdownDetailsModel> investmentBudgetBreakdownDetailsModels = buildTreeFromModel2(investmentBudgetBreakdownVo.getDetails());
            return BaseResponse.ok(investmentBudgetBreakdownDetailsModels);
        }
        return BaseResponse.ok();
    }

    private List<InvestmentBudgetBreakdownDetailsModel> buildTreeFromModel2(List<InvestmentBudgetBreakdownDetailsModel> list) {
        Map<String, InvestmentBudgetBreakdownDetailsModel> nodeMap = new HashMap<>();

        // 1. 先全部放入 map，key 使用 levelId（唯一标识）
        for (InvestmentBudgetBreakdownDetailsModel model : list) {
            InvestmentBudgetBreakdownDetailsModel copy = new InvestmentBudgetBreakdownDetailsModel();
            BeanUtil.copyProperties(model, copy);
            copy.setChildren(new ArrayList<>());
            nodeMap.put(copy.getId(), copy);
        }

        // 2. 组织父子关系
        List<InvestmentBudgetBreakdownDetailsModel> rootList = new ArrayList<>();
        for (InvestmentBudgetBreakdownDetailsModel model : nodeMap.values()) {
            if (model.getParentId() == null || "null".equals(model.getParentId())) {
                rootList.add(model);
            } else {
                InvestmentBudgetBreakdownDetailsModel parent = nodeMap.get(model.getParentId());
                if (parent != null) {
                    parent.getChildren().add(model);
                }
            }
        }

        // 3. 对每一层进行排序
        sortTree(rootList);

        return rootList;
    }
}
