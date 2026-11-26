package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemLibResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeDatasource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 体系模板节点数据源
 *
 * @author tianhelin
 * @date 2025/6/11
 */
public interface IOutlineNodeDatasourceService extends IService<OutlineNodeDatasource> {
    /**
     * 体系模板-目录节点ID获取体系库节点数据源
     * 用于复制
     *
     * @param oslIds
     * @return
     */
    List<OutlineNodeDatasourceResponse> getAllOutlineNodeDatasourceByOslIds(List<String> oslIds);

    BaseResponse createOutlineNodeDatasource(OutlineNodeDatasourceRequest request, String token);

    BaseResponse getOutlineNodeDatasourceList(OutlineNodeDatasourceRequest request);

    Boolean datasourceExist(String odId, String oslId);

    void deleteByOslId(String oslId);

    void deleteByOsmId(String osmId);

    BaseResponse updateOutlineNodeDatasource(OutlineNodeDatasourceRequest request, String token);

    BaseResponse deleteOutlineNodeDatasourceById(String id, String token);

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @return
     */
    String handleDatasourceRender(OutlineSystemLibResponse osl, String pId);

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @param aliasesMap
     * @return
     */
    String handleDatasourceRender(OutlineSystemLibResponse osl, String pId, Map<String, List<String>> aliasesMap);

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @param datasourceList
     * @param aliasesMap
     * @return
     */
    String handleDatasourceRender(OutlineSystemLibResponse osl, String pId, List<OutlineNodeDatasourceResponse> datasourceList, Map<String, List<String>> aliasesMap);

    /**
     * 处理数据源渲染
     *
     * @param html
     * @param datasourceList
     * @param pId
     * @return
     */
    String handleDatasourceRender(String html, List<OutlineNodeDatasourceResponse> datasourceList, String pId, List<String> aliases);

    /**
     * 批量处理数据源渲染
     *
     * @param htmls
     * @param datasourceList
     * @param pId
     * @return
     */
    List<String> batchHandleDatasourceRender(List<String> htmls, List<OutlineNodeDatasourceResponse> datasourceList, String pId);

    /**
     * 获取所有数据源ID和别名的映射
     *
     * @return
     */
    Map<String, String> getOdIdAndAliasMap();

    /**
     * 根据odIds获取对应的odId和tcode
     *
     * @param odIds
     * @return
     */
    Map<String, Set<String>> getTCodesByOdIds(List<String> odIds);
}
