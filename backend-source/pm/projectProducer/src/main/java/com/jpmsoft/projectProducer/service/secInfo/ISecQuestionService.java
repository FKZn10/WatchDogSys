package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQuestionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuestion;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
public interface ISecQuestionService extends IService<SecQuestion> {

    BaseResponse createOrUpdateSecQuestion(SecQuestionDTO request);

    BaseResponse getSecQuestionById(String id);
}
