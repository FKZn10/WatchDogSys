package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.common.model.requestAndResponse.RiskWarn.RiskWarnIndexModel;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnIndex;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IRiskWarnIndexService extends IService<RiskWarnIndex> {

    BaseResponse createOrUpdateRiskWarnIndex(RiskWarnIndexRequest request);

    BaseResponse getRiskWarnIndexList(RiskWarnIndexModel request);

    BaseResponse deleteRiskWarnIndexById(String rwiId);

    BaseResponse getRiskWarnIndexById(String rwiId);

    BaseResponse importRiskWarnIndex(MultipartFile file);

    BaseResponse exportRiskWarnIndex(List<String> ids);
}
