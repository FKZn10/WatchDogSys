package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.TaskPushCallRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskPushCallRecordMapper extends BaseMapper<TaskPushCallRecord> {

    List<TaskPushCallRecord> getTaskPushCallRecordList(@Param("params") Map<String, Object> params);
    Integer getTaskPushCallRecordCount(@Param("params") Map<String, Object> params);
}
