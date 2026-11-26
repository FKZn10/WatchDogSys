package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjecDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjecDatasourceResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjecDatasource;

import java.util.List;

/**
 * 项目管理大纲体系节点数据源配置
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineProjecDatasourceService extends IService<OutlineProjecDatasource> {
    /**
     * 根据项目大纲id查询项目节点数据源
     *
     * @param oplIds
     * @return
     */
    List<OutlineProjecDatasourceResponse> getOutlineProjecDatasourceByOplIds(List<String> oplIds);

    /**
     * 新增项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createProjectDatasource(OutlineProjecDatasourceRequest request, String token);

    /**
     * 删除项目管理大纲体系节点数据源配置
     *
     * @param id
     * @param token
     * @return
     */
    BaseResponse deleteProjectDatasourceById(String id, String token);

    /**
     * 根据odId判断项目管理大纲体系节点数据源配置是否存在
     *
     * @param oplId
     * @param odId
     * @return
     */
    Boolean datasourceExist(String oplId, String odId);

    /**
     * 修改项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse updateProjectDatasource(OutlineProjecDatasourceRequest request, String token);

    /**
     * 获取项目管理大纲体系节点数据源配置
     *
     * @param id
     * @return
     */
    BaseResponse getProjectDatasourceById(String id);

    /**
     * 分页查询项目管理大纲体系节点数据源配置
     *
     * @param request
     * @return
     */
    BaseResponse getProjectDatasourcePage(OutlineProjecDatasourceRequest request);

    /**
     * 根据tCode和oplId查询数据源id列表
     *
     * @param tCode
     * @param oplId
     * @return
     */
    List<String> getOdIdsByTCodeAndOplId(String tCode, String oplId);

    /**
     * 根据oplId和odId查询数据源
     *
     * @param oplId
     * @param odId
     * @return
     */
    OutlineProjecDatasourceResponse getProjectDatasourceByOplIdAndOdId(String oplId, String odId);
}
