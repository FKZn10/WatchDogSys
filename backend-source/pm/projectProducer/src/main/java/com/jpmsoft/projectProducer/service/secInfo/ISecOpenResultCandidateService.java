package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultCandidateModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResultCandidate;

import java.util.List;

public interface ISecOpenResultCandidateService extends IService<SecOpenResultCandidate> {


    void createOrUpdateSecOpenResultCandidate(List<SecOpenResultCandidateModel> secOpenResultCandidateList, String orId);

}
