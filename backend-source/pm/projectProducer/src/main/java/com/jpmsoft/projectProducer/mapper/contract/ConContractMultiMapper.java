package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.Contract.ConContractMultiResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMulti;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 多项目合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Mapper
public interface ConContractMultiMapper extends BaseMapper<ConContractMulti> {
    /**
     * 获取多项目合同分页列表
     *
     * @param page
     * @param query
     * @return
     */
    List<ConContractMultiResponse> getContractMultiPage(@Param("page") Page<ConContractMultiResponse> page, @Param("params") Map<String, Object> query);
}
