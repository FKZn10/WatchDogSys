package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import lombok.Data;

import java.util.List;

/**
 * 合同变更
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 14:25
 */
@Data
public class ConContractChangeVO extends ConContractResponse {
    private String id;
    private String conChangeRemark;
    private List<ConContractMoneyDtlModel> moneyDtlModels;
    private List<ConPayeeDtlModel> payeeDtlModels;
}
