package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Oa.OaSupervisionTaskResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaSupervisionTaskMapper extends BaseMapper<OaSupervisionTask> {


    List<OaSupervisionTaskResponse> getOaSupervisionTaskList(@Param("params") Map<String, Object> params);

    Integer getOaSupervisionTaskListCount(@Param("params") Map<String, Object> params);

    OaSupervisionTaskResponse getOaSupervisionTaskByStId(@Param("stId") String stId);

    /**
     * 查询统计导出数据
     *
     * @param params
     * @return
     */
    List<OaSupervisionTaskResponse> exportOaSupervisionTaskStatistics(@Param("params") Map<String, Object> params);
}
