package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 竣工验收（合并版）
 */
@Data
@TableName("Completion_Acceptance_All")
public class CompletionAcceptanceAll {

    @TableId(type = IdType.INPUT)
    private String caaId;
    private String pId;
    private String conId;
    private String caaTitle;
    private String caaPleaderUid;
    private Date caaStaringTime;
    private Date caaEndTime;
    private String caaSunitId;
    private String caaDunitId;
    private String caaCcunitId;
    private String caaOneSunitUid;
    private String caaOneExplorationUid;
    private String caaOneDesignUid;
    private String caaOneConstructionUid;
    private String caaOneTechnology;
    private String caaOneRtechnology;
    private String caaOneProperty;
    private String caaOneSupervision;
    private String caaOneRemark;
    private String caaOneFile;
    private String caaTwoPleaderUid;
    private String caaTwoSunitUid;
    private String caaTwoExplorationUid;
    private String caaTwoDesignUid;
    private String caaTwoConstructionUid;
    private String caaTwoTechnology;
    private String caaTwoRtechnology;
    private String caaTwoProperty;
    private String caaTwoSupervision;
    private String caaTwoRemark;
    private String caaTwoFile;
    private String caaThreeResponsibleUid;
    private String caaThreePleaderUid;
    private String caaThreeSunitUid;
    private String caaThreeExplorationUid;
    private String caaThreeDesignUid;
    private String caaThreeConstructionUid;
    private String caaThreeTechnology;
    private String caaThreeRtechnology;
    private String caaThreeProperty;
    private String caaThreeSupervision;
    private String caaThreeRemark;
    private String caaThreeFile;
    private String caaCreateUid;
    private String caaCreateUname;
    private Date caaCreateTime;
    private Date caaStateTime;
    private Integer caaState;
    private String caaAcceptanceDoc;
    private String caaConstructionUnit;
    private String caaSurveyUnit;
    private Integer caaDurationTotal;
    private Date caaArchiveTime;
    private String teId;
}
