package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.PaymentReceiptHistoryDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fund.PaymentReceiptHistoryMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceiptHistory;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收款收据变化历史 服务类实现
 *
 * @author tianhelin
 * @date 2025/12/22
 */
@Service
public class PaymentReceiptHistoryImpl extends ServiceImpl<PaymentReceiptHistoryMapper, PaymentReceiptHistory> implements IPaymentReceiptHistoryService {
    /**
     * 批量保存或更新收款收据变化历史
     *
     * @param prId
     * @param historyList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveOrUpdateBatchByDtoList(String prId, List<PaymentReceiptHistoryDTO> historyList, String prIsClaim) {
        if (ValidateUtil.isEmpty(prId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isNotEmpty(prIsClaim) && !"1".equalsIgnoreCase(prIsClaim)) {
            // 清空
            this.remove(Wrappers.<PaymentReceiptHistory>lambdaQuery().eq(PaymentReceiptHistory::getPrId, prId));
            return BaseResponse.ok();
        }
        // 因为可能存在以前的旧数据，所以不能直接删除再增加，需要分别进行增删改
        // 已有数据
        List<PaymentReceiptHistory> histories = this.list(Wrappers.<PaymentReceiptHistory>lambdaQuery().eq(PaymentReceiptHistory::getPrId, prId));
        if (ValidateUtil.isEmpty(histories)) {
            // 没有旧数据，直接批量新增
            List<PaymentReceiptHistory> toAddList = historyList.stream().map(dto -> {
                PaymentReceiptHistory history = new PaymentReceiptHistory();
                BeanUtils.copyProperties(dto, history);
                history.setId(GenerateUUID.getUUID());
                history.setPrId(prId);
                // history.setCreateTime(new Date());
                return history;
            }).collect(Collectors.toList());
            this.saveBatch(toAddList);
            return BaseResponse.ok();
        }
        // 有旧数据，分别处理增删改，根据cifdId进行对比，如果cifdId在旧数据中，就更新，否则新增，如果旧数据中的cifdId不在新的列表中，就删除
        List<PaymentReceiptHistory> toAddList = historyList.stream().filter(dto -> histories.stream()
                .noneMatch(h -> h.getCifdId().equals(dto.getCifdId()))).map(dto -> {
            PaymentReceiptHistory history = new PaymentReceiptHistory();
            BeanUtils.copyProperties(dto, history);
            history.setId(GenerateUUID.getUUID());
            history.setPrId(prId);
            // history.setCreateTime(new Date());
            return history;
        }).collect(Collectors.toList());

        List<PaymentReceiptHistory> toUpdateList = historyList.stream().filter(dto -> histories.stream()
                .anyMatch(h -> h.getCifdId().equals(dto.getCifdId()))).map(dto -> {
            PaymentReceiptHistory history = histories.stream()
                    .filter(h -> h.getCifdId().equals(dto.getCifdId()))
                    .findFirst().orElse(null);
            if (history != null) {
                BeanUtils.copyProperties(dto, history);
                history.setPrId(prId);
                // history.setCreateTime(new Date());
            }
            return history;
        }).collect(Collectors.toList());

        List<PaymentReceiptHistory> toDeleteList = histories.stream().filter(h -> historyList.stream()
                .noneMatch(dto -> dto.getCifdId().equals(h.getCifdId()))).collect(Collectors.toList());

        if (!ValidateUtil.isEmpty(toAddList)) {
            this.saveBatch(toAddList);
        }
        if (!ValidateUtil.isEmpty(toUpdateList)) {
            this.updateBatchById(toUpdateList);
        }
        if (!ValidateUtil.isEmpty(toDeleteList)) {
            List<String> deleteIds = toDeleteList.stream().map(PaymentReceiptHistory::getId).collect(Collectors.toList());
            this.removeByIds(deleteIds);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据收款收据ID获取收款收据变化历史列表
     *
     * @param prId
     * @return
     */
    @Override
    public List<PaymentReceiptHistoryDTO> getByPrId(String prId) {
        if (ValidateUtil.isEmpty(prId)) {
            return Collections.emptyList();
        }
        return baseMapper.getByPrId(prId);
    }
}
