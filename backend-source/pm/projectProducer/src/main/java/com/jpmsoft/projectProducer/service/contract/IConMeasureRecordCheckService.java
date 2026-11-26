package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;

import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface IConMeasureRecordCheckService extends IService<ConMeasureRecordCheck> {

    BaseResponse createConMeasureRecordCheck(ConMeasureRecordCheckDto request);

    BaseResponse updateConMeasureRecordCheck(ConMeasureRecordCheckDto request);

    BaseResponse getConMeasureRecordCheckById(String id);

    BaseResponse getConMeasureRecordCheckList(ConMeasureRecordCheckDto request);

    void deleteConMeasureRecordCheckByMearIds(List<String> mearIds);

    /**
     * 计量报审退回发起人
     * 删除所有审核记录
     *
     * @param meaId
     */
    void deleteConMeasureRecordCheckByMeaId(String meaId);
}
