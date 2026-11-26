package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceCompanyDto;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceCompany;

import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/10 19:36
 */
public interface ICompletionAcceptanceCompanyService extends IService<CompletionAcceptanceCompany> {

    void createOrUpdateCompletionAcceptanceCompany(List<CompletionAcceptanceCompanyDto> datas, String caId);

    void delCompletionAcceptanceCompanyByCaId(String caId);

}
