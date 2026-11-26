package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.common.model.response.pay.ConFundNoVo;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGContactOrder;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGDeclaration;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConBG;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContactList;

import java.math.BigDecimal;
import java.util.List;


public interface IBGDeclarationService extends IService<BGDeclaration> {

    BigDecimal getLJBGMoney(ProBGRequest request);


    List<ProBGDTO> getBGDeclarationListByConId(ProBGRequest request);


    Page<ProBGDTO> getBGDeclarationListByType(ProBGRequest request);

    String SetDeclarationBL(ProBGRequest request);
}
