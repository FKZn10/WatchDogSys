package com.jpmsoft.projectConsumer.pojo.dto.acpm;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 代建投标文件审批
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:53
 */
@Data
public class AcpmDJTBDocumentsApprovalDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -830108154226485988L;

    private String id;
    private String acpId;
    private String daCode;
    private BigDecimal daBidBondMoney;
    private String daArea;
    private Date daTenderClosingTime;
    private String daAuthorizedAgent;
    private String daAuthorizedAgentId;
    private String daSpecialInstructions;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private Integer state;
    private Date stateTime;

    private String piaCode;
    private String piaName;
    //被授权执行主体 单位名
    private String daAuthorAgentName;
    private List<String> daTenderClosingTimes;
    private List<AcpmDJTBSubjectTenderDto> subjectTenders;
}
