package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.HolidayVacations;
import org.apache.ibatis.annotations.Param;

public interface HolidayVacationsMapper extends BaseMapper<HolidayVacations> {

    Integer getWorkDayBetween(@Param("a") String a, @Param("b") String b);
}
