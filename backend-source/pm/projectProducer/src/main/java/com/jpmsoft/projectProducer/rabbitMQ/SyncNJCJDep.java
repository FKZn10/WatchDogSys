package com.jpmsoft.projectProducer.rabbitMQ;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysPositionUserPService;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 监听队列 queueSyncNJCJDep
 * created by:aaron
 * date: 2021-06-03 23:50
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "queueSyncNJCJDep")})
public class SyncNJCJDep {

    @Resource
    private ISysPositionUserPService sysPositionUserPService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IPpOwnerUnitService ppOwnerUnitService;

    @RabbitHandler
    public void receiverInterfaceLog(String msg) {
        DepartmentPCjConfDTO conf = JacksonUtils.parseObject(msg, DepartmentPCjConfDTO.class);
        PreProject project = preProjectService.getById(conf.getProjectId());
        List<PpOwnerUnit> ownerUnits = null;
        if ("007".equals(project.getpBuildModel())) {
            LambdaQueryWrapper<PpOwnerUnit> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PpOwnerUnit::getPId, project.getpId());
            queryWrapper.orderByAsc(PpOwnerUnit::getOrderNumber);
            queryWrapper.last("limit 2");
            ownerUnits = ppOwnerUnitService.list(queryWrapper);
            ownerUnits = ownerUnits.stream().sorted(Comparator.comparingInt(PpOwnerUnit::getOrderNumber)).collect(Collectors.toList());
        }
        log.info("将项目信息上的用户信息绑定到对应部门对应岗位[syncProjectPositionPUser]");
        //region 将项目信息上的用户信息绑定到对应部门对应岗位
        sysPositionUserPService.syncProjectPositionPUser(project, ownerUnits, conf);
        //endregion
    }
}
