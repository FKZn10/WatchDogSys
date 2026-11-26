package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDrawReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 11:11
 */
public interface DesignDrawReviewMapper extends BaseMapper<DesignDrawReview> {

    List<DesignDrawReviewDTO> getDesignDrawReviewList(@Param("page") IPage<DesignDrawReviewDTO> page, @Param("params") Map<String, Object> params);


    DesignDrawReviewDTO getDesignDrawReviewById(@Param("id") String id);
}
