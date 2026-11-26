package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.ConPay;
import com.jpmsoft.gmgws.pojo.entity.PaymentStatistics;

import java.util.List;

public interface ConPayService  extends IService<ConPay> {
    List<ConPay> getIdDataList(List<String> list);

    List<ConPay> getProjectDataList(List<String> list);

}
