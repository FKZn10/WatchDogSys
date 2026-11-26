package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectChangeResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectChange;

import java.util.List;

public interface ISecInfoSubjectChangeService extends IService<SecInfoSubjectChange> {
    List<SecInfoSubjectChangeResponse> getSecInfoSubjectChangeList(List<String> sicIdList);
}
