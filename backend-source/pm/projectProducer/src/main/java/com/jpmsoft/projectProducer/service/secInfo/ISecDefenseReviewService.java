package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDefenseReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDefenseReview;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecDefenseReviewService extends IService<SecDefenseReview> {

    void createOrUpdateSecDefenseReview(List<SecDefenseReviewDTO> data,String sqId);

    void deleteBySqId(String sqId);
}
