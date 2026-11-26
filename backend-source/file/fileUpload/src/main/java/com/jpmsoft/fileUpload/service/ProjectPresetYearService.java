package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface ProjectPresetYearService {

    ResponseEntity<byte[]> getInvPlanYearYwListExcel(InvPlanYearRequest request, String token, HttpServletResponse response);
}
