package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 11:22
 */
@Data
public class QsRecordExcelDTO extends QsRecordResponse {


    private String ptContractorName;
    private String ptSupervisorName;
    private String ptGroupMemberName;
    private String ptCode;
    private String ptMeasure;
    private String ptType;
    private String ptCategory;
    private String ptPitfall;
    private Date ptEvaluationTime;
    private String ptDescription;
    private Date ptRectifyRequireTime;
    
    private List<String> planTaskIds;
    private List<String> qsrIds;
}
