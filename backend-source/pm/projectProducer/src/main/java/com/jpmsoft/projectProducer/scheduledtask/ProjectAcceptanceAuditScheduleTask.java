package com.jpmsoft.projectProducer.scheduledtask;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.projectProducer.pojo.entity.yw.ProjectAcceptanceCalculationAudit;
import com.jpmsoft.projectProducer.service.yw.ProjectAcceptanceCalculationAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class ProjectAcceptanceAuditScheduleTask {
    @Resource
    private ProjectAcceptanceCalculationAuditService projectAcceptanceCalculationAuditService;
    @Value("${task.cron.enableProjectAcceptanceAuditCron:true}")
    private String enableCron;
    @Scheduled(cron = "${task.cron.projectAcceptanceAuditTask:-}")
    public void generateProjectAcceptanceCalculationAuditInfo() {
        log.info("自动生成项目验收及结算审计数据任务:{}",enableCron);
        if("true".equals(enableCron)){
            //判断当前时间是否为本月月底
            boolean isMonthEnd = isLastDayOfMonth();
            log.info("是否为当前月份最后一天:{}",isMonthEnd);
            if (isMonthEnd){
                //生成项目验收及结算审计数据
                QueryWrapper<ProjectAcceptanceCalculationAudit> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("state", ApprovalStatusEnum.Already_Over.getIndex());
                List<ProjectAcceptanceCalculationAudit> projectAcceptanceCalculationAuditList
                        = projectAcceptanceCalculationAuditService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(projectAcceptanceCalculationAuditList)){
                    log.info("项目验收及结算审计数据数量:{}",projectAcceptanceCalculationAuditList.size());
                    for (ProjectAcceptanceCalculationAudit entity:projectAcceptanceCalculationAuditList){
                        projectAcceptanceCalculationAuditService.generatePACA(entity);
                    }
                }
            }
        }
    }
    public boolean isLastDayOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        log.info("当前时间:{}",today);
        log.info("当前月份最后一天:{}",lastDayOfMonth);
        return today.equals(lastDayOfMonth);
    }
}