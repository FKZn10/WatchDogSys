package com.jpmsoft.common.model.request.Qs;

import lombok.Data;

import java.util.List;

@Data
public class QsPlanTaskQsRecordTypeRequest {
    private String qspId;
    /**
     * 质量安全检查记录
     */
    private List<QsRecordTypeRequest> qsRecords;
}
