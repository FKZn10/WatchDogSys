package com.jpmsoft.projectProducer.mapper.actualMeasurement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionResponse;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistributionCheck;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author jjz
 */
public interface TaskDistributionCheckMapper extends BaseMapper<TaskDistributionCheck> {

    List<TaskDistributionCheckModel> getTaskDistributionCheckByTdId(@Param("tdId") String tdId);

    List<TaskDistributionCheckModel> getTaskDistributionCheckList(@Param("page") IPage<TaskDistributionCheckModel> page,
                                                            @Param("params") Map<String, Object> params);

    List<TaskDistributionResponse> getTaskDistributionCheckByDmId(@Param("dmId") String dmId);

    TaskDistributionResponse getTaskDistributionCheckByCreateTimeNew(@Param("params") Map<String, Object> query);

    @Select("SELECT MAX(order_no) FROM task_distribution_check WHERE td_id = #{tdId} FOR UPDATE")
    Integer selectMaxOrderNoForUpdate(@Param("tdId") String tdId);

    // 普通查询最大排序号（兼容 DM）
    @Select("SELECT COALESCE(MAX(order_no), 0) FROM task_distribution_check WHERE td_id = #{tdId}")
    Integer selectMaxOrderNo(@Param("tdId") String tdId);

    @Update("UPDATE task_distribution_check SET td_id = td_id WHERE td_id = #{tdId}")
    void lockTdId(@Param("tdId") String tdId);



}
