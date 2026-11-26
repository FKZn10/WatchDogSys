package com.jpmsoft.projectProducer.service.sporadicSettlement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Maintenance.SporadicSettlementRequest;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceFinanceResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicSettlementDtlResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicSettlementResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.sporadicSettlement.SporadicSettlementMapper;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicSettlement;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicSettlementDtl;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceRecordService;
import com.jpmsoft.projectProducer.service.sporadicSettlement.ISporadicSettlementDtlService;
import com.jpmsoft.projectProducer.service.sporadicSettlement.ISporadicSettlementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ISporadicSettlementServiceImpl extends ServiceImpl<SporadicSettlementMapper, SporadicSettlement> implements ISporadicSettlementService {

    @Resource
    private ISporadicSettlementDtlService sporadicSettlementDtlService;

    @Resource
    private IMaintenanceRecordService maintenanceRecordService;

    @Override
    public BaseResponse setModifymrRepairState(String id, String mrRepairState) {
        SporadicSettlement settlementById = getById(id);
        if (settlementById == null) {
            return BaseResponse.ok("数据为空");
        }
        LambdaQueryWrapper<SporadicSettlementDtl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SporadicSettlementDtl::getSeId,id);
        List<SporadicSettlementDtl> list = sporadicSettlementDtlService.list(queryWrapper);

        if (list == null){
            return BaseResponse.fail();
        }
        if (ValidateUtil.isEmpty(mrRepairState)) {
            mrRepairState = "4";
        }
        MaintenanceRecord record = new MaintenanceRecord();
        for (SporadicSettlementDtl sporadicSettlementDtl : list) {
            record.setMrId(sporadicSettlementDtl.getMrId());
            record.setMrRepairState(mrRepairState);
            maintenanceRecordService.updateById(record);
        }
        return BaseResponse.ok();

    }

    @Override
    public BaseResponse getSporadicSettlementeList(SporadicSettlementRequest request) {
        QueryMap<SporadicSettlementRequest> params = new QueryMap<>(request);
        params.put(request.getSePrices(), "sePriceStart", "sePriceEnd")
                .put(request.getSeAuditTimes(), "seAuditTimeStart", "seAuditTimeEnd")
                .put("sePName").put("seUnitId").put("seUnitName").put("sePrice")
                .put("seAuditTime").put("sePNumber").put("excludePiecemealPayFlow")
                .put("state").put("mrCategory");
        IPage<SporadicSettlementResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SporadicSettlementResponse> datas = baseMapper.getSporadicSettlementeList(page, params.getQuery());
        if (ValidateUtil.isNotEmpty(datas)) {
            datas.forEach(d -> {
                if (d.getSeDate() != null) {

                    d.setSeDateStr(DateFormatUtil.dateToString(d.getSeDate(), "yyyy年M月d日"));
                }
            });
        }
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSporadicSettlementByMrId(String mrId) {
        MaintenanceRecord record = maintenanceRecordService.getById(mrId);
        if(ValidateUtil.isNull(record)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        SporadicSettlement settlement = this.lambdaQuery()
                .like(SporadicSettlement::getSePNumber,record.getMrSerialNumber())
                .eq(SporadicSettlement::getState,ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(SporadicSettlement::getCreateTime)
                .last("limit 1")
                .one();

        if(ValidateUtil.isNull(settlement)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        SporadicSettlementResponse response = new SporadicSettlementResponse();
        BeanUtils.copyProperties(settlement,response);

        List<SporadicSettlementDtl> dtlList = sporadicSettlementDtlService.lambdaQuery()
                .eq(SporadicSettlementDtl::getSeId,settlement.getId())
                .list();
        if(ValidateUtil.isNotEmpty(dtlList)){
            List<SporadicSettlementDtlResponse> dtlResponseList = BeanUtils.copyList(dtlList,SporadicSettlementDtlResponse.class);
            response.setSporadicSettlementDtl(dtlResponseList);
        }


        return BaseResponse.ok(response);
    }




    @Override
    public BaseResponse checkSporadicSettlement(SporadicSettlementRequest request) {

        if (ValidateUtil.isNull(request.getMrIds())) {
            return BaseResponse.fail("mrIds不能为空");
        }
        if (ValidateUtil.isEmpty(request.getId())) {
            request.setId(null);
        }

        // 检查是否存在冲突结算单
        int conflict = baseMapper.checkSporadicSettlement(request.getMrIds(), request.getId());

        // 查询所有经费信息
        List<MaintenanceFinanceResponse> financeList =
                baseMapper.getMaintenanceFinanceByMrIds(request.getMrIds());

        // 按 mrId 分组，收集 mfCode 去重
        Map<String, Set<String>> mrIdToMfCodes = financeList.stream()
                .collect(Collectors.groupingBy(
                        MaintenanceFinanceResponse::getMrId,
                        Collectors.mapping(MaintenanceFinanceResponse::getMfCode, Collectors.toSet())
                ));

        Collection<Set<String>> allSets = mrIdToMfCodes.values();

        // 如果经费号不同，返回 02
        if (allSets.size() > 1) {
            Set<String> first = allSets.iterator().next();
            boolean allSame = allSets.stream().allMatch(first::equals);
            if (!allSame) {
                return BaseResponse.ok("02");
            }
        }

        // 如果存在冲突结算单，返回 03
        if (conflict > 0) {
            return BaseResponse.ok("03");
        }

        // 都没问题，返回 01
        return BaseResponse.ok("01");
    }


}