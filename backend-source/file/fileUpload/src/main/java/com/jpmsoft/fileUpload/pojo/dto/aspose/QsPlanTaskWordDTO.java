package com.jpmsoft.fileUpload.pojo.dto.aspose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导出安全检查结果通知书模板实体
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/9 10:29
 */
@Data
public class QsPlanTaskWordDTO implements Serializable {


    private static final long serialVersionUID = -6048249101654351867L;
    private String ptCode;
    @JsonProperty("pName")
    private String pName;
    private String unitName;
    private String checkDate;
    private String ptType;
    private String ptMeasure;
    private String ptRectifyRequireTime;
    private String leaderName;
    private String ptRectifyReply;

    private List<QsPlanTaskNodeDTO> qsRecords;
}
