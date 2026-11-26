package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirInfo;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
public interface IMemoirInfoService extends IService<MemoirInfo> {

    BaseResponse createOrUpdateMemoirInfo(MemoirInfoDTO request);

    BaseResponse getMemoirInfoList(MemoirInfoDTO request, String token);

    BaseResponse approve2ComplateGanntTask(String id);

    BaseResponse getMemoirInfoById(String id);
}
