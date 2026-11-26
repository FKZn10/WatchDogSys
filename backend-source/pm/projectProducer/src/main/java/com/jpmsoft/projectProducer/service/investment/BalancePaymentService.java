package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.investment.BalancePayment;

public interface BalancePaymentService extends IService<BalancePayment> {
    BalancePayment getBalancePaymentById(String id);
}
