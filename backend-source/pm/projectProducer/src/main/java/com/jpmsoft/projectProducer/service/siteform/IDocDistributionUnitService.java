package com.jpmsoft.projectProducer.service.siteform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.siteform.DocDistributionUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.siteform.DocDistributionUnit;

import java.util.List;

/**
 * 下发单位表
 */
public interface IDocDistributionUnitService extends IService<DocDistributionUnit> {
    /**
     * 批量新增或更新下发单位
     *
     * @param requests
     * @return
     */
    BaseResponse batchCreateOrUpdateDocDistributionUnit(List<DocDistributionUnitDTO> requests);
}
