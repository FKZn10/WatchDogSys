package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoSubjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubject;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SecInfoSubjectServiceImpl extends ServiceImpl<SecInfoSubjectMapper, SecInfoSubject> implements ISecInfoSubjectService {
    @Override
    public void deleteAllOldByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) {
            return;
        }
        baseMapper.deleteAllOldByBpId(bpId);
    }

    @Override
    public List<SecInfoSubjectResponse> getSecInfoSubjectsBySecIds(Collection<String> secIds) {
        if (ValidateUtil.isEmpty(secIds)) {
            return null;
        }
        return baseMapper.getSecInfoSubjectsBySecId(secIds);
    }
}
