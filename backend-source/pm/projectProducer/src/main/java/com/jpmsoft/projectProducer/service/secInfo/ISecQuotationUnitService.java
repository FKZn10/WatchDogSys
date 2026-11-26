package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQuotationUnitDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuotationUnit;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:57
 */
public interface ISecQuotationUnitService extends IService<SecQuotationUnit> {

    void createOrUpdateSecQuotationUnit(List<SecQuotationUnitDto> datas, String sirId);

    void delSecQuotationUnitBySirId(String sirId);

    /**
     * 查询标段下审批通过的询价记录管理的报价单位
     *
     * @param secId
     * @return
     */
    BaseResponse getSecQuotationUnitBySecId(String secId);
}
