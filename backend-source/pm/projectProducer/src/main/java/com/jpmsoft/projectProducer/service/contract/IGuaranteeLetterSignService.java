package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterSignRequest;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterSignResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterSign;

import java.util.List;


public interface IGuaranteeLetterSignService extends IService<GuaranteeLetterSign> {

    BaseResponse createOrUpdateGuaranteeLetterSign(GuaranteeLetterSignRequest request);

    BaseResponse getGuaranteeLetterSignList(GuaranteeLetterSignRequest request, String uId);

    BaseResponse getGuaranteeLetterSignByglsId(String glsId);

    BaseResponse getGuaranteeLetterSignByContract(String conId);

    GuaranteeLetterSignResponse getGuaranteeLetterSignById(String glsId);


    List<GuaranteeLetterSign> getGuaranteeLetterSignByConId(String conId);

    BaseResponse getGuaranteeLetterExistence(String conId);
}
