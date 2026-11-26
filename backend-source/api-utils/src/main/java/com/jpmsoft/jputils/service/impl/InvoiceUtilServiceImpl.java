package com.jpmsoft.jputils.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jputils.dto.InvoiceInfoDto;
import com.jpmsoft.jputils.request.InvoiceInfoRequest;
import com.jpmsoft.jputils.service.InvoiceUtilService;
import com.jpmsoft.jputils.utils.InvoiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yb.Sun
 * @date 2025/1/17 09:36
 */
@Slf4j
@Service
public class InvoiceUtilServiceImpl implements InvoiceUtilService {
    @Override
    public BaseResponse getInvoiceInfo(MultipartFile file, String appCode, String type) {
        log.info("InvoiceUtilServiceImpl.getInvoiceInfo() start");
        return InvoiceUtils.getInvoiceInfo(file, appCode, "normal");

    }

    @Override
    public BaseResponse checkInvoiceInfo(InvoiceInfoRequest request, String appCode) {
        log.info("InvoiceUtilServiceImpl.checkInvoiceInfo() start");
        return InvoiceUtils.checkInvoiceInfo(request, appCode, "normal");
    }
}
