package com.jpmsoft.fileUpload.service;

import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工程量清单解析服务
 */
public interface IBillOfQuantitiesParseService {

    BillOfQuantitiesContext parse(MultipartFile multipartFile) throws Exception;

}
