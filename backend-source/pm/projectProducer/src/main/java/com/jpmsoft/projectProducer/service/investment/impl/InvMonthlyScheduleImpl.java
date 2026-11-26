package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.response.InvMonthlyScheduleModel;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthlyScheduleRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvMonthlyScheduleMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import com.jpmsoft.projectProducer.service.investment.IInvMonthlyScheduleService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvMonthlyScheduleImpl extends ServiceImpl<InvMonthlyScheduleMapper, InvMonthlySchedule> implements IInvMonthlyScheduleService {
    @Override
    public void createOrUpdateInvMonthlySchedule(List<InvMonthlyScheduleRequest> datas, String ipvId) {
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
            saveBatch(models);
        }
    }

    @Override
    public List<InvMonthlySchedule> getInvMonthlyScheduleByIpvId(String ipvId) {
        QueryWrapper<InvMonthlySchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("Ipv_Id", ipvId);
        wrapper.orderByAsc("Ims_Month");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<InvMonthlyScheduleResponse> getInvMonthlyScheduleByIpvIds(List<String> ipvIds) {
        return baseMapper.getInvMonthlyScheduleByIpvIds(ipvIds);
    }

    @Override
    public List<InvMonthlyScheduleModel> getInvMonthlyScheduleByPidsAndYearMon(List<String> pIds, Integer year, Integer month) {
        return baseMapper.getInvMonthlyScheduleByPidsAndYearMon(pIds, year, month);
    }

    @Override
    public List<InvMonthlySchedule> getInvMonthlyScheduleByIpvIdList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) return null;
        QueryWrapper<InvMonthlySchedule> wrapper = new QueryWrapper<>();
        wrapper.in("Ipv_Id",list);
        wrapper.orderByAsc("Ims_Month");
        return baseMapper.selectList(wrapper);

    }

    private void deleteAllByIpvId(String ipvId) {
        QueryWrapper<InvMonthlySchedule> wrapper = new QueryWrapper<>();
        wrapper.eq("Ipv_Id", ipvId);
        baseMapper.delete(wrapper);
    }

    /**
     * 获取月度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getInvMonthlyScheduleStatisticsOverviewList(InvPlanYearRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<InvPlanYearResponse> page = new Page<>(request.getPage(), request.getSize());
        request.setTeId(teId);
        List<InvPlanYearResponse> list = baseMapper.getInvMonthlyScheduleStatisticsOverviewList(page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }
}
