package com.jpmsoft.projectProducer.service.projectOutline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleDtlRequest;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;

import java.util.List;

/**
 * 管理规划子系统 样式详情
 *
 * @author tianhelin
 * @date 2025/8/18
 */
public interface IOutlineProjectStyleDtlService extends IService<OutlineProjectStyleDtl> {
    /**
     * 批量创建体系模板样式详情
     *
     * @param opsId
     * @param styleList
     */
    void batchCreateOutlineStyleDtl(String opsId, List<OutlineProjectStyleDtlRequest> styleList);
}
