package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.Contract.ConContractMultiProjectResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMultiProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiProject;
import com.jpmsoft.projectProducer.service.contract.IConContractMultiProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 多项目合同项目信息
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Slf4j
@Service
public class ConContractMultiProjectImpl extends ServiceImpl<ConContractMultiProjectMapper, ConContractMultiProject> implements IConContractMultiProjectService {
    /**
     * 根据多项目合同ID获取多项目合同项目信息
     *
     * @param id
     * @return
     */
    @Override
    public List<ConContractMultiProjectResponse> getConPayMultiProjectByCcmId(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return Collections.emptyList();
        }
        List<ConContractMultiProjectResponse> list = baseMapper.getConContractMultiProjectByCcmId(id);
        if (ValidateUtil.isNotEmpty(list)) {
            return list;
        }
        return Collections.emptyList();
    }
}
