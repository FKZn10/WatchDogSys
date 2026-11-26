package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubject;

import java.util.Collection;
import java.util.List;

public interface ISecInfoSubjectService extends IService<SecInfoSubject> {
    void deleteAllOldByBpId(String bpId);

    List<SecInfoSubjectResponse> getSecInfoSubjectsBySecIds(Collection<String> secIds);
}
