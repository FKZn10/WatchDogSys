package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface MemoirInfoMapper extends BaseMapper<MemoirInfo> {

    List<MemoirInfoDTO> getMemoirInfoList(@Param("page") IPage<MemoirInfoDTO> page, @Param("params") MemoirInfoDTO params, @Param("authSql") String authSql);

    MemoirInfoDTO getMemoirInfoById(@Param("id") String id);
}
