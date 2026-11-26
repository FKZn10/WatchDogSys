package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDocReview;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:29
 */
public interface IDesignDocReviewService extends IService<DesignDocReview> {

    BaseResponse createOrUpdateDocReview(DesignDocReviewDTO request);

    BaseResponse getDesignDocReviewList(DesignDocReviewDTO request,String uId);

    BaseResponse getDesignDocReviewById(String id);
}
