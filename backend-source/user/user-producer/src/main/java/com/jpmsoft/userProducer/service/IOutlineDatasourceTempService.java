package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineDatasourceTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasourceTemp;

import java.util.List;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
public interface IOutlineDatasourceTempService extends IService<OutlineDatasourceTemp> {
    /**
     * 新增或修改业务功能数据源关联
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateDatasourceTemp(OutlineDatasourceTempRequest request);

    /**
     * 删除业务功能数据源关联
     *
     * @param id
     * @return
     */
    BaseResponse deleteDatasourceTempById(String id);

    /**
     * 分页查询业务功能数据源关联
     *
     * @param request
     * @return
     */
    BaseResponse getDatasourceTempPage(OutlineDatasourceTempRequest request);

    /**
     * 根据odIds查询业务功能数据源关联
     *
     * @param odIds
     * @return
     */
    List<OutlineDatasourceTempResponse> getDatasourceTempByOdIds(List<String> odIds);

    /**
     * 批量新增或修改业务功能数据源关联
     *
     * @param datasourceTempList
     * @return
     */
    BaseResponse batchCreateOrUpdateDatasourceTemp(List<OutlineDatasourceTempRequest> datasourceTempList);
}
