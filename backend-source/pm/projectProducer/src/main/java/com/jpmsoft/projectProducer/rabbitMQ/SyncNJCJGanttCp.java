package com.jpmsoft.projectProducer.rabbitMQ;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听队列 queueSyncNJCJDep
 * created by:aaron
 * date: 2021-06-03 23:50
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "queueSyncPlan2Cp")})
public class SyncNJCJGanttCp {

    @Resource
    private IGanttPlanService ganttPlanService;

    @RabbitHandler
    public void receiverInterfaceLog(String msg) {
        log.info("进度计划变更通过，同步更新建设程序模板以及项目建设程序");
        //region 将项目信息上的用户信息绑定到对应部门对应岗位
        BaseResponse res = ganttPlanService.refreshProjectCplTempAntProjectCp(msg);
        log.info("响应结果:" + JacksonUtils.toJSONString(res));
        //endregion
    }
}
