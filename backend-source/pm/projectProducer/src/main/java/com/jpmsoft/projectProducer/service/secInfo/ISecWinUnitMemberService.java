package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecWinUnitMemberRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitMemberResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnitMember;

import java.util.List;

public interface ISecWinUnitMemberService extends IService<SecWinUnitMember> {

    void crateOrUpdateWinUnitMember(List<SecWinUnitMemberRequest> models, String wnId);

    void deleteWinUnitMemberByWnId(String wnId);

    List<SecWinUnitMemberResponse> getWinUnitMemberByWnId(String wnId);

    Boolean havingWinUnitMemberByWnId(String wnId);
}
