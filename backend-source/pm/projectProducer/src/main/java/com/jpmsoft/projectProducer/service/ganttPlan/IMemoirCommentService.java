package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirImageDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirComment;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface IMemoirCommentService extends IService<MemoirComment> {

    BaseResponse memoirComment(MemoirCommentDTO data,String token);

    BaseResponse deleteMemoirComment(String id,String token);


    List<MemoirCommentDTO> selectByMiIds(List<String> miIds);
}
