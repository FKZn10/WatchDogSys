package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirPraiseDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirPraise;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface IMemoirPraiseService extends IService<MemoirPraise> {

    BaseResponse memoirPraise(String id, String token);

    BaseResponse deleteMemoirPraise(String id, String token);

    List<MemoirPraiseDTO> selectByMiIds(List<String> miIds);
}
