package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.HolidayVacations;

import java.util.List;

public interface IHolidayVacationsService extends IService<HolidayVacations> {
    BaseResponse createHolidayVacations(List<HolidayVacations> model);

    BaseResponse getHolidayVacations(HolidayVacations model);

    BaseResponse createHolidayVacationsOne(HolidayVacations request);

    /**
     * 查询两个年月时间之间的工作日
     *
     * @param a
     * @param b
     * @return
     */
    BaseResponse getWorkDayBetween(String a, String b);

    /**
     * 获取*个工作日之前的日期
     *
     * @param day
     * @return
     */
    BaseResponse getBeforeWorkDay(Integer day);
}
