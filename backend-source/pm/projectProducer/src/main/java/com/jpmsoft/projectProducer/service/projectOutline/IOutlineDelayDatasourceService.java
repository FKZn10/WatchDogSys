package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDelayDatasource;

import java.util.Collection;
import java.util.List;

/**
 * 延迟数据源服务接口
 *
 * @author tianhelin
 * @date 2025/08/15
 */
public interface IOutlineDelayDatasourceService extends IService<OutlineDelayDatasource> {
    /**
     * 处理延迟渲染
     *
     * @param result
     * @param pId
     */
    void handleDelayRender(List<OutlineProjectTempResponse> result, String pId);

    /**
     * 根据opmIds删除延迟渲染数据源
     *
     * @param opmIds
     */
    void deleteByOpmIds(Collection<String> opmIds);

    /**
     * 处理所有的延迟渲染
     */
    void handleAllDelayRender();

    /**
     * 验证并清除模板
     *
     * @param ids
     */
    void verifyAndClearTemplateByIds(List<String> ids);
}
