package com.jpmsoft.projectProducer.service.sporadicSettlement.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Maintenance.SporadicPaymentsRequest;
import com.jpmsoft.common.model.request.Maintenance.SporadicSettlementRequest;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceFinanceResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicPaymentsResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicSettlementResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.sporadicSettlement.SporadicPaymentsMapper;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicPayments;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceRecordService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.sporadicSettlement.ISporadicPaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jjz
 */
@Slf4j
@Service
public class SporadicPaymentsServiceImpl extends ServiceImpl<SporadicPaymentsMapper, SporadicPayments> implements ISporadicPaymentsService {

    @Resource
    private IMaintenanceRecordService maintenanceRecordService;

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public BaseResponse getSporadicPaymentsList(SporadicPaymentsRequest request,String uId) {
        QueryMap<SporadicPaymentsRequest> params = new QueryMap<>(request);
        params.put(request.getSpPFillindata(),"spFillindataStart","spFillindataEnd")
                .put("spPName").put("spPNumber").put("spPPaymentamount").put("spPFillindata")
                .put("spPApplyunitid").put("spPApplyunitname").put("spPPaymentdata")
                .put("spPAreamanagerUid").put("spPAreamanagerUname").put("mrCategory");
        IPage<SporadicPaymentsResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SporadicPaymentsResponse> sporadicPaymentsResponseList = baseMapper.getSporadicPaymentsList(page, params.getQuery());
        return BaseResponse.ok(sporadicPaymentsResponseList, (int) page.getTotal());
    }

    @Override
    public BaseResponse checkSporadicPaymentsByMfCode(List<String> sporadicSettlementIds) {
        if (sporadicSettlementIds == null || sporadicSettlementIds.isEmpty()) {
            return BaseResponse.fail("结算单ID列表不能为空");
        }

        List<MaintenanceFinanceResponse> resultList = baseMapper.getMfCodesBySettlementIds(sporadicSettlementIds);

        if (resultList == null || resultList.isEmpty()) {
            return BaseResponse.fail("未查询到相关的零星维修记录");
        }

        Map<String, List<MaintenanceFinanceResponse>> groupedByMrId = resultList.stream()
                .collect(Collectors.groupingBy(MaintenanceFinanceResponse::getMrId));

        List<String> mrIdList = groupedByMrId.keySet().stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        // 根据 mrIdList 查询区域负责人 id
        String areaManagerId = null;
        if (!mrIdList.isEmpty()) {
            String actCode = sysConfigService.getConfigByCode("ACT_CODE");
            if (actCode == null || actCode.trim().isEmpty()) {
                log.info("未配置 ACT_CODE，默认使用 fz1");
                actCode = "fz1";
            }
            areaManagerId = baseMapper.queryAreaManagerIds(mrIdList,actCode.trim());
        }

        // 构造：每个 mrId 对应的 mfCode 列表
        Map<String, List<String>> mfCodesByMrId = new HashMap<>();
        for (Map.Entry<String, List<MaintenanceFinanceResponse>> e : groupedByMrId.entrySet()) {
            String mrId = e.getKey();
            if (mrId == null || mrId.trim().isEmpty()) continue;
            List<String> codes = e.getValue().stream()
                    .map(MaintenanceFinanceResponse::getMfCode)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            mfCodesByMrId.put(mrId, codes);
        }

        // 若没有有效 mrId（或全部被过滤），直接判为不相同
        if (mfCodesByMrId.isEmpty()) {
            return BaseResponse.fail("没有有效的零星维修记录");
        }

        Map<String, Map<String, Long>> freqByMrId = new HashMap<>();
        for (Map.Entry<String, List<String>> e : mfCodesByMrId.entrySet()) {
            List<String> codes = e.getValue();
            if (codes == null || codes.isEmpty()) {
                freqByMrId.put(e.getKey(), Collections.emptyMap());
                continue;
            }
            Map<String, Long> freq = codes.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            freqByMrId.put(e.getKey(), freq);
        }

        Iterator<Map<String, Long>> it = freqByMrId.values().iterator();
        Map<String, Long> reference = null;
        while (it.hasNext() && reference == null) {
            reference = it.next();
        }

        boolean allSame = true;
        if (reference == null) {
            allSame = false;
        } else {
            for (Map<String, Long> freq : freqByMrId.values()) {
                if (!Objects.equals(reference, freq)) {
                    allSame = false;
                    break;
                }
            }
        }

        // 返回对象组装
        Map<String, Object> data = new HashMap<>();
        data.put("isSame", allSame);
        data.put("spAreaManagerUid", areaManagerId);

        // 构造要返回的 MaintenanceFinanceResponse 列表：
        // - allSame == true: 随机返回一个 mrId 下的原始对象列表（未去重）
        // - allSame == false: 返回按 mfCode 去重后的代表对象列表（保留第一次出现的对象）
        List<MaintenanceFinanceResponse> maintenanceFinanceResponses;
        if (allSame) {
            List<String> candidateMrIds = new ArrayList<>(mfCodesByMrId.keySet());
            int idx = ThreadLocalRandom.current().nextInt(candidateMrIds.size());
            String sampleMrId = candidateMrIds.get(idx);
            maintenanceFinanceResponses = groupedByMrId.getOrDefault(sampleMrId, Collections.emptyList());

        } else {
            // 全量去重：按 mfCode 去重，保留第一次出现的 MaintenanceFinanceResponse
            Map<String, MaintenanceFinanceResponse> uniqueByMfCode = new LinkedHashMap<>();
            for (MaintenanceFinanceResponse r : resultList) {
                String code = r.getMfCode();
                if (code == null) continue;
                code = code.trim();
                if (code.isEmpty()) continue;
                // 如果尚未存在该 mfCode 的代表，就放入（保留第一次遇到的那条记录）
                uniqueByMfCode.putIfAbsent(code, r);
            }
            maintenanceFinanceResponses = new ArrayList<>(uniqueByMfCode.values());
        }

        data.put("maintenanceFinanceResponses", maintenanceFinanceResponses);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse setModifymrRepairState(String id, String mrRepairState) {

        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<MaintenanceRecord> mrRecords = baseMapper.getModifymrRepairState(id);

        if (ValidateUtil.isEmpty(mrRepairState)) {
            mrRepairState = "6";
        }

        if (!mrRecords.isEmpty()) {
            String finalMrRepairState = mrRepairState;
            List<MaintenanceRecord> updatedRecords = mrRecords.stream()
                    .peek(record -> record.setMrRepairState(finalMrRepairState))
                    .collect(Collectors.toList());

            maintenanceRecordService.updateBatchById(updatedRecords);

            return BaseResponse.ok(updatedRecords.stream().map(MaintenanceRecord::getMrId).collect(Collectors.toList()));
        } else {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
    }


    @Override
    public BaseResponse getSporadicPaymentsBySeId(String seId) {
        String spId = baseMapper.getSporadicPaymentsBySeId(seId);
        if (ValidateUtil.isEmpty(spId)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(spId);
    }


}
