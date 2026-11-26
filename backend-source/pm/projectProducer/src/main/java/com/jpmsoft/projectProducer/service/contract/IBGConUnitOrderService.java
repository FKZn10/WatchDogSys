package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGConUnitOrder;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGDeclaration;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConBG;

import java.math.BigDecimal;
import java.util.List;


public interface IBGConUnitOrderService extends IService<BGConUnitOrder> {

    BigDecimal getLJBGMoney(ProBGRequest request);

    List<ProBGDTO> getBGConUnitOrderListByConId(ProBGRequest request);

    Page<ProBGDTO> getBGConUnitOrderListByType(ProBGRequest request);

    String SetConUnitOrderBL(ProBGRequest request);

}
