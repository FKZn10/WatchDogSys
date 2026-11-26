package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.entity.FinancialPayment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FinancialPaymentService  extends IService<FinancialPayment> {
    BaseResponse financeImport(MultipartFile file, String uid) throws IOException;
}
