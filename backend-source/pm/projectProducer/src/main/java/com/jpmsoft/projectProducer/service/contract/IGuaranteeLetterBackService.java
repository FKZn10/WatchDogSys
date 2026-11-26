package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterBackRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterBack;

import java.util.List;


public interface IGuaranteeLetterBackService extends IService<GuaranteeLetterBack> {


    BaseResponse createOrUpdateGuaranteeLetterBack(GuaranteeLetterBackRequest request);

    @Deprecated
    BaseResponse getGuaranteeLetterBackList(GuaranteeLetterBackRequest request, String uId);

    BaseResponse getGuaranteeLetterBackByglbId(String glbId);

    BaseResponse getGuaranteeLetterBackAndSignByglbId(String glbId);

    GuaranteeLetterBack getGuaranteeLetterBackByglsId(String glsId);

    List<GuaranteeLetterBack> getGuaranteeLetterBackByConId(String conId);
}
