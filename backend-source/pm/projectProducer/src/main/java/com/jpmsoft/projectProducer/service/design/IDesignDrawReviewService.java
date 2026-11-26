package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawReview;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 11:27
 */
public interface IDesignDrawReviewService extends IService<DesignDrawReview> {

    BaseResponse createOrUpdateDesignDrawReview(DesignDrawReviewDTO request, String token);

    BaseResponse getDesignDrawReviewList(DesignDrawReviewDTO request, String token);

    BaseResponse getDesignDrawReviewById(String id);
}
