package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.Contract.ConContractMultiProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiProject;

import java.util.List;

/**
 * 多项目合同项目信息
 *
 * @author tianhelin
 * @date 2025/9/8
 */
public interface IConContractMultiProjectService extends IService<ConContractMultiProject> {
    /**
     * 根据多项目合同ID获取多项目合同项目信息
     *
     * @param id
     * @return
     */
    List<ConContractMultiProjectResponse> getConPayMultiProjectByCcmId(String id);
}
