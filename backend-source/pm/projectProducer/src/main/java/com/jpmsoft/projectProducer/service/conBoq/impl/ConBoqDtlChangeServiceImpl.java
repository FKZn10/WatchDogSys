package com.jpmsoft.projectProducer.service.conBoq.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqDtlChangeDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.conBoq.ConBoqDtlChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtlChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlChangeService;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 合同工程量清单变更子表业务实现
 */
@Service
public class ConBoqDtlChangeServiceImpl extends ServiceImpl<ConBoqDtlChangeMapper, ConBoqDtlChange> implements IConBoqDtlChangeService {
    @Resource
    private IConBoqDtlService conBoqDtlService;

    /**
     * 按 childNodes 树结构全量保存变更明细，重新生成节点ID及父子关联，根节点父ID为0。
     *
     * @param dtlList 变更明细根节点集合，为空时清空原有明细
     * @param user    当前操作用户
     * @param boqcId  变更主表ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createConBoqDtlChange(List<ConBoqDtlChangeDto> dtlList, LogUserInfoVo user, String boqcId) {
        this.remove(Wrappers.<ConBoqDtlChange>lambdaQuery().eq(ConBoqDtlChange::getBoqcId, boqcId));
        if (ValidateUtil.isNotEmpty(dtlList)) {
            List<ConBoqDtlChange> changeList = new ArrayList<>();
            collectConBoqDtlChanges(dtlList, "0", boqcId, user, new Date(), changeList);
            if (ValidateUtil.isNotEmpty(changeList)) {
                this.saveBatch(changeList);
            }
        }
    }

    /**
     * 按父节点优先的顺序递归展开明细树，并使用新生成的节点ID建立父子关联。
     *
     * @param dtlList    当前层级明细集合
     * @param parentId   父节点的新ID
     * @param boqcId     变更主表ID
     * @param user       当前操作用户
     * @param now        本次保存时间
     * @param changeList 待批量保存的明细集合
     */
    private void collectConBoqDtlChanges(List<ConBoqDtlChangeDto> dtlList, String parentId, String boqcId,
                                         LogUserInfoVo user, Date now, List<ConBoqDtlChange> changeList) {
        if (ValidateUtil.isEmpty(dtlList)) {
            return;
        }
        for (ConBoqDtlChangeDto dtl : dtlList) {
            ConBoqDtlChange change = new ConBoqDtlChange();
            BeanUtils.copyProperties(dtl, change);
            change.setBoqdcId(GenerateUUID.getUUID());
            change.setBoqdcParentId(parentId);
            change.setBoqcId(boqcId);
            change.setBoqdcCreateTime(now);
            change.setBoqdcCreateUid(user.getId());
            change.setBoqdcCreateUname(user.getName());
            change.setBoqdcUpdateTime(now);
            change.setChildNodes(null);
            changeList.add(change);
            collectConBoqDtlChanges(dtl.getChildNodes(), change.getBoqdcId(), boqcId, user, now, changeList);
        }
    }

    /**
     * 获取变更主表下的平铺明细，父子关系由 boqdcParentId 表示。
     *
     * @param boqcId 变更主表ID
     * @return 变更明细集合
     */
    @Override
    public List<ConBoqDtlChangeDto> getConBoqDtlChangeList(String boqcId) {
        if (ValidateUtil.isEmpty(boqcId)) {
            return Collections.emptyList();
        }
        List<ConBoqDtlChange> list = this.list(Wrappers.<ConBoqDtlChange>lambdaQuery().eq(ConBoqDtlChange::getBoqcId, boqcId));
        return BeanUtils.copyList(list, ConBoqDtlChangeDto.class);
    }

