package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.FlowActivity;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskProcessinMapper extends BaseMapper<TaskProcessin> {
    FlowActivity getCurrentNode(@Param("flowId") String flowId);

    List<TaskProcessin> getTaskProcessListByEntityId(@Param("entityId") String entityId);
}
