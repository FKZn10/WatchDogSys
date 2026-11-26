package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.fund.ClaimIncomingFundsRequest;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.common.model.response.fund.ClaimIncomingFundsResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fund.ClaimIncomingFundsMapper;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFunds;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFundsDtl;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceipt;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceiptHistory;
import com.jpmsoft.projectProducer.service.fund.IClaimIncomingFundsDtlService;
import com.jpmsoft.projectProducer.service.fund.IClaimIncomingFundsService;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptHistoryService;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 来款认领 服务类实现
 *
 * @author tianhelin
 * @date 2025/11/17
 */
@Service
public class ClaimIncomingFundsServiceImpl extends ServiceImpl<ClaimIncomingFundsMapper, ClaimIncomingFunds> implements IClaimIncomingFundsService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IClaimIncomingFundsDtlService claimIncomingFundsDtlService;
    @Resource
    private IPaymentReceiptService paymentReceiptService;
    @Resource
    private IPaymentReceiptHistoryService paymentReceiptHistoryService;

    /**
     * 创建或更新来款认领
     *
     * @param request 来款认领请求对象
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateClaimIncomingFunds(ClaimIncomingFundsRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ClaimIncomingFunds entity = new ClaimIncomingFunds();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getId())) {
            entity.setId(GenerateUUID.getUUID());
            this.save(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getPId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            this.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        // 处理子表
        List<ClaimIncomingFundsDtlDTO> dtlList = request.getDtlList();
        claimIncomingFundsDtlService.saveOrUpdateBatchByCifId(entity.getId(), dtlList);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 来款认领同步到收款收据
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToPaymentReceipt(ClaimIncomingFundsRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ClaimIncomingFunds incomingFunds = this.getById(request.getId());
        if (ValidateUtil.isNull(incomingFunds)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!ApprovalStatusEnum.Already_Over.getIndex().equals(incomingFunds.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 查询子表
        List<ClaimIncomingFundsDtl> fundsDtls = claimIncomingFundsDtlService.list(Wrappers.<ClaimIncomingFundsDtl>lambdaQuery()
                .eq(ClaimIncomingFundsDtl::getCifId, incomingFunds.getId())
                .eq(ValidateUtil.isEmpty(request.getOperationType()), ClaimIncomingFundsDtl::getCifdIsReceipt, "1")
        );

        if (ValidateUtil.isEmpty(fundsDtls)) {
            return BaseResponse.ok("子表数据为空");
        }

        // 过滤出有效的收据ID，防止空指针或空字符串
        List<String> prIds = fundsDtls.stream()
                .map(ClaimIncomingFundsDtl::getCifdReceipt)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());

        Map<String, ClaimIncomingFundsDtl> fundsDtlMap = fundsDtls.stream()
                .filter(dto -> ValidateUtil.isNotEmpty(dto.getCifdReceipt()))
                .collect(Collectors.toMap(ClaimIncomingFundsDtl::getCifdReceipt, dto -> dto, (dto1, dto2) -> dto1));

        List<PaymentReceipt> receipts = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(prIds)) {
            receipts = paymentReceiptService.list(Wrappers.<PaymentReceipt>lambdaQuery()
                    .in(PaymentReceipt::getId, prIds)
                    .eq(ValidateUtil.isEmpty(request.getOperationType()), BaseEntity::getState, ApprovalStatusEnum.Already_Over.getIndex())
            );
        }

        List<PaymentReceipt> updateList = new ArrayList<>();
        Set<ClaimIncomingFundsDtl> updateDtlList = new HashSet<>();
        List<PaymentReceiptHistory> historyList = new ArrayList<>();
        // 取消认领后的处理
        List<String> cancelCifIds = new ArrayList<>();

        if (ValidateUtil.isEmpty(request.getOperationType())) {
            // 认领
            if (ValidateUtil.isNotEmpty(receipts)) {
                // 历史记录
                List<PaymentReceiptHistory> existingHistories = paymentReceiptHistoryService.list(Wrappers.<PaymentReceiptHistory>lambdaQuery()
                        .in(PaymentReceiptHistory::getPrId, prIds)
                );
                Map<String, List<PaymentReceiptHistory>> historyGroupMap = existingHistories.stream()
                        .collect(Collectors.groupingBy(PaymentReceiptHistory::getPrId));
                for (PaymentReceipt receipt : receipts) {
                    ClaimIncomingFundsDtl dtl = fundsDtlMap.get(receipt.getId());
                    if (ValidateUtil.isNull(dtl)) {
                        continue;
                    }
                    receipt.setPrIsClaim("1");
                    receipt.setCifId(dtl.getId());
                    if (ValidateUtil.isEmpty(request.getVerifyStateFlag())) {
                        // 根据金额判断核销状态
                        if (dtl.getCifdAccount().compareTo(new BigDecimal(String.valueOf(receipt.getPrAmount()))) == 0) {
                            // 核销成功
                            dtl.setCifdVerifyState("2");
                            receipt.setPrVerifyState(2);
                        } else {
                            // 核销退回
                            dtl.setCifdVerifyState("3");
                            receipt.setPrVerifyState(3);
                        }
                    } else {
                        // 将子表的核销状态同步到收款收据
                        receipt.setPrVerifyState(Integer.parseInt(dtl.getCifdVerifyState()));
                        // 如果状态是核销成功、核销退回、部分核销，就记录日志
                        if ("2".equals(dtl.getCifdVerifyState()) || "3".equals(dtl.getCifdVerifyState()) || "5".equals(dtl.getCifdVerifyState())) {
                            PaymentReceiptHistory history = new PaymentReceiptHistory();
                            history.setId(GenerateUUID.getUUID());
                            history.setPrId(receipt.getId());
                            history.setCifId(dtl.getCifId());
                            history.setCifdId(dtl.getId());
                            history.setVerifyState(receipt.getPrVerifyState());
                            history.setAmount(dtl.getCifdAccount());
                            history.setCreateTime(new Date());
                            historyList.add(history);
                        }
                    }
                    // 计算未核销金额
                    List<PaymentReceiptHistory> history = historyGroupMap.get(receipt.getId());
                    if (ValidateUtil.isNotEmpty(history)) {
                        // 合计历史核销金额
                        BigDecimal totalHistoryAmount = history.stream()
                                .map(PaymentReceiptHistory::getAmount)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        // 计算未核销金额
                        receipt.setReceiptUnwrittenAmount(receipt.getPrAmount()
                                .subtract(totalHistoryAmount)
                                .subtract(dtl.getCifdAccount()));
                    } else {
                        // 计算未核销金额
                        receipt.setReceiptUnwrittenAmount(receipt.getPrAmount()
                                .subtract(dtl.getCifdAccount()));
                    }
                    updateList.add(receipt);
                    updateDtlList.add(dtl);
                }
            }
        } else {
            // 取消认领
            // 无论是否找到关联的PaymentReceipt，都要把当前认领单ID加入删除列表，确保历史记录被清理
            cancelCifIds.add(incomingFunds.getId());

            // 如果子表没有prId（未同步），尝试从历史记录中查找关联的PaymentReceipt
            List<PaymentReceiptHistory> relatedHistories = paymentReceiptHistoryService.list(Wrappers.<PaymentReceiptHistory>lambdaQuery()
                    .eq(PaymentReceiptHistory::getCifId, incomingFunds.getId())
            );

            Set<String> allPrIds = new HashSet<>();
            if (ValidateUtil.isNotEmpty(prIds)) {
                allPrIds.addAll(prIds);
            }
            if (ValidateUtil.isNotEmpty(relatedHistories)) {
                allPrIds.addAll(relatedHistories.stream()
                        .map(PaymentReceiptHistory::getPrId)
                        .filter(ValidateUtil::isNotEmpty)
                        .collect(Collectors.toSet()));
            }

            // 如果找到了涉及的Receipt ID，重新查询Receipt列表以确保包含所有需要回滚的数据
            if (ValidateUtil.isNotEmpty(allPrIds)) {
                receipts = paymentReceiptService.list(Wrappers.<PaymentReceipt>lambdaQuery()
                        .in(PaymentReceipt::getId, allPrIds)
                );
            }

            if (ValidateUtil.isNotEmpty(receipts)) {
                List<String> prIds2 = receipts.stream().map(PaymentReceipt::getId).collect(Collectors.toList());

                // 预先查询出这些收据关联的“其他”子表记录（排除当前正在取消的cifId）
                List<ClaimIncomingFundsDtl> otherDtls = claimIncomingFundsDtlService.list(Wrappers.<ClaimIncomingFundsDtl>lambdaQuery()
                        .in(ClaimIncomingFundsDtl::getCifdReceipt, prIds2)
                        .ne(ClaimIncomingFundsDtl::getCifId, incomingFunds.getId())
                );
                Map<String, List<ClaimIncomingFundsDtl>> otherDtlsMap = new HashMap<>();
                if (ValidateUtil.isNotEmpty(otherDtls)) {
                    otherDtlsMap = otherDtls.stream().collect(Collectors.groupingBy(ClaimIncomingFundsDtl::getCifdReceipt));
                }

                // 历史记录
                List<PaymentReceiptHistory> existingHistories = paymentReceiptHistoryService.list(Wrappers.<PaymentReceiptHistory>lambdaQuery()
                        .in(PaymentReceiptHistory::getPrId, prIds2)
                );
                Map<String, List<PaymentReceiptHistory>> historyGroupMap = existingHistories.stream()
                        .collect(Collectors.groupingBy(PaymentReceiptHistory::getPrId));

                for (PaymentReceipt receipt : receipts) {
                    // 确定本次取消的金额
                    BigDecimal cancelledAmount = BigDecimal.ZERO;
                    ClaimIncomingFundsDtl currentDtl = fundsDtlMap.get(receipt.getId());

                    if (ValidateUtil.isNotNull(currentDtl)) {
                        cancelledAmount = currentDtl.getCifdAccount();
                    }

                    // 如果当前子表缺失（数据未同步情况），尝试从历史记录中获取本次取消的金额
                    if (cancelledAmount == null || cancelledAmount.compareTo(BigDecimal.ZERO) == 0) {
                        List<PaymentReceiptHistory> history = historyGroupMap.get(receipt.getId());
                        if (ValidateUtil.isNotEmpty(history)) {
                            PaymentReceiptHistory targetHistory = history.stream()
                                    .filter(h -> h.getCifId().equals(incomingFunds.getId()))
                                    .findFirst().orElse(null);
                            if (targetHistory != null) {
                                cancelledAmount = targetHistory.getAmount();
                            }
                        }
                    }

                    // 更新“其他”存在的子表里的集团已到位资金
                    if (cancelledAmount != null && cancelledAmount.compareTo(BigDecimal.ZERO) > 0) {
                        List<ClaimIncomingFundsDtl> relatedDtls = otherDtlsMap.get(receipt.getId());
                        if (ValidateUtil.isNotEmpty(relatedDtls)) {
                            for (ClaimIncomingFundsDtl other : relatedDtls) {
                                if (ValidateUtil.isNotNull(other.getCifdGroupFunds())) {
                                    BigDecimal subtract = other.getCifdGroupFunds().subtract(cancelledAmount);
                                    // 如果计算结果小于0，置为0
                                    other.setCifdGroupFunds(subtract.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : subtract);
                                    updateDtlList.add(other);
                                }
                            }
                        }
                    }

                    // 处理PaymentReceipt状态回滚
                    // 获取该收据的所有历史记录
                    List<PaymentReceiptHistory> history = historyGroupMap.get(receipt.getId());

                    // 过滤掉当前正在取消认领的记录（即本次操作对应的cifId），获取剩余有效的历史记录
                    List<PaymentReceiptHistory> remainingHistory = new ArrayList<>();
                    if (ValidateUtil.isNotEmpty(history)) {
                        remainingHistory = history.stream()
                                .filter(item -> !item.getCifId().equals(incomingFunds.getId()))
                                .collect(Collectors.toList());
                    }

                    BigDecimal prAmount = receipt.getPrAmount();

                    if (ValidateUtil.isEmpty(remainingHistory)) {
                        // 如果没有剩余历史记录，说明完全取消认领
                        receipt.setPrIsClaim("0");
                        receipt.setCifId(null);
                        // 恢复为未核销状态，未核销金额等于总金额
                        receipt.setReceiptUnwrittenAmount(prAmount);
                        receipt.setPrVerifyState(1);
                    } else {
                        // 如果还有其他历史记录，保持认领状态
                        receipt.setPrIsClaim("1");

                        // 合计剩余历史核销金额
                        BigDecimal totalHistoryAmount = remainingHistory.stream()
                                .map(PaymentReceiptHistory::getAmount)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        // 计算未核销金额
                        BigDecimal unwrittenAmount = prAmount.subtract(totalHistoryAmount);
                        receipt.setReceiptUnwrittenAmount(unwrittenAmount);

                        if (unwrittenAmount.compareTo(BigDecimal.ZERO) <= 0) {
                            // 核销成功
                            receipt.setPrVerifyState(2);
                        } else if (unwrittenAmount.compareTo(prAmount) == 0) {
                            // 未核销
                            receipt.setPrVerifyState(1);
                        } else {
                            // 部分核销
                            receipt.setPrVerifyState(5);
                        }
                    }

                    // 如果PaymentReceipt不是审批结束状态，核销状态置为0（申请中）
                    if (!ApprovalStatusEnum.Already_Over.getIndex().equals(receipt.getState())) {
                        receipt.setPrVerifyState(0);
                    }

                    updateList.add(receipt);
                }
            }
        }

        if (ValidateUtil.isNotEmpty(updateList)) {
            paymentReceiptService.updateBatchById(updateList);
        }
        if (ValidateUtil.isNotEmpty(updateDtlList)) {
            claimIncomingFundsDtlService.updateBatchById(updateDtlList);
        }
        if (ValidateUtil.isNotEmpty(historyList)) {
            paymentReceiptHistoryService.saveBatch(historyList);
        }
        if (ValidateUtil.isNotEmpty(cancelCifIds)) {
            paymentReceiptHistoryService.remove(Wrappers.<PaymentReceiptHistory>lambdaQuery()
                    .in(PaymentReceiptHistory::getCifId, cancelCifIds)
            );
        }
        return BaseResponse.ok();
    }

    /**
     * 获取来款认领分页数据
     *
     * @param request 来款认领请求对象
     * @return 分页数据
     */
    @Override
    public BaseResponse getClaimIncomingFundsPage(ClaimIncomingFundsRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<ClaimIncomingFundsRequest> queryMap = new QueryMap<>(request);
        queryMap.put("createUid").put("id").put("createUname").put("createTime").put("state")
                .put("stateTime").put("cifMoney").put("cifAccount").put("cifCa").put("cifAbstract")
                .put("cifPurpose").put("cifPostscript").put("cifIsReceipt").put("cifIsWriteOff")
                .put("cifReceipt").put("cifIsIncoming").put("updateTime").put("updateUserId").put("updateUserName")
                .put("cifVerifyState").put("cifCName").put("cifCId")
                .put(request.getCifMoneys(), "cifMoneyStart", "cifMoneyEnd")
                .put(request.getCifDates(), "cifDateStart", "cifDateEnd");
        Page<ClaimIncomingFundsResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ClaimIncomingFundsResponse> list = baseMapper.getClaimIncomingFundsPage(page, queryMap.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据主键ID获取来款认领详情
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getClaimIncomingFundsByPid(ClaimIncomingFundsRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ClaimIncomingFundsDtlDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ClaimIncomingFundsDtlDTO> list = baseMapper.getClaimIncomingFundsByPid(page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId 集团ID
     * @return
     */
    @Override
    public BaseResponse getGroupFundsCumulative(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return BaseResponse.ok(claimIncomingFundsDtlService.getGroupFundsCumulative(pId));
    }
}
