package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysVersion;
import org.apache.ibatis.annotations.Select;

import java.util.Date;


public interface SysVersionMapper extends BaseMapper<SysVersion> {

    @Select("select EXPIRED_DATE from v$license")
    Date getDatabaseExpiredDate();
}
