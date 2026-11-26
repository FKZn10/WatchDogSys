package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQuestionMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQuestionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuestion;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuestionVO;
import com.jpmsoft.projectProducer.service.secInfo.ISecDefenseReviewService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDefenseSupervisionService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQuestionService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
@Service
public class SecQuestionImpl extends FileImpl<SecQuestionMapper, SecQuestion, SecQuestionDTO> implements ISecQuestionService {
    @Resource
    private ISecDefenseReviewService secDefenseReviewService;

    @Resource
    private ISecDefenseSupervisionService secDefenseSupervisionService;

    @Override
    public BaseResponse createOrUpdateSecQuestion(SecQuestionDTO request) {
        SecQuestion entity = new SecQuestion();
        createOrUpdateFileEntity(entity, request);
        secDefenseReviewService.createOrUpdateSecDefenseReview(request.getSecDefenseReviews(), entity.getSqId());
        secDefenseSupervisionService.createOrUpdateSecDefenseSupervision(request.getSecDefenseSupervisions(), entity.getSqId());
        return BaseResponse.ok(entity.getSqId());
    }

    @Override
    public BaseResponse getSecQuestionById(String id) {
        SecQuestionVO data = baseMapper.getSecQuestionById(id);
        return BaseResponse.ok(data);
    }
}
