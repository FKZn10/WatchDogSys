package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGContactOrder;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGVisaOrder;

import java.math.BigDecimal;
import java.util.List;


public interface IBGVisaOrderService extends IService<BGVisaOrder> {

    BigDecimal getLJBGMoney(ProBGRequest request);

}
