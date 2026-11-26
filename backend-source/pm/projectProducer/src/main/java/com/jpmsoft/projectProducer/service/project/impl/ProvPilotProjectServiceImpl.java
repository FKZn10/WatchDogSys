package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportError;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportResult;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRow;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectRegisterInfoMapper;
import com.jpmsoft.projectProducer.mapper.project.ProvPilotProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfo;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotProject;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.project.IProvPilotProjectService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 省试点项目 Service 实现
 *
 * <p>关联市项目与项目申报 project_register_info 一对一：一个项目申报只能被一个试点项目选择，
 * 唯一性由 {@link #checkRelatedCityProject(String, String)} 代码保证（不依赖数据库唯一约束）。</p>
 *
 * @author whjp
 * @since 2026-09-16
 */
@Slf4j
@Service
public class ProvPilotProjectServiceImpl extends ServiceImpl<ProvPilotProjectMapper, ProvPilotProject> implements IProvPilotProjectService {

    /**
     * 项目类型字典父编码
     */
    private static final String DICT_PROJECT_TYPE = "JPMsoft_ZPXMLX";

    /**
     * 所在区字典父编码
     */
    private static final String DICT_DISTRICT = "JPMsoft_ZPSZQ";

    /**
     * 项目编号前缀
     */
    private static final String CODE_PREFIX = "SX";

    @Resource
    private ProjectRegisterInfoMapper projectRegisterInfoMapper;
    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdate(ProvPilotProjectModel request, LogUserInfoVo user) {
        log.info("createOrUpdateProvPilotProject:{}", request);
        if (request == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (StrUtil.isEmpty(request.getProjectName())) {
            return BaseResponse.fail("项目名称不能为空");
        }
        // 关联市项目校验（填了才校验，且一对一）
        BaseResponse check = checkRelatedCityProject(request.getRelatedCityProjectId(), request.getId());
        if (!check.getSuccess()) {
            return check;
        }
        // 只传了关联市项目 id 时，补全名称（列表按名称筛选需要）
        if (!ValidateUtil.isEmpty(request.getRelatedCityProjectId())
                && ValidateUtil.isEmpty(request.getRelatedCityProjectName())) {
            ProjectRegisterInfo reg = projectRegisterInfoMapper.selectById(request.getRelatedCityProjectId());
            if (reg != null) {
                request.setRelatedCityProjectName(reg.getName());
            }
        }

        if (ValidateUtil.isEmpty(request.getId())) {
            ProvPilotProject entity = new ProvPilotProject();
            buildEntityFromModel(request, entity);
            entity.setId(IdWorker.getGenerateID());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(new Date());
            entity.setState(0);
            entity.setStateTime(entity.getCreateTime());
            entity.setUpdateUid(user.getId());
            entity.setUpdateUname(user.getName());
            entity.setUpdateTime(entity.getCreateTime());
            entity.setTeId(request.getTeId());
            // 生成项目编号：SX-年度-批次-序号
            entity.setProjectCode(generateProjectCode(entity.getAnnualYear(), entity.getBatch(), new HashMap<>()));
            baseMapper.insert(entity);
            return BaseResponse.ok(entity.getId());
        }

        ProvPilotProject old = baseMapper.selectById(request.getId());
        if (old == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 保留主键、创建人、创建时间、状态、状态时间、编号（编号为自动生成，编辑不覆盖）
        String oldId = old.getId();
        String oldCreateUid = old.getCreateUid();
        String oldCreateUname = old.getCreateUname();
        Date oldCreateTime = old.getCreateTime();
        Integer oldState = old.getState();
        Date oldStateTime = old.getStateTime();
        String oldProjectCode = old.getProjectCode();

        buildEntityFromModel(request, old);
        old.setId(oldId);
        old.setCreateUid(oldCreateUid);
        old.setCreateUname(oldCreateUname);
        old.setCreateTime(oldCreateTime);
        old.setState(oldState);
        old.setStateTime(oldStateTime);
        old.setProjectCode(oldProjectCode);
        // 记录修改人、修改时间
        old.setUpdateUid(user.getId());
        old.setUpdateUname(user.getName());
        old.setUpdateTime(new Date());
        baseMapper.updateById(old);
        return BaseResponse.ok(old.getId());
    }

    private void buildEntityFromModel(ProvPilotProjectModel request, ProvPilotProject entity) {
        entity.setAnnualYear(request.getAnnualYear());
        entity.setBatch(request.getBatch());
        entity.setProjectName(request.getProjectName());
        entity.setProjectType(request.getProjectType());
        entity.setDistrict(request.getDistrict());
        entity.setDeclareUnit(request.getDeclareUnit());
        entity.setRelatedCityProjectName(request.getRelatedCityProjectName());
        entity.setRelatedCityProjectId(request.getRelatedCityProjectId());
        entity.setFileId(request.getFileId());
        entity.setTeId(request.getTeId());
    }

    /**
     * 关联市项目校验：存在 + 未被其它试点选择（一对一）
     */
    private BaseResponse checkRelatedCityProject(String relatedCityProjectId, String selfId) {
        if (ValidateUtil.isEmpty(relatedCityProjectId)) {
            return BaseResponse.ok();
        }
        ProjectRegisterInfo registerInfo = projectRegisterInfoMapper.selectById(relatedCityProjectId);
        if (registerInfo == null) {
            return BaseResponse.fail("关联市项目在项目申报中不存在");
        }
        LambdaQueryWrapper<ProvPilotProject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProvPilotProject::getRelatedCityProjectId, relatedCityProjectId);
        if (!ValidateUtil.isEmpty(selfId)) {
            wrapper.ne(ProvPilotProject::getId, selfId);
        }
        if (baseMapper.selectCount(wrapper) > 0) {
            return BaseResponse.fail("该关联市项目已被其他试点项目选择");
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(ProvPilotProjectModel request, String uid) {
        QueryMap<ProvPilotProjectModel> queryMap = buildQueryMap(request);
        IPage<ProvPilotProjectModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProvPilotProjectModel> list = baseMapper.getList(queryMap.getQuery(), page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    /**
     * 组装列表/导出筛选条件（年度范围、所在区多选等）
     */
    private QueryMap<ProvPilotProjectModel> buildQueryMap(ProvPilotProjectModel request) {
        QueryMap<ProvPilotProjectModel> queryMap = new QueryMap<>(request);
        // 年度范围：列表 [起始年度, 截止年度]，第0个开始、第1个结束
        queryMap.put(request.getAnnualYearList(), "annualYearStart", "annualYearEnd");
        queryMap.put("projectName").put("batch").put("declareUnit").put("relatedCityProjectName")
                .put("hasRelatedCityProject");
        // 所在区多选
        if (ValidateUtil.isNotEmpty(request.getDistrictList())) {
            queryMap.put("districtList", request.getDistrictList());
        }
        // 项目类型多选（数据库编码 01/02/03）
        if (ValidateUtil.isNotEmpty(request.getProjectTypeList())) {
            queryMap.put("projectTypeList", request.getProjectTypeList());
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
    public BaseResponse importProvPilotProject(ProvPilotProjectImportRequest request, LogUserInfoVo user) {
        ProvPilotProjectImportResult result = new ProvPilotProjectImportResult();
        if (request == null || request.getRows() == null || request.getRows().isEmpty()) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Date importTime = new Date();
        // 文件 id：由 file 服务上传 Excel 源文件后回传（SYS_FileAnnex.fileId），整批共用
        String batchFileId = request.getFileId();

        // 字典：项目类型、所在区 名称(去空格) -> 字典项
        Map<String, SysDictionary> projectTypeMap = buildNameMap(DICT_PROJECT_TYPE);
        Map<String, SysDictionary> districtMap = buildNameMap(DICT_DISTRICT);

        // 历史同名集合（一次性加载，用于去重）
        Set<String> existingNames = new HashSet<>(list(new LambdaQueryWrapper<ProvPilotProject>()
                .select(ProvPilotProject::getProjectName)).stream()
                .map(ProvPilotProject::getProjectName)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet()));
        // 本批同名去重（落库发生在循环后，用内存 Set 互查）
        Set<String> batchNames = new HashSet<>();
        // 本批编码续号缓存：编码前缀(年度+批次) -> 已用到的最大序号（避免同批内重号）
        Map<String, Integer> codeSeqCache = new HashMap<>();

        List<ProvPilotProject> toInsert = new ArrayList<>();
        for (ProvPilotProjectImportRow row : request.getRows()) {
            List<ProvPilotProjectImportError> errors = validateRow(row, projectTypeMap, districtMap);
            if (!errors.isEmpty()) {
                result.setFailCount(result.getFailCount() + 1);
                result.getErrors().addAll(errors);
                continue;
            }
            // 历史同名或同批同名，跳过
            if (existingNames.contains(row.getColProjectName()) || !batchNames.add(row.getColProjectName())) {
                result.setSkipCount(result.getSkipCount() + 1);
                continue;
            }
            ProvPilotProject entity = new ProvPilotProject();
            entity.setId(IdWorker.getGenerateID());
            entity.setAnnualYear(parseYear(row.getColAnnualYear()));
            entity.setBatch(normalize(row.getColBatch()));
            entity.setProjectName(normalize(row.getColProjectName()));
            entity.setProjectType(projectTypeMap.get(normalize(row.getColProjectType())).getdCode());
            entity.setDistrict(districtMap.get(normalize(row.getColDistrict())).getdCode());
            entity.setDeclareUnit(normalize(row.getColDeclareUnit()));
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(importTime);
            entity.setState(0);
            entity.setStateTime(importTime);
            entity.setTeId(user.getTeId());
            entity.setFileId(batchFileId);
            // 生成项目编号：SX-年度(4位)-批次(2位)-序号(3位)，同 年度+批次 续号
            entity.setProjectCode(generateProjectCode(entity.getAnnualYear(), entity.getBatch(), codeSeqCache));
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

    private ProvPilotProjectImportError newError(ProvPilotProjectImportRow row, String fieldKey, String message) {
        ProvPilotProjectImportError e = new ProvPilotProjectImportError();
        e.setRowNum(row.getExcelRowNum());
        e.setFieldKey(fieldKey);
        e.setMessage(message);
        return e;
    }

    /**
     * 逐行校验（返回该行的所有错误明细）
     */
    private List<ProvPilotProjectImportError> validateRow(ProvPilotProjectImportRow row,
                                                          Map<String, SysDictionary> projectTypeMap,
                                                          Map<String, SysDictionary> districtMap) {
        List<ProvPilotProjectImportError> errors = new ArrayList<>();
        String annualYear = normalize(row.getColAnnualYear());
        String batch = normalize(row.getColBatch());
        String projectName = normalize(row.getColProjectName());
        String projectType = normalize(row.getColProjectType());
        String district = normalize(row.getColDistrict());
        String declareUnit = normalize(row.getColDeclareUnit());

        if (ValidateUtil.isEmpty(annualYear)) {
            errors.add(newError(row, "annualYear", "年度不能为空"));
        } else if (parseYear(annualYear) == null) {
            errors.add(newError(row, "annualYear", "年度必须为数字"));
        }
        if (ValidateUtil.isEmpty(batch)) {
            errors.add(newError(row, "batch", "批次不能为空"));
        }
        if (ValidateUtil.isEmpty(projectName)) {
            errors.add(newError(row, "projectName", "项目名称不能为空"));
        }
        if (ValidateUtil.isEmpty(projectType)) {
            errors.add(newError(row, "projectType", "项目类型不能为空"));
        } else if (!projectTypeMap.containsKey(projectType)) {
            errors.add(newError(row, "projectType", "项目类型[" + projectType + "]不在字典中"));
        }
        if (ValidateUtil.isEmpty(district)) {
            errors.add(newError(row, "district", "所在区不能为空"));
        } else if (!districtMap.containsKey(district)) {
            errors.add(newError(row, "district", "所在区[" + district + "]不在字典中"));
        }
        if (ValidateUtil.isEmpty(declareUnit)) {
            errors.add(newError(row, "declareUnit", "申报单位不能为空"));
        }
        return errors;
    }

    /**
     * 去除首尾及中间空白（用于字典名称匹配，去掉 Excel 单元格多打的空格）
     */
    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\s+", "").trim();
    }

    /**
     * 字典：名称(去空格) -> SysDictionary，用于导入时按名称匹配字典值(D_Code)
     */
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
     * 生成项目编号：SX-年度(4位)-批次(2位)-序号(3位)，如 SX-2024-01-001。
     *
     * <p>序号按「年度+批次」续号：先查库中该前缀最大编码，再结合本批已用序号取下一个。
     * codeSeqCache 用于同批内存续号，避免同批内（尚未落库）重号。</p>
     *
     * @param annualYear   所属年度
     * @param batch        批次
     * @param codeSeqCache 批次前缀 -> 已用到的最大序号（本批内缓存）
     */
    private String generateProjectCode(Integer annualYear, String batch, Map<String, Integer> codeSeqCache) {
        String prefix = buildCodePrefix(annualYear, batch);
        Integer lastSeq = codeSeqCache.get(prefix);
        if (lastSeq == null) {
            lastSeq = 0;
            String maxCode = baseMapper.selectMaxProjectCode(prefix);
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

    /**
     * 编码前缀：SX + 年度(4位) + 批次(2位)，如 SX202401
     */
    private String buildCodePrefix(Integer annualYear, String batch) {
        String yearPart = annualYear == null ? "0000" : String.format("%04d", annualYear);
        return CODE_PREFIX + yearPart + formatBatch(batch);
    }

    /**
     * 批次格式化：纯数字补足 2 位（如 1 -> 01、12 -> 12），非数字原样
     */
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
    public List<ProvPilotProjectModel> getExportList(ProvPilotProjectModel request, String uid) {
        QueryMap<ProvPilotProjectModel> queryMap = buildQueryMap(request);
        // 导出不分页，取全量
        IPage<ProvPilotProjectModel> page = new Page<>(0, -1);
        return baseMapper.getList(queryMap.getQuery(), page);
    }
}