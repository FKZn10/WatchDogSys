package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailInquiryDocDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetailInquiryDoc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecInviteDetailInquiryDocMapper extends BaseMapper<SecInviteDetailInquiryDoc> {


    List<SecInviteDetailInquiryDocDTO> getSecInviteDetailInquiryDocByWsidIds(@Param("wsidIdList") List<String> wsidIdList);


}
