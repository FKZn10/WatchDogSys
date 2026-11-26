package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYearYw;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface InvPlanYearYwService extends IService<InvPlanYearYw> {
    BaseResponse createOrUpdateInvPlanYearYw(InvPlanYearRequest request);

    BaseResponse getInvPlanYearYwByIpvId(String ipvId);

    BaseResponse getInvPlanYearYwList(InvPlanYearRequest request, String token);

    BaseResponse getInvPlanYearYwBypId(String pId,String year);

    ResponseEntity<byte[]> getInvPlanYearYwListExcel(InvPlanYearRequest request, String token) throws UnsupportedEncodingException;

    BaseResponse getInvPlanYearSummaryYwList(InvPlanYearRequest request, String token);

    ResponseEntity<byte[]> getInvPlanYearYwPreListExcel(InvPlanYearRequest request, String token)  throws UnsupportedEncodingException;
}
