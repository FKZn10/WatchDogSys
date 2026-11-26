package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChange;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownDetails;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentEstimateBreakdownVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownChangeService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownService;
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
 * @date 2024/10/25 15:51
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IInvestmentEstimateBreakdownServiceImpl extends BaseFileImpl<InvestmentEstimateBreakdownMapper, InvestmentEstimateBreakdown, InvestmentEstimateBreakdownModel> implements IInvestmentEstimateBreakdownService {
    @Autowired
    private InvestmentEstimateBreakdownDetailsMapper investmentEstimateBreakdownDetailsMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IInvestmentEstimateBreakdownChangeService investmentEstimateBreakdownChangeService;

    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateInvestmentEstimateBreakdown(InvestmentEstimateBreakdownModel model) {
        String primaryId = model.getId();
        InvestmentEstimateBreakdown entity;
        if (primaryId == null) {
            InvestmentEstimateBreakdown oldEntity = baseMapper.selectByProjectId(model.getProjectId());
            if (oldEntity != null) {
                //每个项目只能新增一次，如需修改，使用变更操作。
                return BaseResponse.fail("每个项目只能新增一次");
            }
            entity = new InvestmentEstimateBreakdown();
            BeanUtil.copyProperties(model, entity);
            primaryId = GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            createInvestmentEstimateBreakdownDetails(model, primaryId);
            if (ValidateUtil.isEmpty(model.getPId())) {
                model.setPId(model.getProjectId());
            }
            sysFileService.saveFile(entity.getId(), model.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), model.getPId(), model.getFileTitle(), model.getFileCode(),
                    "create", model.getFileRelationId(), null, model.getTCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(model.getFileRelationId(),model.getInvestMentFile()),
                    Arrays.asList(model.getFileIds(),model.getInvestMentFileIds()),entity.getId());
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(model, entity);
            baseMapper.updateById(entity);
            //删除原有详情
            investmentEstimateBreakdownDetailsMapper.deleteByIsbId(primaryId);
            createInvestmentEstimateBreakdownDetails(model, primaryId);
            if (ValidateUtil.isEmpty(model.getPId())) {
                model.setPId(model.getProjectId());
            }
            sysFileService.saveFile(entity.getId(), model.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), model.getPId(), model.getFileTitle(), model.getFileCode(), "update",
                    model.getFileRelationId(), null, model.getTCode(), null);
/*            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId(),model.getInvestMentFile()), entity.getId());*/
            sysFileAnnexService.refreshRelationFile(Arrays.asList(model.getFileRelationId(),model.getInvestMentFile()),Arrays.asList(model.getFileIds(),model.getInvestMentFileIds()),entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

/*    private void createInvestmentEstimateBreakdownDetails(InvestmentEstimateBreakdownModel model, String primaryId, InvestmentEstimateBreakdown entity) {
        List<InvestmentEstimateBreakdownDetailsModel> list = model.getDetails();
        for (InvestmentEstimateBreakdownDetailsModel detailsModel : list) {
            InvestmentEstimateBreakdownDetails entity2 = new InvestmentEstimateBreakdownDetails();
            detailsModel.setId(GenerateUUID.getUUID());
            detailsModel.setIsbId(primaryId);
            BeanUtil.copyProperties(detailsModel, entity2);
            investmentEstimateBreakdownDetailsMapper.insert(entity2);
        }
    }*/
/*    private void createInvestmentEstimateBreakdownDetails(InvestmentEstimateBreakdownModel model, String primaryId, InvestmentEstimateBreakdown entity) {
        List<InvestmentEstimateBreakdownDetailsModel> childNodes = model.getChildNodes();
        if (CollectionUtil.isEmpty(childNodes)) {
            return;
        }

        AtomicInteger index = new AtomicInteger(1);
        for (InvestmentEstimateBreakdownDetailsModel childNode : childNodes) {
            saveDetailRecursively(childNode, primaryId, null, index); // 根节点 parentId 设为 null
            index.incrementAndGet();
        }
    }*/

    private void createInvestmentEstimateBreakdownDetails(InvestmentEstimateBreakdownModel model, String primaryId) {
        List<InvestmentEstimateBreakdownDetailsModel> rootNodes = model.getDetails();
        if (CollectionUtil.isEmpty(rootNodes)) {
            return;
        }

        List<InvestmentEstimateBreakdownDetails> flatList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);

        for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
            collectDetailsRecursively(root, primaryId, null, index, flatList);
            index.incrementAndGet();
        }


        investmentEstimateBreakdownDetailsMapper.insertBatch(flatList);
    }

    private void collectDetailsRecursively(
            InvestmentEstimateBreakdownDetailsModel detailModel,
            String rootIsbId,
            String parentId,
            AtomicInteger sortIndex,
            List<InvestmentEstimateBreakdownDetails> flatList) {

        InvestmentEstimateBreakdownDetails entity = new InvestmentEstimateBreakdownDetails();
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
            for (InvestmentEstimateBreakdownDetailsModel child : detailModel.getChildNodes()) {
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
    public BaseResponse getInvestmentEstimateBreakdownList(InvestmentEstimateBreakdownModel model,String uId) {
        String authSql=sysUserService.getAuthSql("Investment_Estimate_Breakdown",uId,"isb.Create_Uid",OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isNotEmpty(authSql)) {
            authSql = authSql.replace("mn.P_ID","isb.project_id");
        }
        log.info("authSql:{}", authSql);
        log.info("request:{}", JacksonUtils.toJSONString(model));
        IPage<InvestmentEstimateBreakdownModel> page = new Page<>(model.getPage(), model.getSize());
        QueryMap<InvestmentEstimateBreakdownModel> params = new QueryMap<>(model);
        //未发起流程仅发起人可见，审批中、已作废流程仅发起人、审批人可见
        List<InvestmentEstimateBreakdownVo> investmentEstimateBreakdownList
                = baseMapper.getInvestmentEstimateBreakdownList(params.getDto(), page,authSql);

        log.info("response:{}", JacksonUtils.toJSONString(investmentEstimateBreakdownList));
        return BaseResponse.ok(investmentEstimateBreakdownList, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvestmentEstimateBreakdownById(String id) {
        InvestmentEstimateBreakdownVo investmentEstimateBreakdownVo = baseMapper.getInvestmentEstimateBreakdownById(id);
        if (investmentEstimateBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (CollectionUtil.isNotEmpty(investmentEstimateBreakdownVo.getDetails())) {
            List<InvestmentEstimateBreakdownDetailsModel> investmentEstimateBreakdownDetailsModels = buildTreeFromModel(investmentEstimateBreakdownVo.getDetails());
            investmentEstimateBreakdownVo.setDetails(investmentEstimateBreakdownDetailsModels);
        } else {
            investmentEstimateBreakdownVo.setDetails(new ArrayList<>());
        }

        return BaseResponse.ok(investmentEstimateBreakdownVo);
    }

    private List<InvestmentEstimateBreakdownDetailsModel> buildTreeFromModel(List<InvestmentEstimateBreakdownDetailsModel> list) {
        Map<String, InvestmentEstimateBreakdownDetailsModel> nodeMap = new HashMap<>();

        // 1. 先全部放入 map，key 使用 levelId（唯一标识）
        for (InvestmentEstimateBreakdownDetailsModel model : list) {
            InvestmentEstimateBreakdownDetailsModel copy = new InvestmentEstimateBreakdownDetailsModel();
            BeanUtil.copyProperties(model, copy);
            copy.setChildNodes(new ArrayList<>());
            nodeMap.put(copy.getId(), copy);
        }

        // 2. 组织父子关系
        List<InvestmentEstimateBreakdownDetailsModel> rootList = new ArrayList<>();
        for (InvestmentEstimateBreakdownDetailsModel model : nodeMap.values()) {
            if (model.getParentId() == null || "null".equals(model.getParentId())) {
                rootList.add(model);
            } else {
                InvestmentEstimateBreakdownDetailsModel parent = nodeMap.get(model.getParentId());
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
    private void sortTree(List<InvestmentEstimateBreakdownDetailsModel> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        // 按 sort 字段排序
        nodes.sort(Comparator.comparing(InvestmentEstimateBreakdownDetailsModel::getSort, Comparator.nullsLast(Comparator.naturalOrder())));

        // 递归排序子节点
        for (InvestmentEstimateBreakdownDetailsModel node : nodes) {
            sortTree(node.getChildNodes());
        }
    }




    @Override
    public BaseResponse deleteInvestmentEstimateBreakdownById(String id) {
        InvestmentEstimateBreakdown investmentEstimateBreakdown = baseMapper.selectById(id);
        if (investmentEstimateBreakdown == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        baseMapper.deleteById(investmentEstimateBreakdown);
        investmentEstimateBreakdownDetailsMapper.deleteByIsbId(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse removeInvestmentEstimateBreakdownById(String id) {
        InvestmentEstimateBreakdown investmentEstimateBreakdown = baseMapper.selectById(id);
        if (investmentEstimateBreakdown == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        investmentEstimateBreakdown.setState(ApprovalStatusEnum.Invalid.getIndex());
        baseMapper.updateById(investmentEstimateBreakdown);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectIdList(InvestmentEstimateBreakdownModel request) {
        List<InvestmentEstimateBreakdown> investmentEstimateBreakdowns = baseMapper.getProjectIdList();
        List<String> projectIds = investmentEstimateBreakdowns.stream().map(InvestmentEstimateBreakdown::getProjectId).collect(Collectors.toList());
        return BaseResponse.ok(projectIds);
    }

    @Override
    public BaseResponse getInvestmentEstimateBreakdownByProjectId(String projectId) {
        InvestmentEstimateBreakdownVo investmentEstimateBreakdownVo = baseMapper.getInvestmentEstimateBreakdownByProjectId(projectId);
        if (investmentEstimateBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        InvestmentEstimateBreakdownChange change = investmentEstimateBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentEstimateBreakdownChange>()
                .eq(InvestmentEstimateBreakdownChange::getOriginApplyId, investmentEstimateBreakdownVo.getId())
                .eq(InvestmentEstimateBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdownChange::getState)
                .last("limit 1"));
        if (change == null) {
            return BaseResponse.ok(investmentEstimateBreakdownVo);
        }
        return investmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeById(change.getId());

    }

    @Override
    public BaseResponse getInvestmentEstimateBreakdownByPId(String pId) {
        List<InvestmentEstimateBreakdown> investmentEstimateBreakdownList = baseMapper.getInvestmentEstimateBreakdownByPId(pId);
        InvestmentEstimateBreakdownVo investmentEstimateBreakdownVo = new InvestmentEstimateBreakdownVo();
        if (CollectionUtil.isNotEmpty(investmentEstimateBreakdownList)) {
            BeanUtil.copyProperties(investmentEstimateBreakdownList.get(0), investmentEstimateBreakdownVo);
            List<InvestmentEstimateBreakdownDetailsModel> details = investmentEstimateBreakdownDetailsMapper.getDataByIsbId(investmentEstimateBreakdownVo.getId());
            investmentEstimateBreakdownVo.setDetails(details);
        }
        InvestmentEstimateBreakdownChange change = investmentEstimateBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentEstimateBreakdownChange>()
                .eq(InvestmentEstimateBreakdownChange::getOriginApplyId, investmentEstimateBreakdownVo.getId())
                .eq(InvestmentEstimateBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdownChange::getState)
                .last("limit 1"));
        if (change == null) {
            return BaseResponse.ok(investmentEstimateBreakdownVo);
        }
        return investmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeById(change.getId());
    }

    @Override
    public BaseResponse getUnusedProjectIdList(InvestmentEstimateBreakdownModel request) {
        List<String> projectIdList = baseMapper.getUnusedProjectIdList(request);
        return BaseResponse.ok(projectIdList);
    }

    @Override
    public BaseResponse getLatestInvestmentEstimateBreakdownById(String id) {
        InvestmentEstimateBreakdownVo investmentEstimateBreakdownVo = baseMapper.getInvestmentEstimateBreakdownById(id);
        if (investmentEstimateBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        InvestmentEstimateBreakdownChange change = investmentEstimateBreakdownChangeService.getOne(new LambdaQueryWrapper<InvestmentEstimateBreakdownChange>()
                .eq(InvestmentEstimateBreakdownChange::getOriginApplyId, investmentEstimateBreakdownVo.getId())
                .eq(InvestmentEstimateBreakdownChange::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(InvestmentEstimateBreakdownChange::getStateTime)
                .last("limit 1"));
        if (change == null) {
            return BaseResponse.ok(investmentEstimateBreakdownVo);
        }
        return investmentEstimateBreakdownChangeService.getInvestmentEstimateBreakdownChangeById(change.getId());
    }

}
