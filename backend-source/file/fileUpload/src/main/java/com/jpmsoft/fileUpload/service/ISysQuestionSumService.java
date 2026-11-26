package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysQuestionSum;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 13:37
 */
public interface ISysQuestionSumService extends IService<SysQuestionSum> {

    void createQuestionSum(SysAnswerLibDTO request, Boolean question);

    BaseResponse getSysQuestionSumList(BaseModeRequest request);


}
