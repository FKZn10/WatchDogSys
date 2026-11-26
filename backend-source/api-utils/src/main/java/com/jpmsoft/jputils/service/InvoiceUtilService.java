package com.jpmsoft.jputils.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jputils.dto.InvoiceInfoDto;
import com.jpmsoft.jputils.request.InvoiceInfoRequest;
import com.jpmsoft.jputils.response.InvoiceResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yb.Sun
 * @date 2025/1/17 09:35
 */
public interface InvoiceUtilService {

    /**
     * @param file
     * @param appCode
     * @param type
     * @return
     */
    BaseResponse getInvoiceInfo(MultipartFile file, String appCode, String type);

    BaseResponse checkInvoiceInfo(InvoiceInfoRequest request, String appCode);
}
