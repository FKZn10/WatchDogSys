package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysFileTemplateDtl;

import java.util.Collection;
import java.util.List;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
public interface ISysFileTemplateDtlService extends IService<SysFileTemplateDtl> {
    /**
     * 批量新增或修改文档模板扩展
     *
     * @param fileTemplateDtls
     * @param tId
     * @return
     */
    BaseResponse createOrUpdateSysFileTemplateDtlBatch(List<SysFileTemplateDtlDTO> fileTemplateDtls, String tId);

    /**
     * 根据tIds获取文档模板扩展
     *
     * @param tIds
     * @return
     */
    List<SysFileTemplateDtlDTO> getSysFileTemplateDtlBytIds(Collection<String> tIds);

    /**
     * 新增或修改单个文档模板扩展
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSysFileTemplateDtl(SysFileTemplateDtlDTO request);
}
