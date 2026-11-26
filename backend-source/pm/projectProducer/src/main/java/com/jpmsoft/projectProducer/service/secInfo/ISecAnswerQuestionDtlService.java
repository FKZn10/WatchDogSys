package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnswerQuestionDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnswerQuestionDtl;

import java.util.List;

public interface ISecAnswerQuestionDtlService extends IService<SecAnswerQuestionDtl> {

    void createOrUpdateSecAnswerQuestionDtl(List<SecAnswerQuestionDtlModel> datas, String aqId);

    void delSecAnswerQuestionDtlByAqId(String aqId);

    List<SecAnswerQuestionDtlModel> getSecAnswerQuestionDtlByAqId(String aqId);
}
