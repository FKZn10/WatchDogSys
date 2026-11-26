package com.jpmsoft.projectProducer.service.other;

import com.jpmsoft.projectProducer.pojo.vo.other.MHCompanyResultVO;

import java.util.List;

public interface IMHCompanyService {

    List<MHCompanyResultVO> getCompanyListByApplyType(String applyType, String teId);
}
