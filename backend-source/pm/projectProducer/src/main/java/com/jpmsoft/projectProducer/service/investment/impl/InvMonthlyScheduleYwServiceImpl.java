package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthlyScheduleRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.InvMonthlyScheduleYwMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlyScheduleYw;
import com.jpmsoft.projectProducer.service.investment.IInvMonthlyScheduleService;
import com.jpmsoft.projectProducer.service.investment.InvMonthlyScheduleYwService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvMonthlyScheduleYwServiceImpl  extends ServiceImpl<InvMonthlyScheduleYwMapper, InvMonthlyScheduleYw> implements InvMonthlyScheduleYwService {

    @Resource
    private IInvMonthlyScheduleService iInvMonthlyScheduleService;
    @Override
    public void createOrUpdateInvMonthlyScheduleYw(List<InvMonthlyScheduleRequest> datas, String ipvId) {
        deleteAllByIpvId(ipvId);
        if (datas != null && datas.size() > 0) {
            List<InvMonthlySchedule> models = new ArrayList<>();
            datas.forEach(data -> {
                InvMonthlySchedule model = new InvMonthlySchedule();
                BeanUtils.copyProperties(data, model);
                model.setImsId(GenerateUUID.getUUID());
                model.setIpvId(ipvId);
                model.setImsPlannedInv(MathUtil.DecimalMultiplicationInt(model.getImsPlannedInv(), 10000));
                model.setImsCumulativeInv(MathUtil.DecimalMultiplicationInt(model.getImsCumulativeInv(), 10000));
                model.setImsPlannedPay(MathUtil.DecimalMultiplicationInt(model.getImsPlannedPay(), 10000));
                model.setImsCumulativePay(MathUtil.DecimalMultiplicationInt(model.getImsCumulativePay(), 10000));
                models.add(model);
            });
            iInvMonthlyScheduleService.saveBatch(models);
        }

    }

    private void deleteAllByIpvId(String ipvId) {
        QueryWrapper<InvMonthlySchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("Ipv_Id", ipvId);
        iInvMonthlyScheduleService.getBaseMapper().delete(wrapper);
    }
}
