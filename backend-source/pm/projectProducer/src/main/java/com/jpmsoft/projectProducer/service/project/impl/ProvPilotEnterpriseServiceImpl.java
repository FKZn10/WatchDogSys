package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Project.*;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProvPilotEnterpriseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotEnterprise;
import com.jpmsoft.projectProducer.pojo.vo.project.ProvPilotEnterpriseStatisticsVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.project.IProvPilotEnterpriseService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 省试点企业 Service 实现
 *
 * @author whjp
 * @since 2026-09-17
 */
@Slf4j
@Service
public class ProvPilotEnterpriseServiceImpl extends ServiceImpl<ProvPilotEnterpriseMapper, ProvPilotEnterprise> implements IProvPilotEnterpriseService {

    /**
     * 企业类型字典父编码
     */
    private static final String DICT_ENTERPRISE_TYPE = "JPMsoft_ZPSBZY";

    /**
     * 所在区字典父编码
     */
    private static final String DICT_DISTRICT = "JPMsoft_ZPSZQ";

    /**
     * 企业编号前缀
     */
    private static final String CODE_PREFIX = "SQ";

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdate(ProvPilotEnterpriseModel request, LogUserInfoVo user) {
        if (request == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (StrUtil.isEmpty(request.getEnterpriseName())) {
            return BaseResponse.fail("企业名称不能为空");
        }

        if (ValidateUtil.isEmpty(request.getId())) {
            ProvPilotEnterprise entity = new ProvPilotEnterprise();
            buildEntityFromModel(request, entity);
            entity.setId(IdWorker.getGenerateID());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(new Date());
            entity.setUpdateUid(user.getId());
            entity.setUpdateUname(user.getName());
            entity.setUpdateTime(entity.getCreateTime());
            entity.setTeId(request.getTeId());
            entity.setEnterpriseCode(generateEnterpriseCode(entity.getAnnualYear(), entity.getBatch(), new HashMap<>()));
            baseMapper.insert(entity);
            return BaseResponse.ok(entity.getId());
        }

        ProvPilotEnterprise old = baseMapper.selectById(request.getId());
        if (old == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 保留主键、创建人、创建时间、编号
        String oldId = old.getId();
        String oldCreateUid = old.getCreateUid();
        String oldCreateUname = old.getCreateUname();
        Date oldCreateTime = old.getCreateTime();
        String oldEnterpriseCode = old.getEnterpriseCode();

        buildEntityFromModel(request, old);
        old.setId(oldId);
        old.setCreateUid(oldCreateUid);
        old.setCreateUname(oldCreateUname);
        old.setCreateTime(oldCreateTime);
        old.setEnterpriseCode(oldEnterpriseCode);
        // 记录修改人、修改时间
        old.setUpdateUid(user.getId());
        old.setUpdateUname(user.getName());
        old.setUpdateTime(new Date());
        baseMapper.updateById(old);
        return BaseResponse.ok(old.getId());
    }

    private void buildEntityFromModel(ProvPilotEnterpriseModel request, ProvPilotEnterprise entity) {
        entity.setAnnualYear(request.getAnnualYear());
        entity.setBatch(request.getBatch());
        entity.setEnterpriseName(request.getEnterpriseName());
        entity.setEnterpriseType(request.getEnterpriseType());
        entity.setDistrict(request.getDistrict());
        entity.setTeId(request.getTeId());
    }

    @Override
    public BaseResponse getList(ProvPilotEnterpriseModel request, String uid) {
        QueryMap<ProvPilotEnterpriseModel> queryMap = buildQueryMap(request);
        IPage<ProvPilotEnterpriseModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProvPilotEnterpriseModel> list = baseMapper.getList(queryMap.getQuery(), page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    private QueryMap<ProvPilotEnterpriseModel> buildQueryMap(ProvPilotEnterpriseModel request) {
        QueryMap<ProvPilotEnterpriseModel> queryMap = new QueryMap<>(request);
        // 年度范围：列表 [起始年度, 截止年度]
        queryMap.put(request.getAnnualYearList(), "annualYearStart", "annualYearEnd");
        queryMap.put("enterpriseName");
        if (ValidateUtil.isNotEmpty(request.getEnterpriseTypeList())) {
            queryMap.put("enterpriseTypeList", request.getEnterpriseTypeList());
        }
        if (ValidateUtil.isNotEmpty(request.getDistrictList())) {
            queryMap.put("districtList", request.getDistrictList());
        }
        return queryMap;
    }

    @Override
    public BaseResponse getById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        return BaseResponse.ok(baseMapper.getById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteBatch(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return BaseResponse.fail(ResMsg.notId);
        }
        baseMapper.deleteBatchIds(ids);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importProvPilotEnterprise(ProvPilotEnterpriseImportRequest request, LogUserInfoVo user) {
        ProvPilotEnterpriseImportResult result = new ProvPilotEnterpriseImportResult();
        if (request == null || request.getRows() == null || request.getRows().isEmpty()) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Date importTime = new Date();

        Map<String, SysDictionary> enterpriseTypeMap = buildNameMap(DICT_ENTERPRISE_TYPE);
        Map<String, SysDictionary> districtMap = buildNameMap(DICT_DISTRICT);

        Set<String> existingNames = new HashSet<>(list(new LambdaQueryWrapper<ProvPilotEnterprise>()
                .select(ProvPilotEnterprise::getEnterpriseName)).stream()
                .map(ProvPilotEnterprise::getEnterpriseName)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet()));
        Set<String> batchNames = new HashSet<>();
        Map<String, Integer> codeSeqCache = new HashMap<>();

        List<ProvPilotEnterprise> toInsert = new ArrayList<>();
        for (ProvPilotEnterpriseImportRow row : request.getRows()) {
            List<ProvPilotEnterpriseImportError> errors = validateRow(row, enterpriseTypeMap, districtMap);
            if (!errors.isEmpty()) {
                result.setFailCount(result.getFailCount() + 1);
                result.getErrors().addAll(errors);
                continue;
            }
            String enterpriseName = normalize(row.getColEnterpriseName());
            if (existingNames.contains(enterpriseName) || !batchNames.add(enterpriseName)) {
                result.setSkipCount(result.getSkipCount() + 1);
                continue;
            }
            ProvPilotEnterprise entity = new ProvPilotEnterprise();
            entity.setId(IdWorker.getGenerateID());
            entity.setAnnualYear(parseYear(normalize(row.getColAnnualYear())));
            entity.setBatch(normalize(row.getColBatch()));
            entity.setEnterpriseName(enterpriseName);
            entity.setEnterpriseType(enterpriseTypeMap.get(normalize(row.getColEnterpriseType())).getdCode());
            entity.setDistrict(districtMap.get(normalize(row.getColDistrict())).getdCode());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(importTime);
            entity.setTeId(user.getTeId());
            entity.setEnterpriseCode(generateEnterpriseCode(entity.getAnnualYear(), entity.getBatch(), codeSeqCache));
            toInsert.add(entity);
        }

        // 有任何校验错误则整批不导入，返回 fail（前端据此判断）
        if (result.getFailCount() > 0) {
            return BaseResponse.fail("导入失败，存在错误数据", result);
        }
        if (!toInsert.isEmpty()) {
            saveBatch(toInsert);
            result.setSuccessCount(toInsert.size());
        }
        return BaseResponse.ok(result);
    }

    private ProvPilotEnterpriseImportError newError(ProvPilotEnterpriseImportRow row, String fieldKey, String message) {
        ProvPilotEnterpriseImportError e = new ProvPilotEnterpriseImportError();
        e.setRowNum(row.getExcelRowNum());
        e.setFieldKey(fieldKey);
        e.setMessage(message);
        return e;
    }

    private List<ProvPilotEnterpriseImportError> validateRow(ProvPilotEnterpriseImportRow row,
                                                             Map<String, SysDictionary> enterpriseTypeMap,
                                                             Map<String, SysDictionary> districtMap) {
        List<ProvPilotEnterpriseImportError> errors = new ArrayList<>();
        String annualYear = normalize(row.getColAnnualYear());
        String batch = normalize(row.getColBatch());
        String enterpriseName = normalize(row.getColEnterpriseName());
        String enterpriseType = normalize(row.getColEnterpriseType());
        String district = normalize(row.getColDistrict());

        if (ValidateUtil.isEmpty(annualYear)) {
            errors.add(newError(row, "annualYear", "年度不能为空"));
        } else if (parseYear(annualYear) == null) {
            errors.add(newError(row, "annualYear", "年度必须为数字"));
        }
        if (ValidateUtil.isEmpty(batch)) {
            errors.add(newError(row, "batch", "批次不能为空"));
        }
        if (ValidateUtil.isEmpty(enterpriseName)) {
            errors.add(newError(row, "enterpriseName", "企业名称不能为空"));
        }
        if (ValidateUtil.isEmpty(enterpriseType)) {
            errors.add(newError(row, "enterpriseType", "企业类型不能为空"));
        } else if (!enterpriseTypeMap.containsKey(enterpriseType)) {
            errors.add(newError(row, "enterpriseType", "企业类型[" + enterpriseType + "]不在字典中"));
        }
        if (ValidateUtil.isEmpty(district)) {
            errors.add(newError(row, "district", "所在区不能为空"));
        } else if (!districtMap.containsKey(district)) {
            errors.add(newError(row, "district", "所在区[" + district + "]不在字典中"));
        }
        return errors;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\s+", "").trim();
    }

    private Map<String, SysDictionary> buildNameMap(String code) {
        List<SysDictionary> list = sysDictionaryService.getDictionaryByParentCode(code);
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, SysDictionary> map = new HashMap<>();
        for (SysDictionary d : list) {
            map.put(normalize(d.getdName()), d);
        }
        return map;
    }

    private Integer parseYear(String value) {
        if (ValidateUtil.isEmpty(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 生成企业编号：SQ + 年度(4位) + 批次(2位) + 序号(3位)，如 SQ202401001，按 年度+批次 续号
     */
    private String generateEnterpriseCode(Integer annualYear, String batch, Map<String, Integer> codeSeqCache) {
        String prefix = CODE_PREFIX + (annualYear == null ? "0000" : String.format("%04d", annualYear)) + formatBatch(batch);
        Integer lastSeq = codeSeqCache.get(prefix);
        if (lastSeq == null) {
            lastSeq = 0;
            String maxCode = baseMapper.selectMaxEnterpriseCode(prefix);
            if (ValidateUtil.isNotEmpty(maxCode) && maxCode.length() > prefix.length()) {
                try {
                    lastSeq = Integer.parseInt(maxCode.substring(prefix.length()));
                } catch (NumberFormatException e) {
                    lastSeq = 0;
                }
            }
        }
        int nextSeq = lastSeq + 1;
        codeSeqCache.put(prefix, nextSeq);
        return prefix + String.format("%03d", nextSeq);
    }

    private String formatBatch(String batch) {
        if (ValidateUtil.isEmpty(batch)) {
            return "00";
        }
        String b = batch.trim();
        if (b.matches("\\d+")) {
            return String.format("%02d", Integer.parseInt(b));
        }
        return b;
    }

    @Override
    public List<ProvPilotEnterpriseModel> getExportList(ProvPilotEnterpriseModel request, String uid) {
        QueryMap<ProvPilotEnterpriseModel> queryMap = buildQueryMap(request);
        IPage<ProvPilotEnterpriseModel> page = new Page<>(0, -1);
        return baseMapper.getList(queryMap.getQuery(), page);
    }

    /**
     * 统计全部年度试点企业，启用租户时只统计当前租户；评估及验收标记为暂不可用。
     *
     * @param user 当前登录用户
     * @return 企业总数及评估、验收可用性
     */
    @Override
    public BaseResponse getEnterpriseStatistics(LogUserInfoVo user) {
        if (user == null || StrUtil.isBlank(user.getId())) {
            return BaseResponse.fail("请先登录");
        }
        String teId = null;
        if (user.isEnableTenant()) {
            if (StrUtil.isBlank(user.getTeId())) {
                return BaseResponse.fail("当前登录用户未关联租户");
            }
            teId = user.getTeId();
        }
        ProvPilotEnterpriseStatisticsVO result = new ProvPilotEnterpriseStatisticsVO();
        result.setTotal(baseMapper.countEnterpriseStatistics(teId));
        return BaseResponse.ok(result);
    }
}