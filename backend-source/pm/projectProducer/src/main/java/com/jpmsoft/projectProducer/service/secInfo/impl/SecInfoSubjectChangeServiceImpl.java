package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectChangeResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoSubjectChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectChange;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectChangeService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SecInfoSubjectChangeServiceImpl extends ServiceImpl<SecInfoSubjectChangeMapper, SecInfoSubjectChange> implements ISecInfoSubjectChangeService {
    @Override
    public List<SecInfoSubjectChangeResponse> getSecInfoSubjectChangeList(List<String> sicIdList) {
        if (ValidateUtil.isEmpty(sicIdList)) {
            return Collections.emptyList();
        }
        return baseMapper.getSecInfoSubjectChangeList(sicIdList);
    }
}
