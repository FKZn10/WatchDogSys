package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程验收
 */
@Data
@TableName("Completion_Acceptance")
public class CompletionAcceptance {

    @TableId(type = IdType.INPUT)
    private String caId;
    private String caContractorId;
    private String caSupervisorId;
    private String caType;
    private String pId;
    private String conId;
    private Date caDate;
    private String caContent;
    private String caBuilder;
    private String caStaffs;
    private String caCreateUid;
    private String caCreateUname;
    private Date caCreateTime;
    private Integer CaState;
    private Date caStateTime;
    private String caDoc;
    private Integer caCheckResult;
    private String caCheckUid;
    private String caCheckUname;
    private Date caCheckTime;
    private String caCheckContent;
    private String caCheckDoc;
    private Integer caMeeting;
    private Date caMeetingTime;
    private String caMeetingTitle;
    private String caMeetingMember;
    private String caMeetingDoc;
    private String caRectifyProblem;
    private String caRectifyContent;
    private Integer caRectifyResult;
    private String caRectifyDoc;
    private Integer caCategory;
    private String caInformation;
    private Boolean caContractorPromise;
    private String caContractorPromiseText;
    private Boolean caSupervisorPromise;
    private String caSupervisorPromiseText;
    private String caFileStand;
    private String caFileContract;
    private String caFileArchives;
    private String caFileExperimental;
    private String caFileReport;
    private String caFileSettlement;
    private String caFileSupData;
    private String caFileCheckRectfy;
    private String caFileRectfy;
    private String caFilePlan;
    private String caABook;
    private String caOtherDoc;

    private String caApplicant;
    private Date caPlanStartDate;
    private Date caPlanEndDate;
    private Date caActualStartDate;
    private Date caActualEndDate;
    private BigDecimal caConMoney;
    private String caConFile;
    private String caTechnologyFile;
    private String caQualifiedFile;
    private String caSelfCheckFile;
    private String caPreAcceptance;
    private String caAcceptance;
    private String caSettlementFile;

    private String secId;
    private BigDecimal caSettlementMoney;
    private String teId;
    private Date commencementTime;
}
