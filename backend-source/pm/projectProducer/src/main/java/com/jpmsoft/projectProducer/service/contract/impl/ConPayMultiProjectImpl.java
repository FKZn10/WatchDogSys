package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.Contract.ConPayMultiProjectResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMultiProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiProject;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiProjectService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 多项目合同支付项目信息
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Service
public class ConPayMultiProjectImpl extends ServiceImpl<ConPayMultiProjectMapper, ConPayMultiProject> implements IConPayMultiProjectService {
    /**
     * 根据多项目合同支付ID获取多项目合同支付项目信息
     *
     * @param id
     * @return
     */
    @Override
    public List<ConPayMultiProjectResponse> getConPayMultiProjectByCpmId(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return Collections.emptyList();
        }
        List<ConPayMultiProjectResponse> list = baseMapper.getConPayMultiProjectByCpmId(id);
        if (ValidateUtil.isNotEmpty(list)) {
            return list;
        }
        return Collections.emptyList();
    }
}
