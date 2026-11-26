package com.jpmsoft.projectProducer.pojo.vo.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 质量安全检查记录
 */

@Data
public class QsRecordBimResponse extends ProjectCommonRes {

    private String qsrId;
    private String qstId;
    private String pId;
    private String conId;
    private String qsrType;
    private String qsrPosition;
    private String qsrDes;
    private String qsrProblemNature;
    private String qsrRectifyRequire;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrRectifyRequireTime;
    private String qsrRectifyUid;
    private String qsrRectifyUame;
    private String rectifyUname;
    private String qsrSuperviseUid;
    private String qsrSuperviseUname;
    private Integer qsrShortMessage;
    private String qsrPic;
    private String qsrRectifyPic;
    private String qsrCreateUid;
    private String qsrCreateUname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrCreateTime;
    private Integer qsrState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrStateTime;
    private Integer qsrCategory;
    private String qsrRectifyRealUname;
    private String qsrRectifyReply;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrRectifyFinishTime;
    private String qsrPatrolObject;
    /**
     * 巡检任务ID
     */
    private String ptId;
    /**
     * 问题类型（监理或施工）
     */
    private String qsrProblemType;
    /**
     * 责任单位
     */
    private String qsrDutyCpy;
    /**
     * 整改状态
     */
    private String qsrRectifyStatus;
    /**
     * 隐患等级
     */
    private String qsrHazardLevel;
    private String qsrLocation;
    private String qsrReasonType;
    private String qsrFormation;


    private String qsrPicIds;
    private String qsrRectifyPicIds;
    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String qstName;
    private String qstCode;
    private String qstItem;
    private String qsrDutyCpyName;

    private String qrdCpyNames;
    private String qrdUserNames;
    private List<QsRecordDutyDTO> qsRecordDutys;
}
