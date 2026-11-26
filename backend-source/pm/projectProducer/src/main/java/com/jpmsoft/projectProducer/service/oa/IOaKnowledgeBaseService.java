package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseModel;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBase;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

public interface IOaKnowledgeBaseService extends IService<OaKnowledgeBase> {

    BaseResponse createOrUpdateOaKnowledgeBase(OaKnowledgeBaseModel request,String teId);

    BaseResponse getOaKnowledgeBaseTree(OaKnowledgeBaseModel request, LogUserInfoVo user);

    BaseResponse deleteOaKnowledgeBaseById(String kbId);

    BaseResponse onDragOaKnowledgeBase(OnDragDto request);

    List<OaKnowledgeBase> getAll();

    List<String> getAllChildsIds(String kbId);
}
