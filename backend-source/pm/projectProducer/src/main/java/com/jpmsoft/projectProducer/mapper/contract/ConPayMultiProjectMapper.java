package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Contract.ConPayMultiProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 多项目合同支付项目信息
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Mapper
public interface ConPayMultiProjectMapper extends BaseMapper<ConPayMultiProject> {
    /**
     * 根据多项目合同支付ID获取多项目合同支付项目信息
     *
     * @param id
     * @return
     */
    List<ConPayMultiProjectResponse> getConPayMultiProjectByCpmId(@Param("id") String id);
}
