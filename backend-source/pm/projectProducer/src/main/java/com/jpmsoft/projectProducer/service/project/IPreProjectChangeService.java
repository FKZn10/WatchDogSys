package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectChangeModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChange;

public interface IPreProjectChangeService extends IService<PreProjectChange> {

    BaseResponse createOrUpdatePreProjectChange(PreProjectChangeModel request, String uId);

    BaseResponse getPreProjectChangeList(PreProjectChangeModel request, String uId);

    BaseResponse getPreProjectChangeById(String pcId);

    BaseResponse approvalSyncProInfo(String pcId, String uId);

}
