package com.jpmsoft.projectProducer.service.blxw;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwTypeModel;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwType;

public interface IBlxwTypeService extends IService<BlxwType> {
    BaseResponse createOrUpdateBlxwType(BlxwTypeModel request);

    BaseResponse deleteBlxwTypeById(String id);

    BaseResponse getBlxwTypeTreeList(BlxwTypeModel request,String uid);

    BaseResponse upOrDownBlxwType(String id,String action);

    BaseResponse getBlxwTypeByParentId(String parentId);
}
