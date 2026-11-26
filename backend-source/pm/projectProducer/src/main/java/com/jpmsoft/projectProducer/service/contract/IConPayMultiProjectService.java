package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.Contract.ConPayMultiProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiProject;

import java.util.List;

/**
 * 多项目合同支付项目信息
 *
 * @author tianhelin
 * @date 2025/9/11
 */
public interface IConPayMultiProjectService extends IService<ConPayMultiProject> {
    /**
     * 根据多项目合同支付ID获取多项目合同支付项目信息
     *
     * @param id
     * @return
     */
    List<ConPayMultiProjectResponse> getConPayMultiProjectByCpmId(String id);
}
