package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.pojo.entity.ConContract;
import com.jpmsoft.gmgws.pojo.vo.ContractTotlaVo;

import java.util.List;

public interface ConContractService  extends IService<ConContract> {


   List<ConContract>  getConName( List<String> conName, List<String> pId );

    List<ContractTotlaVo> getPayList(List<String> ids);

    BaseResponse  selectConContract(ConContractRequest request);
}
