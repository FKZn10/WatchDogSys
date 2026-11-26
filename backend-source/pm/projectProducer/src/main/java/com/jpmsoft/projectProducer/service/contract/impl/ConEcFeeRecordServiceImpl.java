package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConEcFeeRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFeeRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;
import com.jpmsoft.projectProducer.service.contract.IConEcFeeRecordService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRecordDto;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConEcFeeRecordServiceImpl extends ServiceImpl<ConEcFeeRecordMapper, ConEcFeeRecord> implements IConEcFeeRecordService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private FileFeign fileFeign;


    @Override
    public void createOrUpdateConEcFeeRecord(List<ConEcFeeRecordDto> datas, String meaId) {
        List<ConEcFeeRecord> olds = getConEcFeeRecordByMeaId(meaId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<String> oldIds = olds.stream().map(ConEcFeeRecord::getId).collect(Collectors.toList());
            Date now = new Date();

            // 删除数据
            List<String> dels = oldIds.stream()
                    .filter(n -> !datas.stream().map(ConEcFeeRecordDto::getId).collect(Collectors.toList()).contains(n))
                    .collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(dels)) {
                baseMapper.deleteBatchIds(dels);
            }

            // 查询当前已有的最大序号
            int maxOrder = olds.stream()
                    .map(ConEcFeeRecord::getMearOrder)
                    .filter(n -> !ValidateUtil.isEmpty(n))
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(0);

            // 新增数据
            List<ConEcFeeRecordDto> adds = datas.stream()
                    .filter(n -> ValidateUtil.isEmpty(n.getId()) || !oldIds.contains(n.getId()))
                    .collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(adds)) {
                List<ConEcFeeRecord> addRecords = new ArrayList<>();

                for (ConEcFeeRecordDto add : adds) {
                    ConEcFeeRecord record = new ConEcFeeRecord();
                    BeanUtils.copyProperties(add, record);
                    record.setId(GenerateUUID.getUUID());
                    record.setMearOrder(++maxOrder); // 自动递增序号
                    record.setCreateTime(now);
                    record.setStateTime(now);
                    record.setMeaId(meaId);
                    record.setMearFinalAmount(record.getMearDeclareAmount());
                    addRecords.add(record);
                }
                saveBatch(addRecords);
            }

            // 更新数据
            List<ConEcFeeRecordDto> updates = datas.stream()
                    .filter(n -> ValidateUtil.isNotEmpty(n.getId()) && oldIds.contains(n.getId()))
                    .collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(updates)) {
                List<ConEcFeeRecord> upRecords = new ArrayList<>();
                for (ConEcFeeRecordDto update : updates) {
                    ConEcFeeRecord old = olds.stream().filter(n -> n.getId().equals(update.getId())).findFirst().get();
                    ConEcFeeRecord record = new ConEcFeeRecord();
                    BeanUtils.copyProperties(update, record);
                    record.setCreateUid(old.getCreateUid());
                    record.setCreateUname(old.getCreateUname());
                    record.setCreateTime(old.getCreateTime());
                    upRecords.add(record);
                }
                updateBatchById(upRecords);
            }
        }
    }


    private List<ConEcFeeRecord> getConEcFeeRecordByMeaId(String meaId) {
        QueryWrapper<ConEcFeeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Mea_Id", meaId);
        return baseMapper.selectList(wrapper);
    }

}