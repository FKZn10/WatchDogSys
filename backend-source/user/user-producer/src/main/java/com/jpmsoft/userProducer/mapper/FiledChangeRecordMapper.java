package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.FiledChangeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FiledChangeRecordMapper extends BaseMapper<FiledChangeRecord> {


    List<FiledChangeRecord> getFiledLastChangeByKey(@Param("fcrKey") String fcrKey);
}
