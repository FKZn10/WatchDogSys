package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceContractDto;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceContract;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:37
 */
public interface ICompletionAcceptanceContractService extends IService<CompletionAcceptanceContract> {

    void createOrUpdateCompletionAcceptanceContract(List<CompletionAcceptanceContractDto> datas, String caId);

    void delCompletionAcceptanceContractByCaId(String caId);
}
