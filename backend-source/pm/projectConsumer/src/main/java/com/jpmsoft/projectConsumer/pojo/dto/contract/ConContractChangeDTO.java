package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractMoneyDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import lombok.Data;

import java.util.List;

/**
 * 合同变更
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:52
 */
@Data
public class ConContractChangeDTO extends ConContractRequest {
    private String id;
    private String conChangeRemark;
    private List<ConContractMoneyDtlModel> moneyDtlModels;
    private List<ConPayeeDtlModel> payeeDtlModels;
}
