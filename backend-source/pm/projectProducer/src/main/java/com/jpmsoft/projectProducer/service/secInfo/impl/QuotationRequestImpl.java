package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.QuotationRequestDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.QuotationRequestMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.QuotationRequest;
import com.jpmsoft.projectProducer.service.secInfo.IQuotationRequestService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 标段询价调研明细服务实现。
 */
@Service
public class QuotationRequestImpl extends ServiceImpl<QuotationRequestMapper, QuotationRequest> implements IQuotationRequestService {

    /**
     * 同步当前标段的明细，保留已有明细主键；拒绝重复ID和其他标段的明细ID。
     *
     * @param data  null 表示保留原数据，空列表表示清空
     * @param secId 所属标段ID，由标段主表提供
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(List<QuotationRequestDTO> data, String secId) {
        if (data == null) {
            return;
        }
        if (!StringUtils.hasText(secId)) {
            throw new IllegalArgumentException("标段ID不能为空");
        }
        List<QuotationRequest> oldRows = list(Wrappers.<QuotationRequest>lambdaQuery()
                .eq(QuotationRequest::getSecId, secId));
        Map<String, QuotationRequest> oldRowsById = oldRows.stream()
                .collect(Collectors.toMap(QuotationRequest::getId, row -> row));
        Set<String> oldIds = oldRowsById.keySet();
        Set<String> retainedIds = new HashSet<>();
        List<QuotationRequest> adds = new ArrayList<>();
        List<QuotationRequest> updates = new ArrayList<>();
        for (QuotationRequestDTO dto : data) {
            if (dto == null) {
                throw new IllegalArgumentException("询价调研明细不能为空");
            }
            QuotationRequest entity = new QuotationRequest();
            BeanUtils.copyProperties(dto, entity);
            entity.setSecId(secId);
            if (StringUtils.hasText(dto.getId())) {
                if (!oldIds.contains(dto.getId()) || !retainedIds.add(dto.getId())) {
                    throw new IllegalArgumentException("询价调研明细ID重复或不属于当前标段");
                }
                QuotationRequest oldRow = oldRowsById.get(dto.getId());
                entity.setCreateUid(oldRow.getCreateUid());
                entity.setCreateUname(oldRow.getCreateUname());
                entity.setCreateTime(oldRow.getCreateTime());
                updates.add(entity);
            } else {
                entity.setId(GenerateUUID.getUUID());
                if (entity.getCreateTime() == null) {
                    entity.setCreateTime(new Date());
                }
                adds.add(entity);
            }
        }
        Set<String> removedIds = new HashSet<>(oldIds);
        removedIds.removeAll(retainedIds);
        if (!removedIds.isEmpty()) {
            removeBatchByIds(removedIds);
        }
        if (!updates.isEmpty()) {
            updateBatchById(updates);
        }
        if (!adds.isEmpty()) {
            saveBatch(adds);
        }
    }

    /**
     * 按创建时间及主键查询标段明细。
     *
     * @param secId 标段ID
     * @return 明细列表
     */
    @Override
    public List<QuotationRequestDTO> selectBySecId(String secId) {
        if (!StringUtils.hasText(secId)) {
            return new ArrayList<>();
        }
        return list(Wrappers.<QuotationRequest>lambdaQuery().eq(QuotationRequest::getSecId, secId)
                .orderByAsc(QuotationRequest::getCreateTime, QuotationRequest::getId)).stream()
                .map(entity -> {
                    QuotationRequestDTO dto = new QuotationRequestDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    /**
     * 删除指定标段的全部明细。
     *
     * @param secId 标段ID
     */
    @Override
    public void deleteBySecId(String secId) {
        if (StringUtils.hasText(secId)) {
            remove(Wrappers.<QuotationRequest>lambdaQuery().eq(QuotationRequest::getSecId, secId));
        }
    }

    /**
     * 删除招标计划下全部标段的明细。
     *
     * @param bpId 招标计划ID
     */
    @Override
    public void deleteByBpId(String bpId) {
        if (StringUtils.hasText(bpId)) {
            baseMapper.deleteByBpId(bpId);
        }
    }
}
