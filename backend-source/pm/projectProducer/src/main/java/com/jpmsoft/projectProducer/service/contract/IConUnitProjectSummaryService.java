package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConUnitProjectSummaryDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConUnitProjectSummary;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 13:59
 */
public interface IConUnitProjectSummaryService extends IService<ConUnitProjectSummary> {

    void createOrUpdateConUnitProjectSummary(List<ConUnitProjectSummaryDto> datas, String meaId);
}
