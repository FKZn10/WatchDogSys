package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawReviewDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 11:27
 */
public interface IDesignDrawReviewDtlService extends IService<DesignDrawReviewDtl> {

    void createOrUpdate(List<DesignDrawReviewDtlDTO> data, String drId,String token);
}
