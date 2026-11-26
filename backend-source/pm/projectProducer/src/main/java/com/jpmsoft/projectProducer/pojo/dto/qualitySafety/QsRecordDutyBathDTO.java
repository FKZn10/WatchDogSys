package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:45
 */
@Data
public class QsRecordDutyBathDTO implements Serializable {


    private static final long serialVersionUID = 289546897934085424L;
    private String qsrId;
    private List<QsRecordDutyDTO> qsRecordDutys;

}
