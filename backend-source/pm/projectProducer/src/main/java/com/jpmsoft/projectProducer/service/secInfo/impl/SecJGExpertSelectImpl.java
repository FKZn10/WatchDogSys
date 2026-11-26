package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertSelectMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertSelect;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertSelectService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

@Service
public class SecJGExpertSelectImpl extends BaseFileImpl<SecJGExpertSelectMapper, SecJGExpertSelect, SecJGExpertSelectDto> implements ISecJGExpertSelectService {


    @Override
    public void updateState(String id, Integer state) {
        if (ValidateUtil.isNotEmpty(id) && ValidateUtil.isNotNull(state))
            baseMapper.updateState(id, state);
    }
}
