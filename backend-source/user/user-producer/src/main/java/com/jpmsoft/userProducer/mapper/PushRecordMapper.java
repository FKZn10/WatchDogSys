package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.PushData.PushRecordModel;
import com.jpmsoft.userProducer.pojo.entity.PushRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PushRecordMapper extends BaseMapper<PushRecord> {

    List<PushRecordModel> getPushRecordList(@Param("params") Map<String, Object> params);

    Integer getPushRecordListCount(@Param("params") Map<String, Object> params);
}
