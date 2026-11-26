package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreCommandPost;

public interface IPreCommandPostService extends IService<PreCommandPost> {

    BaseResponse createOrUpdatePreCommandPost(PreCommandPostModel request);

    BaseResponse getPreCommandPostList(PreCommandPostModel request, String uId);

    BaseResponse getPreCommandPostStatisticsList(PreCommandPostModel request, String uId);

    BaseResponse getPreCommandPostById(String comId);
}
