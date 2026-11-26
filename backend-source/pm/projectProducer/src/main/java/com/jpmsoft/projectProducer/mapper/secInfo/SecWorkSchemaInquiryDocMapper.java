package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaInquiryDocDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWorkSchemaInquiryDoc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecWorkSchemaInquiryDocMapper extends BaseMapper<SecWorkSchemaInquiryDoc> {


    List<SecWorkSchemaInquiryDocDTO> getSecWorkSchemaInquiryDocList(@Param("page") IPage<SecWorkSchemaInquiryDocDTO> page, @Param("params") Map<String, Object> params);


    SecWorkSchemaInquiryDocDTO getSecWorkSchemaInquiryDocByWsidId(@Param("wsidId") String wsidId);

    List<SecWorkSchemaInquiryDocDTO> getSecWorkSchemaInquiryDocBySecId(@Param("secId") String secId);

}
