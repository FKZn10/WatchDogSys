package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttDataImportMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttMilestoneStatistics;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttDataImportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class GanttDataImportImpl extends ServiceImpl<GanttDataImportMapper, GanttMilestoneStatistics> implements GanttDataImportService {

    private final GanttDataImportMapper ganttDataImportMapper;

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse dataImport() {
        List<GanttMilestoneStatistics> GanttMilestoneStatisticsList = ganttDataImportMapper.dataImport();

        List<GanttMilestoneStatistics> newGanttMilestoneStatisticsList=GanttMilestoneStatisticsList.stream()
                .collect(Collectors.groupingBy(item -> Arrays.asList(
                        item.getPId(),
                        item.getSetTime(),
                        item.getStartTime(),
                        item.getFinishTime(),
                        item.getCompleteTime(),
                        item.getCompleteRecordsTime(),
                        item.getPRemoveTime(),
                        item.getAccountSubmitTime(),
                        item.getAccountReplyTime()
                )))
                .values().stream()
                .map(group -> {
                    GanttMilestoneStatistics merged = new GanttMilestoneStatistics();
                    BeanUtils.copyProperties(group.get(0), merged);

                    Arrays.asList("setTimeSchedule", "startTimeSchedule",  "finishTimeSchedule", "completeTimeSchedule",
                                    "completeRecordsTimeSchedule", "pRemoveTimeSchedule", "accountSubmitTimeSchedule", "accountReplyTimeSchedule",
                                    "setTimeFinish", "startTimeFinish", "finishTimeFinish", "completeTimeFinish",
                                    "completeRecordsTimeFinish", "pRemoveTimeFinish", "accountSubmitTimeFinish", "accountReplyTimeFinish")
                            .forEach(fieldName -> copyFirstNonNullField(group, merged, fieldName));

                    // 合并 gsId 和 gsName
                    String mergedGsId = group.stream()
                            .map(GanttMilestoneStatistics::getGsId)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.joining(","));
                    String mergedGsName = group.stream()
                            .map(GanttMilestoneStatistics::getGsName)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.joining(","));

                    merged.setGsId(mergedGsId);
                    merged.setGsName(mergedGsName);

                    merged.setId(GenerateUUID.getUUID());
                    return merged;
                })
                .collect(Collectors.toList());


        List<GanttMilestoneStatistics> projectList = ganttDataImportMapper.getProjectList();

        //获取里程碑通过的项目的PId
        Set<String> existingPIds = newGanttMilestoneStatisticsList.stream()
                .map(GanttMilestoneStatistics::getPId)
                .collect(Collectors.toSet());

        // 合并两个列表（过滤 + 追加）
        List<GanttMilestoneStatistics> mergedList = Stream.concat(
                newGanttMilestoneStatisticsList.stream(),
                projectList.stream()
                        .filter(proj -> !existingPIds.contains(proj.getPId()))
        ).collect(Collectors.toList());

        //获取gantt_milestone_statistics所有数据
        List<GanttMilestoneStatistics> oldGanttMilestoneStatisticsList = ganttDataImportMapper.List();

        // 创建旧数据组合键集合（pId+gsId）
        Set<String> existingKeys = oldGanttMilestoneStatisticsList.stream()
                .map(item -> item.getPId() + "|" + item.getGsId())
                .collect(Collectors.toSet());

        // 创建ID映射关系（用于更新时继承原ID）
        Map<String, String> keyToIdMap = oldGanttMilestoneStatisticsList.stream()
                .collect(Collectors.toMap(
                        item -> item.getPId() + "|" + item.getGsId(),
                        GanttMilestoneStatistics::getId,
                        (oldId, newId) -> oldId // 处理重复键的情况，保留首次出现的ID
                ));

        // 分割需要插入/更新的数据
        Map<Boolean, List<GanttMilestoneStatistics>> partitioned = mergedList.stream()
                .peek(item -> {
                    String compositeKey = item.getPId() + "|" + item.getGsId();
                    // 继承数据库原ID（如果存在）
                    if (keyToIdMap.containsKey(compositeKey)) {
                        item.setId(keyToIdMap.get(compositeKey));
                    }
                })
                .collect(Collectors.partitioningBy(
                        item -> !existingKeys.contains(item.getPId() + "|" + item.getGsId())
                ));

        List<GanttMilestoneStatistics> toInsert = partitioned.get(true);
        List<GanttMilestoneStatistics> toUpdate = partitioned.get(false);

        toInsert.forEach(item -> item.setId(GenerateUUID.getUUID()));

        toInsert.addAll(toUpdate);
        setPProjectStage(toInsert);

        // 调试：检查重复ID
        log.debug("===== 开始检查重复ID =====");
        Set<String> idSet = new HashSet<>();
        List<GanttMilestoneStatistics> duplicateItems = toInsert.stream()
                .filter(item -> !idSet.add(item.getId()))
                .collect(Collectors.toList());

        if (!duplicateItems.isEmpty()) {
            duplicateItems.forEach(item ->
                    log.error("发现重复ID: {} | P_ID={} | GS_ID={}",
                            item.getId(), item.getPId(), item.getGsId()));

            // 生成更详细的重复报告
            Map<String, Long> idCountMap = toInsert.stream()
                    .collect(Collectors.groupingBy(
                            GanttMilestoneStatistics::getId,
                            Collectors.counting()
                    ));

            idCountMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .forEach(entry -> {
                        log.error("ID {} 重复 {} 次，涉及数据：",
                                entry.getKey(), entry.getValue());
                        toInsert.stream()
                                .filter(item -> item.getId().equals(entry.getKey()))
                                .forEach(item -> log.error(" - P_ID: {}, GS_ID: {}",
                                        item.getPId(), item.getGsId()));
                    });

            throw new RuntimeException("存在重复ID，终止操作");
        }
        log.debug("===== 重复ID检查完成 =====");


        ganttDataImportMapper.delete(null);
        ganttDataImportMapper.batchInsert(toInsert);

        return BaseResponse.ok();
    }

    public void setPProjectStage(List<GanttMilestoneStatistics> data) {
//        List<GanttPlanTimes> projectTimes = ganttDataImportMapper.getProjectTimes();
//
//        Map<String, GanttPlanTimes> timeMap = projectTimes.stream()
//                .collect(Collectors.toMap(
//                        item -> item.getPlanId() + "|" + item.getGsId(),
//                        item -> item,
//                        (oldItem, newItem) -> oldItem
//                ));

        data.forEach(item -> {
            if (item.getPlanId() == null) {
                item.setPProjectStage("01");
            } else {
//                String compositeKey = item.getPlanId() + "|" + (item.getGsId()==null?"":item.getGsId());
//                GanttPlanTimes timeData = timeMap.get(compositeKey.split(",")[0]);
//                if (item != null) {
                    if(item.getStartTimeFinish()==null)
                        item.setPProjectStage("02");
                    else if(item.getFinishTimeFinish()==null)
                        item.setPProjectStage("03");
                    else if (item.getCompleteTimeFinish()==null || item.getCompleteRecordsTimeFinish()==null || item.getPRemoveTimeFinish()==null ||
                            item.getAccountSubmitTimeFinish()==null || item.getAccountReplyTimeFinish()==null)
                        item.setPProjectStage("04");
                    else item.setPProjectStage("05");
//                }else item.setPProjectStage("02");
            }
        });

        data.forEach(item -> {
//            String compositeKey = item.getPlanId() + "|" + item.getGsId();
//            GanttPlanTimes timeData = timeMap.get(compositeKey.split(",")[0]);
//            if(timeData != null){
                if(item.getPlanId()!=null){
                    if(!ValidateUtil.isEmpty(item.getSetTimeHandle())&&item.getSetTimeHandle()){
                          item.setSetTimeStatus("01");
                    }else item.setSetTimeStatus(timeCheck(item.getSetTimeSchedule(), item.getSetTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getStartTimeHandle())&&item.getStartTimeHandle()){
                        item.setStartTimeStatus("01");
                    }else item.setStartTimeStatus(timeCheck(item.getStartTimeSchedule(), item.getStartTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getFinishTimeHandle())&&item.getFinishTimeHandle()){
                        item.setFinishTimeStatus("01");
                    }else item.setFinishTimeStatus(timeCheck(item.getFinishTimeSchedule(), item.getFinishTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getCompleteTimeHandle())&&item.getCompleteTimeHandle()){
                        item.setCompleteTimeStatus("01");
                    }else item.setCompleteTimeStatus(timeCheck(item.getCompleteTimeSchedule(), item.getCompleteTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getCompleteRecordsTimeHandle())&&item.getCompleteRecordsTimeHandle()){
                        item.setCompleteRecordsTimeStatus("01");
                    }else item.setCompleteRecordsTimeStatus(timeCheck(item.getCompleteRecordsTimeSchedule(), item.getCompleteRecordsTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getPRemoveTimeHandle())&&item.getPRemoveTimeHandle()){
                        item.setPRemoveTimeStatus("01");
                    }else item.setPRemoveTimeStatus(timeCheck(item.getPRemoveTimeSchedule(), item.getPRemoveTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getAccountSubmitTimeHandle())&&item.getAccountSubmitTimeHandle()){
                        item.setAccountSubmitTimeStatus("01");
                    }else item.setAccountSubmitTimeStatus(timeCheck(item.getAccountSubmitTimeSchedule(), item.getAccountSubmitTimeFinish()));

                    if(!ValidateUtil.isEmpty(item.getAccountReplyTimeHandle())&&item.getAccountReplyTimeHandle()){
                        item.setAccountReplyTimeStatus("01");
                    }else item.setAccountReplyTimeStatus(timeCheck(item.getAccountReplyTimeSchedule(), item.getAccountReplyTimeFinish()));
                }
//            }
        });
    }

    public String timeCheck(Date schedule, Date finish) {
        LocalDate now = LocalDate.now();
        LocalDate scheduleDate = schedule != null ?
                schedule.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate finishDate = finish != null ?
                finish.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        if (finishDate == null) {
            // 未完成状态
            if (scheduleDate == null) {
                return "01"; // 无需办理
            } else if (now.isAfter(scheduleDate)) {
                return "03"; // 逾期未完成
            } else {
                return "02"; // 未开始
            }
        } else {
            // 已完成状态
            if (scheduleDate == null) {
                return "01"; // 无计划时间按完成处理
            } else if (finishDate.isAfter(scheduleDate)) {
                return "05"; // 逾期完成
            } else {
                return "04"; // 按期完成
            }
        }
    }

    public static void copyFirstNonNullField(List<GanttMilestoneStatistics> group,
                                             GanttMilestoneStatistics merged,
                                             String fieldName) {
        try {
            Field targetField = GanttMilestoneStatistics.class.getDeclaredField(fieldName);
            targetField.setAccessible(true); // 允许访问私有字段

            // 2. 遍历组内对象寻找第一个非空值
            Object firstNonNullValue = group.stream()
                    .map(item -> {
                        try {
                            return targetField.get(item);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("字段访问失败: " + fieldName, e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            // 3. 设置到合并对象
            if (firstNonNullValue != null) {
                targetField.set(merged, firstNonNullValue);
            }
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("无效字段: " + fieldName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("字段赋值失败: " + fieldName, e);
        }
    }
}
