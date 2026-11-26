package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleRequest;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyle;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;

import java.util.Map;

/**
 * 管理规划子系统 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
public interface IOutlineProjectStyleService extends IService<OutlineProjectStyle> {
    /**
     * 获取体系模板样式分页列表
     *
     * @param request
     * @return
     */
    BaseResponse getOutlineStylePage(OutlineProjectStyleRequest request);

    /**
     * 根据ID获取体系模板样式
     *
     * @param id
     * @return
     */
    BaseResponse getOutlineStyleById(String id);

    /**
     * 删除体系模板样式
     *
     * @param request
     * @return
     */
    BaseResponse deleteOutlineStyleById(OutlineProjectStyleRequest request);

    /**
     * 创建或更新体系模板样式
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateOutlineStyle(OutlineProjectStyleRequest request, String token);

    /**
     * 初始化样式数据
     *
     * @param token
     */
    void initializationOutlineStyle(String token);

    /**
     * 根据类别获取样式明细映射
     *
     * @param category
     * @return
     */
    Map<String, OutlineProjectStyleDtl> getStyleDtlMapByCategory(String category);
}
