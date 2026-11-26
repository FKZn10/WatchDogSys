package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Design.DesignConstructionResponse;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignDocReviewDTO;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDocReview;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:28
 */
public interface DesignDocReviewMapper extends BaseMapper<DesignDocReview> {

    List<DesignDocReviewDTO> getDesignDocReviewList(@Param("page") IPage<DesignDocReviewDTO> page, @Param("params") Map<String, Object> params);


    DesignDocReviewDTO getDesignDocReviewById(@Param("id") String id);
}
