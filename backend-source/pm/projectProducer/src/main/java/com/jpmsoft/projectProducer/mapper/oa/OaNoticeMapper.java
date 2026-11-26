package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Oa.OaNoticeResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaNoticeMapper extends BaseMapper<OaNotice> {

    List<OaNoticeResponse> getOaNoticeList(@Param("page") IPage<OaNoticeResponse> page, @Param("params") Map<String, Object> params, @Param("specificScopeids") List<String> specificScopeids);

    List<OaNoticeResponse> getUserOaNoticeList(@Param("page") IPage<OaNoticeResponse> page, @Param("params") Map<String, Object> params);

    OaNoticeResponse getOaNoticeByNid(@Param("nId") String nId);
}
