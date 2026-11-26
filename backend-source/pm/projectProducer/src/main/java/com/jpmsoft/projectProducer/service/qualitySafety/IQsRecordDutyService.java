package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPlanTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDutyBathDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecordDuty;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:42
 */
public interface IQsRecordDutyService extends IService<QsRecordDuty> {


    void createOrUpdate(List<QsRecordDutyBathDTO> data);

    void deleteByQsrIds(List<String> ids);

    List<QsRecordDutyDTO> getQsRecordDutyByQsrIds(List<String> ids);

    QsRecordDuty getQsRecordQsrId(String qsrId);
}
