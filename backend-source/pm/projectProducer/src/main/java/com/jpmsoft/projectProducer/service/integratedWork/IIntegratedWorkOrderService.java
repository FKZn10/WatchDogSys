package com.jpmsoft.projectProducer.service.integratedWork;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.integratedWork.IntegratedWorkOrder;

/**
 * 三联单主表 服务类
 */
public interface IIntegratedWorkOrderService extends IService<IntegratedWorkOrder> {
    /**
     * 获取上周工作周计划
     *
     * @param conConstructionId 施工合同ID
     * @param conSupervisionId  监理合同ID
     * @param weeklyStartDate   周计划开始日期
     * @param id                三联单主表ID
     * @return
     */
    BaseResponse getLastWeekOrders(String conConstructionId, String conSupervisionId, String weeklyStartDate, String id);
}
