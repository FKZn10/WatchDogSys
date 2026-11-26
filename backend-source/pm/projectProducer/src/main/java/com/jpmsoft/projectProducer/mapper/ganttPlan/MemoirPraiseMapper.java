package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirPraiseDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirPraise;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface MemoirPraiseMapper extends BaseMapper<MemoirPraise> {

    @Select("select max(mp_sort) from memoir_praise where mi_id=#{miId}")
    Integer maxSort(@Param("miId") String miId);

    List<MemoirPraiseDTO> selectByMiIds(java.util.List<String> miIds);
}
