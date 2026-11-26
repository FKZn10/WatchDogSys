package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.entity.PaymentStatistics;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PaymentStatisticsService extends IService<PaymentStatistics> {

    BaseResponse importExcel(MultipartFile file) throws IOException;


}
