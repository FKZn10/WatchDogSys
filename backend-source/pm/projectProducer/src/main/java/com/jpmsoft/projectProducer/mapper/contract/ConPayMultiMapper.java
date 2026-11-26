package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.Contract.ConPayMultiResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMulti;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@Mapper
public interface ConPayMultiMapper extends BaseMapper<ConPayMulti> {
    /**
     * 多项目合同支付分页查询
     *
     * @param page
     * @param query
     * @return
     */
    List<ConPayMultiResponse> getContractMultiPage(@Param("page") Page<ConPayMultiResponse> page, @Param("params") Map<String, Object> query);
}
