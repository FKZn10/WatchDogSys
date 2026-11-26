package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInfoChangeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

public interface ISecInfoChangeService extends IService<SecInfoChange> {

    void createOrUpdateSecInfoChange(List<SecInfoChangeRequest> datas, String bpcId, LogUserInfoVo user, String action, String pId);

    void deleteByBpcId(String bpcId);

    List<SecInfoChangeRes> getSecInfoChangeList(String bpcId);

    List<String> getSecInfoChangeIds(String bpcId);
}
