package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.HolidayVacationsMapper;
import com.jpmsoft.userProducer.pojo.entity.HolidayVacations;
import com.jpmsoft.userProducer.service.IHolidayVacationsService;
import com.jpmsoft.userProducer.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayVacationsImpl extends ServiceImpl<HolidayVacationsMapper, HolidayVacations> implements IHolidayVacationsService {


    @Override
    public BaseResponse createHolidayVacations(List<HolidayVacations> model) {
        if (model.size() > 0) {
            Integer Year = model.get(0).getHvYear();
            QueryWrapper<HolidayVacations> HolidayVacationsWrapper = new QueryWrapper<>();
            HolidayVacationsWrapper.eq("Hv_Year", Year);
            List<HolidayVacations> count = baseMapper.selectList(HolidayVacationsWrapper);
            if (count.size() == 0) {
                model.forEach(n -> {
                    baseMapper.insert(n);
                });
            } else {
                //当前年度已存在信息，则开始更细信息
                count.forEach(n -> {
                    String nowDay = DateUtil.Date2String(n.getHvDay(), "YYYY-MM-DD");
                    List<HolidayVacations> newList = model.stream().filter(k -> k.getHvDay() != null && DateUtil.Date2String(k.getHvDay(), "YYYY-MM-DD").equals(nowDay)).collect(Collectors.toList());
                    if (newList.size() > 0) {
                        HolidayVacations obj = newList.get(0);
                        if (obj.getHvWeekday() != null) {
                            n.setHvWeekday(obj.getHvWeekday());
                        }
                        if (obj.getHvWeek() != null) {
                            n.setHvWeek(obj.getHvWeek());
                        }
                        if (obj.getHvDay() != null) {
                            n.setHvDay(obj.getHvDay());
                        }
                        if (obj.getHvCompensatory() != null) {
                            n.setHvCompensatory(obj.getHvCompensatory());
                        }
                        if (obj.getHvDays() != null) {
                            n.setHvDays(obj.getHvDays());
                        }
                        if (obj.getHvFestivalday() != null) {
                            n.setHvFestivalday(obj.getHvFestivalday());
                        }
                        if (obj.getHvHolidayvacations() != null) {
                            n.setHvHolidayvacations(obj.getHvHolidayvacations());
                        }
                        if (obj.getHvLegalholiday() != null) {
                            n.setHvLegalholiday(obj.getHvLegalholiday());
                        }
                        if (obj.getHvMonth() != null) {
                            n.setHvMonth(obj.getHvMonth());
                        }
                        baseMapper.updateById(n);
                    }
                });
            }
        }
        return new BaseResponse(true, "同步成功！");
    }


    @Override
    public BaseResponse createHolidayVacationsOne(HolidayVacations request) {
        QueryWrapper<HolidayVacations> HolidayVacationsWrapper = new QueryWrapper<>();
        HolidayVacationsWrapper.eq("Hv_Day", request.getHvDay());
        List<HolidayVacations> count = baseMapper.selectList(HolidayVacationsWrapper);
        if (count.size() == 0) {
            baseMapper.insert(request);
        } else {
            baseMapper.updateById(request);
        }
        return new BaseResponse(true, "同步成功！");
    }

    @Override
    public BaseResponse getWorkDayBetween(String a, String b) {
        if (a.equals(b)) return BaseResponse.ok(0);
        return BaseResponse.ok(baseMapper.getWorkDayBetween(a, b));
    }

    @Override
    public BaseResponse getBeforeWorkDay(Integer day) {
        QueryWrapper<HolidayVacations> wrapper = new QueryWrapper<>();
        wrapper.eq("Hv_Weekday", 1);
        wrapper.le("Hv_Day", DateUtil.Date2String(new Date(), "yyyy-MM-dd"));
        wrapper.orderByDesc("Hv_Day");
        wrapper.last(" limit " + day);
        List<HolidayVacations> data=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
        {
            return BaseResponse.ok(DateUtil.Date2String(data.get(data.size()-1).getHvDay(),"yyyy-MM-dd"));
        }
        return BaseResponse.fail(BaseMsg.cannotHoliday);
    }

    @Override
    public BaseResponse getHolidayVacations(HolidayVacations model) {
        QueryWrapper<HolidayVacations> HolidayVacationsWrapper = new QueryWrapper<>();
        HolidayVacationsWrapper.eq("Hv_Year", model.getHvYear());
        List<HolidayVacations> list = baseMapper.selectList(HolidayVacationsWrapper);
        return new BaseResponse(true, "获取数据成功", list);
    }


}
