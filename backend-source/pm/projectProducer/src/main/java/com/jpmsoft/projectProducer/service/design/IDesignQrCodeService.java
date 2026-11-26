package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignQrCode;

public interface IDesignQrCodeService extends IService<DesignQrCode>  {
    BaseResponse createOrupdateDesignQrCode(DesignQrCodeModel request, String token);

    DesignQrCodeModel getDesignQrCodeById(String id, String token);

    BaseResponse deleteDesignQrCodeById(String id);

    BaseResponse checkDqcuserd(DesignQrCodeModel request, String token);

    BaseResponse getDesignQrCodeList(DesignQrCodeRequest request, String uId);

    BaseResponse getDesignQrCodeClassInfo(DesignQrCodeRequest request, String uId);

    BaseResponse getDesignQrCodeDateById(String id, String uid);
}
