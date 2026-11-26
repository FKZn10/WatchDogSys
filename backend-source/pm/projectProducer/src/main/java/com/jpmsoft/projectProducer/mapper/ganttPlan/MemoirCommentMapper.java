package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface MemoirCommentMapper extends BaseMapper<MemoirComment> {


    @Select("select max(mc_sort) from memoir_comment where mi_id=#{miId}")
    Integer maxSort(@Param("miId") String miId);

    List<MemoirCommentDTO> selectByMiIds(@Param("miIds") List<String> miIds);
}
