package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.SysPersonalBpmAuthorizeModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.SysPersonalBpmAuthorize;

public interface ISysPersonalBpmAuthorizeService extends IService<SysPersonalBpmAuthorize> {

    BaseResponse createOrUpdatePersonalBpmAuthorize(SysPersonalBpmAuthorizeModel request,String uid);

    BaseResponse getSelfPersonalBpmAuthorizeList(SysPersonalBpmAuthorizeModel request, String uId);

    BaseResponse getPersonalBpmAuthorizeList(SysPersonalBpmAuthorizeModel request);

    BaseResponse getPersonalBpmAuthorizeById(String baId);


}
