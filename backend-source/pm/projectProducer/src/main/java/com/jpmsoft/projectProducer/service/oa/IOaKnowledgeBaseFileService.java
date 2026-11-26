package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBaseFile;

public interface IOaKnowledgeBaseFileService extends IService<OaKnowledgeBaseFile> {

    BaseResponse createOrUpdateOaKnowledgeBaseFile(OaKnowledgeBaseFileModel request);

    BaseResponse getOaKnowledgeBaseFileList(OaKnowledgeBaseFileModel request);

    BaseResponse getOaKnowledgeBaseFileAllList(String teId);

    BaseResponse deleteOaKnowledgeBaseFileById(String kbfId);

    Integer getOaKnowledgeBaseFileCountByKbId(String kbId);
}
