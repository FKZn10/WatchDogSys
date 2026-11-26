package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.entity.Agreement;
import com.jpmsoft.gmgws.pojo.entity.ConContract;

import java.util.Map;

public interface AgreementService  extends IService<Agreement> {
    BaseResponse relieve(String id,String aRelieveId);
}
