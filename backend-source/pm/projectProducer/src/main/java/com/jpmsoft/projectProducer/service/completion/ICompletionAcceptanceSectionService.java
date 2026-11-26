package com.jpmsoft.projectProducer.service.completion;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceSectionDto;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceSection;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:38
 */
public interface ICompletionAcceptanceSectionService extends IService<CompletionAcceptanceSection> {

    void createOrUpdateCompletionAcceptanceSection(List<CompletionAcceptanceSectionDto> datas, String caId);

    void delCompletionAcceptanceSectionByCaId(String caId);
}
