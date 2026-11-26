package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroup;

public interface ISecQeGroupService extends IService<SecQeGroup> {

    BaseResponse createOrUpdateSecQeGroup(SecQeGroupRequest request);

    BaseResponse deleteSecQeGroupByQegId(String qegId);

    BaseResponse getSecQeGroupByQegId(String qegId);

}
