package com.jpmsoft.projectProducer.service.secInfo.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDetailModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertCheckDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.service.secInfo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecExpertCheckDetailServiceImpl extends ServiceImpl<SecExpertCheckDetailMapper, SecExpertCheckDetail> implements ISecExpertCheckDetailService {

    @Resource
    private ISecExpertCheckDtlService secExpertCheckDtlService;

    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;

    @Resource
    private ISecSelectionRecordService secSelectionRecordService;

    @Resource
    private ISecExpertCheckService secExpertCheckService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrupdateSecExpertCheckDetailAndDtl(SecExpertCheckDetailModel request, String uid) {
//        ObjectMapper objectMapper = new ObjectMapper();
/*        Map<String, String> selection = request.getSelection();

        String selectionStr = "";
        try {
            if (!selection.isEmpty()) {
                selectionStr = objectMapper.writeValueAsString(selection);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return BaseResponse.fail(ResMsg.actError);
        }*/

        boolean isUpdate = !ValidateUtil.isEmpty(request.getId());

        SecExpertCheckDetail secExpertCheckDetail;
        if (isUpdate) {
            secExpertCheckDetail = getById(request.getId());
            if (secExpertCheckDetail == null) {
                return BaseResponse.fail("记录不存在，ID: " + request.getId());
            }
        } else {
            secExpertCheckDetail = new SecExpertCheckDetail();
            secExpertCheckDetail.setId(GenerateUUID.getUUID());
        }

//        secExpertCheckDetail.setSelection(selectionStr);
        secExpertCheckDetail.setSrId(request.getSrId());
        secExpertCheckDetail.setEcId(request.getEcId());
        secExpertCheckDetail.setExId(request.getExId());
        secExpertCheckDetail.setSrdId(request.getSrdId());
        secExpertCheckDetail.setTotalDeduction(request.getTotalDeduction());
        secExpertCheckDetail.setSecdResult(request.getSecdResult());


        saveOrUpdate(secExpertCheckDetail);
        secExpertCheckDtlService.createOrupdateSecExpertCheckDetailAndDtl(request.getRawResult(), secExpertCheckDetail.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("scdId", secExpertCheckDetail.getId());
        data.put("srdId", request.getSrdId());
        data.put("totalDeduction", request.getTotalDeduction());
        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse getSecExpertCheckDetailAndDtlById(String scdId, String uid) {
        if (ValidateUtil.isEmpty(scdId)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        SecExpertCheckDetail secExpertCheckDetail = getById(scdId);
        if (secExpertCheckDetail == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<SecExpertCheckDtlModel> rawResult = secExpertCheckDtlService.getSecExpertCheckDtlByScdId(scdId);
        SecExpertCheckDetailModel model = new SecExpertCheckDetailModel();
        model.setId(secExpertCheckDetail.getId());
        model.setSrdId(secExpertCheckDetail.getSrdId());
        model.setSrId(secExpertCheckDetail.getSrId());
        model.setEcId(secExpertCheckDetail.getEcId());
        model.setTotalDeduction(secExpertCheckDetail.getTotalDeduction());
        model.setSecdResult(secExpertCheckDetail.getSecdResult());
        model.setRawResult(rawResult);
/*        String selectionStr = secExpertCheckDetail.getSelection();
        if (selectionStr != null && !selectionStr.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> selectionMap = objectMapper.readValue(selectionStr, Map.class);
                model.setSelection(selectionMap);
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.fail(ResMsg.actError);
            }
        }*/
        return BaseResponse.ok(model);
    }

/*    @Override
    public BaseResponse getSecExpertCheckDetailAndDtlSelect(String scdId, String uid) {
        // 1. 获取专家检查详情
        SecExpertCheckDetail secExpertCheckDetail = getById(scdId);
        if (secExpertCheckDetail == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(secExpertCheckDetail.getSrdId())) {
            return BaseResponse.fail("实体类缺少必要参数 srd: " + secExpertCheckDetail.getSrdId());
        }

        // 2. 获取选择记录详情
        SecSelectionRecordDtl secSelectionRecordDtl = secSelectionRecordDtlService.getById(secExpertCheckDetail.getSrdId());
        String exId = secSelectionRecordDtl.getExId();

        // 3. 查询专家所有选择记录
        List<SecSelectionRecordDtl> secSelectionRecordDtlList = secSelectionRecordDtlService.list(
                new LambdaQueryWrapper<SecSelectionRecordDtl>()
                        .eq(SecSelectionRecordDtl::getExId, exId)
        );
        if (ValidateUtil.isEmpty(secSelectionRecordDtlList)) {
            return BaseResponse.ok(false);
        }

        // 4. 过滤一年内的有效记录
        List<String> srIds = secSelectionRecordDtlList.stream()
                .map(SecSelectionRecordDtl::getSrId)
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        List<SecSelectionRecord> validRecords = secSelectionRecordService.list(
                new LambdaQueryWrapper<SecSelectionRecord>()
                        .in(SecSelectionRecord::getSrId, srIds)
                        .ge(SecSelectionRecord::getSrCreateTime, today.minusYears(1))
                        .le(SecSelectionRecord::getSrCreateTime, today)
        );

        Set<String> validSrIds = validRecords.stream()
                .map(SecSelectionRecord::getSrId)
                .collect(Collectors.toSet());

        // 5. 获取有效的检查详情ID
        Set<String> validSrdIds = secSelectionRecordDtlList.stream()
                .filter(dtl -> validSrIds.contains(dtl.getSrId()))
                .map(SecSelectionRecordDtl::getSrdId)
                .collect(Collectors.toSet());

        List<SecExpertCheckDetail> checkDetails = list(
                new LambdaQueryWrapper<SecExpertCheckDetail>()
                        .in(SecExpertCheckDetail::getSrdId, validSrdIds)
        );
        if (ValidateUtil.isEmpty(checkDetails)) {
            return BaseResponse.ok(false);
        }

        // 6. 查询所有根节点（序号1）
        List<String> scdIds = checkDetails.stream()
                .map(SecExpertCheckDetail::getId)
                .collect(Collectors.toList());

        List<SecExpertCheckDtl> rootNodes = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getScdId, scdIds)
                        .eq(SecExpertCheckDtl::getSecdSort, 1)
        );
        if (ValidateUtil.isEmpty(rootNodes)) {
            return BaseResponse.ok(false);
        }

        // 7. 优化查询：批量获取所有子节点
        Set<String> rootIds = rootNodes.stream()
                .map(SecExpertCheckDtl::getId)
                .collect(Collectors.toSet());

        Map<String, List<SecExpertCheckDtl>> childrenMap = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getParentId, rootIds)
        ).stream().collect(Collectors.groupingBy(SecExpertCheckDtl::getParentId));

        // 8. 核心修改：按根节点统计通过次数
        long qualifiedRootCount = rootIds.stream()
                .filter(rootId -> {
                    List<SecExpertCheckDtl> children = childrenMap.getOrDefault(rootId, Collections.emptyList());
                    return children.stream().anyMatch(dtl -> dtl.getSecdResult() == 1);
                })
                .count();

        return BaseResponse.ok(qualifiedRootCount >= 2);
    }*/

/*    @Override
    public BaseResponse getSecExpertCheckDetailAndDtlSelect(String scdId, String uid) {
        // 1. 获取专家检查详情
        SecExpertCheckDetail secExpertCheckDetail = getById(scdId);
        if (secExpertCheckDetail == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(secExpertCheckDetail.getSrdId())) {
            return BaseResponse.fail("实体类缺少必要参数 srd: " + secExpertCheckDetail.getSrdId());
        }

        // 2. 获取选择记录详情
        SecSelectionRecordDtl secSelectionRecordDtl = secSelectionRecordDtlService.getById(secExpertCheckDetail.getSrdId());
        String exId = secSelectionRecordDtl.getExId();

        // 3. 查询专家所有选择记录
        List<SecSelectionRecordDtl> secSelectionRecordDtlList = secSelectionRecordDtlService.list(
                new LambdaQueryWrapper<SecSelectionRecordDtl>()
                        .eq(SecSelectionRecordDtl::getExId, exId)
        );
        if (ValidateUtil.isEmpty(secSelectionRecordDtlList)) {
            return BaseResponse.ok();
        }

        // 4. 过滤一年内的有效记录
        List<String> srIds = secSelectionRecordDtlList.stream()
                .map(SecSelectionRecordDtl::getSrId)
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        LocalDate lastYearToday = today.minusYears(1);

        List<SecSelectionRecord> validRecords = secSelectionRecordService.list(
                new LambdaQueryWrapper<SecSelectionRecord>()
                        .in(SecSelectionRecord::getSrId, srIds)
                        .between(SecSelectionRecord::getSrCreateTime, lastYearToday, today)
        );

        Set<String> validSrIds = validRecords.stream()
                .map(SecSelectionRecord::getSrId)
                .collect(Collectors.toSet());

        // 5. 获取有效的检查详情ID
        Set<String> validSrdIds = secSelectionRecordDtlList.stream()
                .filter(dtl -> validSrIds.contains(dtl.getSrId()))
                .map(SecSelectionRecordDtl::getSrdId)
                .collect(Collectors.toSet());

        List<SecExpertCheckDetail> checkDetails = list(
                new LambdaQueryWrapper<SecExpertCheckDetail>()
                        .in(SecExpertCheckDetail::getSrdId, validSrdIds)
        );
        if (ValidateUtil.isEmpty(checkDetails)) {
            return BaseResponse.ok();
        }

        // 6. 查询所有根节点（排序号1）
        List<String> scdIds = checkDetails.stream()
                .map(SecExpertCheckDetail::getId)
                .collect(Collectors.toList());

        List<SecExpertCheckDtl> rootNodes = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getScdId, scdIds)
                        .eq(SecExpertCheckDtl::getSecdSort, 1)
        );
        if (ValidateUtil.isEmpty(rootNodes)) {
            return BaseResponse.ok();
        }

        // 7. 获取所有根节点ID
        Set<String> rootIds = rootNodes.stream()
                .map(SecExpertCheckDtl::getId)
                .collect(Collectors.toSet());

        // 8. 查询所有子节点（排序号1和2）
        List<SecExpertCheckDtl> childNodes = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getParentId, rootIds)
                        .in(SecExpertCheckDtl::getSecdSort, Arrays.asList(1, 2))
        );

        // 9. 分别统计排序号1和2的合格次数
        long sort1Count = childNodes.stream()
                .filter(dtl -> dtl.getSecdSort() == 1 && dtl.getSecdResult() == 1)
                .count();

        long sort2Count = childNodes.stream()
                .filter(dtl -> dtl.getSecdSort() == 2 && dtl.getSecdResult() == 1)
                .count();

        Map<String,Integer> totalCount = new HashMap<>();
        totalCount.put("sort1Count", (int) sort1Count);
        totalCount.put("sort2Count", (int) sort2Count);
        // 11. 返回包含详细统计结果的对象
        return BaseResponse.ok(totalCount);
    }*/

    @Override
    public BaseResponse getSecExpertCheckDetailAndDtlSelect(String srdId, String uid) {
/*        // 1. 获取专家检查详情
        SecExpertCheckDetail secExpertCheckDetail = getById(scdId);
        if (secExpertCheckDetail == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(secExpertCheckDetail.getSrdId())) {
            return BaseResponse.fail("实体类缺少必要参数 srd: " + secExpertCheckDetail.getSrdId());
        }*/

        // 2. 获取选择记录详情
        if (ValidateUtil.isEmpty(srdId)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        SecSelectionRecordDtl secSelectionRecordDtl = secSelectionRecordDtlService.getById(srdId);
        if (secSelectionRecordDtl == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        String exId = secSelectionRecordDtl.getExId();

        // 3. 查询专家所有选择记录
        List<SecSelectionRecordDtl> secSelectionRecordDtlList = secSelectionRecordDtlService.list(
                new LambdaQueryWrapper<SecSelectionRecordDtl>()
                        .eq(SecSelectionRecordDtl::getExId, exId)
        );
        if (ValidateUtil.isEmpty(secSelectionRecordDtlList)) {
            return BaseResponse.ok(Collections.singletonMap("data", createDefaultCountResult()));
        }

        // 4. 过滤一年内的有效记录
        List<String> srIds = secSelectionRecordDtlList.stream()
                .map(SecSelectionRecordDtl::getSrId)
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        LocalDate lastYearToday = today.minusYears(1);

        List<SecSelectionRecord> validRecords = secSelectionRecordService.list(
                new LambdaQueryWrapper<SecSelectionRecord>()
                        .in(SecSelectionRecord::getSrId, srIds)
                        .between(SecSelectionRecord::getSrCreateTime, lastYearToday, today)
        );

        Set<String> validSrIds = validRecords.stream()
                .map(SecSelectionRecord::getSrId)
                .collect(Collectors.toSet());

        // 5. 获取有效的检查详情ID
        Set<String> validSrdIds = secSelectionRecordDtlList.stream()
                .filter(dtl -> validSrIds.contains(dtl.getSrId()))
                .map(SecSelectionRecordDtl::getSrdId)
                .collect(Collectors.toSet());

        List<SecExpertCheckDetail> checkDetails = list(
                new LambdaQueryWrapper<SecExpertCheckDetail>()
                        .in(SecExpertCheckDetail::getSrdId, validSrdIds)
                        .exists("SELECT 1 FROM Sec_Expert_Check sec WHERE sec.id = Sec_Expert_Check_Detail.ec_id AND sec.state = 7")
        );
        if (ValidateUtil.isEmpty(checkDetails)) {
            return BaseResponse.ok(Collections.singletonMap("data", createDefaultCountResult()));
        }

        // 6. 查询所有根节点（排序号1）
        List<String> scdIds = checkDetails.stream()
                .map(SecExpertCheckDetail::getId)
                .collect(Collectors.toList());

        List<SecExpertCheckDtl> rootNodes = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getScdId, scdIds)
                        .eq(SecExpertCheckDtl::getSecdSort, 1)
        );
        if (ValidateUtil.isEmpty(rootNodes)) {
            return BaseResponse.ok(Collections.singletonMap("data", createDefaultCountResult()));
        }

        // 7. 获取所有根节点ID
        Set<String> rootIds = rootNodes.stream()
                .map(SecExpertCheckDtl::getId)
                .collect(Collectors.toSet());

        // 8. 查询所有子节点（排序号0和1）
        List<SecExpertCheckDtl> childNodes = secExpertCheckDtlService.list(
                new LambdaQueryWrapper<SecExpertCheckDtl>()
                        .in(SecExpertCheckDtl::getParentId, rootIds)
                        .in(SecExpertCheckDtl::getSecdSort, Arrays.asList(0, 1))
        );

        // 9. 分别统计排序号1和2的合格次数
        long sort1Count = childNodes.stream()
                .filter(dtl -> dtl.getSecdSort() == 0 &&
                        dtl.getSelected() != null &&
                        dtl.getSelected() == 1)
                .count();

        long sort2Count = childNodes.stream()
                .filter(dtl -> dtl.getSecdSort() == 1 &&
                        dtl.getSelected() != null &&
                        dtl.getSelected() == 1)
                .count();


        Map<String, Integer> totalCount = new HashMap<>();
        totalCount.put("sort1Count", (int) sort1Count);
        totalCount.put("sort2Count", (int) sort2Count);

        return BaseResponse.ok(totalCount);
    }

    @Override
    public BaseResponse deleteSecExpertCheckDetailBySrId(String srId, String uid) {
        if (ValidateUtil.isEmpty(srId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LambdaQueryWrapper<SecExpertCheckDetail> secExpertCheckDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secExpertCheckDetailLambdaQueryWrapper.eq(SecExpertCheckDetail::getSrdId, srId);
        List<SecExpertCheckDetail> secExpertCheckDetails = list(secExpertCheckDetailLambdaQueryWrapper);
        if (ValidateUtil.isNotEmpty(secExpertCheckDetails)) {
            secExpertCheckDetails.forEach(secExpertCheckDetail -> {
                secExpertCheckDetail.setStatus("01");
                updateById(secExpertCheckDetail);
            });
        }
        LambdaQueryWrapper<SecExpertCheck> secExpertCheckLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secExpertCheckLambdaQueryWrapper.eq(SecExpertCheck::getSrId, srId);
        List<SecExpertCheck> secExpertChecks = secExpertCheckService.list(secExpertCheckLambdaQueryWrapper);
        if (ValidateUtil.isNotEmpty(secExpertChecks)) {
            secExpertChecks.forEach(secExpertCheck -> {
                secExpertCheck.setStatus("01");
                secExpertCheckService.updateById(secExpertCheck);
            });
        }
        return BaseResponse.ok("作废成功");
    }


    private Map<String, Integer> createDefaultCountResult() {
        Map<String, Integer> result = new HashMap<>();
        result.put("sort1Count", 0);
        result.put("sort2Count", 0);
        return result;
    }




}