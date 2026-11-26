package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.Contract.ConMeasurePriceResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasurePrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同计量计价 Mapper
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Mapper
public interface ConMeasurePriceMapper extends BaseMapper<ConMeasurePrice> {
    /**
     * 获取合同计量计价列表
     *
     * @param page
     * @param query
     * @return
     */
    List<ConMeasurePriceResponse> getConMeasurePriceList(@Param("page") Page<ConMeasurePriceResponse> page, @Param("params") Map<String, Object> query);

    /**
     * 获取合同计量计价详情
     *
     * @param id
     * @return
     */
    ConMeasurePriceResponse getConMeasurePriceById(@Param("id") String id);
}
