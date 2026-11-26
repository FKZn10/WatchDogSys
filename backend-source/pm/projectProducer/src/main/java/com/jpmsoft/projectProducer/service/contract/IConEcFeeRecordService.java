package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRecordDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFeeRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;

import java.util.List;

public interface IConEcFeeRecordService extends IService<ConEcFeeRecord> {

    void createOrUpdateConEcFeeRecord(List<ConEcFeeRecordDto> datas, String meaId);
}
