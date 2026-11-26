package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Contract.ConSecondMemberRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConSecondMember;

import java.util.List;

public interface IConSecondMemberService extends IService<ConSecondMember> {


    void createOrUpdateConSecondMember(List<ConSecondMemberRequest> datas, String conId);

    void deleteAllConSecondMemberByConId(String conId);

    List<ConSecondMember> getConSecondMemberByConIds(List<String> conIds);

    List<ConSecondMemberRequest> getConSecondMemberByConId(String conId);
}
