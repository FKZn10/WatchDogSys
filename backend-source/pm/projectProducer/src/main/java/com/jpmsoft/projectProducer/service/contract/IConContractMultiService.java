package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractMultiRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMulti;

/**
 * 多项目合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
public interface IConContractMultiService extends IService<ConContractMulti> {
    /**
     * 创建或更新多项目合同
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse createOrUpdateContractMulti(ConContractMultiRequest request, String token);

    /**
     * 审批结束后同步到合同表
     *
     * @param request
     * @return
     */
    BaseResponse syncToContract(ConContractMultiRequest request);

    /**
     * 审批通过后同步多项目合同及扩展字段。
     *
     * @param request 包含多项目合同ID和目标合同模板编码tCode的请求
     * @return 同步结果
     */
    BaseResponse syncToContractJNDX(ConContractMultiRequest request);

    /**
     * 获取多项目合同分页列表
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getContractMultiPage(ConContractMultiRequest request, String token);

    /**
     * 获取多项目合同详情
     *
     * @param id
     * @return
     */
    BaseResponse getContractMultiById(String id);
}
