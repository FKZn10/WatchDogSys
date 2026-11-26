package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConUnitProjectSummaryMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConUnitProjectSummaryDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConUnitProjectSummary;
import com.jpmsoft.projectProducer.service.contract.IConUnitProjectSummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 14:00
 */
@Service
public class ConUnitProjectSummaryImpl extends ServiceImpl<ConUnitProjectSummaryMapper, ConUnitProjectSummary>
        implements IConUnitProjectSummaryService {
    @Override
    public void createOrUpdateConUnitProjectSummary(List<ConUnitProjectSummaryDto> datas, String meaId) {
        List<ConUnitProjectSummary> olds = getConUnitProjectSummaryByMeaId(meaId);
        if (ValidateUtil.isNotEmpty(datas)) {
            Date now = new Date();
            List<String> oldIds = olds.stream().map(ConUnitProjectSummary::getId).collect(Collectors.toList());
            //region 删除数据
            List<String> dels = oldIds.stream().filter(n -> !datas.stream().map(ConUnitProjectSummaryDto::getId).collect(Collectors.toList()).contains(n)).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(dels))
                baseMapper.deleteBatchIds(dels);
            //endregion

            //region 新增数据
            List<ConUnitProjectSummaryDto> adds = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getId()) || !oldIds.contains(n.getId())).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(adds)) {
                List<ConUnitProjectSummary> addRecords = new ArrayList<>();
                for (ConUnitProjectSummaryDto add : adds) {
                    ConUnitProjectSummary record = new ConUnitProjectSummary();
                    BeanUtils.copyProperties(add, record);
                    record.setId(GenerateUUID.getUUID());
                    record.setCreateTime(now);
                    record.setMeaId(meaId);
                    addRecords.add(record);
                }
                saveBatch(addRecords);
            }
            //endregion

            //region 更新数据
            List<ConUnitProjectSummaryDto> updates = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId()) && (ValidateUtil.isNotEmpty(oldIds) && oldIds.contains(n.getId()))).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(updates)) {
                List<ConUnitProjectSummary> upRecords = new ArrayList<>();
                for (ConUnitProjectSummaryDto update : updates) {
                    ConUnitProjectSummary old = olds.stream().filter(n -> n.getId().equals(update.getId())).findFirst().get();
                    ConUnitProjectSummary record = new ConUnitProjectSummary();
                    BeanUtils.copyProperties(update, record);
                    record.setCreateUid(old.getCreateUid());
                    record.setCreateUname(old.getCreateUname());
                    record.setCreateTime(old.getCreateTime());
                    upRecords.add(record);
                }
                updateBatchById(upRecords);
            }
            //endregion
        }
    }

    private List<ConUnitProjectSummary> getConUnitProjectSummaryByMeaId(String meaId) {
        QueryWrapper<ConUnitProjectSummary> wrapper = new QueryWrapper<>();
        wrapper.eq("Mea_Id", meaId);
        return baseMapper.selectList(wrapper);
    }
}
