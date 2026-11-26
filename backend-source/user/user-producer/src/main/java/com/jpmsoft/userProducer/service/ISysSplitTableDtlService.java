package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTableDtl;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:04
 */
public interface ISysSplitTableDtlService extends IService<SysSplitTableDtl> {

    BaseResponse createSysSplitTableDtl(SysSplitTableDtlDTO request, String token);

    BaseResponse deleteSysSplitTableDtlById(String id);

    BaseResponse getSysSplitTableDtlList(SysSplitTableDtlDTO request);

    /**
     * 文档模板编码或者表编码获取分表配置编码
     *
     * @param code
     * @return
     */
    String getSplitTableCode(String code);
}
