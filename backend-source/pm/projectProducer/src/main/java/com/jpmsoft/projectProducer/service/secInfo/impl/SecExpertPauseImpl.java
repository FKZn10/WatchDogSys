package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.TaskMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertPauseMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertPauseDTO;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.Task;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertPause;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertPauseVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertPauseService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:08
 */
@Slf4j
@Service
public class SecExpertPauseImpl extends FileImpl<SecExpertPauseMapper, SecExpertPause, SecExpertPauseDTO> implements ISecExpertPauseService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ISysFileService sysFileService;

    @Override
    public BaseResponse createOrUpdateSecExpertPause(SecExpertPauseDTO request) {
        if (ValidateUtil.isNull(request.getSepTime())) request.setSepTime(new Date());
        SecExpertPause entity = new SecExpertPause();
        request.setSepDisabledYear("1");
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertPauseList(SecExpertPauseDTO request, LogUserInfoVo user) {
        String authSql = sysUserService.onlyShowCancel(user.getId(), "Create_Uid", "State");
        QueryMap<SecExpertPauseDTO> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", authSql).put("exName").put("state").put("createUname").put("exApplicantId")
                .put("year").put("years").put("exNames").put("states").put("sepDaysYear")
                .put("exId")
                .put(request.getSepDaysYears(), "sepDaysYearStart", "sepDaysYearEnd")
                .put(request.getSepStartTimes(), "sepStartTimeStart", "sepStartTimeEnd")
                .put(request.getSepEndTimes(), "sepEndTimeStart", "sepEndTimeEnd")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        IPage<SecExpertPauseVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertPauseVO> data = baseMapper.getSecExpertPauseList(page, queryMap.getQuery());
        data.forEach(d -> {
            d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
        });
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    @Override
    public BaseResponse getSecExpertPauseListClass(SecExpertPauseDTO request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String authSql = sysUserService.onlyShowCancel(user.getId(), "Create_Uid", "State");
        QueryMap<SecExpertPauseDTO> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", authSql).put("exName").put("state").put("createUname").put("exApplicantId")
                .put("year").put("years").put("exNames").put("states")
                .put(request.getSepStartTimes(), "sepStartTimeStart", "sepStartTimeEnd")
                .put(request.getSepEndTimes(), "sepEndTimeStart", "sepEndTimeEnd")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecWinNoticeRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, request);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getWinNoticeListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecExpertPauseById(String id) {
        SecExpertPauseVO data = baseMapper.getSecExpertPauseById(id);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNotEmpty(data.getExIdNumber())) {
            data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approveSecExpertPauseById(String id) {
        SecExpertPause entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        // 获取配置文件
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        Integer outTime = ValidateUtil.isNotNull(conf.getPauseYearSum()) ? conf.getPauseYearSum() : 180;
        if (baseMapper.getPauseDays(entity.getExId(), DateUtil.Date2String(entity.getSepTime(), "yyyy")) > outTime) {
            secExpertService.updateSecExpertStatus(entity.getExId(), 0);
        } else {
            if (Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
                secExpertService.updateSecExpertStatus(entity.getExId(), 3);
        }
        return BaseResponse.ok();
    }

    /**
     * 定时任务审批通过暂停专家
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approveSecExpertPause() {
        // 0：禁用，1：启用，2：停用中
        List<SecExpertPause> list = this.list(Wrappers.<SecExpertPause>lambdaQuery()
                .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .in(SecExpertPause::getSepDisabledYear, "1", "2")
        );
        if (ValidateUtil.isNotEmpty(list)) {
            // 参考文件
            SysFile file = sysFileService.getById(list.get(0).getId());

            // 根据 ExId 分组，统计每个专家的请假天数
            Map<String, Integer> pauseDaysMap = new HashMap<>();
            for (SecExpertPause pause : list) {
                String exId = pause.getExId();
                Integer sepDays = pause.getSepDays() != null ? pause.getSepDays() : 0;
                pauseDaysMap.put(exId, pauseDaysMap.getOrDefault(exId, 0) + sepDays);
            }

            // 根据 exId 分组
            Map<String, List<SecExpertPause>> pauseMap = list.stream()
                    .filter(p -> ValidateUtil.isNotEmpty(p.getExId()))
                    .collect(Collectors.groupingBy(SecExpertPause::getExId));

            // 获取配置
            SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
            Integer outTime = ValidateUtil.isNotNull(conf.getPauseYearSum()) ? conf.getPauseYearSum() : 180;

            Date now = new Date();

            // 最终状态分组
            List<String> disableExIds = new ArrayList<>();
            List<String> disableStartExIds = new ArrayList<>();
            List<String> enableExIds = new ArrayList<>();
            List<SecExpertPause> addPauses = new ArrayList<>();

            for (Map.Entry<String, List<SecExpertPause>> entry : pauseMap.entrySet()) {
                String exId = entry.getKey();
                List<SecExpertPause> pauses = entry.getValue();
                if (ValidateUtil.isEmpty(pauses)) {
                    continue;
                }

                Integer totalSepDays = pauseDaysMap.getOrDefault(exId, 0);

                List<SecExpertPause> validPauses = pauses.stream()
                        .filter(p -> p.getSepStartTime() != null
                                && p.getSepEndTime() != null
                                && !p.getSepStartTime().after(p.getSepEndTime()))
                        .sorted(Comparator.comparing(SecExpertPause::getSepStartTime))
                        .collect(Collectors.toList());

                if (ValidateUtil.isEmpty(validPauses)) {
                    enableExIds.add(exId);
                    continue;
                }

                // 合并区间，同时判断当前是否在请假区间内，并拿到最后一个合并区间的结束时间
                Date mergedStart = validPauses.get(0).getSepStartTime();
                Date mergedEnd = validPauses.get(0).getSepEndTime();
                boolean inPause = false;

                for (int i = 1; i < validPauses.size(); i++) {
                    SecExpertPause next = validPauses.get(i);
                    Date nextStart = next.getSepStartTime();
                    Date nextEnd = next.getSepEndTime();

                    // 时间段重叠或相连，进行合并
                    if (!nextStart.after(mergedEnd)) {
                        if (nextEnd.after(mergedEnd)) {
                            mergedEnd = nextEnd;
                        }
                    } else {
                        // 判断当前时间是否在上一个合并后的区间内
                        if (!now.before(mergedStart) && !now.after(mergedEnd)) {
                            inPause = true;
                        }
                        // 进入下一个区间
                        mergedStart = nextStart;
                        mergedEnd = nextEnd;
                    }
                }

                // 最后一个合并区间也要判断
                if (!now.before(mergedStart) && !now.after(mergedEnd)) {
                    inPause = true;
                }

                // 最后一个结束时间后一天，才允许从停用中切到启用/禁用
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mergedEnd);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDayAfterEnd = calendar.getTime();

                if (inPause) {
                    // 当前仍在请假区间内，保持停用中
                    disableStartExIds.add(exId);
                } else if (!now.before(nextDayAfterEnd)) {
                    // 已经过了最后结束时间后一天，再根据累计天数决定启用还是禁用
                    if (totalSepDays >= outTime) {
                        disableExIds.add(exId);

                        SecExpertPause pause = new SecExpertPause();
                        pause.setId(GenerateUUID.getUUID());
                        pause.setExId(exId);
                        pause.setSepDisabledYear("0");
                        pause.setSepStartTime(nextDayAfterEnd);
                        pause.setSepDaysYear(totalSepDays);
                        pause.setCreateTime(new Date());
                        pause.setState(ApprovalStatusEnum.Already_Over.getIndex());
                        pause.setCreateUid(validPauses.get(0).getCreateUid());
                        pause.setCreateUname(validPauses.get(0).getCreateUname());

                        if (ValidateUtil.isNotNull(file)) {
                            sysFileService.saveFile(
                                    pause.getId(),
                                    file.gettId(),
                                    pause.getState() + "",
                                    pause.getCreateUid(),
                                    pause.getCreateUname(),
                                    pause.getCreateTime(),
                                    null,
                                    file.getFiletitle(),
                                    file.getFilecode(),
                                    "create",
                                    GenerateUUID.getUUID(),
                                    null,
                                    file.gettCode(),
                                    null
                            );
                        }

                        addPauses.add(pause);
                    } else {
                        enableExIds.add(exId);
                    }
                } else {
                    // 不在请假区间内，但还没到结束后一天，仍然保持停用中
                    disableStartExIds.add(exId);
                }
            }

            // 更新专家表：禁用
            if (ValidateUtil.isNotEmpty(disableExIds)) {
                secExpertService.update(Wrappers.<SecExpert>lambdaUpdate()
                        .set(SecExpert::getExStatus, 0)
                        .in(SecExpert::getExId, disableExIds));

                // 请假表同步为禁用
                this.update(Wrappers.<SecExpertPause>lambdaUpdate()
                        .set(SecExpertPause::getSepDisabledYear, "0")
                        .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .in(SecExpertPause::getExId, disableExIds));

                if (ValidateUtil.isNotEmpty(addPauses)) {
                    this.saveBatch(addPauses);
                }
            }

            // 更新专家表：停用中
            if (ValidateUtil.isNotEmpty(disableStartExIds)) {
                secExpertService.update(Wrappers.<SecExpert>lambdaUpdate()
                        .set(SecExpert::getExStatus, 2)
                        .in(SecExpert::getExId, disableStartExIds));

                // 请假表同步为停用中
                this.update(Wrappers.<SecExpertPause>lambdaUpdate()
                        .set(SecExpertPause::getSepDisabledYear, "2")
                        .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .in(SecExpertPause::getExId, disableStartExIds));
            }

            // 更新专家表：启用
            if (ValidateUtil.isNotEmpty(enableExIds)) {
                secExpertService.update(Wrappers.<SecExpert>lambdaUpdate()
                        .set(SecExpert::getExStatus, 1)
                        .in(SecExpert::getExId, enableExIds));

                // 请假表同步为启用
                this.update(Wrappers.<SecExpertPause>lambdaUpdate()
                        .set(SecExpertPause::getSepDisabledYear, "1")
                        .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .in(SecExpertPause::getExId, enableExIds));
            }
        }

        // 重新计算年龄
        List<SecExpert> experts = secExpertService.list(Wrappers.<SecExpert>lambdaQuery()
                .eq(SecExpert::getExStatus, 1)
                .eq(SecExpert::getExState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        experts.forEach(expert -> {
            Integer age = expert.getExAge();
            try {
                String idNumber = apiUtilsService.SelfSm2Decrypt(expert.getExIdNumber());
                age = IdcardUtil.getAgeByIdCard(idNumber);
            } catch (Exception e) {
                log.warn("专家{}（ID：{}）解密或年龄计算失败，尝试备用身份证号", expert.getExName(), expert.getExId(), e);
                try {
                    age = IdcardUtil.getAgeByIdCard(expert.getExPlaintextNum());
                } catch (Exception ex) {
                    log.error("专家{}（ID：{}）使用备用身份证号计算年龄失败，保留原年龄", expert.getExName(), expert.getExId(), ex);
                }
            }
            expert.setExAge(age);
        });
        if (ValidateUtil.isNotEmpty(experts)) {
            secExpertService.updateBatchById(experts);
        }
        return BaseResponse.ok();
    }

    @Override
    public Integer getSepDaysYear(String exId, Integer year) {
        if (ValidateUtil.isEmpty(exId) || ValidateUtil.isNull(year)) {
            return 0;
        }
        SecExpertPause pause = this.getOne(Wrappers.<SecExpertPause>lambdaQuery()
                .eq(SecExpertPause::getExId, exId)
                .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(BaseEntity::getCreateTime)
                .last("limit 1")
        );
        if (ValidateUtil.isNull(pause)) {
            return 0;
        }
        return (ValidateUtil.isNull(pause.getSepDays()) ? 0 : pause.getSepDays()) + (ValidateUtil.isNull(pause.getSepDaysYear()) ? 0 : pause.getSepDaysYear());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse invalidProcessData(List<String> exIds) {
        if (ValidateUtil.isEmpty(exIds)) {
            return BaseResponse.ok();
        }

        // 清洗输入
        List<String> cleanExIds = exIds.stream()
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(cleanExIds)) {
            return BaseResponse.ok();
        }

        List<String> invalidIds = this.list(Wrappers.<SecExpertPause>lambdaQuery()
                        .select(SecExpertPause::getId)
                        .in(SecExpertPause::getExId, cleanExIds)
                        .ne(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .ne(SecExpertPause::getState, ApprovalStatusEnum.Invalid.getIndex()))
                .stream()
                .map(SecExpertPause::getId)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());

        if (ValidateUtil.isEmpty(invalidIds)) {
            return BaseResponse.ok();
        }

        // 作废暂停记录
        this.update(Wrappers.<SecExpertPause>lambdaUpdate()
                .set(SecExpertPause::getState, ApprovalStatusEnum.Invalid.getIndex())
                .in(SecExpertPause::getId, invalidIds));
        // 作废附件流转状态
        sysFileService.update(Wrappers.<SysFile>lambdaUpdate()
                .set(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                .in(SysFile::getFileid, invalidIds));
        // 删除待办
        taskMapper.delete(Wrappers.<Task>lambdaQuery()
                .in(Task::getTaskentityid, invalidIds));
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse handleExpertPause(Map<String, Integer> exIdToDays) {
        if (ValidateUtil.isEmpty(exIdToDays)) {
            return BaseResponse.ok();
        }

        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        int outTime = 180;
        if (conf != null && ValidateUtil.isNotNull(conf.getPauseYearSum())) {
            outTime = conf.getPauseYearSum();
        }
        Date now = new Date();
        String currentYear = DateUtil.Date2String(now, "yyyy");

        List<String> exIds = exIdToDays.keySet().stream()
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(exIds)) {
            return BaseResponse.ok();
        }

        // 已审批通过的历史暂停（用于算最大结束时间 + 本年已用天数）
        List<SecExpertPause> existingPauses = this.list(Wrappers.<SecExpertPause>lambdaQuery()
                .in(SecExpertPause::getExId, exIds)
                .eq(SecExpertPause::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );

        // 每个专家历史最大结束时间
        Map<String, Date> exIdMaxEndTimeMap = existingPauses.stream()
                .filter(p -> ValidateUtil.isNotEmpty(p.getExId()) && p.getSepEndTime() != null)
                .collect(Collectors.toMap(
                        SecExpertPause::getExId,
                        SecExpertPause::getSepEndTime,
                        (d1, d2) -> d1.after(d2) ? d1 : d2
                ));

        // 本年已停用总时长（按 sepTime 年份统计，沿用你当前口径）
        Map<String, Integer> exIdUsedDaysMap = existingPauses.stream()
                .filter(p -> ValidateUtil.isNotEmpty(p.getExId()))
                .filter(p -> p.getSepTime() != null && currentYear.equals(DateUtil.Date2String(p.getSepTime(), "yyyy")))
                .collect(Collectors.groupingBy(
                        SecExpertPause::getExId,
                        Collectors.summingInt(p -> p.getSepDays() == null ? 0 : p.getSepDays())
                ));

        String config = sysConfigService.getConfigByCode("archiveUserInfo");
        if (ValidateUtil.isEmpty(config)) {
            return BaseResponse.fail("缺少配置项：archiveUserInfo");
        }
        String[] split = config.split(",");
        FileTemplateTree fileTemplate = baseMapper.getFileTemplateByFileCode("SecExpertRecord");

        // 年末时间（当年12/31 23:59:59）
        Calendar yearEndCal = Calendar.getInstance();
        yearEndCal.set(Calendar.YEAR, Integer.parseInt(currentYear));
        yearEndCal.set(Calendar.MONTH, Calendar.DECEMBER);
        yearEndCal.set(Calendar.DAY_OF_MONTH, 31);
        yearEndCal.set(Calendar.HOUR_OF_DAY, 23);
        yearEndCal.set(Calendar.MINUTE, 59);
        yearEndCal.set(Calendar.SECOND, 59);
        yearEndCal.set(Calendar.MILLISECOND, 999);
        Date yearEnd = yearEndCal.getTime();

        List<SecExpertPause> addList = new ArrayList<>();
        List<String> directDisableExIds = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : exIdToDays.entrySet()) {
            String exId = entry.getKey();
            Integer requestDays = entry.getValue();

            if (ValidateUtil.isEmpty(exId) || requestDays == null || requestDays <= 0) {
                continue;
            }

            // 规则1：传入即180天（或>=180）直接禁用，但也要创建停用记录
            if (requestDays >= outTime) {
                directDisableExIds.add(exId);

                // 开始时间：历史最大结束+1；无历史则当天
                Date startTime;
                Date maxEndTime = exIdMaxEndTimeMap.get(exId);
                if (maxEndTime != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(maxEndTime);
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    startTime = cal.getTime();
                } else {
                    startTime = now;
                }

                Calendar endCal = Calendar.getInstance();
                endCal.setTime(startTime);
                endCal.add(Calendar.DAY_OF_MONTH, outTime - 1);
                Date endTime = endCal.getTime();

                SecExpertPause pause = new SecExpertPause();
                pause.setId(GenerateUUID.getUUID());
                pause.setExId(exId);
                pause.setSepStartTime(startTime);
                pause.setSepEndTime(endTime);
                pause.setSepDays(outTime);
                pause.setSepDaysYear(outTime);
                pause.setSepTime(now);
                pause.setSepDisabledYear("0");
                pause.setState(ApprovalStatusEnum.Already_Over.getIndex());
                pause.setCreateTime(now);
                pause.setSepReason("专家在一标一评中扣分满足自动停用条件，系统自动生成停用记录");

                addList.add(pause);

                sysFileService.saveFile(
                        pause.getId(),
                        fileTemplate.gettId(),
                        pause.getState() + "",
                        split[0],
                        split[1],
                        pause.getCreateTime(),
                        null,
                        fileTemplate.gettName(),
                        fileTemplate.gettCode(),
                        "create",
                        GenerateUUID.getUUID(),
                        null,
                        fileTemplate.gettCode(),
                        null
                );

                continue;
            }

            // 普通停用：开始时间 = 历史最大结束+1；无历史则当天
            Date startTime;
            Date maxEndTime = exIdMaxEndTimeMap.get(exId);
            if (maxEndTime != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(maxEndTime);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                startTime = cal.getTime();
            } else {
                startTime = now;
            }

            // 若开始时间已跨到下一年，不再生成当年停用
            if (startTime.after(yearEnd)) {
                continue;
            }

            int usedDays = exIdUsedDaysMap.getOrDefault(exId, 0);
            int remainByYearLimit = outTime - usedDays;
            if (remainByYearLimit <= 0) {
                continue;
            }

            int actualDaysByLimit = Math.min(requestDays, remainByYearLimit);

            // 先按天数算结束
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(startTime);
            endCal.add(Calendar.DAY_OF_MONTH, actualDaysByLimit - 1);
            Date rawEnd = endCal.getTime();

            // 规则2：跨年只截到12/31
            Date finalEnd = rawEnd.after(yearEnd) ? yearEnd : rawEnd;

            // 重新计算实际天数（含首尾）
            long diffMs = finalEnd.getTime() - startTime.getTime();
            int actualDays = (int) (diffMs / (24L * 60 * 60 * 1000)) + 1;
            if (actualDays <= 0) {
                continue;
            }

            SecExpertPause pause = new SecExpertPause();
            pause.setId(GenerateUUID.getUUID());
            pause.setExId(exId);
            pause.setSepStartTime(startTime);
            pause.setSepEndTime(finalEnd);
            pause.setSepDays(actualDays);
            pause.setSepDaysYear(usedDays + actualDays);
            pause.setSepTime(now);
            pause.setSepDisabledYear("1");
            pause.setState(ApprovalStatusEnum.Already_Over.getIndex());
            pause.setCreateTime(now);
            pause.setSepReason("专家在一标一评中扣分满足自动停用条件，系统自动生成停用记录");

            addList.add(pause);

            sysFileService.saveFile(
                    pause.getId(),
                    fileTemplate.gettId(),
                    pause.getState() + "",
                    split[0],
                    split[1],
                    pause.getCreateTime(),
                    null,
                    fileTemplate.gettName(),
                    fileTemplate.gettCode(),
                    "create",
                    GenerateUUID.getUUID(),
                    null,
                    fileTemplate.gettCode(),
                    null
            );
        }

        // 先处理“直接禁用”
        if (ValidateUtil.isNotEmpty(directDisableExIds)) {
            List<String> disableIds = directDisableExIds.stream().distinct().collect(Collectors.toList());

            secExpertService.update(Wrappers.<SecExpert>lambdaUpdate()
                    .set(SecExpert::getExStatus, 0)
                    .in(SecExpert::getExId, disableIds));

            // 该专家所有停用申请 sepDisabledYear = 0
            this.update(Wrappers.<SecExpertPause>lambdaUpdate()
                    .set(SecExpertPause::getSepDisabledYear, "0")
                    .in(SecExpertPause::getExId, disableIds));
        }

        if (ValidateUtil.isNotEmpty(addList)) {
            this.saveBatch(addList);
        }

        return BaseResponse.ok();
    }
}
