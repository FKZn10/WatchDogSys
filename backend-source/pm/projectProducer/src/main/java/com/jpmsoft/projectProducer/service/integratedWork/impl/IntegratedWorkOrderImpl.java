package com.jpmsoft.projectProducer.service.integratedWork.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.integratedWork.IntegratedWorkOrderDTO;
import com.jpmsoft.common.model.requestAndResponse.integratedWork.IntegratedWorkOrderDtlDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.integratedWork.IntegratedWorkOrderDtlMapper;
import com.jpmsoft.projectProducer.mapper.integratedWork.IntegratedWorkOrderMapper;
import com.jpmsoft.projectProducer.pojo.entity.integratedWork.IntegratedWorkOrder;
import com.jpmsoft.projectProducer.service.integratedWork.IIntegratedWorkOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 三联单主表 服务实现类
 */
@Service
public class IntegratedWorkOrderImpl extends ServiceImpl<IntegratedWorkOrderMapper, IntegratedWorkOrder> implements IIntegratedWorkOrderService {
    @Resource
    private IntegratedWorkOrderDtlMapper integratedWorkOrderDtlMapper;

    /**
     * 获取上周工作周计划
     *
     * @param conConstructionId 施工合同ID
     * @param conSupervisionId  监理合同ID
     * @param weeklyStartDate   周计划开始日期
     * @param id                三联单主表ID
     * @return
     */
    @Override
    public BaseResponse getLastWeekOrders(String conConstructionId, String conSupervisionId, String weeklyStartDate, String id) {
        if (ValidateUtil.isEmpty(conConstructionId) || ValidateUtil.isEmpty(conSupervisionId)) {
            return BaseResponse.ok();
        }
        List<IntegratedWorkOrderDtlDTO> list = integratedWorkOrderDtlMapper.getLastWeekOrders(conConstructionId, conSupervisionId, weeklyStartDate, id);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok();
        }
        IntegratedWorkOrder order = this.getById(list.get(0).getIwoId());
        IntegratedWorkOrderDTO orderDTO = new IntegratedWorkOrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setDataList(list);
        return BaseResponse.ok(orderDTO);
    }
}