    /**
     * 删除指定变更主表下的全部明细。
     *
     * @param id 变更主表ID
     */
    @Override
    public void deleteByBoqcId(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return;
        }
        this.remove(Wrappers.<ConBoqDtlChange>lambdaQuery().eq(ConBoqDtlChange::getBoqcId, id));
    }

    /**
     * 同步变更明细到正式清单，先分配正式节点ID，再转换新增及变更节点的父ID。
     * 未变更节点也参与ID映射，避免其子节点同步后失去父子关联。
     *
     * @param id    变更主表ID
     * @param boqId 正式清单主表ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncConBoqChangeDtl(String id, String boqId) {
        if (ValidateUtil.isEmpty(id)) {
            return;
        }
        List<ConBoqDtlChange> changeDetailList = this.list(Wrappers.<ConBoqDtlChange>lambdaQuery().eq(ConBoqDtlChange::getBoqcId, id));
        if (ValidateUtil.isEmpty(changeDetailList)) {
            return;
        }
        // 旧列表
        List<ConBoqDtl> oldList = conBoqDtlService.list(Wrappers.<ConBoqDtl>lambdaQuery().eq(ConBoqDtl::getBoqId, boqId));
        Map<String, ConBoqDtl> oldMap = oldList.stream().collect(Collectors.toMap(ConBoqDtl::getBoqdId, Function.identity()));
        Map<String, String> nodeIdMap = new HashMap<>();
        for (ConBoqDtlChange change : changeDetailList) {
            if ("新增".equals(change.getChangeStatus())) {
                nodeIdMap.put(change.getBoqdcId(), GenerateUUID.getUUID());
            } else if (!"删除".equals(change.getChangeStatus()) && oldMap.containsKey(change.getBoqdId())) {
                nodeIdMap.put(change.getBoqdcId(), change.getBoqdId());
            }
        }
        List<ConBoqDtl> addList = new ArrayList<>();
        List<ConBoqDtl> changeList = new ArrayList<>();
        List<String> deleteList = new ArrayList<>();
        changeDetailList.forEach(change -> {
            if ("新增".equals(change.getChangeStatus())) {
                ConBoqDtl dtl = new ConBoqDtl();
                dtl.setBoqdId(nodeIdMap.get(change.getBoqdcId()));
                dtl.setBoqdCode(change.getBoqdcCode());
                dtl.setBoqdName(change.getBoqdcName());
                dtl.setBoqdFeature(change.getBoqdcFeature());
                dtl.setBoqdContent(change.getBoqdcContent());
                dtl.setBoqdUnit(change.getBoqdcUnit());
                dtl.setBoqdAmount(change.getBoqdcAmount());
                dtl.setBoqdPrice(change.getBoqdcPrice());
                dtl.setBoqdTotalPrice(change.getBoqdcTotalPrice());
                dtl.setBoqdRemark(change.getBoqdcRemark());
                dtl.setBoqdState(change.getBoqdcState());
                dtl.setBoqdParentId(resolveSyncParentId(change.getBoqdcParentId(), nodeIdMap, oldMap));
                dtl.setBoqId(boqId);
                dtl.setBoqdSort(change.getBoqdcSort());
                dtl.setBoqdBddId(change.getBoqdcBddId());
                dtl.setBoqdCreateUid(change.getBoqdcCreateUid());
                dtl.setBoqdCreateUname(change.getBoqdcCreateUname());
                dtl.setBoqdCreateTime(new Date());
                dtl.setBoqdUpdateTime(new Date());
                dtl.setBoqdType(change.getBoqdcType());
                dtl.setBoqdProvisionalEstimate(change.getBoqdcProvisionalEstimate());
                dtl.setBoqdRate(change.getBoqdcRate());
                dtl.setBoqdXh(change.getBoqdcXh());
                dtl.setBoqdAqsg(change.getBoqdcAqsg());
                dtl.setBoqdGf(change.getBoqdcGf());
                dtl.setBoqdJsjc(change.getBoqdcJsjc());
                dtl.setBoqdQfjs(change.getBoqdcQfjs());
                dtl.setBoqdJsgz(change.getBoqdcJsgz());
                dtl.setBoqdRgf(change.getBoqdcRgf());
                dtl.setBoqdZcf(change.getBoqdcZcf());
                dtl.setBoqdSbf(change.getBoqdcSbf());
                dtl.setBoqdFcf(change.getBoqdcFcf());
                dtl.setBoqdClf(change.getBoqdcClf());
                dtl.setBoqdJxf(change.getBoqdcJxf());
                dtl.setBoqdGlf(change.getBoqdcGlf());
                dtl.setBoqdLr(change.getBoqdcLr());
                dtl.setBoqdZgr(change.getBoqdcZgr());
                dtl.setBoqdRcjhl(change.getBoqdcRcjhl());
                dtl.setBoqdRcjId(change.getBoqdcRcjId());
                dtl.setBoqdJzDj(change.getBoqdcJzDj());
                dtl.setBoqdTbDj(change.getBoqdcTbDj());
                dtl.setBoqdFxxs(change.getBoqdcFxxs());
                dtl.setBoqdFormula(change.getBoqdcFormula());
                dtl.setBoqdFeesRatio(change.getBoqdcFeesRatio());
                dtl.setBoqdOtherRatio(change.getBoqdcOtherRatio());
                dtl.setBoqdTaxesRatio(change.getBoqdcTaxesRatio());
                dtl.setPageType(change.getPageType());
                dtl.setBoqdChangePreId(change.getBoqdcChangePreId());
                dtl.setCName(change.getCName());
                dtl.setCId(change.getCId());
                dtl.setCCreditCode(change.getCCreditCode());
                addList.add(dtl);
            } else if ("变更".equals(change.getChangeStatus())) {
                ConBoqDtl boqDtl = oldMap.get(change.getBoqdId());
                if (boqDtl == null) {
                    return;
                }
                boqDtl.setBoqdCode(change.getBoqdcCode());
                boqDtl.setBoqdName(change.getBoqdcName());
                boqDtl.setBoqdFeature(change.getBoqdcFeature());
                boqDtl.setBoqdContent(change.getBoqdcContent());
                boqDtl.setBoqdUnit(change.getBoqdcUnit());
                boqDtl.setBoqdAmount(change.getBoqdcAmount());
                boqDtl.setBoqdPrice(change.getBoqdcPrice());
                boqDtl.setBoqdTotalPrice(change.getBoqdcTotalPrice());
                boqDtl.setBoqdRemark(change.getBoqdcRemark());
                boqDtl.setBoqdState(change.getBoqdcState());
                boqDtl.setBoqdParentId(resolveSyncParentId(change.getBoqdcParentId(), nodeIdMap, oldMap));
                boqDtl.setBoqId(boqId);
                boqDtl.setBoqdSort(change.getBoqdcSort());
                boqDtl.setBoqdBddId(change.getBoqdcBddId());
                boqDtl.setBoqdCreateUid(change.getBoqdcCreateUid());
                boqDtl.setBoqdCreateUname(change.getBoqdcCreateUname());
                boqDtl.setBoqdCreateTime(new Date());
                boqDtl.setBoqdUpdateTime(new Date());
                boqDtl.setBoqdType(change.getBoqdcType());
                boqDtl.setBoqdProvisionalEstimate(change.getBoqdcProvisionalEstimate());
                boqDtl.setBoqdRate(change.getBoqdcRate());
                boqDtl.setBoqdXh(change.getBoqdcXh());
                boqDtl.setBoqdAqsg(change.getBoqdcAqsg());
                boqDtl.setBoqdGf(change.getBoqdcGf());
                boqDtl.setBoqdJsjc(change.getBoqdcJsjc());
                boqDtl.setBoqdQfjs(change.getBoqdcQfjs());
                boqDtl.setBoqdJsgz(change.getBoqdcJsgz());
                boqDtl.setBoqdRgf(change.getBoqdcRgf());
                boqDtl.setBoqdZcf(change.getBoqdcZcf());
                boqDtl.setBoqdSbf(change.getBoqdcSbf());
                boqDtl.setBoqdFcf(change.getBoqdcFcf());
                boqDtl.setBoqdClf(change.getBoqdcClf());
                boqDtl.setBoqdJxf(change.getBoqdcJxf());
                boqDtl.setBoqdGlf(change.getBoqdcGlf());
                boqDtl.setBoqdLr(change.getBoqdcLr());
                boqDtl.setBoqdZgr(change.getBoqdcZgr());
                boqDtl.setBoqdRcjhl(change.getBoqdcRcjhl());
                boqDtl.setBoqdRcjId(change.getBoqdcRcjId());
                boqDtl.setBoqdJzDj(change.getBoqdcJzDj());
                boqDtl.setBoqdTbDj(change.getBoqdcTbDj());
                boqDtl.setBoqdFxxs(change.getBoqdcFxxs());
                boqDtl.setBoqdFormula(change.getBoqdcFormula());
                boqDtl.setBoqdFeesRatio(change.getBoqdcFeesRatio());
                boqDtl.setBoqdOtherRatio(change.getBoqdcOtherRatio());
                boqDtl.setBoqdTaxesRatio(change.getBoqdcTaxesRatio());
                boqDtl.setPageType(change.getPageType());
                boqDtl.setBoqdChangePreId(change.getBoqdcChangePreId());
                boqDtl.setCName(change.getCName());
                boqDtl.setCId(change.getCId());
                boqDtl.setCCreditCode(change.getCCreditCode());
                changeList.add(boqDtl);
            } else if ("删除".equals(change.getChangeStatus())) {
                deleteList.add(change.getBoqdId());
            }
        });
        if (ValidateUtil.isNotEmpty(addList)) {
            conBoqDtlService.saveBatch(addList);
        }
        if (ValidateUtil.isNotEmpty(changeList)) {
            conBoqDtlService.updateBatchById(changeList);
        }
        if (ValidateUtil.isNotEmpty(deleteList)) {
            conBoqDtlService.removeByIds(deleteList);
        }
    }

    /**
     * 将变更明细父ID转换为正式明细父ID，兼容历史数据直接引用正式父节点的情况。
     * 无法找到父节点时终止同步，由事务回滚，避免写入孤立节点。
     *
     * @param parentId  变更明细中的父ID
     * @param nodeIdMap 变更节点ID到正式节点ID的映射
     * @param oldMap    当前清单已有的正式节点
     * @return 正式父节点ID，根节点返回0
     */
    private String resolveSyncParentId(String parentId, Map<String, String> nodeIdMap,
                                       Map<String, ConBoqDtl> oldMap) {
        if (ValidateUtil.isEmpty(parentId) || "0".equals(parentId)) {
            return "0";
        }
        String targetParentId = nodeIdMap.get(parentId);
        if (ValidateUtil.isNotEmpty(targetParentId)) {
            return targetParentId;
        }
        if (oldMap.containsKey(parentId)) {
            return parentId;
        }
        throw new IllegalStateException("同步清单失败，父节点不存在：" + parentId);
    }
}

