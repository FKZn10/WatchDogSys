package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBaseFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaKnowledgeBaseFileMapper extends BaseMapper<OaKnowledgeBaseFile> {

    List<OaKnowledgeBaseFileModel> getOaKnowledgeBaseFileList(@Param("params") Map<String, Object> params, @Param("kbIds") List<String> kbIds);

    List<OaKnowledgeBaseFileModel> getOaKnowledgeBaseFileAllList(@Param("teId") String teId);

    Integer getOaKnowledgeBaseFileListCount(@Param("params") Map<String, Object> params, @Param("kbIds") List<String> kbIds);

}
