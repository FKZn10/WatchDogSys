package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.SecInfo.SecNoticeResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecNoticeMapper extends BaseMapper<SecNotice> {
    List<SecNoticeResponse> getSecNoticeList(@Param("page")  IPage<SecNoticeResponse> page,@Param("params") Map<String, Object> params);

    SecNoticeResponse getSecNoticeByNid(@Param("nId") String nId);

    SecNoticeResponse getSecNoticeLastApprovedBySecId(@Param("secId") String secId);

}
