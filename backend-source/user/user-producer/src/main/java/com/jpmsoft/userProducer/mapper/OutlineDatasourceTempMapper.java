package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineDatasourceTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasourceTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/26
 */
@Mapper
public interface OutlineDatasourceTempMapper extends BaseMapper<OutlineDatasourceTemp> {
    /**
     * 分页查询业务功能数据源关联
     *
     * @param request
     * @return
     */
    List<OutlineDatasourceTempResponse> getDatasourceTempPage(@Param("page") Page<OutlineDatasourceTemp> page, @Param("params") OutlineDatasourceTempRequest request);

    /**
     * 根据odIds查询业务功能数据源关联
     *
     * @param collect
     * @return
     */
    List<OutlineDatasourceTempResponse> getDatasourceTempByOdIds(List<String> collect);
}
